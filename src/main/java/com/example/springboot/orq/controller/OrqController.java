package com.example.springboot.orq.controller;

import com.example.springboot.orq.dto.RequestDTO;
import com.example.springboot.orq.service.DominioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping(path = "/api/orq/v1", produces = "application/json")
@Slf4j
public class OrqController {

    @Autowired
    private DominioService dominioService;


    @PostMapping("/procesar")
    public ResponseEntity<String> procesar(@Valid @RequestBody RequestDTO requestDTO) {

        try {
            // Enviar la solicitud al microservicio de dominio
            dominioService.enviarDatos(requestDTO);

            // Si todo está bien, retornar 200 OK
            return ResponseEntity.ok("Datos guardados correctamente");
        } catch (Exception e) {
            // Loggear el error
            log.error("Error al procesar la solicitud: {}", e.getMessage());
            // Retornar error 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    // Manejo de excepciones para validaciones fallidas
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body("Error de validación: " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

}
