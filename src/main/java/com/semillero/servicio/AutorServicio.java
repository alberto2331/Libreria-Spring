package com.semillero.servicio;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.semillero.entidades.Autor;
import com.semillero.repositorio.AutorRepositorio;

@Service
public class AutorServicio {

	@Autowired
	private AutorRepositorio aR;
	
	@Transactional
	public void guardar(String nombre) {
		Autor a = new Autor();
		a.setNombre(nombre);		
		aR.save(a);	
	}	
	
	@Transactional
	public void eliminar(String id) throws Exception {
		Optional<Autor> respuesta = aR.findById(id);
		if(respuesta.isPresent()) {
			Autor autor = respuesta.get();
			System.out.println("El autor debería borrarse"+ autor.getNombre());	
			aR.delete(autor);
		} else {
			throw new Exception("No author found with that id");				
		}
	}
	@Transactional
	public void modificar(Autor autor) {
		aR.save(autor);		
	}
}
