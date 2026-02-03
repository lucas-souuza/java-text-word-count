package HASH;

public class Hash<K, T> implements IHash<K, T> {
    private static final int TAM_MIN = 16;
    private static final float FC = 0.75f;

    private int             quantidade; // número de itens do hash
    private NoHash<K, T>[]  v;          // vetor com os itens do hash

    private class NoHash<K, T> {
        public K chave;
        public T item;
        public boolean ocupado;
        public No head;             // referência para o 1º item
    }

    private class No{
        public K chave;
        public T item;
        public No prox;

        public No(K chave, T item) {
            this.chave = chave;
            this.item = item;
        }
    }

    public Hash() {
        v = criaHash(TAM_MIN);
    }

    @Override
    public void insere(K chave, T e) {
        // Insere o item no vetor v
        quantidade += insereItem(v, chave, e);

        // Se ultrapassou o fator de carga, então aumenta o vetor
        if (quantidade > (FC * v.length)) {
            aumentaVetor();
        }
    }

    @Override
    public T remove(K chave) {
        int key = chave.hashCode();
        int pos = hash(key, v.length);

        if (! v[pos].ocupado) {
            // Se o vetor não está ocupado, então a chave não existe
            return null;
        }
        else if (v[pos].chave.equals(chave)) {
            // Encontrou a chave no vetor
            T item = v[pos].item;

            if (v[pos].head == null) {
                // Se a lista encadeada está vazia, marca a posição do vetor como desocupada
                v[pos].ocupado = false;
            }
            else {
                // Se tem elementos na lista encadeada, retira o primeiro
                // da lista e coloca no vetor -> O(1)
                v[pos].chave = v[pos].head.chave;
                v[pos].item = v[pos].head.item;
                v[pos].head = v[pos].head.prox;
            }

            quantidade--;
            return item;
        }
        else {
            // Busca a chave sequencialmente na lista e o remove
            No aux = v[pos].head;
            No ant = null;
            while (aux != null && ! aux.chave.equals(chave)) {
                ant = aux;
                aux = aux.prox;
            }

            if (aux != null) {
                // Se encontrou, remove a chave da lista e retorna o item
                if (ant == null)
                    // Remove 1o elemento da lista
                    v[pos].head = aux.prox;
                else
                    // Remove os demais elementos da lista
                    ant.prox = aux.prox;

                quantidade--;

                return aux.item;
            }
            else {
                // Se não encontrou retorna nulo
                return null;
            }
        }
    }

    @Override
    public T recupera(K chave) {
        int key = chave.hashCode();
        int pos = hash(key, v.length);

        if (! v[pos].ocupado) {
            return null;
        }
        else if (v[pos].chave.equals(chave)) {
            return v[pos].item;
        }
        else if (v[pos].head == null) {
            // Lista encadeada está vazia
            return null;
        }
        else {
            // Busca a chave sequencialmente na lista encadeada
            No aux = v[pos].head;
            while (aux != null && ! aux.chave.equals(chave))
                aux = aux.prox;

            return aux == null ? null : aux.item;
        }
    }

    @Override
    public boolean existe(K chave) {
        return recupera(chave) != null;
    }

    @Override
    public int quantidade() {
        return quantidade;
    }

    @Override
    public boolean estaVazio() {
        return quantidade == 0;
    }

    @Override
    public void removeTodos() {
        v = criaHash(TAM_MIN);
        quantidade = 0;
    }

    private int hash(int key, int tam) {
        return ((key >>> 16) ^ key) & (tam-1);
    }

    private NoHash<K,T>[] criaHash(int tam) {
        NoHash<K, T>[]  vetor = new NoHash[tam];

        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = new NoHash<>();
        }

        return vetor;
    }

    private int insereItem(NoHash<K, T>[] vetor, K chave, T e) {
        int key = chave.hashCode();
        int pos = hash(key, vetor.length);

        if (! vetor[pos].ocupado) {
            // Posição desocupada -> insere o item
            vetor[pos].chave = chave;
            vetor[pos].item = e;
            vetor[pos].ocupado = true;
            return 1;
        }
        else if (vetor[pos].chave.equals(chave)) {
            // Item já está no vetor -> atualiza o item
            vetor[pos].item = e;
        }
        else {
            // Busca a chave sequencialmente na lista encadeada
            No aux = vetor[pos].head;
            while (aux != null && ! aux.chave.equals(chave))
                aux = aux.prox;

            if (aux == null) {
                // Não existe na lista, então insere no início -> O(1)
                aux = vetor[pos].head;
                vetor[pos].head = new No(chave, e);
                vetor[pos].head.prox = aux;
                return 1;
            }
            else {
                // Já existe na lista -> atualiza o item
                aux.item = e;
            }
        }

        return 0;
    }

    private void aumentaVetor() {
        int novoTam = v.length * 2;

        var novo = criaHash(novoTam);

        // Copia o vetor v para o vetor novo, recalculando a posição de cada elemento
        rehash(v, novo);

        v = novo;
    }

    private void rehash(NoHash<K, T>[] old, NoHash<K, T>[] novo) {
        for (var elemento : old) {
            if (elemento.ocupado) {
                // Se tem um elemento no vetor, insere no novo hash
                insereItem(novo, elemento.chave, elemento.item);

                // Se tem lista encadeada, insere cada elemento da lista no novo hash
                if (elemento.head != null) {
                    No aux = elemento.head;
                    while (aux != null) {
                        insereItem(novo, aux.chave, aux.item);
                        aux = aux.prox;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();

        for (int i = 0; i < v.length; i++) {
            builder.append(String.format("%d)", i));
            if (v[i].ocupado) {
                // Imprime a chave
                builder.append(String.format(" [%s]", v[i].chave.toString()));

                // Se tem lista encadeada, insere cada elemento da lista no novo hash
                if (v[i].head != null) {
                    builder.append(" => ");
                    No aux = v[i].head;
                    while (aux != null) {
                        builder.append(String.format("[%s] ", aux.chave.toString()));
                        aux = aux.prox;
                    }
                }
            }
            else {
                builder.append(" nulo");
            }
            builder.append('\n');
        }

        return builder.toString();
    }
}
