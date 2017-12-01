package com.lemaf.dicionario;

import java.util.Scanner;

public class BuscaPalavras {

    static boolean isEntradaValida(String palavra) {
        String numeroRegex = "\\d*";
        return !palavra.equals("") && !palavra.equals(":q") && !palavra.matches(numeroRegex);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String entrada = "";

        while (!entrada.equals(":q")) {
            System.out.println();
            System.out.println("Digite a palavra a ser pesquisada ou ':q' para sair:");
            entrada = scanner.nextLine();
            if (isEntradaValida(entrada)) {
                HttpUtil.encontrarPosicaoPalavraNoDicionario(entrada);
            } else {
                System.out.println("Digite uma palavra válida!");
            }
        }
    }
}
