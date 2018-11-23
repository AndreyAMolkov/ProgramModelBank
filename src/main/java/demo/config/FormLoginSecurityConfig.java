package demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class FormLoginSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static Logger log =LoggerFactory.getLogger("FormLoginSecurityConfig");
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		String nameMethod = "authProvider";
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		log.trace(nameMethod + "--------------------------------    ");

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		String nameMethod = "configureGlobal";

		auth.authenticationProvider(authProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String nameMethod = "configure";
		System.out.println(nameMethod + "--------------------------------    ");

		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/client/**").hasRole("CLIENT")
				.antMatchers("/cashier/**").hasRole("CASHIER")
				.anyRequest().authenticated()
					.and()
				.formLogin()
				.loginPage("/login").loginProcessingUrl("/login").permitAll()
				.failureUrl("/login?error=true")
				.defaultSuccessUrl("/home")
					.and()
				.logout().logoutSuccessUrl("/login")
					.and()
				.exceptionHandling().accessDeniedPage("/login?error=true")
					.and()
				.csrf().disable();

	}

}
