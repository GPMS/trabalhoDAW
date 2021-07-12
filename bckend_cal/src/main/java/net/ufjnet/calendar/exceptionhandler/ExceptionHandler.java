package net.ufjnet.calendar.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.ufjnet.calendar.services.exceptions.BusinessException;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource msg;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<StandardError.Fields> errorsFields = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String name = ((FieldError)error).getField();
			String message = msg.getMessage(error, LocaleContextHolder.getLocale());
			errorsFields.add(new StandardError.Fields(name, message));
		}
		
		StandardError errors = new StandardError(status.value(), LocalDateTime.now(), "Verifique o preenchimento dos campos!", errorsFields);
		return handleExceptionInternal(ex, errors, headers, status, request);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
	public ResponseEntity<StandardError> DataIntegrity(BusinessException ex) {
		StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), ex.getMessage(), null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
}
