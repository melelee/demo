package com.melelee.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author menglili
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {
    @Override
    //配置令牌url安全约束
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //配置端点/oauth/token_key的访问策略，这个endpoint当使用JwtToken且使用非对称加密时，资源服务用于获取公钥而开放的，这里指这个endpoint完全公开。
                .tokenKeyAccess("permitAll()")
                //配置/oauth/check_token端点的访问测试
                .checkTokenAccess("permitAll()")
                //允许表单认证获取token
                .allowFormAuthenticationForClients();
        ;

    }

    @Override
    //哪些客户端可以访问，且指定客户端支持的授权类型
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //自定义的客户端数据获取service，相当于security中的UserDetailsService
//        clients.withClientDetails(new ClientDetailsService() {
//            @Override
//            public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
//                return null;
//            }
//        });
        clients.inMemory()// 使用in-memory存储
                //客户端id
                .withClient("c1")
                //客户端密钥
                .secret(new BCryptPasswordEncoder().encode("secret"))
                //c1客户端可以方位的资源列表，对应资源服务上的什么东西？
                .resourceIds("res1")
                // 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
                // 允许的授权范围，相当于一个标识，通过此标识来判断是不是在授权范围内
                .scopes("all")
                //授权码模式下，false时会跳转到授权页面,true则会自动授权
                .autoApprove(false)
                //加上验证回调地址
                .redirectUris("http://www.baidu.com")
        ;
    }


    @Resource
    ClientDetailsService clientDetailsService;

    @Bean
    //配置令牌生成策略
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        //客户端信息从哪里来，
        defaultTokenServices.setClientDetailsService(clientDetailsService);
        //能否产生刷新令牌
        defaultTokenServices.setSupportRefreshToken(true);
        //令牌存储策略
        //指定令牌的生成方式为内存方式
        defaultTokenServices.setTokenStore(new InMemoryTokenStore());
        //令牌有效时间
        defaultTokenServices.setAccessTokenValiditySeconds(3600);
        //刷新令牌有效时间
        defaultTokenServices.setRefreshTokenValiditySeconds(3 * 24 * 3600);
        return defaultTokenServices;
    }



    //设置授权码模式的授权码如何存取
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    @Resource
    AuthorizationCodeServices authorizationCodeServices;
    @Resource
    AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //配置令牌url和令牌生成方法

        //oauth2内置了以下端点
///oauth/authorize：授权端点。
///oauth/token：令牌端点。
///oauth/confirm_access：用户确认授权提交端点。
///oauth/error：授权服务错误信息端点。
///oauth/check_token：用于资源服务访问的令牌解析端点。
///oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话。
        endpoints
                //配置断点
//                .pathMapping()
                //密码模式
                .authenticationManager(authenticationManager)
                //授权码模式
                .authorizationCodeServices(authorizationCodeServices)
                //
                .tokenServices(authorizationServerTokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }
}
