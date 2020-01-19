/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ss.ebooking.config;

import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.ss.ebooking.config.security.AuthUsernamePasswordFilter;
import org.ss.ebooking.constants.AppURLs;

/**
 * Spring security configuration.
 * @author Alexandr Omeluaniuk
 */
@Configuration
@EnableWebSecurity/*(debug = true)*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /** Authentication entry point. */
    @Autowired
    private AuthenticationEntryPoint authEntryPoint;
    /** Authentication manager. */
    @Autowired
    private AuthenticationManager authManager;
    /** Authentication success handler. */
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    /** Authentication failure handler. */
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    /** Logout success handler. */
    @Autowired
    private LogoutSuccessHandler logoutSuccesshandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().mvcMatchers(AppURLs.APP_ADMIN_REST_API).authenticated()
                .and().addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin().loginPage(AppURLs.APP_LOGIN_PAGE).permitAll().and()
                .logout().logoutUrl(AppURLs.APP_LOGOUT).logoutSuccessHandler(logoutSuccesshandler)
                .invalidateHttpSession(true).and().exceptionHandling()
                .authenticationEntryPoint(authEntryPoint);
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/locales/**", "/*.json", "/*.js",
                "/*.ico", "/*.html", "/", AppURLs.APP_ADMIN_VIEWS + "/**");
    }
    /**
     * Create custom authentication filter.
     * @return authentication filter.
     */
    private Filter authFilter() {
        AuthUsernamePasswordFilter filter = new AuthUsernamePasswordFilter();
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(AppURLs.APP_LOGIN, "POST"));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setAuthenticationManager(authManager);
        return filter;
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
}
