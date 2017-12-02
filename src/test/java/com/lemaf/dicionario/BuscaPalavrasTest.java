package com.lemaf.dicionario;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertThat;

public class BuscaPalavrasTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testarPalavraEncontrada() throws Exception {
        String[] args = {};
        // entrada
        String entrada = "Pessoa";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(entrada.getBytes());

        System.setIn(byteArrayInputStream);
        BuscaPalavras.main(args);

        assertThat(outContent.toString(), CoreMatchers.containsString(">>>>>>>>> Palavra encontrada na posição: "));
    }

    @Test
    public void testarPalavraNaoEncontrada() throws Exception {
        String[] args = {};
        // entrada
        String entrada = "Fffrasg";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(entrada.getBytes());

        System.setIn(byteArrayInputStream);
        BuscaPalavras.main(args);

        assertThat(outContent.toString(), CoreMatchers.containsString(HttpUtil.PALAVRA_NAO_ENCONTRADA));
    }

}
