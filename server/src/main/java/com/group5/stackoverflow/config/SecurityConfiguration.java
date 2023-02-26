package com.group5.stackoverflow.config;

import com.group5.stackoverflow.auth.filter.JwtAuthenticationFilter;
import com.group5.stackoverflow.auth.filter.JwtVerificationFilter;
import com.group5.stackoverflow.auth.handler.MemberAccessDeniedHandler;
import com.group5.stackoverflow.auth.handler.MemberAuthenticationEntryPoint;
import com.group5.stackoverflow.auth.handler.MemberAuthenticationFailureHandler;
import com.group5.stackoverflow.auth.handler.MemberAuthenticationSuccessHandler;
import com.group5.stackoverflow.auth.tokenizer.JwtTokenizer;
import com.group5.stackoverflow.auth.utils.CustomAuthorityUtils;
import com.group5.stackoverflow.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Slf4j
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils; // 추가
    private final MemberRepository memberRepository;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils, MemberRepository memberRepository) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.memberRepository = memberRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // (1) 추가
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST, "/members").permitAll()
                        .antMatchers(HttpMethod.PATCH, "/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/members").permitAll()
                        .antMatchers(HttpMethod.GET, "/members/**").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/members/**").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.GET, "/questions").permitAll()
                        .antMatchers(HttpMethod.GET, "/questions/**").hasAnyRole("USER", "ADMIN")
//                        .mvcMatchers(HttpMethod.POST, "/questions/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/members/*/questions").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.PATCH, "/questions/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/questions/*/vote").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/questions/**").hasRole("USER")
                                .antMatchers(HttpMethod.POST, "/questions/*/answers").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/*/answers/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/answers/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/tags").permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();
    }

    // (7)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // (8)
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        log.info("cors config");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");

            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler(memberRepository));  // (3) 추가
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());  // (4) 추가

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            builder
                .addFilter(jwtAuthenticationFilter)
                .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);   // (3)추가
        }
    }
}
