package com.example.springboot.orq.controller;

import com.example.springboot.orq.dto.RequestDTO;
import com.example.springboot.orq.service.DominioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrqControllerTest {

    @Mock
    private DominioService dominioService;

    @InjectMocks
    private OrqController orqController;

    @Test
    public void testProcesar_Success() {
        // Crear un objeto RequestDTO de prueba
        RequestDTO requestDTO = new RequestDTO();
        // Aquí podrías configurar el objeto según sea necesario

        // Configurar el comportamiento del mock dominioService
        doNothing().when(dominioService).enviarDatos(requestDTO);

        // Llamar al método procesar del controlador
        ResponseEntity<String> response = orqController.procesar(requestDTO);

        // Verificar que el método enviarDatos del servicio dominioService se haya llamado exactamente una vez
        verify(dominioService, times(1)).enviarDatos(requestDTO);

        // Verificar que se devuelva un ResponseEntity con el código de estado HttpStatus.OK
        assert response.getStatusCode() == HttpStatus.OK;

        // Verificar que el mensaje de respuesta sea "Datos guardados correctamente"
        assert response.getBody().equals("Datos guardados correctamente");
    }
}
