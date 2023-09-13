package com.atos.exceptions;

// Clase para diferenciar las excepciones que proceden de validaciones, para mostrar
// directamente el mensaje de error al usuario.
public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1625387921881474922L;

	public ValidationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
}
