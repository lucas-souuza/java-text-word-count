package HASH;

public interface IHash<K, T> {
    void insere(K chave, T e);
    T remove(K chave);
    T recupera(K chave);
    boolean existe(K chave);
    int quantidade();
    boolean estaVazio();
   void removeTodos();
}
