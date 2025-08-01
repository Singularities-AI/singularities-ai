package com.singularities.api.filter;

import com.singularities.api.data.entity.UserModel;
import com.singularities.api.exception.SingularitiesAIForbiddenException;
import com.singularities.api.service.AuthTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.singularities.api.exception.ExceptionMessage.USER_AUTHENTICATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final AuthTokenService authTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && authTokenService.validateJwtToken(jwt)) {
                UserModel user = authTokenService.getUserFromJwt(jwt);
                List<String> roles = authTokenService.getRolesFromJwt(jwt);

                //create authentication without password
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(),
                                null,
                                roles.stream().map(SimpleGrantedAuthority::new).toList()
                        );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error(USER_AUTHENTICATION, e);
            throw new SingularitiesAIForbiddenException(USER_AUTHENTICATION);
        }

        filterChain.doFilter(request, response);
    }


    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
