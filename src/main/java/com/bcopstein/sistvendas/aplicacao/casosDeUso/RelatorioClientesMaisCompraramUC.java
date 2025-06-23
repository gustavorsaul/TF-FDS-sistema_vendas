package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import com.bcopstein.sistvendas.aplicacao.dtos.ClienteComprasDTO;
import com.bcopstein.sistvendas.aplicacao.relatorios.RelatorioClientesMaisCompraram;
import com.bcopstein.sistvendas.aplicacao.relatorios.RelatorioClientesMaisCompraramTexto;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IOrcamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RelatorioClientesMaisCompraramUC {
    private final IOrcamentoRepositorio orcamentoRepositorio;
    private final RelatorioClientesMaisCompraram geradorRelatorio;

    @Autowired
    public RelatorioClientesMaisCompraramUC(IOrcamentoRepositorio orcamentoRepositorio) {
        this.orcamentoRepositorio = orcamentoRepositorio;
        this.geradorRelatorio = new RelatorioClientesMaisCompraramTexto();
    }

    public String run(LocalDate inicio, LocalDate fim) {
        List<OrcamentoModel> orcamentos = orcamentoRepositorio.orcamentosEfetivadosNoPeriodo(inicio, fim);
        Map<String, Double> totalPorCliente = new HashMap<>();
        for (OrcamentoModel o : orcamentos) {
            String nome = o.getNomeCliente();
            double valor = o.valorFinal();
            totalPorCliente.put(nome, totalPorCliente.getOrDefault(nome, 0.0) + valor);
        }
        List<ClienteComprasDTO> clientes = totalPorCliente.entrySet().stream()
                .map(e -> new ClienteComprasDTO(e.getKey(), e.getValue()))
                .sorted(Comparator.comparingDouble(ClienteComprasDTO::getTotalGasto).reversed())
                .collect(Collectors.toList());
        return geradorRelatorio.gerarRelatorioTexto(clientes);
    }
} 