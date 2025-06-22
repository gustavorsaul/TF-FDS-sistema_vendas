package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.ItemPedidoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoRequestDTO;
import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.modelos.PedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeEstoque;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeVendas;

@Component
public class CriaOrcamentoUC {
    private ServicoDeVendas servicoDeVendas;
    private ServicoDeEstoque servicoDeEstoque;

    @Autowired
    public CriaOrcamentoUC(ServicoDeVendas servicoDeVendas, ServicoDeEstoque servicoDeEstoque) {
        this.servicoDeVendas = servicoDeVendas;
        this.servicoDeEstoque = servicoDeEstoque;
    }

    public OrcamentoDTO run(OrcamentoRequestDTO requestDTO) {
        PedidoModel pedido = new PedidoModel(0);

        // monta a lista de itens do pedido
        for (ItemPedidoDTO item : requestDTO.getItens()) {
            ProdutoModel produto = servicoDeEstoque.produtoPorCodigo(item.getIdProduto());
            if (produto == null) {
                throw new IllegalArgumentException("Produto não encontrado: ID " + item.getIdProduto());
            }

            ItemPedidoModel itemPedido = new ItemPedidoModel(produto, item.getQtdade());
            pedido.addItem(itemPedido);
        }

        // cria orcamento com os dados do cliente e localizacao
        OrcamentoModel orcamento = servicoDeVendas.criaOrcamento(
                pedido,
                requestDTO.getNomeCliente(),
                requestDTO.getPais(),
                requestDTO.getEstado()
        );

        return OrcamentoDTO.fromModel(orcamento);
    }
}
