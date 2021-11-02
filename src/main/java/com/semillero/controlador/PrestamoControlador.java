package com.semillero.controlador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.semillero.entidades.Cliente;
import com.semillero.entidades.Libro;
import com.semillero.entidades.Prestamo;
import com.semillero.repositorio.ClienteRepositorio;
import com.semillero.repositorio.LibroRepositorio;
import com.semillero.repositorio.PrestamoRepositorio;



@Controller
@RequestMapping("/prestamo")
public class PrestamoControlador {

	Libro lib = new Libro(); //Este lo cree para poder usarlo en 2 funciones:
			// 1)"registro"
			// 2) "asignarLibro" y 
			// 3) "guardar"
	Prestamo prestamo = new Prestamo();

	@Autowired
	private LibroRepositorio libroRepositorio;
	@Autowired
	private ClienteRepositorio clienteRepo;
	@Autowired
	private PrestamoRepositorio prestamoRepo;
	
	@GetMapping("/prestamo")
	public String registro(ModelMap modelo){
		List<Libro> libros = new ArrayList<>();				
		libros=libroRepositorio.findAll();					
		modelo.put("libros", libros);
		System.out.println("En el array vienen "+libros.size());		
		return"prestamo.html";
	}
	
	@PostMapping("/buscar")
	public String asignarLibro(@RequestParam Long isbn) {
		System.out.println("El isbn del libro es: "+ isbn);
		lib=libroRepositorio.getById(isbn);
		System.out.println("Los ejemplares son: "+lib.getEjemplares());
		prestamo.setLibro(lib);
		return"prestamo1.html";		
	}
	
	@PostMapping("/guardar")
	@Transactional
	public String guardar(	
			ModelMap model,										
			@RequestParam String devolucion) throws Exception {		
			System.out.println("Los ejemplares son: "+lib.getEjemplares());
			System.out.println("Los prestados son: "+lib.getPrestados());
			if((lib.getEjemplares()-lib.getPrestados())>0) {
				lib.setPrestados(lib.getPrestados()+1);
				libroRepositorio.save(lib);
				System.out.println("Los prestados luego del prestamo son: "+lib.getPrestados());
					Date fecha = new Date();									
					prestamo.setFecha(fecha);
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
					Date fechaDev = sd.parse(devolucion);
					prestamo.setDevolucion(fechaDev);					
					prestamo.setMulta(0.0);
					prestamoRepo.save(prestamo);
				return"index.html";			
			}else {				
				return "libroCantidadLibro.html"; //con este mostramos que no quedan libros para prestar		
			}						
	}						
		
	@GetMapping("/consultarCliente")
	@Transactional
	public String guardar(@RequestParam Long documento) throws Exception {		
		
		Optional<Cliente> res = clienteRepo.findById(documento); 									
		if(res.isPresent()) {
			Cliente cliente = res.get();
			prestamo.setCliente(cliente);
			prestamoRepo.save(prestamo);
			}												
		return"prestamo2.html";
	}
	@Transactional
	@PostMapping("/guardarCliente")
	public String guardar(
			@RequestParam Long documento,
			@RequestParam String nombre,			
			@RequestParam String apellido,
			@RequestParam String domicilio,
			@RequestParam String telefono){		
		Cliente c = new Cliente();
			c.setApellido(apellido);
			c.setDocumento(documento);
			c.setDomicilio(domicilio);
			c.setNombre(nombre);
			c.setTelefono(telefono);
			clienteRepo.save(c);
			prestamo.setCliente(c);
			
		return "prestamo2.html";
	}
}