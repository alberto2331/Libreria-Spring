package com.semillero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.semillero.servicio.UsuarioServicio;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)// esto me permite autorizar las urls
public class seguridadAlbertSaiz extends WebSecurityConfigurerAdapter{

	
	//UserDatailService--> loadByUserName --> UserioServicio
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	//un metodo que configura la autenticación
	@Autowired
	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	//la configuracion de las peticiones http
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/*","/img/*").permitAll()
			.and().formLogin()
				.loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/")
				.loginProcessingUrl("/logincheck")
				.failureUrl("/login?error=error")
				.permitAll()
			.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
			.and().csrf().disable();
	}
}
