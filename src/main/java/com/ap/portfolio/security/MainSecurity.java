package com.ap.portfolio.security;

import com.ap.portfolio.security.jwt.JWTEntryPoint;
import com.ap.portfolio.security.jwt.JWTTokenFilter;
import com.ap.portfolio.security.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JWTEntryPoint jwtEntryPoint;

    @Bean
    public JWTTokenFilter jwtTokenFilter(){
        return new JWTTokenFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/cv/download").permitAll()
                .antMatchers("/login", "/register").permitAll() // Allow public access to login and register pages
                .antMatchers("/dashboard/profile/**").denyAll()
                .antMatchers("/dashboard/home","/dashboard/mock-users", "home", "/mock-users").permitAll() // Secure all routes under /dashboard and require USER authority
                .antMatchers( "/create-experience", "/edit-experience/**", "/edit-education/**", "/create-education",
                        "/create-skill", "/edit-skill/**", "/edit-web-user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/auth/**").permitAll()
                .antMatchers("/api/**").hasAuthority("USER")
                //EDUCATION
                .antMatchers("/edu/active-edu").permitAll()
                .antMatchers("/edu/active-edu-by-id/{id}").permitAll()
                .antMatchers("/edu/get-edu/{id}").permitAll()
                .antMatchers(HttpMethod.PATCH,"/edu/disable-edu/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/edu/create-edu").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH,"/edu/update-edu/{id}").hasAnyRole("ADMIN", "USER")
                //EXPERIENCE
                .antMatchers("/exp/all-exp").permitAll()
                .antMatchers("/exp/active-exp").permitAll()
                .antMatchers("/exp/get-exp/{id}").permitAll()
                .antMatchers("/exp/active-exp-by-id/{id}").permitAll()
                .antMatchers(HttpMethod.POST,"/exp/create-exp").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH,"/exp/update-exp/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH,"/exp/disable-exp/{id}").hasAnyRole("ADMIN", "USER")
                //SKILL
                .antMatchers("/skill/active-skill").permitAll()
                .antMatchers("/skill/get-skill/{id}").permitAll()
                .antMatchers(HttpMethod.POST,"/skill/create-skill").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH,"/skill/update-skill/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH,"/skill/disable-skill/{id}").hasAnyRole("ADMIN", "USER")
                //MOCK USERS
                .antMatchers("/mock/all-users").permitAll()
                .antMatchers("/mock/user/{id}").permitAll()
                .antMatchers(HttpMethod.POST,"/mock/create-mock-user").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/mock/edit-mock-user").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE,"/mock/delete-mock-user/{id}").hasAnyRole("ADMIN", "USER")
               //WEB USERS
                .antMatchers("/web-user/all-users").permitAll()
                .antMatchers("/web-user/get-web-user/{id}").permitAll()
                .antMatchers("/web-user/get-me").permitAll()
                .antMatchers("/web-user/get-current-user-id").permitAll()
                .antMatchers("/web-user/get-is-admin").permitAll()
                .antMatchers(HttpMethod.POST,"/web-user/create-web-user").permitAll()
                .antMatchers(HttpMethod.PATCH,"/web-user/update-web-user/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers("/web-user/current-web-user").permitAll()

                .anyRequest().authenticated()
                .and()

//                .formLogin()
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .loginPage("/api/login")

//                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
