package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import com.bcopstein.sistvendas.aplicacao.dtos.PerfilComprasClienteDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.PerfilComprasClienteDTO.ProdutoCompradoDTO;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IOrcamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PerfilComprasClienteUC {
    private final IOrcamentoRepositorio orcamentoRepositorio;

    @Autowired
    public PerfilComprasClienteUC(IOrcamentoRepositorio orcamentoRepositorio) {
        this.orcamentoRepositorio = orcamentoRepositorio;
    }

    public PerfilComprasClienteDTO run(String nomeCliente) {
        List<OrcamentoModel> orcamentos = orcamentoRepositorio.todos().stream()
                .filter(o -> o.isEfetivado() && o.getNomeCliente().equalsIgnoreCase(nomeCliente))
                .toList();

        double totalGasto = orcamentos.stream().mapToDouble(OrcamentoModel::valorFinal).sum();
        int quantidadeCompras = orcamentos.size();

        Map<Long, ProdutoCompradoDTO> produtosMap = new HashMap<>();
        for (OrcamentoModel orc : orcamentos) {
            orc.getItens().forEach(item -> {
                long id = item.getProduto().getId();
                String desc = item.getProduto().getDescricao();
                int qtd = item.getQuantidade();
                double valor = qtd * item.getProduto().getPrecoUnitario();
                produtosMap.compute(id, (k, v) -> {
                    if (v == null) return new ProdutoCompradoDTO(id, desc, qtd, valor);
                    return new ProdutoCompradoDTO(id, desc, v.getQuantidade() + qtd, v.getValorTotal() + valor);
                });
            });
        }
        List<ProdutoCompradoDTO> produtosComprados = new ArrayList<>(produtosMap.values());
        return new PerfilComprasClienteDTO(nomeCliente, totalGasto, quantidadeCompras, produtosComprados);
    }
} 