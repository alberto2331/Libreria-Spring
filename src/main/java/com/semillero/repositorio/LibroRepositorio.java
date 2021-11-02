package com.semillero.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.semillero.entidades.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro,Long>{

	@Query("select c from Libro c where c.titulo like :titulo")
	public List<Libro> listarPorTituloQueEmpiecen(String titulo);
}
