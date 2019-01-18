package com.tareas.core.controller;

import java.util.List;

import com.tareas.core.exceptions.UsuarioNotFoundException;
import com.tareas.core.externalServices.UsuariosService;
import com.tareas.core.service.TareaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.tareas.core.entity.Token;
import com.tareas.core.entity.Usuarios;

@Controller
@RequestMapping("/v1")
@EnableCaching
public class UsuariosController {
	private static final Log logger = LogFactory.getLog(UsuariosController.class);

	@Autowired
	UsuariosService usuariosService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> obtenerToken() {
		logger.info("UsuariosController - Entering GET /login");
		try {
			Token tObtenido = usuariosService.obtenerToken();
			return new ResponseEntity<>(tObtenido, HttpStatus.OK);
		}
		catch(Exception e) {
			logger.info("Error al obtener Token. Descripción: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<Usuarios>> findAll() {
		logger.info("UsuariosController - Entering GET /users");
		try {
			List<Usuarios> arr = usuariosService.findAll();
			if(arr.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(arr, HttpStatus.OK);
		}
		catch(Exception e) {
			logger.info("Error al obtener Tareas. Descripción: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}	
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Usuarios> getById(@PathVariable("id") int id) throws UsuarioNotFoundException{
		logger.info("UsuariosController - Entering GET /users/{id} with id: " + id);
		if(!usuariosService.validarUsuario(id)){
			String errorMessage = "No existe usuario con ID: " + id;
			UsuarioNotFoundException usuarioNotFoundException = new UsuarioNotFoundException(errorMessage);
			throw usuarioNotFoundException;
		}

		try {
			Usuarios user = usuariosService.getById(id);
			return new ResponseEntity<>(user, HttpStatus.OK);
			}
		catch(Exception e) {
			logger.info("Error al obtener Usuario con ID: " + id + ". Descripción: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
