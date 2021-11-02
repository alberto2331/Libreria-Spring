package com.semillero.servicio;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semillero.entidades.Cliente;
import com.semillero.repositorio.ClienteRepositorio;


@Service
public class ClienteServicio {

	@Autowired
	private ClienteRepositorio cR;
	
	@Transactional
	public void guardar(Long documento, String nombre, String apellido, String domicilio, String telefono) {
		Cliente c = new Cliente();
		c.setApellido(apellido);
		c.setDocumento(documento);
		c.setDomicilio(domicilio);
		c.setNombre(nombre);
		c.setTelefono(telefono);
		cR.save(c);
	}
	
	public Cliente findByDocument(Long documento) {
		Optional<Cliente> respuesta = cR.findById(documento);
		if(respuesta.isPresent()) {
			Cliente cliente = cR.getById(documento);			
			return cliente;
		}
		return null; 
	}
	
}
