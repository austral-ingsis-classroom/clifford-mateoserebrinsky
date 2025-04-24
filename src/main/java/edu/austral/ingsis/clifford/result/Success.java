package edu.austral.ingsis.clifford.result;

public record Success<T>(T value) implements Result<T> {
  @Override
  public boolean isSuccess() {
    return true;
  }

  @Override
  public T getValue() {
    return value;
  }

  @Override
  public String getError() {
    return null;
  }
}
