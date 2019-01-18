package com.tareas.core.clients;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tareas.core.entity.Token;
import com.tareas.core.entity.Usuarios;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersClient {
    private static final Log logger = LogFactory.getLog(UsersClient.class);
    private static final String urlUsers = "https://gentle-eyrie-95237.herokuapp.com/users";
    private static final String urlLogin = "https://gentle-eyrie-95237.herokuapp.com/login";
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private ObjectMapper objectMapper = new ObjectMapper();
    private Gson g = new Gson();

    public void setHeaders(){
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("authorization", obtenerToken().getToken());
    }

    public List<Usuarios> findAll(){
        try{
            setHeaders();

            HttpEntity<String> entity = new HttpEntity<String>(headers);
            ResponseEntity<String> response = restTemplate.exchange(urlUsers, HttpMethod.GET, entity, String.class);
            List<Usuarios> lista = new ArrayList<>();

            try {
                lista = objectMapper.readValue(response.getBody(), new TypeReference<List<Usuarios>>(){});
            } catch (JsonParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return lista;
        }catch (Exception e){
            logger.info(e.getMessage());
            throw e;
        }
    }

    public Usuarios getById(int id){
        setHeaders();

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try{
            ResponseEntity<String> response = restTemplate.exchange(urlUsers + "/" + id, HttpMethod.GET, entity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Usuarios usuarioObtenido;
            usuarioObtenido = objectMapper.readValue(response.getBody(), Usuarios.class);
            return usuarioObtenido;
        }
        catch(Exception e) {
            logger.info("Usuario con ID: " + id + " no válido");
            return null;
        }
    }

    public Token obtenerToken(){
        try{
            JsonObject objetoJson = new JsonObject();
            objetoJson.addProperty("username", "kinexo");
            objetoJson.addProperty("password", "kinexo");

            HttpEntity<String> entity = new HttpEntity<String>(objetoJson.toString(), headers);
            ResponseEntity<String> response = restTemplate
                    .exchange(urlLogin, HttpMethod.POST, entity, String.class);

            Token t = g.fromJson(response.getBody(), Token.class);
            return t;
        }catch(Exception e){
            throw e;
        }
    }
}
