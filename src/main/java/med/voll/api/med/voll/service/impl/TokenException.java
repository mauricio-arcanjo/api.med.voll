package med.voll.api.med.voll.service.impl;

public class TokenException extends RuntimeException{
    public TokenException(String message) {
        super(message, null, false, false);
    }
}
