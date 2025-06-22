package com.bcopstein.sistvendas.interfaceAdaptadora;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.sistvendas.aplicacao.casosDeUso.BuscaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ConsultaEstoqueUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.CriaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.EfetivaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ProdutosDisponiveisUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.VolumeVendasPeriodoUC;
import com.bcopstein.sistvendas.aplicacao.dtos.EstoqueDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.ItemPedidoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoRequestDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.ProdutoDTO;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.TotalVendasPorProdutoUC;
import com.bcopstein.sistvendas.aplicacao.dtos.VendasPorProdutoDTO;

@RestController
public class Controller {
    private ProdutosDisponiveisUC produtosDisponiveis;
    private CriaOrcamentoUC criaOrcamento;
    private EfetivaOrcamentoUC efetivaOrcamento;
    private BuscaOrcamentoUC buscaOrcamento;

    @Autowired
    public Controller(ProdutosDisponiveisUC produtosDisponiveis,
                      CriaOrcamentoUC criaOrcamento,
                      EfetivaOrcamentoUC efetivaOrcamento,
                      BuscaOrcamentoUC buscaOrcamento){
        this.produtosDisponiveis = produtosDisponiveis;
        this.criaOrcamento = criaOrcamento;
        this.efetivaOrcamento = efetivaOrcamento;
        this.buscaOrcamento = buscaOrcamento;
    }
    @Autowired
    private ConsultaEstoqueUC consultaEstoqueUC;
    
    @Autowired
    private VolumeVendasPeriodoUC volumeVendasPeriodoUC;

    @Autowired
    private TotalVendasPorProdutoUC totalVendasPorProdutoUC;

     @GetMapping("/estoque")
    @CrossOrigin(origins = "*")
    public List<EstoqueDTO> listaEstoque() {
        return consultaEstoqueUC.run();
    }
    @GetMapping("/vendas/volume")
    @CrossOrigin(origins = "*")
    public double volumeVendasPorPeriodo(
    @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
    @RequestParam("fim")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
    return volumeVendasPeriodoUC.run(inicio, fim);
    }

    @GetMapping("/vendas/por-produto")
    @CrossOrigin(origins = "*")
    public List<VendasPorProdutoDTO> totalVendasPorProduto(
    @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
    @RequestParam("fim")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
    return totalVendasPorProdutoUC.run(inicio, fim);
    }

    @GetMapping("")
    @CrossOrigin(origins = "*")
    public String welcomeMessage(){
        return("Bem vindo às lojas ACME");
    }

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
}
