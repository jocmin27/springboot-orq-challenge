package com.example.springboot.orq.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Getter
@Setter
public class RequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Min(value = 1, message = "La edad debe ser mayor a 0")
    private int edad;

    @DecimalMin(value = "1.0", message = "El peso debe ser mayor a 0")
    private double peso;

}
