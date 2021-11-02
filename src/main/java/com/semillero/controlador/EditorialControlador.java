package com.semillero.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.semillero.servicio.EditorialServicio;



@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

	@Autowired
	private EditorialServicio eS;
	@GetMapping("/editorial")
	public String registro(){
		return"editorial.html";
	}
	
	@PostMapping("/guardar")
	public String guardar(			
			@RequestParam String nombre){			
		eS.guardar(nombre);
		return "index.html";
	}
}
