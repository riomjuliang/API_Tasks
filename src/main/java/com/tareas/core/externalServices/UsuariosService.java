package com.tareas.core.externalServices;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tareas.core.clients.UsersClient;
import com.tareas.core.entity.Token;
import com.tareas.core.entity.Usuarios;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsuariosService {

    @Autowired
    UsersClient usersClient;

    public List<Usuarios> findAll(){
        return usersClient.findAll();
    }

    @Cacheable(value = "userCache", key="#id")
    public Usuarios getById(int id){
        return  usersClient.getById(id);
    }

    public Token obtenerToken(){
        return usersClient.obtenerToken();
    }

    public boolean validarUsuario(int idUsuario){
        Usuarios user = getById(idUsuario);
        return user != null;
    }
}
