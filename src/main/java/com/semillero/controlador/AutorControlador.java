package com.semillero.controlador;




import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.semillero.entidades.Autor;
import com.semillero.repositorio.AutorRepositorio;
import com.semillero.servicio.AutorServicio;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

	
	@Autowired
	private AutorServicio autorServicio;
	@Autowired
	private AutorRepositorio aR;
	
	@GetMapping("/autor")
	public String registro(){
		return"autor.html";
	}
	
	@PostMapping("/guardar")
	public String guardar(			
			@RequestParam String nombre){			
		System.out.println(nombre);
		autorServicio.guardar(nombre);
		return "index.html";
	}
	//----------------Consultar Autores:
	@GetMapping("/consulta")
	public String consulta(){
		return"autor-consulta.html";
	}
	
	@GetMapping("/consultaAutor")
	public String consultaAutor(ModelMap modelo,
								@RequestParam(required=false)
								String nombre){
		List<Autor> lista = new ArrayList<>();
		if(nombre!=null) {
			lista = aR.buscarAutores("%"+nombre+"%");
		}
		modelo.put("autores", lista);
		return"autor-consulta.html";	
	}
	
	//----------------Modificar Autor:
	
	@GetMapping("/modificarAutor")
	public String modificarAutor(){
		return "autor-modificacion.html";
	}

	@GetMapping("/modificar")
	public String modificar(
			ModelMap model,				
			String id,
			String nombre){
		
		Autor autor=aR.getById(id);
		autor.setNombre(nombre);
		autorServicio.modificar(autor);
		model.put("autor", autor);		
		return "autor-modificacion.html";
	}
	//----------------Eliminar Autor:

	@GetMapping("/eliminarAutor")
	public String eliminarAutor(){
		return "autor-eliminar.html";
	}
	@Transactional
	@PostMapping("/eliminar")
	public String eliminar(ModelMap model,
						   @RequestParam String id){			
		System.out.println("El id es: "+id +"------------------------");
		try {
			//aR.deleteById(id);
			autorServicio.eliminar(id);
			List<Autor> autores = aR.findAll();
			model.put("autores", autores);
			return "index.html";
		}catch (Exception e){
			System.err.println("Ocurrio un error:" + e.getMessage());
			model.put("error", e.getMessage());
			return "autor-eliminar.html";
		}
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@Transactional
	@GetMapping("/eliminarBoton")
	public String eliminarBoton(ModelMap model,
						   @RequestParam String id){			
		System.out.println("El id es: "+id +"------------------------");				
		aR.deleteById(id);					
		return "index.html";	
	}	
}