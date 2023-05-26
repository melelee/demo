package com.melelee.security;

import com.auth0.jwt.JWT;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.melelee.security.DemoController.REDIS;

/**
 * @author menglili
 */
@Component
public class AuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        //todo 生成时用了密码，解密时不用，安全吗？
        String username = JWT.decode(token).getClaim("username").asString();
        if (!StringUtils.hasText(username)) {
            throw new RuntimeException("认证失败");
        }
        //todo 只要能解析出username就算登录成功？拿历史的已经登出同样可以，此处有问题：如果用户的确登录了，但此时用历史的已经登出的token同样可以认证成功
        User user = REDIS.get(username);
        if (Objects.isNull(user)) {
            throw new RuntimeException("认证失败");
        }
        //todo 必须用三个参数的构造器
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
