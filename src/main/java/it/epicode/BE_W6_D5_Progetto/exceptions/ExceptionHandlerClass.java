package it.epicode.BE_W6_D5_Progetto.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerClass extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>("Entity not found | " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            if (fieldName.contains(".")) {
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
            }
            errors.put(fieldName, violation.getMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (var error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), "Errore controller: " + error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    //Ho aggiunto questo OVERRIDE per gestire il problema con l'enum sia in creazione di un viaggio, sia in modifica di
    //un viaggio, in modo che se si inserisce come StatoViaggio un valore diverso da COMPLETATO o IN_PROGRAMMA, lancia
    //questo errore |
    //              v

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException ife && ife.getTargetType().isEnum()) {
            String fieldName = ife.getPath().get(0).getFieldName();
            String invalidValue = String.valueOf(ife.getValue());

            Object[] enumConstants = ife.getTargetType().getEnumConstants();
            String allowedValues = Arrays.stream(enumConstants).map(Object::toString).collect(Collectors.joining(", "));

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, "Valore '" + invalidValue + "' non valido. Valori ammessi: " + allowedValues);

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    //Stessa cosa qui sotto, per gestire l'errore in formato JSON, quando si cerca di assegnare un viaggio nella stessa
    //data di un altro viaggio assegnato allo stesso dipendente, lancerà l'errore presente in PrenotazioneService
    //                                           |
    //                                           v


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("Errore", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //Altre eccezioni aggiunte CUSTOM per prevenire l'errore 500
    //                                           |
    //                                           v

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (ex.getMessage().equals("Username già esistente") ||
                ex.getMessage().equals("Utente gia registrato con questa email") ||
                ex.getMessage().equals("La dataRichiesta della prenotazione non puo' avvenire dopo la partenza del viaggio") ||
                ex.getMessage().startsWith("Impossibile eliminare il dipendente")) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {

        return new ResponseEntity<>("Non è possibile eliminare questo viaggio/dipendente poichè è ancora assegnato a una prenotazione", HttpStatus.BAD_REQUEST);
    }

}


