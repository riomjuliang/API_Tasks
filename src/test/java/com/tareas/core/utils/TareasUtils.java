package com.tareas.core.utils;

import com.tareas.core.entity.Tareas;

import java.util.ArrayList;

public class TareasUtils {

    public static Tareas getTarea(){
        Tareas tarea = new Tareas();
        tarea.setIdTarea(47);
        tarea.setDescripcion("Descripcion tarea 12");
        tarea.setFecha_creacion(java.sql.Date.valueOf( "2018-12-17" ));
        tarea.setIdUsuario(1);
        tarea.setNombre("Tarea 12");
        ArrayList<Integer> array = new ArrayList<>();
        array.add(3);
        array.add(2);
        tarea.setUsuarios(array);

        return tarea;
    }


}
