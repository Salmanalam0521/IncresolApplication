package com.incresol.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.incresol.app.security.CustomJwtAuthenticationEntryPoint;
import com.incresol.app.security.JwtAuthenticationFilter;

@Configuration
//@EnableMethodSecurity
@EnableWebSecurity(debug = true)
public class SecurityConfig {

	@Autowired
	private CustomJwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// requestMatchers("/home").authenticated()
		http.csrf(csrf -> csrf.disable()) // .requestMatchers("auth/get-user").permitAll()
				.cors(cors -> cors
						.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers("/user/**").permitAll()

						.requestMatchers("/admin/**").hasAnyAuthority("ROLE_Admin", "ROLE_User")

						.requestMatchers("/project/createProject1/**", "/project/createTask/**",
								"/project/getUserProjects/**", "/project/task/**", "/project/getAllUsers/**"

						).permitAll()

						.requestMatchers("/org/**","/businessplaces/**").hasAnyAuthority("ROLE_Admin")
						.requestMatchers("/password/**")
						.permitAll()

						.anyRequest().authenticated())

				.formLogin(form -> form.loginProcessingUrl("http://localhost:8383/login"))
				.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
