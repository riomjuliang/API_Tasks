package com.tareas.core.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tareas.core.entity.Tareas;

@Repository
public interface TareasRepositorio extends JpaRepository<Tareas, Serializable>, PagingAndSortingRepository<Tareas, Serializable>{
	Tareas findByIdTarea(int idTarea);
	Page<Tareas> findAll(Pageable pageable);
}	
