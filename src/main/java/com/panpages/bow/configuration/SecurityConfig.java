package com.panpages.bow.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource dataSource;
 
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication().dataSource(dataSource)
			.passwordEncoder(passwordEncoder())
			.usersByUsernameQuery("select user_name as username, password, active as enabled from user where login_name = ?")
			.authoritiesByUsernameQuery("select u.user_name as username, r.role_name as role " +
										"from user u left join user_role_relation re on u.user_id = re.user_id left join user_role r on re.role_id = r.role_id " +
										"where u.login_name = ?");
	}	
 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
		    .authorizeRequests()
		    .antMatchers("/j_spring_security_check").access("hasRole('ROLE_ANONYMOUS')")
		    .antMatchers("/login.html").access("hasRole('ROLE_ANONYMOUS')")
		    .antMatchers("/admin.html").access("hasRole('ROLE_ADMIN')")
		    .and()
		    .formLogin().loginPage("/login.html")
		    .failureUrl("/login.html?error")
		    .defaultSuccessUrl("/")
		    .loginProcessingUrl("/j_spring_security_check")
		    .usernameParameter("username").passwordParameter("password")
		    .and()
		    .logout().logoutSuccessUrl("/login.html?logout").logoutUrl("/j_spring_security_logout")
		    .and()
		    .exceptionHandling().accessDeniedPage("/403.html")
		    .and()
		    .csrf();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
