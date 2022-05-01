package com.semillero.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.semillero.entidades.Libro;
import com.semillero.repositorio.LibroRepositorio;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    private LibroRepositorio libroRepositorio;   

    @GetMapping("/libro/{isbn}")
    public ResponseEntity<byte[]> getByProducto(@PathVariable Long isbn) throws Exception {
    	System.out.println("Recibimos el isbn: "+ isbn);
        try {
            
        	Libro libro = libroRepositorio.getById(isbn);

            if (libro.getPortada() == null) {
                throw new Exception("El producto no tiene una imagen.");
            }
            byte[] imagen = libro.getPortada().getContenido();

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity(imagen, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("Hubo un problema al cargar la imagen del producto");
        }
    }
}