package com.bcopstein.sistvendas.dominio.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IProdutoRepositorio;

@Service
public class ServicoDeProduto {

    private IProdutoRepositorio produtoRepositorio;

    public ServicoDeProduto(IProdutoRepositorio produtoRepositorio) {
        this.produtoRepositorio = produtoRepositorio;
    }

    public List<ProdutoModel> todosProdutos() {
        return produtoRepositorio.todos();
    }

    public ProdutoModel produtoPorId(long id) {
        return produtoRepositorio.consultaPorId(id);
    }

}
