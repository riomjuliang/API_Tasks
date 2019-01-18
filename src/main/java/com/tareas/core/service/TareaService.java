package com.tareas.core.service;

import java.util.*;

import com.tareas.core.externalServices.UsuariosService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.tareas.core.entity.Tareas;
import com.tareas.core.entity.Usuarios;
import com.tareas.core.repository.TareasRepositorio;

@Service
public class TareaService {
	private static final Log logger = LogFactory.getLog(TareaService.class);

	@Autowired
	TareasRepositorio tareasRepositorio;

	@Autowired
	UsuariosService usuariosService;

	public Tareas crear(Tareas tarea, int idUsuario){
		try {
			java.util.Date utilDate = new java.util.Date();
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			tarea.setFecha_creacion(sqlDate);
			tarea.setIdUsuario(idUsuario);
			tareasRepositorio.save(tarea);

			return tarea;
		}
		catch(Exception e) {
			logger.info(e.getMessage());
			throw e;
		}
	}

	@CachePut(value="taskCache",key="#tarea.idTarea")
	public boolean actualizar(Tareas tarea) {
		try {
			Tareas tareaN = tareasRepositorio.findByIdTarea(tarea.getIdTarea());
			if(tareaN != null) {
				tareasRepositorio.save(tarea);
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
			throw e;
		}
	}

	@CacheEvict(value="taskCache",key="#idTarea")
	public boolean borrar(int idTarea) {
		try {
			Tareas tareaO = tareasRepositorio.findByIdTarea(idTarea);
			tareasRepositorio.delete(tareaO);
			return true;
		}
		catch(Exception e) {
			logger.info(e.getMessage());
			return false;
		}
	}

	@Cacheable(value = "taskCache", key="#idTarea")
	public Tareas obtenerPorId(int idTarea){
		try{
			Tareas tareaObt = tareasRepositorio.findByIdTarea(idTarea);
			return tareaObt != null ? tareaObt : null;
		}
		catch(Exception e){
			logger.info(e.getMessage());
			throw e;
		}
	}

	public List<Tareas> obtenerPorPaginacion(Pageable pageable){
		try{
			return tareasRepositorio.findAll(pageable).getContent();
		}
		catch(Exception e){
			logger.info("ERROR AL OBTENER TAREAS: " + "\n" + "DESCRIPCION: " + e.toString());
			throw e;
		}

	}

	public String compartirTarea(int idTarea, ArrayList<Integer> usuarios){
		String message = "";
		Tareas tarea = tareasRepositorio.findByIdTarea(idTarea);
		if(tarea!=null) {
			ArrayList<Integer> usuariosRepetidos = new ArrayList<>();
			ArrayList<Integer> listaUsuariosNoExistentes = new ArrayList<>();
			List<Usuarios> usuariosAPI = usuariosService.findAll();

			HashSet<Integer> hs = new HashSet<Integer>(tarea.getUsuarios());
			List<Integer> listaUsuarios = new ArrayList<>(hs);
			if(!listaUsuarios.isEmpty()) {
				for(Integer i : listaUsuarios) {
					for(Integer u : usuarios) {
						if(i == u) {
							usuariosRepetidos.add(i);
						}

						if(!usuariosAPI.isEmpty()) {
							boolean encontrado = false;

							for(Usuarios user : usuariosAPI) {
								if(u == user.getId()) {
									logger.info("IDUSUARIO: " + u);
									encontrado = true;
								}
							}

							if(encontrado == false) {
								listaUsuariosNoExistentes.add(u);
							}
						}
					}
				}

				if(!usuariosRepetidos.isEmpty()) {
					message = "El/los usuario/s con ID: " + usuariosRepetidos.toString() + " ya comparten la tarea. \n";
				}

				if(!listaUsuariosNoExistentes.isEmpty()) {
					message += "El/los usuario/s con ID: " + listaUsuariosNoExistentes.toString() + " no existe/n";
				}

				if(message.equals("")) {
					tarea.setUsuarios(usuarios);
					tareasRepositorio.save(tarea);
				}
			}
			else {
				tarea.setUsuarios(usuarios);
				tareasRepositorio.save(tarea);
			}
		}
		
		return message;
	}

	public List<Usuarios> obtenerUsuariosPorTarea(int idTarea){
		List<Usuarios> listadoUsersAPI = new ArrayList<>();
		listadoUsersAPI = usuariosService.findAll();

		Tareas tareaObtenida = obtenerPorId(idTarea);
		List<Usuarios> listadoUsersTarea = new ArrayList<Usuarios>();

		if(!listadoUsersAPI.isEmpty()) {
			if(!tareaObtenida.getUsuarios().isEmpty()) {
				for(Usuarios user : listadoUsersAPI) {
					boolean encontrado = false;

					for(Integer i : tareaObtenida.getUsuarios()) {
						if(i == user.getId()) { encontrado = true; }
					}

					if(encontrado == true) { listadoUsersTarea.add(user); }
				}
			}else{
				logger.info("LA TAREA: " + idTarea + ", NO TIENE USUARIOS ASIGNADOS");
				return null;
			}
		}
		else{
			logger.info("ERROR AL OBTENER USUARIOS EXTERNOS");
			return null;
		}

		return listadoUsersTarea;
	}
}
