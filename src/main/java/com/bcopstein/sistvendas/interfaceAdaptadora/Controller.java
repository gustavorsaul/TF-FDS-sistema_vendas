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
import com.bcopstein.sistvendas.aplicacao.casosDeUso.CatalogoProdutosUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ChegadaEstoqueUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.CriaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.DisponiveisCatalogoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.DisponiveisProdutosInfUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.EfetivaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.OrcamentosEfetPeriodoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ProdutosDisponiveisUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.TaxaConversaoUC;
import com.bcopstein.sistvendas.aplicacao.dtos.EstoqueProdutoDTO;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.VolumeVendasPeriodoUC;
import com.bcopstein.sistvendas.aplicacao.dtos.ItemPedidoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.ListaIdProdutoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoRequestDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.ProdutoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.TaxaConversaoDTO;
import com.bcopstein.sistvendas.dominio.modelos.ItemDeEstoqueModel;

import com.bcopstein.sistvendas.aplicacao.casosDeUso.TotalVendasPorProdutoUC;
import com.bcopstein.sistvendas.aplicacao.dtos.VendasPorProdutoDTO;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.PerfilComprasClienteUC;
import com.bcopstein.sistvendas.aplicacao.dtos.PerfilComprasClienteDTO;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.RelatorioClientesMaisCompraramUC;

@RestController
public class Controller {
    private ProdutosDisponiveisUC produtosDisponiveis;
    private CriaOrcamentoUC criaOrcamento;
    private EfetivaOrcamentoUC efetivaOrcamento;
    private BuscaOrcamentoUC buscaOrcamento;
    private CatalogoProdutosUC catalogoProdutos;
    private ChegadaEstoqueUC chegadaEstoque;
    private DisponiveisCatalogoUC disponiveisCatalogo;
    private DisponiveisProdutosInfUC disponiveisProdutosInf;
    private OrcamentosEfetPeriodoUC orcamentosEfetPeriodo;
    private TaxaConversaoUC taxaConversao;

    @Autowired
    public Controller(ProdutosDisponiveisUC produtosDisponiveis,
                      CriaOrcamentoUC criaOrcamento,
                      EfetivaOrcamentoUC efetivaOrcamento,
                      BuscaOrcamentoUC buscaOrcamento,
                      CatalogoProdutosUC catalogoProdutos,
                      ChegadaEstoqueUC chegadaEstoque,
                      DisponiveisCatalogoUC disponiveisCatalogo,
                      DisponiveisProdutosInfUC disponiveisProdutosInf,
                      OrcamentosEfetPeriodoUC orcamentosEfetPeriodo,
                      TaxaConversaoUC taxaConversao){
        this.produtosDisponiveis = produtosDisponiveis;
        this.criaOrcamento = criaOrcamento;
        this.efetivaOrcamento = efetivaOrcamento;
        this.buscaOrcamento = buscaOrcamento;
        this.catalogoProdutos = catalogoProdutos;
        this.chegadaEstoque = chegadaEstoque;
        this.disponiveisCatalogo = disponiveisCatalogo;
        this.disponiveisProdutosInf = disponiveisProdutosInf;
        this.orcamentosEfetPeriodo = orcamentosEfetPeriodo;
        this.taxaConversao = taxaConversao;
    }
    
    @Autowired
    private VolumeVendasPeriodoUC volumeVendasPeriodoUC;

    @Autowired
    private TotalVendasPorProdutoUC totalVendasPorProdutoUC;

    @Autowired
    private PerfilComprasClienteUC perfilComprasClienteUC;

    @Autowired
    private RelatorioClientesMaisCompraramUC relatorioClientesMaisCompraramUC;

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

    @CrossOrigin(origins = "*")
    @PostMapping("chegadaEstoque")
    public ItemDeEstoqueModel chegadaEstoque(
        @RequestBody ItemPedidoDTO item) {
            return chegadaEstoque.run(item);
    }
    
    @GetMapping("disponiveisCatalogo")
    @CrossOrigin(origins = "*")
    public List<EstoqueProdutoDTO> disponiveisCatalogo() {
        return disponiveisCatalogo.run();
    }
    
    @PostMapping("/disponiveisInformados")
    @CrossOrigin(origins = "*")
    public List<EstoqueProdutoDTO> produtosDisponiveisLista(@RequestBody ListaIdProdutoDTO lista) {
        return disponiveisProdutosInf.run(lista.getIdsProdutos());
    }
    
    @GetMapping("/orcamentosEfetivadosPeriodo")
    public List<OrcamentoDTO> orcamentosEfetiavadosPeriodo(
        @RequestParam("dataInicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
        @RequestParam("dataFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        return orcamentosEfetPeriodo.run(dataInicial, dataFinal);
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
    public List<VendasPorProdutoDTO> totalVendasPorProduto() {
        return totalVendasPorProdutoUC.run();
    }

    @GetMapping("/vendas/taxa-conversao")
    @CrossOrigin(origins = "*")
    public TaxaConversaoDTO taxaConversao() {
        return taxaConversao.run();
    }

    @GetMapping("/perfilCompras/{nomeCliente}")
    @CrossOrigin(origins = "*")
    public PerfilComprasClienteDTO perfilComprasCliente(@PathVariable String nomeCliente) {
        return perfilComprasClienteUC.run(nomeCliente);
    }

    @GetMapping("/relatorio/clientes-mais-compraram")
    @CrossOrigin(origins = "*")
    public String relatorioClientesMaisCompraram(
        @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
        @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return relatorioClientesMaisCompraramUC.run(inicio, fim);
    }

}
