package com.thoughtmechanix.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import javax.annotation.Resource;

@EnableAuthorizationServer  //开启验证服务器
@Configuration
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager manager;

    @Resource
    private UserDetailsService service;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(encoder)   // 编码器设定为BCryptPasswordEncoder
                .allowFormAuthenticationForClients()    //允许客户端使用表单验证
                .checkTokenAccess("permitAll()");   //允许所有的Token查询请求
    }

    /**
     * 这个方法是对客户端进行配置，一个验证服务器可以预设很多个客户端，
     * 之后这些指定的客户端就可以按照下面指定的方式进行验证
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("web")  //客户端名称
                .secret(encoder.encode("654321"))   //只与客户端分享的secret，随便写，但是注意要加密
                .autoApprove(false) //自动审批
                .scopes("book", "user", "borrow")   //授权范围
                .redirectUris("http://localhost:9031/login")    // 验证服务器需要将结果给回客户端
                .authorizedGrantTypes("client_credentials", "password", "implicit",
                        "authorization_code", "refresh_token"); //授权模式，一共支持5种
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 由于SpringSecurity新版本的一些底层改动，这里需要配置一下authenticationManager，才能正常使用password模式
        endpoints
                .userDetailsService(service)
                .authenticationManager(manager);
    }
}
