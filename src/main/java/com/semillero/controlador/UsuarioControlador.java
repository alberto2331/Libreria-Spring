package com.semillero.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.semillero.servicio.UsuarioServicio;

@Controller
@RequestMapping("/registro")
public class UsuarioControlador {
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@GetMapping("")//significa que cuando entre a "localhost:8080/registro" va a ir a este GetMapping
	public String registro() {
		return "registro";
	}
	@PostMapping("") //--> nombre url
	public String registroSave(Model model,@RequestParam Long documento,@RequestParam String user,@RequestParam String password,@RequestParam String password2) {
		try {
			usuarioServicio.save(documento,user, password, password2);			
			return "redirect:/"; //esto indica que va a ir a "localhost:8080/"--> nombre vista
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());			
			return "registro";
		}		
	}
}
