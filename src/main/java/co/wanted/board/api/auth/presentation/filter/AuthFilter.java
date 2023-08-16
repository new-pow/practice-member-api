package co.wanted.board.api.auth.presentation.filter;

import co.wanted.board.api.auth.application.jwt.JwtProvider;
import co.wanted.board.api.auth.application.jwt.exception.AuthException;
import co.wanted.board.api.auth.infrastructure.config.JwtProperties;
import co.wanted.board.api.auth.presentation.dto.Logined;
import co.wanted.board.global.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProperties;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String headValue = request.getHeader(jwtProperties.getHeaderKey());

            Logined loginMember = Logined.empty();
            if (headValue != null && headValue.startsWith(jwtProperties.getAccessTokenType())) {
                String token = getToken(headValue);
                Claims claim = jwtProvider.getClaim(token);
                loginMember = objectMapper.readValue((String) claim.get(jwtProperties.getAccessClaimKey()), Logined.class);
            }
            request.setAttribute(jwtProperties.getAccessClaimKey(), loginMember);

            chain.doFilter(request, response);
        } catch (AuthException ex) {
            log.error("Auth filter Exception ={}", ex.getMessage());
            log.error("exception trace : ", ex.getStackTrace());

            setResponse(response, ex);
        }
    }

    private void setResponse(HttpServletResponse response, AuthException ex) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.send(ex.getDefaultStatus(), ex);

        response.setStatus(ex.getDefaultStatus().value());
        response.getWriter()
                .write(objectMapper.writeValueAsString(errorResponse));
    }

    private String getToken(String headValue) {
        return headValue.split(" ")[1];
    }
}
