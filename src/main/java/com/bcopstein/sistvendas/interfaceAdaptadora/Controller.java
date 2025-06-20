package com.bcopstein.sistvendas.interfaceAdaptadora;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.sistvendas.aplicacao.casosDeUso.BuscaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.CatalogoProdutosUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ChegadaEstoqueUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.CriaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.EfetivaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ProdutosDisponiveisUC;
import com.bcopstein.sistvendas.aplicacao.dtos.ItemPedidoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoRequestDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.ProdutoDTO;
import com.bcopstein.sistvendas.dominio.modelos.ItemDeEstoqueModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.persistencia.ItemDeEstoque;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class Controller {
    private ProdutosDisponiveisUC produtosDisponiveis;
    private CriaOrcamentoUC criaOrcamento;
    private EfetivaOrcamentoUC efetivaOrcamento;
    private BuscaOrcamentoUC buscaOrcamento;
    private CatalogoProdutosUC catalogoProdutos;
    private ChegadaEstoqueUC chegadaEstoque;

    @Autowired
    public Controller(ProdutosDisponiveisUC produtosDisponiveis,
                      CriaOrcamentoUC criaOrcamento,
                      EfetivaOrcamentoUC efetivaOrcamento,
                      BuscaOrcamentoUC buscaOrcamento,
                      CatalogoProdutosUC catalogoProdutos,
                      ChegadaEstoqueUC chegadaEstoque){
        this.produtosDisponiveis = produtosDisponiveis;
        this.criaOrcamento = criaOrcamento;
        this.efetivaOrcamento = efetivaOrcamento;
        this.buscaOrcamento = buscaOrcamento;
        this.catalogoProdutos = catalogoProdutos;
        this.chegadaEstoque = chegadaEstoque;
    }

    @GetMapping("")
    @CrossOrigin(origins = "*")
    public String welcomeMessage(){
        return("Bem vindo às lojas ACME");
    }

    // lista com todos os produtos catalogados
    @GetMapping("catalogoProdutos")
    @CrossOrigin(origins = "*")
    public List<ProdutoDTO> catalogoProdutos() {
        return catalogoProdutos.run();
    }
    
    // lista com todos os produtos disponiveis em estoque
    @GetMapping("produtosDisponiveis")
    @CrossOrigin(origins = "*")
    public List<ProdutoDTO> produtosDisponiveis(){
        return produtosDisponiveis.run();
    }    

    @PostMapping("novoOrcamento")
    @CrossOrigin(origins = "*")
    public OrcamentoDTO novoOrcamento(@RequestBody OrcamentoRequestDTO requestDTO){
        return criaOrcamento.run(requestDTO);
    }

    @GetMapping("efetivaOrcamento/{id}")
    @CrossOrigin(origins = "*")
    public OrcamentoDTO efetivaOrcamento(@PathVariable(value="id") long idOrcamento){
        return efetivaOrcamento.run(idOrcamento);
    }

    @GetMapping("buscaOrcamento/{id}")
    @CrossOrigin(origins = "*")
    public OrcamentoDTO buscaOrcamento(@PathVariable(value="id") long idOrcamento){
        return OrcamentoDTO.fromModel(buscaOrcamento.run(idOrcamento));
    }

    @PostMapping("chegadaEstoque")
    public ItemDeEstoqueModel chegadaEstoque(
        @RequestBody ItemPedidoDTO item) {
            return chegadaEstoque.run(item);
    }
    

    /*
    - Informar a chegada de produtos no estoque
    - Retornar a quantidade disponível 
        no estoque para todos os itens do catálogo
    - Retornar a quantidade disponível 
        no estoque para uma lista de produtos informados
    - Retornar a lista de orçamentos 
        efetivados em um determinado período 
        (informar data inicial e data final)
     */


}
