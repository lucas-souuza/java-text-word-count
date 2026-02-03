package main;

//Classe auxiliar para agrupar as palavras com mesma frequência
public class frequenciaGrupo {
    public int freq;
    public String palavra;  // lista das palavras com essa frequência

    public frequenciaGrupo(int freq, String palavra) {
        this.freq = freq;
        this.palavra = palavra;
    }

    @Override
    public String toString() {
        return freq + " -> " + palavra;
    }


    public int getFreq() {
        return freq;
    }

    public String getPalavra() {
        return palavra;
    }

    @Override
    public int hashCode() {
        return palavra.hashCode();
    }
}