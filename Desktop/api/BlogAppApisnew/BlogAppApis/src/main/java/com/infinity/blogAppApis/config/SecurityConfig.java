package com.infinity.blogAppApis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.infinity.blogAppApis.security.CustomUserDetailService;
import com.infinity.blogAppApis.security.JWTAuthenticationEntryPoint;
import com.infinity.blogAppApis.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static final String[] PUBLIC_URLS = {"/api/auth/**", "/v3/api-docs", "/v2/api-docs",
			"/swagger-resources/**", "/swagger-ui/**", "/webjars/**"

	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeHttpRequests().antMatchers(PUBLIC_URLS).permitAll().antMatchers(HttpMethod.GET)
				.permitAll().antMatchers("/api/users/delete").hasRole("ADMIN").anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(this.jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder);

	}

}
