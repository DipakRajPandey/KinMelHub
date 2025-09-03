package dipak.kinmelhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dipak.kinmelhub.service.CustomUserService;
import dipak.kinmelhub.utils.JwtFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	private  JwtFilter jwtFilter;
    private  CustomUserService userDetailsService;
    public SpringSecurityConfig(JwtFilter jwtFilter, CustomUserService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain secutityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf->csrf.disable())
		.httpBasic(httpBasic->httpBasic.disable())
		.formLogin(form->form.disable())
		.authorizeHttpRequests(auth->{
			auth.requestMatchers("/","/login","/register","/vendor/register","/allproduct").permitAll()
			.anyRequest().permitAll();
		})
		.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	
	}
	  @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
}
