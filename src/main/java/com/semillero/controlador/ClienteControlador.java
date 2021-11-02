package com.semillero.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.semillero.servicio.ClienteServicio;


@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

	@Autowired
	private ClienteServicio clienteServicio;
	@GetMapping("/registroCliente")
	public String registro(){
		return"registroCliente.html";
	}
	
	@PostMapping("/guardar")
	public String guardar(
			@RequestParam Long documento,
			@RequestParam String nombre,			
			@RequestParam String apellido,
			@RequestParam String domicilio,
			@RequestParam String telefono){			
		clienteServicio.guardar(documento, nombre, apellido, domicilio,telefono);
		return "index.html";
	}
}
