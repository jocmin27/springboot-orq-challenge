package com.example.springboot.orq.service.impl;

import com.example.springboot.orq.dto.RequestDTO;
import com.example.springboot.orq.service.DominioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class DominioServiceImpl implements DominioService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url.domain}")
    private String apiUrl;

    @Override
    public void enviarDatos(RequestDTO usuario) {

        // Lógica para enviar los datos a tráves de la api de domino a la base de datos

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, usuario, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                // Aquí puedes procesar el cuerpo de la respuesta si es necesario
                log.info("Respuesta del API dominio: {}", responseBody);
            } else {
                log.error("Error al enviar datos al API dominio. Código de estado: {}", response.getStatusCodeValue());
                // Manejar el error de acuerdo a tus necesidades
            }
        } catch (RestClientException e) {
            log.error("Error al comunicarse con el API dominio: {}", e.getMessage());
            // Manejar el error de comunicación con el API dominio
        }

    }
}
