package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.VendasPorProdutoDTO;
import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeVendas;

@Component
public class TotalVendasPorProdutoUC {
    private final ServicoDeVendas servicoVendas;

    @Autowired
    public TotalVendasPorProdutoUC(ServicoDeVendas servicoVendas) {
        this.servicoVendas = servicoVendas;
    }

    /* public List<VendasPorProdutoDTO> run(LocalDate inicio, LocalDate fim) {
        List<OrcamentoModel> vendidos = servicoVendas.todosOrcamentos().stream()
            .filter(o -> o.isEfetivado()
                      && !o.getData().isBefore(inicio)
                      && !o.getData().isAfter(fim))
            .toList();

        Map<Long, VendasPorProdutoDTO> map = vendidos.stream()
            .flatMap(o -> o.getItens().stream())
            .collect(Collectors.toMap(
                ip -> ip.getProduto().getId(),
                ip -> new VendasPorProdutoDTO(
                    ip.getProduto().getId(),
                    ip.getProduto().getDescricao(),
                    ip.getQuantidade(),
                    ip.getQuantidade() * ip.getProduto().getPrecoUnitario()
                ),
                (a, b) -> {
                    int qt = a.getQuantidadeVendida() + b.getQuantidadeVendida();
                    double vl = a.getValorTotal() + b.getValorTotal();
                    return new VendasPorProdutoDTO(a.getProdutoId(), a.getDescricao(), qt, vl);
                }
            ));

        return map.values().stream().toList();
    } */

    public List<VendasPorProdutoDTO> run() {
        List<OrcamentoModel> todos = servicoVendas.todosOrcamentos();

        Map<Long, VendasPorProdutoDTO> mapaVendas = new HashMap<>();

        todos.stream()
            .filter(OrcamentoModel::isEfetivado)
            .flatMap(o -> o.getItens().stream())
            .forEach(item -> {
                long idProd = item.getProduto().getId();
                String descricao = item.getProduto().getDescricao();
                int quantidade = item.getQuantidade();
                double subtotal = quantidade * item.getProduto().getPrecoUnitario();

                mapaVendas.compute(idProd, (id, venda) -> {
                    if (venda == null) {
                        return new VendasPorProdutoDTO(idProd, descricao, quantidade, subtotal);
                    } else {
                        return new VendasPorProdutoDTO(
                            idProd,
                            descricao,
                            venda.getQuantidadeVendida() + quantidade,
                            venda.getValorTotal() + subtotal
                        );
                    }
                });
            });

        return new ArrayList<>(mapaVendas.values());
    }
}
