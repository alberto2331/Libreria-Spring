package com.semillero.servicio;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semillero.entidades.Autor;
import com.semillero.entidades.Editorial;
import com.semillero.entidades.Libro;
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
	
	@Transactional
	public void guardar(Libro libro) {		
		Autor autor = new Autor();
		Editorial editorial = new Editorial();
		
		editorial= libro.getEditorial();
		libro.setPrestados(0);
		//Esto es el caso de que el autor no exista
		autor= libro.getAutor();
		aR.save(autor);
		
		//Esto es el caso de que la editorial no exista
		editorial=libro.getEditorial();
		eR.save(editorial);	
		
		libroRepositorio.save(libro);
	}

	public List<Libro> buscarPorTitulo(String titulo) {
		return libroRepositorio.listarPorTituloQueEmpiecen(titulo+"%");				
	}
}
