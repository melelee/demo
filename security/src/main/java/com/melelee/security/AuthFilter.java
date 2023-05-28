package com.melelee.security;

import com.auth0.jwt.JWT;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        //此处应该先验证token是否合法
        String username = JWT.decode(token).getClaim("username").asString();
        if (!StringUtils.hasText(username)) {
            throw new UsernameNotFoundException("认证失败");
        }
        //todo 只要能解析出username就算登录成功？拿历史的已经登出同样可以，此处有问题：如果用户的确登录了，但此时用历史的已经登出的token同样可以认证成功
        //这里我推荐使用uuid作为key值（uuid每次登录生成都会不相同），用户访问登录接口成功时生成uuid加密为token响应回去，并且将uuid作为LoginUser在redis中的key存入缓存，此后每次访问获取token使用jwt解码为redis中的key，根据key查找LoginUser的信息。
        //同时为了防止用户不正常退出，导致每次访问时产生新的缓存（旧的用户信息缓存堆积），需要设置缓存的过期时间，并且每次访问时都去更新缓存过期时间
        User user = REDIS.get(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("认证失败");
        }
        //todo 必须用三个参数的构造器
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
