package cc.starxy.benchmarkspring.config;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * JWT登录授权过滤器
 *
 * @author sdmq
 */
@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getHeader("token");
        if (token == null){
            chain.doFilter(request, response);
            return;
        }
        JWT jwt = JWTUtil.parseToken(token);

        boolean verify = JWTUtil.verify(token, "token".getBytes());
        if (verify) {
            log.info(jwt.getPayload("amework.boot.autoconfigure.Sprin").toString());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("loginUser",
                    null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        chain.doFilter(request, response);
    }
}
