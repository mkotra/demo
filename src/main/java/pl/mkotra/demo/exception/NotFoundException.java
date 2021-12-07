package pl.mkotra.demo.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Unable to find resource");
    }

    public NotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
