package com.tareas.core.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.tareas.core.exceptions.*;
import com.tareas.core.externalServices.UsuariosService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tareas.core.entity.Tareas;
import com.tareas.core.entity.Usuarios;
import com.tareas.core.service.TareaService;

@RestController
@RequestMapping("/v1")
@EnableCaching
public class TareasController{
	private static final Log logger = LogFactory.getLog(TareaService.class);
	
	@Autowired
	TareaService tareaService;

	@Autowired
	UsuariosService usuariosService;
	
	@RequestMapping(value = "/tareas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> agregarTarea(@RequestBody Tareas tarea,
											   @RequestHeader(value="X-Caller-Id") int idUsuario) throws UsuarioNotFoundException, RequestValidationException {
		logger.info("TareasController - Entering POST /tareas with Tarea: " + tarea.toString());
		if(!usuariosService.validarUsuario(idUsuario)){
			String errorMessage = "No existe usuario con ID: " + idUsuario;
			UsuarioNotFoundException usuarioNotFoundException= new UsuarioNotFoundException(errorMessage);
			throw usuarioNotFoundException;
		}

		if(!validarTarea(tarea)){
			String errorMessage = "Los campos nombre y descripcion son obligatorios";
			RequestValidationException requestValidationException = new RequestValidationException(errorMessage);
			throw requestValidationException;
		}

		Map<String, Object> respuesta = new HashMap<>();
		Tareas tareaNueva = tareaService.crear(tarea, idUsuario);
		if(tareaNueva!=null){
			respuesta.put("data", tareaNueva);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/tareas", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> actualizarTarea(@RequestBody Tareas tarea,
												  @RequestHeader(value="X-Caller-Id") int idUsuario) throws UsuarioNotFoundException, RequestValidationException {
		logger.info("TareasController - Entering PUT /tareas with Tarea: " + tarea.toString());
		if(!usuariosService.validarUsuario(idUsuario)){
			String errorMessage = "No existe usuario con ID: " + idUsuario;
			UsuarioNotFoundException usuarioNotFoundException = new UsuarioNotFoundException(errorMessage);
			throw usuarioNotFoundException;
		}

		if(!validarTarea(tarea)){
			String errorMessage = "Los campos nombre y descripcion son obligatorios";
			RequestValidationException requestValidationException = new RequestValidationException(errorMessage);
			throw requestValidationException;
		}

		if(tareaService.actualizar(tarea)) {
			Map<String, Object> respuesta = new HashMap<>();
			respuesta.put("data", tarea);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		}
		else { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
	}
	
	@RequestMapping(value = "/tareas/{idTarea}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> borrarTarea(@PathVariable("idTarea") int idTarea,
											  @RequestHeader(value="X-Caller-Id") int idUsuario) throws TareaNotFoundException, UsuarioNotFoundException{
		logger.info("TareasController - Entering DELETE /tareas/{idTarea} with idTarea: " + idTarea);
		if(!usuariosService.validarUsuario(idUsuario)){
			String errorMessage = "No existe usuario con ID: " + idUsuario;
			UsuarioNotFoundException usuarioNotFoundException= new UsuarioNotFoundException(errorMessage);
			throw usuarioNotFoundException;
		}

		if(tareaService.borrar(idTarea)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else{
			String errorMessage = "No existe tarea con ID: " + idTarea;
			TareaNotFoundException tareaNotFoundException = new TareaNotFoundException(errorMessage);
			throw tareaNotFoundException;
		}
	}
	
	@RequestMapping(value = "/tareas/{idTarea}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obtenerPorId(@PathVariable("idTarea") int idTarea,
											   @RequestHeader(value="X-Caller-Id") int idUsuario) throws TareaNotFoundException, UsuarioNotFoundException{
		logger.info("TareasController - Entering GET /tareas/{idTarea} with idTarea: " + idTarea);
		if(!usuariosService.validarUsuario(idUsuario)){
			String errorMessage = "No existe usuario con ID: " + idUsuario;
			UsuarioNotFoundException usuarioNotFoundException= new UsuarioNotFoundException(errorMessage);
			throw usuarioNotFoundException;
		}

		Tareas tareaObtenida = tareaService.obtenerPorId(idTarea);
		if(tareaObtenida != null) {
			Map<String, Object> respuesta = new HashMap<>();
			respuesta.put("data", tareaObtenida);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		}
		else {
			String errorMessage = "No existe tarea con ID: " + idTarea;
			TareaNotFoundException tareaNotFoundException = new TareaNotFoundException(errorMessage);
			throw tareaNotFoundException;
		}
	}

	@RequestMapping(value = "/tareas/{idTarea}/usuarios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obtenerUsuariosPorTarea(@PathVariable("idTarea") int idTarea,
														  @RequestHeader(value="X-Caller-Id") int idUsuario) throws TareaNotFoundException, UsuarioNotFoundException{
		logger.info("TareasController - Entering GET /tareas/{idTarea}/usuarios with idTarea: " + idTarea);
		if(!usuariosService.validarUsuario(idUsuario)){
			String errorMessage = "No existe usuario con ID: " + idUsuario;
			UsuarioNotFoundException usuarioNotFoundException= new UsuarioNotFoundException(errorMessage);
			throw usuarioNotFoundException;
		}

		if(tareaService.obtenerPorId(idTarea) != null) {
			List<Usuarios> listaObtenida = new ArrayList<>();
			listaObtenida = tareaService.obtenerUsuariosPorTarea(idTarea);
			if(listaObtenida==null || listaObtenida.isEmpty()){
				String errorMessage = "La tarea con ID: " + idTarea + " no tiene usuarios asignados";
				UsuarioNotFoundException usuarioNotFoundException= new UsuarioNotFoundException(errorMessage);
				throw usuarioNotFoundException;
			}else{
				Map<String, Object> responseObj = new HashMap<>();
				responseObj.put("data", listaObtenida);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
		}
		else {
			String errorMessage = "No existe tarea con ID: " + idTarea;
			TareaNotFoundException tareaNotFoundException = new TareaNotFoundException(errorMessage);
			throw tareaNotFoundException;
		}
	}

	@RequestMapping(value = "/tareas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obtenerTareas(Pageable pageable,
												@RequestHeader(value="X-Caller-Id") int idUsuario) throws TareaNotFoundException, UsuarioNotFoundException{
		logger.info("TareasController - Entering GET /tareas");
		if(!usuariosService.validarUsuario(idUsuario)){
			String errorMessage = "No existe usuario con ID: " + idUsuario;
			UsuarioNotFoundException usuarioNotFoundException= new UsuarioNotFoundException(errorMessage);
			throw usuarioNotFoundException;
		}

		List<Tareas> listaObt = tareaService.obtenerPorPaginacion(pageable);
		if(listaObt.isEmpty()) { return new ResponseEntity<>(HttpStatus.NO_CONTENT); }

		Map<String, Object> responseObj = new HashMap<>();
		responseObj.put("data", listaObt);
		return new ResponseEntity<>(responseObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/tareas/{idTarea}/usuarios", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> compartirTarea(@RequestBody @Valid ArrayList<Integer> listaUsuarios,
												 @PathVariable("idTarea") int idTarea,
												 @RequestHeader(value="X-Caller-Id") int idUsuario) throws TareaNotFoundException, UsuarioNotFoundException{
		logger.info("TareasController - Entering PATCH /tareas/{idTarea}/usuarios with idTarea: " + idTarea);
		if(!usuariosService.validarUsuario(idUsuario)){
			String errorMessage = "No existe usuario con ID: " + idUsuario;
			UsuarioNotFoundException usuarioNotFoundException= new UsuarioNotFoundException(errorMessage);
			throw usuarioNotFoundException;
		}

		try {
			String message = tareaService.compartirTarea(idTarea, listaUsuarios);
			if(message.equals("")) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e) {
			String errorMessage = "No existe tarea con ID: " + idTarea;
			TareaNotFoundException tareaNotFoundException = new TareaNotFoundException(errorMessage);
			throw tareaNotFoundException;
		}
	}

	public boolean validarTarea(Tareas tarea){
		if( tarea.getDescripcion() == null || tarea.getDescripcion().equals("") ||
			tarea.getNombre() == null || tarea.getNombre().equals("")
		){ return false; }
		else{ return true; }
	}
}
