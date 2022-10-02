package com.algaworks.algafood.core.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiDeprecationHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getRequestURI().startsWith("/v2/")) {
            response.addHeader("X-AlgaFood-Deprecated",
                "Essa versão da API está depreciada e deixou de existir a partir de 01/01/2021."
                    + "Use a versão 1 da API.");

            response.setStatus(HttpServletResponse.SC_GONE);
            return false;
        }

        return true;
    }

}