package br.com.iridiumit.controleos.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

import br.com.iridiumit.controleos.domain.exception.NegocioException;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;

		var problema = new RespostaExceptions();
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(LocalDateTime.now());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		var campos = new ArrayList<Campo>();

		for (ObjectError erro : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) erro).getField();
			String mensagem = erro.getDefaultMessage();

			campos.add(new Campo(nome, mensagem));
		}

		var resposta = new RespostaExceptions();
		resposta.setStatus(status.value());
		resposta.setDataHora(LocalDateTime.now());
		resposta.setTitulo( "Um ou mais campos estão preenchidos de forma incorreta, " 
							+ "Faça a correção e tente novamente!!");

		resposta.setCampos(campos);
		return super.handleExceptionInternal(ex, resposta, headers, status, request);
	}	
	
}
