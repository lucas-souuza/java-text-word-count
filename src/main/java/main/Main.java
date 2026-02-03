package main;

public class Main {
    public static void main(String[] args) {
        // Verifica se foram passados pelo menos 4 argumentos
        if (args.length != 4) {
            System.out.println("Uso: java Operacoes <pathTexto> <pathExclusoes> [F|A] [C|D]");
            System.out.println("Exemplo 1: java Operacoes texto.txt exclusoes.txt");
            System.out.println("Exemplo 2: java Operacoes texto.txt exclusoes.txt F D");
            System.out.println("Exemplo 3: java Operacoes texto.txt exclusoes.txt A C");
            return;
        }

        String pathTexto = args[0];
        String pathExcl = args[1];

        Operacoes op = new Operacoes(pathTexto, pathExcl);

            char func = args[2].charAt(0);  // Terceiro argumento: 'F' ou 'A'
            char ordem = args[3].charAt(0); // Quarto argumento: 'C' ou 'D'
            op.executa(func, ordem);


//        //Falhas
//        op.executa('A', 'A');
//        System.out.println("-----------------------------------------------");
//        op.executa('C', 'A');
//        System.out.println("-----------------------------------------------");
//        op.executa('X', 'X');
//        System.out.println("Testes Frequencia\n -----------------------------------------------");
//        System.out.println("Teste 3: Frequencia crescente");
//        op.executa('F', 'C');
//        System.out.println("-----------------------------------------------");
//        System.out.println("Teste 4: Frequencia decrescente");
//        op.executa('F', 'D');
//        System.out.println("-----------------------------------------------");
//        //Falhas
//        op.executa('F', 'F');
//        System.out.println("-----------------------------------------------");
//        op.executa('C', 'F');
//        System.out.println("-----------------------------------------------");
//        op.executa('X', 'X');
    }
}
