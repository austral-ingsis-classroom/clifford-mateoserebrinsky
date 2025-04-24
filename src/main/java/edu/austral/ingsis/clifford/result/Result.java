package edu.austral.ingsis.clifford.result;

public interface Result<T> {
  boolean isSuccess();

  T getValue();

  String getError();
}
