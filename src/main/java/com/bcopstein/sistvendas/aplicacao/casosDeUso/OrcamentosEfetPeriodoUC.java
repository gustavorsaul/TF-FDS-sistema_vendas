package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoDTO;
import com.bcopstein.sistvendas.dominio.persistencia.IOrcamentoRepositorio;

@Component
public class OrcamentosEfetPeriodoUC {

    private IOrcamentoRepositorio orcamentoRepositorio;

    @Autowired
    public OrcamentosEfetPeriodoUC(IOrcamentoRepositorio orcamentoRepositorio) {
        this.orcamentoRepositorio = orcamentoRepositorio;
    }

    public List<OrcamentoDTO> run(LocalDate dataInicial, LocalDate dataFinal) {
        return orcamentoRepositorio.orcamentosEfetivadosNoPeriodo(dataInicial, dataFinal)
            .stream()
            .map(orc -> OrcamentoDTO.fromModel(orc))
            .toList();
    }
    
}
