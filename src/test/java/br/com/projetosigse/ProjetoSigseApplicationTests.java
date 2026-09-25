package br.com.projetosigse;

import br.com.projetosigse.util.NomePainel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProjetoSigseApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void nomePainelRespeitaLgpd() {
        assertEquals("Lucas S.", NomePainel.formatar("Lucas Silva"));
        assertEquals("Ana", NomePainel.formatar("Ana"));
    }
}
