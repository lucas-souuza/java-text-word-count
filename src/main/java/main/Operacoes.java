package main;

import AVL.*;
import HASH.*;
import LSE.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Operacoes {

    private final Hash<String, Integer> mapa = new Hash<>();
    private final LSE<String> hasSeen = new LSE<String>();
    private final String pathTexto;
    private final String pathExclusoes;

    public Operacoes(String pathTexto, String pathExclusoes) {

        this.pathTexto = pathTexto;
        this.pathExclusoes = pathExclusoes;

        Hash<String,Boolean> exclusoes = new Hash<>();
        String regex = "[\\d\\u2018\\u2019\\u201C\\u201D\"'!@#$%\\u0308&*()_\\-+=`\\u0301\\{\\[\\]\\}\\^~<,>.:;?/\\\\|]";

        //Carregar exclusões em um Hashmap
        try {
            Scanner exc = new Scanner(new File(pathExclusoes), "UTF-8");
            while (exc.hasNextLine()) {
                String palavra = exc.nextLine().trim().toLowerCase().replace("\uFEFF", "");
                if (!palavra.isEmpty()) {
                    exclusoes.insere(palavra, true);
                }
            }
            exc.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Arquivo de exclusões não encontrado.");
        }
//        System.out.println(exclusoes);


        //Lendo texto principal e ignorando palavras no pathExclusoes
        try {
            Scanner texto = new Scanner(new File(pathTexto), "UTF-8");

            while (texto.hasNext()) {
                String palavra = texto.next().trim().toLowerCase().replaceAll(regex, "").
                        replace("\uFEFF", "");

                //Verificação palavra vazia ou presente no pathExclusoes
                if (palavra.isEmpty() || exclusoes.existe(palavra)) {
                    continue;
                }
                    Integer valor = mapa.recupera(palavra);

                    if (valor == null) {
                        valor = 0; // valor default
                        hasSeen.inserirFim(palavra); //vai ser util para coletar as frequencias de cada String
                    }
                    mapa.insere(palavra, valor + 1);
            }
            texto.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Arquivo de texto não encontrado.");
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////
//Executa a função que foi pedida na main
    public void executa(char func, char ordem){
        func = Character.toUpperCase(func);
        ordem = Character.toUpperCase(ordem);

        if (func == 'F' && (ordem == 'C' || ordem == 'D'))
            frequencia(ordem);
        else if (func == 'A' && (ordem == 'C' || ordem == 'D'))
            ordemAlfabetica(ordem);
        else
            System.out.println("Caractere inválido");
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////
//Calcular a frequencia das palavras do Texto, a = ascendente d  = decrescente
    public void frequencia(char ordem) {

        //Foi utilizada a AVL por sua caracteristica de organizar "naturalmente"
        AVL<frequenciaGrupo, frequenciaGrupo> arvore =
                new AVL<>(
                        (frequenciaGrupo fg1,frequenciaGrupo fg2) -> {                       // Comparator<K>
                if (fg1.getFreq() != fg2.getFreq()) {
                    return Integer.compare(fg1.getFreq(), fg2.getFreq());   // ordena por frequência
                }
                return fg1.getPalavra().compareTo(fg2.getPalavra());
            }
            ,
                            (frequenciaGrupo fg) -> fg);

//        System.out.println(mapa);
        // Preencher a AVL
        for (int i = 0; i<hasSeen.quantidade(); i++) {

            var strAtual = hasSeen.getItem(i);

            int freq = mapa.recupera(strAtual);

            var fg = new frequenciaGrupo(freq, strAtual);
            arvore.inserir(fg);

//            System.out.println(freq + " -> " + strAtual);

        }

        // impressão:
        if (ordem == 'C') {
            arvore.emOrdem(item -> System.out.println(item));
        } else {
            arvore.emOrdemInvertida(item -> System.out.println(item));
        }
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////
//Função para ordenar o texto por ordem alfabética

    public void ordemAlfabetica(char ordem) {

        //Foi utilizada a AVL por sua caracteristica de organizar "naturalmente", a = ascendente d  = decrescente
        // chave = própria palavra
        // comparação natural de Strings
        AVL<frequenciaGrupo, String> arvore = new AVL<>(
                (String s1, String s2) -> s1.compareTo(s2),
                (frequenciaGrupo fg) -> fg.getPalavra());

        // Inserir todas as palavras das ja vistas na LSE
        for (int i = 0; i<hasSeen.quantidade(); i++) {
            var freq = mapa.recupera(hasSeen.getItem(i));
            var strAtual = hasSeen.getItem(i);

            var fg = new frequenciaGrupo(freq, strAtual);
            arvore.inserir(fg);

        }
        // Ordem ascendente
        if (ordem == 'C')
            arvore.emOrdem(p -> {System.out.println(p);});

        // Ordem decrescente
        else
            arvore.emOrdemInvertida(p -> {System.out.println(p);});
    }
}