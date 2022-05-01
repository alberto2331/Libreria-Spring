package com.semillero.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.semillero.entidades.Portada;

@Repository
public interface PortadaRepositorio extends JpaRepository <Portada, String>{

	public Portada save(List<MultipartFile> foto);
}
