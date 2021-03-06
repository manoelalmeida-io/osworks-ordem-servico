package com.example.osworks.api.exceptionhandler;

import com.example.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.example.osworks.domain.exception.NegocioException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var problema = new ExceptionResponseBody();
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDatahora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var problema = new ExceptionResponseBody();
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDatahora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var campos = new ArrayList<ExceptionResponseBody.Campo>();
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError)error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new ExceptionResponseBody.Campo(nome, mensagem));
		}
		
		var response = new ExceptionResponseBody();
		response.setStatus(status.value());
		response.setTitulo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente");
		response.setDatahora(OffsetDateTime.now());
		response.setCampos(campos);
		
		return super.handleExceptionInternal(ex, response, headers, status, request);
	}
}
