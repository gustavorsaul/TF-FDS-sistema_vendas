package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeVendas;

@Component
public class VolumeVendasPeriodoUC {
    private final ServicoDeVendas servicoDeVendas;

    @Autowired
    public VolumeVendasPeriodoUC( ServicoDeVendas servicoDeVendas){
         this.servicoDeVendas = servicoDeVendas;

    }

    public double run(LocalDate inicio, LocalDate fim){
        List<OrcamentoModel> todos = servicoDeVendas.todosOrcamentos();

        return todos.stream()
            .filter(o -> o.isEfetivado()
            && !o.getDataCriacao().isBefore(inicio)
            && !o.getDataCriacao().isAfter(fim))
            .mapToDouble(OrcamentoModel::valorFinal)
            .sum();
    }
}
