package com.semillero.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.semillero.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
	
	@Query("select p from Usuario p where p.documento = :documento")
	Usuario findByDni(@Param("documento") Long documento);
	
	@Query("select p from Usuario p where p.username= :username")
	Usuario findByUsername(@Param("username") String username);
}
