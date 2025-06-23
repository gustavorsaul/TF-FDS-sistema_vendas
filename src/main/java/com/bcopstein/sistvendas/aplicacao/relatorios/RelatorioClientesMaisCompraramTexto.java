package com.bcopstein.sistvendas.aplicacao.relatorios;

import com.bcopstein.sistvendas.aplicacao.dtos.ClienteComprasDTO;
import java.util.List;

public class RelatorioClientesMaisCompraramTexto implements RelatorioClientesMaisCompraram {
    @Override
    public String gerarRelatorioTexto(List<ClienteComprasDTO> clientes) {
        StringBuilder sb = new StringBuilder();
        sb.append("Relatório: Clientes que mais compraram no período\n");
        sb.append("---------------------------------------------\n");
        for (ClienteComprasDTO c : clientes) {
            sb.append(String.format("Cliente: %s | Total Gasto: R$ %.2f\n", c.getNomeCliente(), c.getTotalGasto()));
        }
        return sb.toString();
    }
} 