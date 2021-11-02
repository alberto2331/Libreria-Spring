package com.semillero.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.semillero.entidades.Autor;
import com.semillero.entidades.Editorial;
import com.semillero.entidades.Libro;
import com.semillero.repositorio.AutorRepositorio;
import com.semillero.repositorio.EditorialRepositorio;
import com.semillero.repositorio.LibroRepositorio;
import com.semillero.servicio.LibroServicio;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
	Libro libro = new Libro();
	Libro libroModificar = new Libro(); //Me sirve para ir de un metodo a otro

	@Autowired
	private LibroServicio libroServicio;
	
	@Autowired
	private LibroRepositorio libroRepo;
	
	@Autowired
	private AutorRepositorio aR;
	
	@Autowired
	private EditorialRepositorio eR;
	
	@GetMapping("/libro")
	public String registro(){
		return"libro.html";
	}
	//Esto es para consultar todos los libros de la base de datos:
	@GetMapping("/libroConsulta")
	public String consulta(
			ModelMap modelo){		
		List<Libro> libros =  libroServicio.buscarPorTitulo("");
		modelo.put("libros", libros);
		return"libro-consulta.html";
	}
	
	@PostMapping("/libro")
	public String guardar(
			@RequestParam Long isbn,
			@RequestParam String titulo,
			@RequestParam Integer anio,
			@RequestParam Integer ejemplares){	
	
		libro.setIsbn(isbn);
		libro.setTitulo(titulo);
		libro.setAnio(anio);
		libro.setEjemplares(ejemplares);
		return "libro1.html";
	}
	
	@GetMapping("/libroAutor")  //Esto es para buscar un autor
	public String buscarAutor(
			ModelMap modelo,
			@RequestParam(required=false)
			String nombre){
		List<Autor> lista = new ArrayList<>();
		if(nombre!=null) {		
			lista = aR.buscarAutores("%"+nombre+"%");
		}
		modelo.put("autores", lista);
		return "libro1.html";
	}
	@PostMapping("/libro1")  //Esto es para cuando el autor NO existe en la base de datos
	public String guardarAutor(
			@RequestParam String nombreAutor){
		Autor autor = new Autor();
		autor.setNombre(nombreAutor);
		libro.setAutor(autor);
		return "libro2.html";
	}
	@PostMapping("/libroAutor2") //Esto es para cuando el autor ya existe en la base de datos
	public String setearAutor(
			@RequestParam String id){
		Autor autor = aR.getById(id); 		
		libro.setAutor(autor);
		return "libro2.html";
	}
	//------------------------------------------------Editorial---------------------------------------
	@GetMapping("/libroEditorial") //Esto es para buscar editoriales con nombre similar al buscado por el usuario
	public String buscarEditorial(
			ModelMap modelo,
			@RequestParam(required=false)
			String nombreEditorial){
		List<Editorial> lista = new ArrayList<>();
		if(nombreEditorial!=null) {		
			lista = eR.buscarEditorial("%"+nombreEditorial+"%");
		}
		modelo.put("editoriales", lista);
		return "libro2.html";		
	}
		
	@PostMapping("/libroEditorial2") //Esto es para cuando la editorial ya existe en la base de datos
	public String setearEditorial(
			@RequestParam String id){
		Editorial editorial= eR.getById(id); 		
		libro.setEditorial(editorial);
		libroServicio.guardar(libro);
		return "index.html";
	}
	
	@PostMapping("/libro2")  //Esto es para cuando la editorial NO existe en la base de datos
	public String guardarEditorial(
			@RequestParam String nombreEditorial){
		Editorial editorial = new Editorial();
		editorial.setNombre(nombreEditorial);
		libro.setEditorial(editorial);
		libroServicio.guardar(libro);		
		return "index.html";
	}
	
	//----------------------------------------- Post para buscar un libro y agregar unidades ofrecidas del mismo:
	@GetMapping("/buscarlibro")
	public String libroModificacion() {	
		return"libro-modificacion.html";
	}
	
	@GetMapping("/buscarlibroisbn")
	public String modificarLibro(
							ModelMap modelo,
							Long isbn) {	
		Optional<Libro> respuesta = libroRepo.findById(isbn);
		if(respuesta.isPresent()) {			
			libroModificar = libroRepo.getById(isbn);			
			modelo.put("libroModificar", libroModificar);
			return "libro-modificacion.html";
		}	
		return "libro-modificacion.html";
	}
	
	@PostMapping("/unidadesAgregadas")
	public String agregarLibros(							
							Integer unidades) {	
		Optional<Libro> respuesta = libroRepo.findById(libroModificar.getIsbn());
		if(respuesta.isPresent()) {			
			Libro libro1 = libroRepo.getById(libroModificar.getIsbn());
			System.out.println("El Titulo "+ libro1.getTitulo());
			libro1.setEjemplares(libro1.getEjemplares()+unidades);
			System.out.println("La nueva cantidad del libro es de: "+libro1.getEjemplares());
			libroRepo.save(libro1);
			libro1=null;
			return "libro-modificacion.html";
		}	
		return "libro-modificacion.html";
	}	

	//----------------------------------------- Post para eliminar un libro:

	@GetMapping("/eliminarlibro")
	public String libroEliminacion() {	
		return"libro-eliminar.html";
	}
	@PostMapping("/unidadeseliminar")
	public String eliminarLibro(
							ModelMap modelo,							
							Long isbn) {	
		Optional<Libro> respuesta = libroRepo.findById(isbn);
		if(respuesta.isPresent()) {			
			
			Libro libro1 = libroRepo.getById(isbn);
			System.out.println("El Titulo "+ libro1.getTitulo());				
			String mensaje = "Book erased successfully";
			libroRepo.delete(libro1);						
			modelo.put("mensaje", mensaje);
			return "libro-eliminar.html";
		}	
		String mensaje1 = "Wrong ISBN";
		modelo.put("mensaje1", mensaje1);
		return "libro-eliminar.html";
	}	
}
