package com.lemaf.dicionario;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class BuscaPalavras {

    static boolean isEntradaValida(String palavra) {
        String numeroRegex = "\\d*";
        return !palavra.equals("") && !palavra.matches(numeroRegex);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        Scanner scanner = new Scanner(System.in);

        String entrada = "";


        while (!entrada.equals("0")) {
            System.out.println("-----------");
            System.out.println("Digite a palavra a ser pesquisada ou '0' para sair:");
            entrada = scanner.nextLine();
            if (isEntradaValida(entrada)) {
                Integer posicaoFinal = HttpUtil.obterPosicaoFinalDoDicionario();
                HttpUtil.encontrarPosicaoPalavraNoDicionario(posicaoFinal, entrada);
            } else {
                System.out.println("Digite uma palavra válida!");
            }
        }
    }
}
