package com.semillero.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.semillero.entidades.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long>{

}
