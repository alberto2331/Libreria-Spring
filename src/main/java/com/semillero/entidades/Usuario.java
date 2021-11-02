package com.semillero.entidades;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.semillero.enums.Role;


@Entity
public class Usuario extends Cliente{
	
	private String username;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role rol;
	
	public String getUsername() {
		return username;
	}
	public Role getRol() {
		return rol;
	}
	public void setRol(Role rol) {
		this.rol = rol;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
