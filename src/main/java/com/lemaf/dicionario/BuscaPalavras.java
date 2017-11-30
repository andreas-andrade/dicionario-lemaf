package com.lemaf.dicionario;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Scanner;

public class BuscaPalavras {

    static String buscarPalavra(String palavra) throws URISyntaxException, IOException {
        if (!palavra.equals("") && !palavra.equals("0")) {
            URIBuilder uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("testes.ti.lemaf.ufla.br")
                    .setPath("/api/Dicionario/" + palavra);

            // REQUEST
            HttpGet httpGet = new HttpGet(uri.build());
            // CLIENT
            CloseableHttpClient client = HttpClients.createDefault();
            // RESPOSTA
            CloseableHttpResponse response = client.execute(httpGet);
            // TRATANDO RESPOSTA
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() != 200) {
                System.out.println("=================================");
                System.out.println("==== Erro ao buscar palavra. ====");
                System.out.println("=================================");
                return "";
            }

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();

        }
        return "";
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        Scanner scanner = new Scanner(System.in);

        String entrada = "";

        while (!entrada.equals("0")) {
            System.out.println("-----------");
            System.out.println("Digite a palavra a ser pesquisada ou '0' para sair:");
            entrada = scanner.nextLine();
            System.out.println(buscarPalavra(entrada));
//            System.out.println(entrada);
        }
    }
}
