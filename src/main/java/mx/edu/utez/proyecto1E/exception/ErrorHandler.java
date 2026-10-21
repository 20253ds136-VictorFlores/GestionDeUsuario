package mx.edu.utez.proyecto1E.exception;

import mx.edu.utez.proyecto1E.exception.customExceptions.CustomBadException;
import mx.edu.utez.proyecto1E.exception.customExceptions.CustomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validar(
            MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            errores.put(err.getField(), err.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(CustomBadException.class)
    public ResponseEntity<Map<String, String>> manejarCustomBadRequest(
            CustomBadException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(errores);
    }

    //El registro que se busca no existe en la base de datos -> 404
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarNotFound(
            CustomNotFoundException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errores);
    }

    //Por ejemplo: GET /usuario/abc (el id no es numerico) -> 400
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> manejarTipoInvalido(
            MethodArgumentTypeMismatchException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", "El valor '" + ex.getValue()
                + "' no es válido para el parámetro '" + ex.getName() + "'");
        return ResponseEntity.badRequest().body(errores);
    }
}
