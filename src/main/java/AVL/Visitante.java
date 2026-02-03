package AVL;

@FunctionalInterface
public interface Visitante<T> {
    void visita(T item);
}

