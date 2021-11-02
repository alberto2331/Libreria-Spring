package com.semillero.controlador;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorsControlador implements ErrorController{

	@RequestMapping(value ="/error", method= {RequestMethod.GET, RequestMethod.POST})
	public String mostrarPaginaError(Model model, HttpServletRequest httpServletRequest) {
		//HttpServletRequest:
		//1) http: es el protocolo web
		//2) Servlet: son las clases que se encargan de manejar las paginas en java
		//3) Request: es la petición
		String mensajeError="";		
		int codigoError = (int)httpServletRequest.getAttribute("javax.servlet.error.status_code"); 
		switch(codigoError) {
		case 400:
			mensajeError="El recurso solicitado no existe";
			break;
		case 401:
			mensajeError="No se encuentra autorizado";//necesitas iniciar sesión
			break;
		case 403:
			mensajeError="No tiene permisos";//Por cuestiones de roles
			break;
		case 404:
			mensajeError="El recurso solicitado no se ha encontrado";
			break;
		case 500:
			mensajeError="El servidor no pudo realizar la peticion con exito";
			break;
		default:
		}
		model.addAttribute("codigo", codigoError);
		model.addAttribute("mensaje", mensajeError);
		return"error";
	}
}
