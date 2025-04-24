package edu.austral.ingsis.clifford.result;


public record Error<T>(String error) implements Result<T> {
    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public T getValue() {
        throw new UnsupportedOperationException("Cannot get value from Error");
    }

    @Override
    public String getError() {
        return error;
    }
}




