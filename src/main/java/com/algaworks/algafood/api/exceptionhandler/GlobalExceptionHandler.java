package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.listener.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String GENERIC_ERROR_MSG = "Ocorreu um erro interno inesperado no sistema. "
        + "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
        HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return ResponseEntity.status(status).headers(headers).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseErrorTypeEnum errorType = ResponseErrorTypeEnum.ERRO_DE_SISTEMA;

        // Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
        // fazendo logging) para mostrar a stacktrace no console
        // Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
        // para você durante, especialmente na fase de desenvolvimento
//        ex.printStackTrace();

        log.error(ex.getMessage(), ex);

        ResponseError problem = createResopnseErrorBuilder(status, errorType, GENERIC_ERROR_MSG).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    public ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {

        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @ExceptionHandler({ ValidacaoException.class })
    public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(),
            HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
                                                            HttpStatus status, WebRequest request) {
        ResponseErrorTypeEnum problemType = ResponseErrorTypeEnum.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<ResponseError.Object> responseErrorObjects = bindingResult.getAllErrors().stream()
            .map(objectError -> {
                String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                String name = objectError.getObjectName();

                if(objectError instanceof FieldError) {
                    name = ((FieldError) objectError).getField();
                }

                return ResponseError.Object.builder()
                    .name(name)
                    .userMessage(message)
                    .build();
            })
            .collect(Collectors.toList());

        ResponseError error = createResopnseErrorBuilder(status, problemType, detail)
            .userMessage(detail)
            .objects(responseErrorObjects)
            .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers, HttpStatus status, WebRequest request) {

        ResponseErrorTypeEnum problemType = ResponseErrorTypeEnum.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.",
            ex.getRequestURL());

        ResponseError error = createResopnseErrorBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
        MethodArgumentTypeMismatchException ex, HttpHeaders headers,
        HttpStatus status, WebRequest request) {

        ResponseErrorTypeEnum responseErrorType = ResponseErrorTypeEnum.PARAMETRO_INVALIDO;

        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        ResponseError responseError = createResopnseErrorBuilder(status, responseErrorType, detail).build();

        return handleExceptionInternal(ex, responseError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if(rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }

        if(rootCause instanceof IgnoredPropertyException) {
            return handleIgnoredPropertyException((IgnoredPropertyException) rootCause, headers, status, request);
        }

        if(rootCause instanceof UnrecognizedPropertyException) {
            return handleUnrecognizedPropertyException((UnrecognizedPropertyException) rootCause, headers, status, request);
        }

        ResponseErrorTypeEnum responseErrorType = ResponseErrorTypeEnum.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição está inválido. Verifique o erro de sintaxe.";

        ResponseError responseError = createResopnseErrorBuilder(status, responseErrorType, detail).build();

        return handleExceptionInternal(ex, responseError, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = ex.getPath().stream()
            .map(JsonMappingException.Reference::getFieldName)
            .collect(Collectors.joining("."));

        ResponseErrorTypeEnum responseErrorType = ResponseErrorTypeEnum.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. Corrija  e informe um valor compatível com o tipo %s.",
            path, ex.getValue(), ex.getTargetType().getSimpleName());

        ResponseError responseError = createResopnseErrorBuilder(status, responseErrorType, detail)
            .userMessage(GENERIC_ERROR_MSG)
            .build();

        return handleExceptionInternal(ex, responseError, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleIgnoredPropertyException(IgnoredPropertyException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = ex.getPath().stream()
            .map(JsonMappingException.Reference::getFieldName)
            .collect(Collectors.joining("."));

        ResponseErrorTypeEnum responseErrorType = ResponseErrorTypeEnum.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' não é permitida.", path);

        ResponseError responseError = createResopnseErrorBuilder(status, responseErrorType, detail)
            .userMessage(detail)
            .build();

        return handleExceptionInternal(ex, responseError, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = ex.getPath().stream()
            .map(JsonMappingException.Reference::getFieldName)
            .collect(Collectors.joining("."));

        ResponseErrorTypeEnum responseErrorType = ResponseErrorTypeEnum.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' não é reconhecida.", path);

        ResponseError responseError = createResopnseErrorBuilder(status, responseErrorType, detail)
            .userMessage(detail)
            .build();

        return handleExceptionInternal(ex, responseError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({EntidadeNaoEncontradaException.class})
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ResponseErrorTypeEnum responseErrorType = ResponseErrorTypeEnum.RECURSO_NAO_ENCONTRADO;
        String detail = ex.getMessage();

        ResponseError responseError = createResopnseErrorBuilder(status, responseErrorType, detail)
            .userMessage(GENERIC_ERROR_MSG)
            .build();

        return handleExceptionInternal(ex, responseError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({EntidadeEmUsoException.class})
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ResponseErrorTypeEnum responseErrorType = ResponseErrorTypeEnum.ENTIDADE_EM_USO;
        String detail = ex.getMessage();

        ResponseError responseError = createResopnseErrorBuilder(status, responseErrorType, detail)
            .userMessage(detail)
            .build();

        return handleExceptionInternal(ex, responseError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({NegocioException.class})
    public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ResponseErrorTypeEnum responseErrorType = ResponseErrorTypeEnum.ERRO_NEGOCIO;
        String detail = e.getMessage();

        ResponseError responseError = createResopnseErrorBuilder(status, responseErrorType, detail)
            .userMessage(GENERIC_ERROR_MSG)
            .build();

        return handleExceptionInternal(e, responseError, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if(body == null) {
        body = ResponseError.builder()
            .timestamp(OffsetDateTime.now())
            .title(status.getReasonPhrase())
            .status(String.valueOf(status.value()))
            .userMessage(GENERIC_ERROR_MSG)
            .build();
        } else if(body instanceof String) {
            body = ResponseError.builder()
                .timestamp(OffsetDateTime.now())
                .title((String) body)
                .status(String.valueOf(status.value()))
                .userMessage(GENERIC_ERROR_MSG)
                .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseError.ResponseErrorBuilder createResopnseErrorBuilder(HttpStatus status, ResponseErrorTypeEnum responseErrorType, String detail) {
        return ResponseError.builder()
            .status(String.valueOf(status.value()))
            .type(responseErrorType.getUri())
            .title(responseErrorType.getTitle())
            .detail(detail)
            .timestamp(OffsetDateTime.now());
    }
}
