package pl.lukan.app;

public class InvalidBarcodeException extends RuntimeException {
    public InvalidBarcodeException(String message) {
        super(message);
    }
}
