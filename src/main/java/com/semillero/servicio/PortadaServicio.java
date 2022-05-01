package com.semillero.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.semillero.entidades.Portada;
import com.semillero.repositorio.PortadaRepositorio;

import java.util.List;
import javax.transaction.Transactional;

@Service
public class PortadaServicio {

    @Autowired
    private PortadaRepositorio portadaRepositorio;
    
    @Transactional
    public Portada guardarFoto(MultipartFile archivo) {
        if (archivo != null) {
            try {
                Portada foto = new Portada();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return portadaRepositorio.save(foto);                		
            } catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }      
}