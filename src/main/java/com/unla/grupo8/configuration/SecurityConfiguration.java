package com.unla.grupo8.configuration;

//import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.unla.grupo8.service.implementation.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	private final CustomUserDetailsService userService;

	public SecurityConfiguration(CustomUserDetailsService userService) {
		this.userService = userService;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationSuccessHandler successHandler)
			throws Exception {
		System.out.println(" SecurityFilterChain inicializado con successHandler");
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> {
					// auth.requestMatchers("/cliente/**").hasRole("CLIENTE");
					// auth.requestMatchers("/empleado/**").hasRole("EMPLEADO");
					auth.requestMatchers("/css/*", "/imgs/*", "/js/*", "/vendor/bootstrap/css/*",
							"/vendor/jquery/", "/vendor/bootstrap/js/", "/api/v1/**").permitAll();
					auth.requestMatchers("/formularios/formularioInicial").permitAll();
					auth.requestMatchers("/formularios/guardarRegistro").permitAll();

					auth.anyRequest().authenticated();
				})
				.formLogin(login -> {
					login.loginPage("/login");
					login.loginProcessingUrl("/loginprocess");
					login.usernameParameter("username");
					login.passwordParameter("password");
					login.successHandler(successHandler);// se agrego
					login.permitAll();
				})
				.logout(logout -> {
					logout.logoutUrl("/logout");
					logout.logoutSuccessUrl("/login");
					logout.permitAll();
				})
				.build();
	}

	// @Bean
	// AuthenticationManager authenticationManager(AuthenticationConfiguration
	// authenticationConfiguration) throws Exception {
	// return authenticationConfiguration.getAuthenticationManager();
	// }

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userService);
		return provider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}