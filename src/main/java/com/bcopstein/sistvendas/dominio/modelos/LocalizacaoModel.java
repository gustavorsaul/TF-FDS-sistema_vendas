package com.bcopstein.sistvendas.dominio.modelos;

import java.util.Arrays;
import java.util.List;

public class LocalizacaoModel {
    private String pais;
    private String estado;

    private static final List<String> LOCALIZACOES_ATENDIDAS = Arrays.asList(
        "Brasil-RS",
        "Brasil-SP",
        "Brasil-RJ"
    );

    public LocalizacaoModel(String pais, String estado) {
        this.pais = pais;
        this.estado = estado;
    }

    public boolean localAtendido() {
        String chave = (pais + "-" + estado).toUpperCase();
        return LOCALIZACOES_ATENDIDAS.stream()
            .map(String::toUpperCase)
            .anyMatch(chave::equals);
    }

    public String getPais() {
        return pais;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Localizacao [pais=" + pais + ", estado=" + estado + "]";
    }
}
