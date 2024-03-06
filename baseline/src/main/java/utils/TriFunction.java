package utils;

public interface TriFunction<T, U, X, Y> {
	Y apply(T t, U u, X x);
}
