package com.semillero.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tomcat.util.codec.binary.Base64;
import com.semillero.entidades.Autor;
import com.semillero.entidades.Editorial;
import com.semillero.entidades.Libro;
import com.semillero.entidades.Portada;
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
	public String registro(
			ModelMap modelo){
		List<Autor> listaAutores = new ArrayList<>();
		listaAutores = aR.findAll();
		modelo.put("autores", listaAutores);
		List<Editorial> listaEditoriales = new ArrayList<>();
		listaEditoriales = eR.findAll();
		modelo.put("editoriales", listaEditoriales);
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
			@RequestParam Integer ejemplares,
			@RequestParam String autorId,
			@RequestParam String editorialId,
			@RequestParam MultipartFile portada){	
	
		libroServicio.guardar( isbn, titulo, anio, ejemplares, autorId, editorialId, portada);		
		return "index";
	}
	
	//El siguiente metodo es para conseguir la portada:
	@GetMapping("/portada/{isbn}")
    public String getPortadaByProducto(@PathVariable Long isbn) throws Exception {
        Libro libro = libroServicio.encontrarPorId(isbn);
        Portada portada = libro.getPortada();
        String datosPortada = Base64.encodeBase64String(portada.getContenido());
        return datosPortada;
    }
		
	
	//------------------------------------------------Autor:---------------------------------------
	@GetMapping("/libroAutor")  //Esto es para buscar un autor
	public String buscarAutor(
			ModelMap modelo,
			@RequestParam(required=false)
			String nombre){
		List<Autor> lista = new ArrayList<>();
		if(nombre!=null) {		
			lista = aR.findAll();
		}
		modelo.put("autores", lista);
		return "libro.html";
	}
	@PostMapping("/libro1")  //Esto es para cuando el autor NO existe en la base de datos
	public String guardarAutor(
			@RequestParam String nombreAutor){
		Autor autor = new Autor();
		autor.setNombre(nombreAutor);
		libro.setAutor(autor);
		return "libro.html";
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
			lista = eR.findAll();
		}
		modelo.put("editoriales", lista);
		return "libro2.html";		
	}
		
	@PostMapping("/libroEditorial2") //Esto es para cuando la editorial ya existe en la base de datos
	public String setearEditorial(
			@RequestParam String id){
		Editorial editorial= eR.getById(id); 		
		libro.setEditorial(editorial);
		//libroServicio.guardar(libro);
		return "index.html";
	}
													
	@PostMapping("/libro2")  //Esto es para cuando la editorial NO existe en la base de datos
	public String guardarEditorial(
			@RequestParam String nombreEditorial){
		Editorial editorial = new Editorial();
		editorial.setNombre(nombreEditorial);
		libro.setEditorial(editorial);
		//libroServicio.guardar(libro);		
		return "index.html";
	}
	
	//----------------------------------------- Editar Libro:
	@GetMapping("/buscarlibro")
	public String libroModificacion(
			@RequestParam Long isbn,
			ModelMap modelo) throws Exception {			
		Optional<Libro> respuesta = libroRepo.findById(isbn);
		if(respuesta.isPresent()) {			
			libroModificar = libroRepo.getById(isbn);			
			modelo.put("libro", libroModificar);
			
			//Autores:
			List<Autor> listaAutores = new ArrayList<>();
			listaAutores = aR.findAll();
			modelo.put("autores", listaAutores);
			
			//Editoriales:
			List<Editorial> listaEditoriales = new ArrayList<>();
			listaEditoriales = eR.findAll();
			modelo.put("editoriales", listaEditoriales);
			
			//Metodo para obtener la portada:
			modelo.addAttribute("datosPortada",getPortadaByProducto(libroModificar.getIsbn()));
			return "libro-modificacion.html";
		}
		return "libro-modificacion.html";
	}
	
	@PostMapping("/modificar")
	public String libroModificado(
			@RequestParam(required = false) Long isbn,
			@RequestParam(required = false) String titulo,
			@RequestParam(required = false) Integer anio,
			@RequestParam(required = false) Integer ejemplares,
			@RequestParam(required = false) String autor,
			@RequestParam(required = false) String editorial,
			@RequestParam(required = false) MultipartFile portada) {
						
		libroServicio.modificar( isbn, titulo, anio, ejemplares, autor, editorial, portada);
		return "index";
	}
	
	//----------------------------------------- Post para eliminar un libro:
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@Transactional
	@GetMapping("/eliminarLibro")
	public String libroEliminacion(
			@RequestParam Long isbn) {	
		System.out.println("-------------------------------"+ isbn);
		libroServicio.borrar(isbn);
		return"index";
	}	
}
