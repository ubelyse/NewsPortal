package Exceptions;

public class ApiExceptions extends RuntimeException{

    private final int statusCode;

    public ApiExceptions (int statusCode, String msg){
        super(msg); //see explanation below
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
