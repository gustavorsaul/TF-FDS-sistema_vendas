package com.bcopstein.sistvendas.persistencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.bcopstein.sistvendas.dominio.modelos.ItemDeEstoqueModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;

@Repository
@Primary
public class EstoqueRepJPA implements IEstoqueRepositorio{
    
    private EstoqueJPA_ItfRep estoque;
    private ProdutoJPA_ItfRep produtoJpa;

    @Autowired
    public EstoqueRepJPA(EstoqueJPA_ItfRep estoque, ProdutoJPA_ItfRep produtoJpa) {
        this.estoque = estoque;
        this.produtoJpa = produtoJpa;
    }

    @Override
    public List<ProdutoModel> todos() {
        List<ItemDeEstoque> itens = estoque.findAll();
        return itens.stream()
                .map(it->Produto.toProdutoModel(it.getProduto()))
                .toList();
    }

    @Override
    public List<ProdutoModel> todosComEstoque() {
        List<ItemDeEstoque> itens = estoque.findAll();
        return itens.stream()
                .filter(it->it.getQuantidade()>0)
                .map(it->Produto.toProdutoModel(it.getProduto()))
                .toList();
    }

    @Override
    public int quantidadeEmEstoque(long codigo) {
        ItemDeEstoque item = this.findByProdId(codigo);
        System.out.println("item: "+item);
        if (item == null){
            return 0;
        }else{
            return item.getQuantidade();
        }
            
    }

    @Override
    public int baixaEstoque(long codProd, int qtdade) {
        ItemDeEstoque item = this.findByProdId(codProd);
        if (item == null){
            throw new IllegalArgumentException("Produto inexistente");
        }
        if (item.getQuantidade() < qtdade){
            throw new IllegalArgumentException("Quantidade em estoque insuficiente");
        }
        int novaQuantidade = item.getQuantidade() - qtdade;
        item.setQuantidade(novaQuantidade);
        estoque.save(item);
        return novaQuantidade;
    }

    private ItemDeEstoque findByProdId(long cod){
        return estoque.findAll().stream()
                    .filter(it->it.getProduto().getId()==cod)
                    .findFirst()
                    .orElse(null);
    }

    @Override
    public ItemDeEstoqueModel adicionaEstoque(long codProd, int qtdade) {
        if (qtdade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }

        ItemDeEstoque item = this.findByProdId(codProd);

        if (item == null) {

            Produto prod = produtoJpa.findById(codProd);
            if (prod == null) {
                throw new IllegalArgumentException("Produto inexistente no catálogo");
            }
                
            item = new ItemDeEstoque(
                    codProd,
                    prod,
                    qtdade,
                    0, 
                    50 
            );

            estoque.save(item);
        } else {

            int novaQuantidade = item.getQuantidade() + qtdade;

            if (novaQuantidade > item.getEstoqueMax()) {
                throw new IllegalArgumentException("Operação excede o estoque máximo permitido.");
            }

            item.setQuantidade(novaQuantidade);
            estoque.save(item);
        }

        return new ItemDeEstoqueModel(
                item.getId(),
                Produto.toProdutoModel(item.getProduto()),
                item.getQuantidade(),
                item.getEstoqueMin(),
                item.getEstoqueMax()
        );
    }


}
