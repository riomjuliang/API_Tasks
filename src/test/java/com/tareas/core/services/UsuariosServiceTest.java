package com.tareas.core.services;

import com.tareas.core.ApiRestApplication;
import com.tareas.core.datatransfer.Jsonizable;
import com.tareas.core.entity.Usuarios;
import com.tareas.core.externalServices.UsuariosService;
import com.tareas.core.utils.UsuariosUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UsuariosServiceTest {

    @Autowired
    UsuariosService usuariosService;

    @Test
    public void getUsuarioSuccessfully(){
        Usuarios userService = usuariosService.getById(2);
        Usuarios userUtils = UsuariosUtils.getUsuario();
        userUtils.setBirthDate(userService.getBirthDate());

        assertEquals(userService.getId(), userUtils.getId());
        assertEquals(userService.getBirthDate(), userUtils.getBirthDate());
        assertEquals(userService.getFirstName(), userUtils.getFirstName());
        assertEquals(userService.getLastName(), userUtils.getLastName());
        assertEquals(userService.getCity().getId(), userUtils.getCity().getId());
        assertEquals(userService.getCity().getName(), userUtils.getCity().getName());
    }




}
