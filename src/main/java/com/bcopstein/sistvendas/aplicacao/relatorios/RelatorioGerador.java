package com.bcopstein.sistvendas.aplicacao.relatorios;

import com.bcopstein.sistvendas.aplicacao.dtos.ClienteRankingDTO;
import java.time.LocalDate;
import java.util.List;

public interface RelatorioGerador {
    String gerarRelatorio(List<ClienteRankingDTO> dados, LocalDate inicio, LocalDate fim);
} 