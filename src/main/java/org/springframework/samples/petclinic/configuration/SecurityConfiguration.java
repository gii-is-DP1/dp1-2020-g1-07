package org.springframework.samples.petclinic.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/","/oups").permitAll()
				.antMatchers("/users/new").permitAll()
				.antMatchers("/casinotables").permitAll()
				.antMatchers("/casinotables/**").hasAnyAuthority("admin")
				.antMatchers("/slotgames").permitAll()
				.antMatchers("/slotgames/**").hasAnyAuthority("admin")
				.antMatchers("/slotmachines").permitAll()
				.antMatchers("/slotmachines/**").hasAnyAuthority("admin")
				.antMatchers("/slotgains").permitAll()
				.antMatchers("/slotgains/**").hasAnyAuthority("admin")
				.antMatchers("/restauranttables").permitAll()
                .antMatchers("/restauranttables/**").hasAnyAuthority("admin")
                .antMatchers("/restaurantreservations/**").permitAll()
                .antMatchers("/restaurantreservations").hasAnyAuthority("admin")
				//.antMatchers("/employees").hasAnyAuthority("admin")
				//.antMatchers("/employees/**").hasAnyAuthority("admin")
				.antMatchers("/schedules").hasAnyAuthority("admin")
				.antMatchers("/schedules/user").hasAnyAuthority("employee", "admin")
				.antMatchers("/schedules/**").hasAnyAuthority("admin")
				.antMatchers("/administrators").hasAnyAuthority("admin")
				.antMatchers("/administrators/**").hasAnyAuthority("admin")
				.antMatchers("/artists").hasAnyAuthority("admin")
				.antMatchers("/artists/**").hasAnyAuthority("admin")
				.antMatchers("/chefs").hasAnyAuthority("admin")
				.antMatchers("/chefs/**").hasAnyAuthority("admin")
				.antMatchers("/cooks").hasAnyAuthority("admin")
				.antMatchers("/cooks/**").hasAnyAuthority("admin")
				.antMatchers("/croupiers").hasAnyAuthority("admin")
				.antMatchers("/croupiers/**").hasAnyAuthority("admin")
				.antMatchers("/events").hasAnyAuthority("admin")
				.antMatchers("/events/**").hasAnyAuthority("admin")
				.antMatchers("/stages").hasAnyAuthority("admin")
				.antMatchers("/stages/**").hasAnyAuthority("admin")
				.antMatchers("/maintenanceWorkers").hasAnyAuthority("admin")
				.antMatchers("/maintenanceWorkers/**").hasAnyAuthority("admin")
				.antMatchers("/waiters").hasAnyAuthority("admin")
				.antMatchers("/waiters/**").hasAnyAuthority("admin")
				.antMatchers("/dishes").permitAll()
				.antMatchers("/dishes/**").hasAnyAuthority("admin")
				.antMatchers("/menus").permitAll()
				.antMatchers("/menus/**").hasAnyAuthority("admin")
				.antMatchers("/clients").permitAll()
				.antMatchers("/clients/**").hasAnyAuthority("admin")
				.antMatchers("/games").permitAll()
				.antMatchers("/games/**").hasAnyAuthority("admin")
				.antMatchers("/cgains").hasAnyAuthority("employee", "admin")
				.antMatchers("/cgains/user").hasAnyAuthority("client")
				.antMatchers("/cgains/user/**").hasAnyAuthority("client")
				.antMatchers("/cgains/**").hasAnyAuthority("employee", "admin")
				.antMatchers("/admin/**").hasAnyAuthority("admin")
				.antMatchers("/owners/**").hasAnyAuthority("owner","admin")				
				.antMatchers("/vets/**").authenticated()
				.anyRequest().denyAll()
				.and()
				 	.formLogin()
				 	/*.loginPage("/login")*/
				 	.failureUrl("/login-error")
				.and()
					.logout()
						.logoutSuccessUrl("/"); 
                // Configuración para que funcione la consola de administración 
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
                http.headers()
    	        .defaultsDisabled()
    	        .contentTypeOptions();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
	       "select username,password,enabled "
	        + "from users "
	        + "where username = ?")
	      .authoritiesByUsernameQuery(
	       "select username, authority "
	        + "from authorities "
	        + "where username = ?")	      	      
	      .passwordEncoder(passwordEncoder());
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {	    
		PasswordEncoder encoder =  NoOpPasswordEncoder.getInstance();
	    return encoder;
	}
	
	/*
	protected void configureHeaders(HttpSecurity http) throws Exception {
	    http.headers()
	        .defaultsDisabled()
	        .contentTypeOptions()
	        .disable();
	}*/
	
}


