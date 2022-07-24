package br.com.product.controllers.exceptions;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class StandardError implements Serializable{

	private static final long serialVersionUID = -1678405275130119250L;
	
	private String titleError;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm",  timezone = "Brazil/East", locale = "pt-BR")
	private Instant timestamp = Instant.now();
	
	private Integer statusHttp;
	
	private String message;
}
