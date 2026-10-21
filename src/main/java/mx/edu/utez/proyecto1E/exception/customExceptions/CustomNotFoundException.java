package mx.edu.utez.proyecto1E.exception.customExceptions;

public class CustomNotFoundException extends RuntimeException {
    //constructor
    public CustomNotFoundException(String message) {
        super(message);
    }
}
