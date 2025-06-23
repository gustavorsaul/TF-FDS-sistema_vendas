package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.TaxaConversaoDTO;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeVendas;

@Component
public class TaxaConversaoUC {
    private final ServicoDeVendas servicoDeVendas;

    @Autowired
    public TaxaConversaoUC(ServicoDeVendas servicoDeVendas) {
        this.servicoDeVendas = servicoDeVendas;
    }

    public TaxaConversaoDTO run() {
        List<OrcamentoModel> todos = servicoDeVendas.todosOrcamentos();

        int totalOrcamentos = todos.size();
        int efetivados = (int) todos.stream().filter(OrcamentoModel::isEfetivado).count();

        double taxa = totalOrcamentos == 0 ? 0.0 : (efetivados * 100.0) / totalOrcamentos;

        return new TaxaConversaoDTO(totalOrcamentos, efetivados, taxa);
    }
}
