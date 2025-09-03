package dipak.kinmelhub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
	       ErrorResponse error=new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getLocalizedMessage());
	       return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	    }
	
	 @ExceptionHandler(DublicateResourceException.class)
	 public ResponseEntity<ErrorResponse> handleDublicateResource(DublicateResourceException ex){
		 ErrorResponse error=new ErrorResponse(HttpStatus.BAD_REQUEST.value(),ex.getLocalizedMessage());
		 return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	 }
	 @ExceptionHandler(RuntimeException.class)
	 public ResponseEntity<ErrorResponse>handleRuntimeException(RuntimeException ex){
		 ErrorResponse error=new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(),ex.getLocalizedMessage());
		 return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
	 }
	 @ExceptionHandler(AccessDeniedException.class)
	 public ResponseEntity<ErrorResponse>handleAccessSenied(AccessDeniedException ex){
		 ErrorResponse error=new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(),ex.getLocalizedMessage());
		 return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
	 }
	 @ExceptionHandler(IllegalStateException.class)
	 public ResponseEntity<ErrorResponse>handleIllegalStateException(IllegalStateException ex){
		 ErrorResponse error=new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(),ex.getLocalizedMessage());
		 return new ResponseEntity<>(error,HttpStatus.CONFLICT);
	 }
}
