package com.tareas.core.utils;

import com.tareas.core.entity.Ciudades;
import com.tareas.core.entity.Usuarios;

import java.time.Instant;

public class UsuariosUtils {

    public static Usuarios getUsuario(){
        Ciudades ciudad = new Ciudades();

        ciudad.setName("Buenos Aires");
        ciudad.setId(2);

        Usuarios user = new Usuarios();
        user.setId(2);
        user.setFirstName("Juan");
        user.setLastName("Perez");
        user.setCity(ciudad);

        return user;
    }
}
