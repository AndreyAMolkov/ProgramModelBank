package demo.config;

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
	@Autowired
	  private UserDetailsService userDetailsService;
	
	 @Bean
	  public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }
	 
	 @Bean
	 public DaoAuthenticationProvider authProvider() {
		
	     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	     
	       String nameMethod = "DaoAuthenticationProvider";
		  System.out.println(nameMethod + "--------------------------------    ");
	     
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
	  String nameMethod = "configure(HttpSecurity";
	  System.out.println(nameMethod + "--------------------------------    ");
	 
//	 http.authorizeRequests().anyRequest()
//	.hasRole("CLIENT")
//    .and()
//    .formLogin().loginPage("/login").loginProcessingUrl("/login")
//    //.failureUrl("/login?error=true")
//    .defaultSuccessUrl("/listAll")
//    .failureUrl("/failureUrl")
//    .and()
//    .logout().logoutSuccessUrl("/failureUrl")
//    .and()
//    .exceptionHandling().accessDeniedPage("/accessDenied")
//    .and()
//    .csrf().disable();
	  http.authorizeRequests().anyRequest().hasRole("CLIENT")
	    .and()
	    .authorizeRequests().antMatchers("/**").permitAll()
	    .and()
	    .formLogin().loginPage("/login").loginProcessingUrl("/login").permitAll()
	    .and()
	    .logout().logoutSuccessUrl("/listAll").permitAll()
	    .and()
	    .csrf().disable();
  }



}
