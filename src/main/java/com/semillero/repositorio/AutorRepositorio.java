package com.semillero.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.semillero.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{

	@Query("select c from Autor c where c.nombre like :nombre")
	public List<Autor> buscarAutores(String nombre);
}
