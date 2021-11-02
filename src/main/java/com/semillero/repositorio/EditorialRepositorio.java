package com.semillero.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.semillero.entidades.Editorial;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{

	@Query("select c from Editorial c where c.nombre like :nombre")
	public List<Editorial> buscarEditorial(String nombre);
}
