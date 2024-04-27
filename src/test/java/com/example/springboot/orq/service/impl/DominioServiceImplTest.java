package com.example.springboot.orq.service.impl;

import com.example.springboot.orq.dto.RequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

@Slf4j
public class DominioServiceImplTest {

    @Value("${api.url.domain}")
    private String apiUrl;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DominioServiceImpl dominioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEnviarDatos_Success() {
        // Arrange
        RequestDTO requestDTO = new RequestDTO();
        ResponseEntity<String> responseEntity = new ResponseEntity<>("OK", HttpStatus.OK);
        when(restTemplate.postForEntity(apiUrl, requestDTO, String.class)).thenReturn(responseEntity);

        // Act
        dominioService.enviarDatos(requestDTO);

        // Assert
        verify(restTemplate, times(1)).postForEntity(apiUrl, requestDTO, String.class);
        // Aquí podrías agregar más aserciones según lo que necesites probar
    }

    @Test
    public void testEnviarDatos_Error() {
        // Arrange
        RequestDTO requestDTO = new RequestDTO();
        RestClientException restClientException = new RestClientException("Error de comunicación");
        when(restTemplate.postForEntity(apiUrl, requestDTO, String.class)).thenThrow(restClientException);

        // Act
        dominioService.enviarDatos(requestDTO);

        // Assert
        verify(restTemplate, times(1)).postForEntity(apiUrl, requestDTO, String.class);
        // Aquí podrías agregar más aserciones según lo que necesites probar
    }
}
