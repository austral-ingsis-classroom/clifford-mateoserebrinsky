package edu.austral.ingsis.clifford.result;

import java.util.function.Function;


public sealed interface Result<T> permits Result.Success, Result.Error {
  record Success<T>(T value) implements Result<T> {}
  record Error<T>(String message) implements Result<T> {}

  default <R> Result<R> map(Function<T, R> mapper) {
    return switch (this) {
      case Success<T>(var v) -> new Success<>(mapper.apply(v));
      case Error<T>(var e) -> new Error<>(e);
    };
  }

  default boolean isSuccess() {
    return this instanceof Success<T>;
  }

  default boolean isError() {
    return this instanceof Error<T>;
  }

  default T getValue() {
    return switch (this) {
      case Success<T>(var v) -> v;
      case Error<T>(var e)-> throw new IllegalStateException("Cannot get value from Error");
    };
  }

  default String getErrorMessage() {
    return switch (this) {
      case Success<T>(var e) -> throw new IllegalStateException("Cannot get error message from Success");
      case Error<T>(var e) -> e;
    };
  }

  default <R> Result<R> flatMap(Function<T, Result<R>> mapper) {
    return switch (this) {
      case Success<T>(var v) -> mapper.apply(v);
      case Error<T>(var e) -> new Error<>(e);
    };
  }
}
