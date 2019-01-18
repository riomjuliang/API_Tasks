package com.tareas.core.controllers;

import com.google.gson.Gson;
import com.tareas.core.ApiRestApplication;
import com.tareas.core.controller.TareasController;
import com.tareas.core.externalServices.UsuariosService;
import com.tareas.core.service.TareaService;
import com.tareas.core.utils.TareasUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TareasControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TareaService tareaService;

    @Autowired
    UsuariosService usuariosService;

    @Test
    public void getTareaSuccessfully() throws Exception{
        given(this.tareaService.obtenerPorId(47)).willReturn(TareasUtils.getTarea());

        ArrayList<Integer> array = new ArrayList<>();
        array.add(3);
        array.add(2);

        mvc.perform(get("/v1/tareas/47")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Caller-Id", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.idTarea").value(47))
                .andExpect(jsonPath("$.data.nombre").value("Tarea 12"))
                .andExpect(jsonPath("$.data.descripcion").value("Descripcion tarea 12"))
                .andExpect(jsonPath("$.data.idUsuario").value(1))
                .andExpect(jsonPath("$.data.fecha_creacion").value("2018-12-17"))
                .andExpect(jsonPath("$.data.usuarios").value(array));
    }

    @Test
    public void getTareasWithUnauthorizedUser() throws Exception{
        mvc.perform(get("/v1/tareas")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Caller-Id", 8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteTareaWithNotFoundIdTarea() throws Exception{
        mvc.perform(get("/v1/tareas/delete/651651")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Caller-Id", 2))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUsuariosFromTareaShouldReturn400WhenListIsEmptyOrNull() throws Exception{
        mvc.perform(get("/v1/tareas/68/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Caller-Id", 2))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTareasWithoutXCallerIdHeaderShouldReturn400() throws Exception{
        mvc.perform(get("/v1/tareas")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
