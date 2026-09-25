package br.com.projetosigse.util;

public final class NomePainel {

    private NomePainel() {
    }

    /**
     * LGPD: primeiro nome + inicial do sobrenome. Ex.: Lucas Silva -> Lucas S.
     */
    public static String formatar(String nomeCompleto) {
        if (nomeCompleto == null || nomeCompleto.isBlank()) {
            return "";
        }
        String[] partes = nomeCompleto.trim().split("\\s+");
        if (partes.length == 1) {
            return partes[0];
        }
        String sobrenome = partes[partes.length - 1];
        return partes[0] + " " + Character.toUpperCase(sobrenome.charAt(0)) + ".";
    }
}
