package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import com.bcopstein.sistvendas.aplicacao.dtos.ClienteRankingDTO;
import com.bcopstein.sistvendas.aplicacao.relatorios.RelatorioGerador;
import com.bcopstein.sistvendas.aplicacao.relatorios.RelatorioTextoGerador;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeVendas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RankingClientesPeriodoUC {
    private final ServicoDeVendas servicoDeVendas;
    private final RelatorioGerador relatorioGerador;

    @Autowired
    public RankingClientesPeriodoUC(ServicoDeVendas servicoDeVendas) {
        this.servicoDeVendas = servicoDeVendas;
        this.relatorioGerador = new RelatorioTextoGerador(); // Futuramente pode ser injetado
    }

    public String run(LocalDate inicio, LocalDate fim) {
        List<OrcamentoModel> orcamentos = servicoDeVendas.orcamentosEfetivadosNoPeriodo(inicio, fim);
        Map<String, List<OrcamentoModel>> porCliente = orcamentos.stream()
                .collect(Collectors.groupingBy(OrcamentoModel::getNomeCliente));
        List<ClienteRankingDTO> ranking = porCliente.entrySet().stream()
                .map(e -> new ClienteRankingDTO(
                        e.getKey(),
                        e.getValue().stream().mapToDouble(OrcamentoModel::getCustoConsumidor).sum(),
                        e.getValue().size()
                ))
                .sorted(Comparator.comparingDouble(ClienteRankingDTO::getValorTotalComprado).reversed())
                .collect(Collectors.toList());
        return relatorioGerador.gerarRelatorio(ranking, inicio, fim);
    }
} 