package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ResponseErrorTypeEnum {
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro Inválido"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.");

    private final String title;
    private final String uri;
 
    ResponseErrorTypeEnum(String path, String title) {
        this.uri = "https://algafood.com.br/" + path;
        this.title = title;
    }
}
