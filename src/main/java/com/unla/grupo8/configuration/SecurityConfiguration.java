package com.unla.grupo8.configuration;

//import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.unla.grupo8.service.implementation.ClienteService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	private final ClienteService userService;

	public SecurityConfiguration(ClienteService userService) {
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
					auth.requestMatchers("/css/*", "/imgs/*", "/js/*", "/vendor/bootstrap/css/*",
<<<<<<< HEAD
							"/vendor/jquery/", "/vendor/bootstrap/js/", "/api/v1/**","/contacto/**").permitAll(); 

=======
							"/vendor/jquery/*", "/vendor/bootstrap/js/*", "/api/v1/**").permitAll();
							auth.requestMatchers("/formularios/formularioRegistro").permitAll(); 
>>>>>>> f7453b0dbfe89de31e7709433afedb8ad7fd3233
					auth.anyRequest().authenticated();
				})
				.formLogin(login -> {
					login.loginPage("/login");
					login.loginProcessingUrl("/loginprocess");
					login.usernameParameter("username");
					login.passwordParameter("password");
					login.defaultSuccessUrl("/index", true);
					login.successHandler(successHandler);
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

	// @Bean
	// AuthenticationProvider authenticationProvider(){
	// DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	// provider.setPasswordEncoder(passwordEncoder());
	// provider.setUserDetailsService(userService);
	// return provider;
	// }

	// @Bean
	// PasswordEncoder passwordEncoder(){
	// return new BCryptPasswordEncoder();
	// }
}