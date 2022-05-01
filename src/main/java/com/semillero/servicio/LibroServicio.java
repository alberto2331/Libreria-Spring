package com.semillero.servicio;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.semillero.entidades.Autor;
import com.semillero.entidades.Editorial;
import com.semillero.entidades.Libro;
import com.semillero.entidades.Portada;
import com.semillero.repositorio.AutorRepositorio;
import com.semillero.repositorio.EditorialRepositorio;
import com.semillero.repositorio.LibroRepositorio;

@Service
public class LibroServicio {

	@Autowired
	private LibroRepositorio libroRepositorio;
	@Autowired
	private EditorialRepositorio eR;
	@Autowired
	private AutorRepositorio aR;
	@Autowired
	private PortadaServicio portadaServicio;
	
	@Transactional
	public void guardar(Long isbn, String titulo, Integer anio, Integer ejemplares,String autorId, String editorialId,MultipartFile portada) {		
		
		Libro libro = new Libro();
		Portada foto = portadaServicio.guardarFoto(portada);
        libro.setPortada(foto);
		
		libro.setIsbn(isbn);
		libro.setTitulo(titulo);
		libro.setAnio(anio);
		libro.setEjemplares(ejemplares);
		libro.setAutor(aR.getById(autorId));
		libro.setEditorial(eR.getById(editorialId));
		libro.setPrestados(0);
		libroRepositorio.save(libro);			
	}

	public List<Libro> buscarPorTitulo(String titulo) {
		return libroRepositorio.listarPorTituloQueEmpiecen(titulo+"%");				
	}
	
	public Libro encontrarPorId(Long id) {
		Libro libro = libroRepositorio.getById(id);
		return libro;
	}
	
	@Transactional
	public void modificar(Long isbn, String titulo, Integer anio, Integer ejemplares,String autorId, String editorialId,MultipartFile portada) {		
		
		Libro libro = libroRepositorio.getById(isbn);
		Portada foto = portadaServicio.guardarFoto(portada);
        libro.setPortada(foto);
				
		libro.setTitulo(titulo);
		libro.setAnio(anio);
		libro.setEjemplares(ejemplares);
		libro.setAutor(aR.getById(autorId));
		libro.setEditorial(eR.getById(editorialId));
		libro.setPrestados(0);
		libroRepositorio.save(libro);			
	}
	
	@Transactional
	public void borrar(Long isbn) {
		libroRepositorio.deleteById(isbn);
	}
}
