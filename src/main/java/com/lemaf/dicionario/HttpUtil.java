package com.lemaf.dicionario;

import org.apache.commons.lang3.StringUtils;
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

    /**
     * Método que monta a requisição HTTP e devolve objeto com as informações da resposta.
     * @param posicao Posição passada por parâmetro na requisição na API do Lemaf.
     * @return Um objeto contendo a resposta da chamada a API.
     * @throws URISyntaxException
     * @throws IOException
     */
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
    private static String obterPalavra(Integer posicao) throws URISyntaxException, IOException {
        CloseableHttpResponse response = doGetApi(posicao);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString().replace("\"", "");
    }

    /**
     * Valida se a requisição na posição informada é válida (status 200).
     *
     * @param posicao Posição a ser verificada na API
     * @return Verdadeiro se a requisição foi bem sucedida.
     * @throws IOException
     * @throws URISyntaxException
     */
    private static Boolean isRequisicaoValida(Integer posicao) throws IOException, URISyntaxException {
        CloseableHttpResponse response = doGetApi(posicao);
        StatusLine statusLine = response.getStatusLine();
        return statusLine.getStatusCode() == 200;
    }


    /**
     * Método que obtém a posição da última palavra do dicionário.
     * @return O índice da última palavra.
     * @throws Exception
     */
    private static Integer obterPosicaoFinalDoDicionario() throws Exception {
        int posicaoFinal = 1;
        int posicaoInicial;
        int meio;

        while (isRequisicaoValida(posicaoFinal)) {
            System.out.println(">>>>>>>  Mais gatinhos mortos. Total até agora: " + gatinhosMortos);
            posicaoFinal = 2 * posicaoFinal;
        }

        posicaoInicial = posicaoFinal / 2;

        // BUSCA BINÁRIA
        while (posicaoFinal >= posicaoInicial) {
            meio = (posicaoInicial + posicaoFinal) / 2;

            // ENCONTROU O FIM
            if (isRequisicaoValida(meio) && !isRequisicaoValida(meio + 1)) {
                System.out.println(">>>>>>> Gatinhos mortos na busca pela posição final: " + gatinhosMortos);
                return meio;
            }
            // BUSCA BINÁRIA
            if (!isRequisicaoValida(meio)) {
                posicaoFinal = meio - 1;
            } else {
                posicaoInicial = meio + 1;
            }
            System.out.println(">>>>>>>  Mais gatinhos mortos. Total até agora: " + gatinhosMortos);
        }

        throw new ArrayIndexOutOfBoundsException(">>>>>>> Dicionário inválido");
    }

    /**
     * Método que encontra a posição da palavra informada no dicionáraio.
     *
     * @param palavra A palavra a ser buscada.
     * @return O índice da palavra encontrada.
     * @throws IOException
     * @throws URISyntaxException
     */
    static Integer encontrarPosicaoPalavraNoDicionario(String palavra) throws Exception {
        int posicaoInicial = 0;
        int meio = 0;
        boolean palavraEncontrada = false;
        int posicaoFinal;
        System.out.println(">>>>>>> Buscando pela palavra: " + palavra);

        gatinhosMortos = 0;

        // OBTÉM O FINAL DO DICIONÁRIO
        posicaoFinal = HttpUtil.obterPosicaoFinalDoDicionario();

        while (!palavraEncontrada && posicaoInicial <= posicaoFinal) {
            meio = (posicaoInicial + posicaoFinal) / 2;
            String palavraBuscada = obterPalavra(meio);

            if (palavra.equals(palavraBuscada)) {
                palavraEncontrada = true;
            } else if (StringUtils.stripAccents(palavraBuscada).compareTo(StringUtils.stripAccents(palavra)) > 0) {
                posicaoFinal = meio - 1;
            } else {
                posicaoInicial = meio + 1;
            }
            System.out.println(">>>>>>>  Mais gatinhos mortos. Total até agora: " + gatinhosMortos);
        }

        if (!palavraEncontrada) {
            System.out.println(">>>>>>> Palavra não encontrada! <<<<<<<");
            return -1;
        }
        System.out.println();
        System.out.println(">>>>>>>>> Palavra encontrada na posição: " + meio);
        System.out.println();
        System.out.println(">>>>>>>>> Gatinhos mortos na busca pelo índice da palavra: " + gatinhosMortos);
        return meio;
    }

}
