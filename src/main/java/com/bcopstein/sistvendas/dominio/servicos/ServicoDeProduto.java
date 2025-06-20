package com.bcopstein.sistvendas.dominio.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IProdutoRepositorio;

@Service
public class ServicoDeProduto {

    private IProdutoRepositorio produtos;

    @Autowired
    public ServicoDeProduto(IProdutoRepositorio produtos) {
        this.produtos = produtos;
    }

    public List<ProdutoModel> todosProdutos() {
        return produtos.todos();
    }


}
