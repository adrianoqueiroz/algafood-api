package com.algaworks.algafood.api.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Locale;

public class ValorZeroIncluiDescricaoValidator  implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {
    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        this.valorField = constraintAnnotation.valorField();
        this.descricaoField = constraintAnnotation.descricaoField();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), valorField)
                .getReadMethod().invoke(objetoValidacao);

            String descricao = (String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), descricaoField)
                .getReadMethod().invoke(objetoValidacao);

            if(valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                valid = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
            }

            return valid;

        } catch (Exception e) {
            throw new ValidationException();
        }
    }

}
