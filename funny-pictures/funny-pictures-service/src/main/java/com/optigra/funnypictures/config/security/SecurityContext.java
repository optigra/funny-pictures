package com.optigra.funnypictures.config.security;

import com.optigra.funnypictures.dao.user.UserDao;
import com.optigra.funnypictures.service.user.DefaultSocialUserDetailsService;
import com.optigra.funnypictures.service.user.RepositoryUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.annotation.Resource;

/**
 * @author oleh.zasadnyy
 */
@Configuration
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {

    @Resource(name = "userDao")
    private UserDao userDao;

    /**
     * Configuration for ignoring requests to static resources.
     * @param web
     * @throws Exception spring exeption
     */
    public void configure(final WebSecurity web) throws Exception {
        web
                // Spring Security ignores request to static resources such as CSS or JS
                // files.
                .ignoring().antMatchers("/static/**");
    }

    /**
     * Configuration of login page.
     * @param http
     * @throws Exception http
     */
    protected void configure(final HttpSecurity http) throws Exception {
        http
                // Configures form login
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/authenticate")
                .failureUrl("/login?error=bad_credentials")
                        // Configures the logout function
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                        // Configures url based authorization
                .and()
                .authorizeRequests()
                        // Anyone can access the urls
                .antMatchers("/auth/**", "/login", "/signup/**",
                        "/user/register/**").permitAll()
                // The rest of the our application is protected.
                .antMatchers("/**").hasRole("USER")
                // Adds the SocialAuthenticationFilter to Spring Security's
                // filter chain.
                .and().apply(new SpringSocialConfigurer());
    }

    /**
     *
     * @param auth
     * @throws Exception auth
     */
    protected void configure(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(
                passwordEncoder());
    }

    /**
     *
     * @return PasswordEncoder Bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /**
     *
     * @return SocialUserDetailsService Bean
     */
    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
        return new DefaultSocialUserDetailsService(userDetailsService());
    }

    /**
     *
     * @return UserDetailsService Bean
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new RepositoryUserDetailsService(userDao);
    }
}
