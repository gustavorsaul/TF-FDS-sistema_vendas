package com.bcopstein.sistvendas.aplicacao.relatorios;

import com.bcopstein.sistvendas.aplicacao.dtos.ClienteRankingDTO;
import java.time.LocalDate;
import java.util.List;

public class RelatorioTextoGerador implements RelatorioGerador {
    @Override
    public String gerarRelatorio(List<ClienteRankingDTO> dados, LocalDate inicio, LocalDate fim) {
        StringBuilder sb = new StringBuilder();
        sb.append("Relatório de Clientes que Mais Compraram\n");
        sb.append("Período: " + inicio + " a " + fim + "\n\n");
        sb.append(String.format("%-30s %-10s %-15s\n", "Cliente", "Compras", "Total R$"));
        sb.append("---------------------------------------------------------------\n");
        for (ClienteRankingDTO dto : dados) {
            sb.append(String.format("%-30s %-10d %-15.2f\n", dto.getNomeCliente(), dto.getQuantidadeCompras(), dto.getValorTotalComprado()));
        }
        return sb.toString();
    }
} 