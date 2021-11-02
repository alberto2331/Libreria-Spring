package com.semillero.servicio;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semillero.entidades.Editorial;
import com.semillero.repositorio.EditorialRepositorio;


@Service
public class EditorialServicio {

	@Autowired
	private EditorialRepositorio eR;
	
	@Transactional
	public void guardar(String nombre) {
		Editorial e = new Editorial();	
		e.setNombre(nombre);
		eR.save(e);
	}
}
