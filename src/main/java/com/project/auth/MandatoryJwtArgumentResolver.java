package com.project.auth;

import static org.springframework.http.HttpHeaders.*;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.project.auth.exception.TokenMissingException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MandatoryJwtArgumentResolver implements HandlerMethodArgumentResolver {

    private final JWTProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class)
                && parameter.getParameterType().equals(AuthCredentials.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request.getHeader(AUTHORIZATION) == null) {
            throw new TokenMissingException();
        }
        String accessToken = BearerTokenExtractor.extract(request);

        Long id = jwtProvider.getPayload(accessToken);
        return new AuthCredentials(id);
    }
}
