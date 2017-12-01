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

class HttpUtil {

    private static Integer gatinhosMortos = 0;

    private static CloseableHttpResponse doGetApi(Integer posicao) throws URISyntaxException, IOException {
        gatinhosMortos++;

        URIBuilder uri = new URIBuilder()
                .setScheme("http")
                .setHost("testes.ti.lemaf.ufla.br")
                .setPath("/api/Dicionario/" + posicao);

        // REQUEST
        HttpGet httpGet = new HttpGet(uri.build());
        // CLIENT
        CloseableHttpClient client = HttpClients.createDefault();
        // RESPOSTA
        return client.execute(httpGet);
    }

    /**
     * Obtém uma palavra do dicionário.
     *
     * @param posicao Posição a ser passada para a API.
     * @return A palavra obtida.
     * @throws URISyntaxException
     * @throws IOException
     */
    static String obterPalavra(Integer posicao) throws URISyntaxException, IOException {
        CloseableHttpResponse response = doGetApi(posicao);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    /**
     * Valida se a requisição na posição informada é válida (retorna 200).
     *
     * @param posicao Posição a ser verificada na API
     * @return Verdadeiro se a requisição foi bem sucedida.
     * @throws IOException
     * @throws URISyntaxException
     */
    static Boolean isRequisicaoValida(Integer posicao) throws IOException, URISyntaxException {
        gatinhosMortos++;
        CloseableHttpResponse response = doGetApi(posicao);
        StatusLine statusLine = response.getStatusLine();
        return statusLine.getStatusCode() == 200;
    }


    static Integer obterPosicaoFinalDoDicionario() throws IOException, URISyntaxException {
        int posicaoFinal = 1;
        int posicaoInicial;
        int meio = 0;
        boolean posicaoFinalEncontrada = false;

        while (isRequisicaoValida(posicaoFinal)) {
            posicaoFinal = 2 * posicaoFinal;
        }

        posicaoInicial = posicaoFinal / 2;

        // busca binária
        while (!posicaoFinalEncontrada && posicaoFinal >= posicaoInicial) {
            meio = (posicaoInicial + posicaoFinal) / 2;

            if (isRequisicaoValida(meio) && !isRequisicaoValida(meio + 1)) {
                return meio;
            }
            if (!isRequisicaoValida(meio)) {
                posicaoFinal = meio - 1;
            } else {
                posicaoInicial = meio + 1;
            }
        }

        return null;
    }

    /**
     * Método que encontra a posição da palavra informada no dicionáraio.
     *
     * @param posicaoFinal O índice do último elemento do dicionário.
     * @param palavra      A palavra a ser buscada.
     * @return O índice da palavra encontrada..........
     * @throws IOException
     * @throws URISyntaxException
     */
    static Integer encontrarPosicaoPalavraNoDicionario(Integer posicaoFinal, String palavra) throws IOException, URISyntaxException {
        int posicaoInicial = 0;
        int meio = 0;
        boolean palavraEncontrada = false;

        while (!palavraEncontrada && posicaoInicial <= posicaoFinal) {
            meio = (posicaoInicial + posicaoFinal) / 2;
            String palavraBuscada = obterPalavra(meio);
            if (palavra.equals(palavraBuscada)) {
                palavraEncontrada = true;
            } else if (palavraBuscada.compareTo(palavra) > 0) {
                posicaoFinal = meio - 1;
            } else {
                posicaoInicial = meio + 1;
            }
        }

        if (!palavraEncontrada) {
            return -1;
        }

        return meio;
    }

    /**
     * @return Retorna o atributo gatinhosMortos
     */
    public static Integer getGatinhosMortos() {
        return gatinhosMortos;
    }

    /**
     * @param gatinhosMortos Atribui o valor do parâmetro no atributo gatinhosMortos
     */
    public static void setGatinhosMortos(Integer gatinhosMortos) {
        HttpUtil.gatinhosMortos = gatinhosMortos;
    }
}
