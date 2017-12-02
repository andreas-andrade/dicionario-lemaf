package dicionario;

import com.lemaf.dicionario.BuscaPalavras;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class BuscaPalavrasTest {

    @Test
    public void testarPalavraEncontrada() throws Exception {
        String[] args = {};
        // entrada
        String entrada = "Pessoa";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(entrada.getBytes());

        System.setIn(byteArrayInputStream);
        BuscaPalavras.main(args);
    }
}
