package com.semillero.servicio;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.semillero.entidades.Cliente;
import com.semillero.entidades.Usuario;
import com.semillero.enums.Role;
import com.semillero.repositorio.ClienteRepositorio;
import com.semillero.repositorio.UsuarioRepositorio;


@Service
public class UsuarioServicio implements UserDetailsService{
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private ClienteServicio clienteServicio;
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	@Transactional
	public Usuario save(Long documento,String username, String password, String password2) throws Exception {
		Usuario usuario = new Usuario();
		Cliente cliente = clienteServicio.findByDocument(documento);
		//Validaciones:
		
		//Validaciones de cliente
		if(documento==null) {
			throw new Exception("El documento esta vacío");
		}
		System.out.println("1--------------------------");
		
		if(cliente==null) {
			throw new Exception("No se puede registrar un usuario con un DNI que NO existe en la base de datos de 'Clientes'");
		}
		System.out.println("2--------------------------");
		//hasta aca esta funcionando ok
		//Validaciones de usuario
		usuario.setApellido(cliente.getApellido());
		usuario.setNombre(cliente.getNombre());
		usuario.setDomicilio(cliente.getDomicilio());
		usuario.setTelefono(cliente.getTelefono());
		System.out.println("3--------------------------");	
		
		if(username==null || username.isEmpty()) {
			throw new Exception("El usuario no puede estar vacío");
		}
		System.out.println("4--------------------------");
		if(findByUsername(username)!=null) {
			throw new Exception("El usuario ya esta registrado");
		}
		System.out.println("5--------------------------");
		if(password==null || password2==null ||password.isEmpty() || password2.isEmpty()) {
			throw new Exception("Las contraseña no pueden estar vacía");
		}
		System.out.println("6--------------------------");
		if(!password.equals(password2)) {
			throw new Exception("Las contraseñas deben ser iguales");	
		}
		System.out.println("7--------------------------");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //Con esta clase instanciamos el encriptador 
		
		usuario.setDocumento(cliente.getDocumento());		
		usuario.setUsername(username);
		usuario.setRol(Role.USER);
		usuario.setPassword(encoder.encode(password));//seteamos la contraseña encriptada en el usuario
		System.out.println("El usuario tiene como contraseña: "+usuario.getPassword()+"------");
		System.out.println("El usuario tiene como username: "+usuario.getUsername()+"------");
		System.out.println("El usuario tiene como docuemento: "+usuario.getDocumento()+"------");
		clienteRepositorio.deleteById(documento);
		return usuarioRepositorio.save(usuario);		
	}

	public Usuario findByUsername(String username) {
		return usuarioRepositorio.findByUsername(username);
	}
	public Optional<Usuario> findByDni (Long documento) {
	 	return Optional.of(usuarioRepositorio.findByDni(documento));
	 	//  
	}

	@Override// este metodo se va a llamar cuando un usuario se quiera loggear 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Usuario usuario = usuarioRepositorio.findByUsername(username);
			User user;
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_"+	usuario.getRol()));
			return new User(username, usuario.getPassword(), authorities);
		}catch(Exception e) {
			throw new UsernameNotFoundException("El usuario solicitado no existe");
		}
		
	}
}
