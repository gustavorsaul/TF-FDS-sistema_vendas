const API = (() => {

    async function getProdutosDisponiveis() {
        const response = await fetch(`http://localhost:8080/produtosDisponiveis`);
        if (!response.ok) {
            throw new Error("Erro na requisição");
        }
        return response.json();
    }

    async function criarNovoOrcamento(dadosOrcamento) {
        const response = await fetch(`http://localhost:8080/novoOrcamento`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dadosOrcamento)
        });
        if (!response.ok) {
            const erro = await response.text();
            throw new Error(`Erro ao criar orçamento: ${erro}`);
        }
        return response.json();
    }

    async function buscarOrcamentoPorId(id) {
        const response = await fetch(`http://localhost:8080/buscaOrcamento/${id}`);
        if (!response.ok) {
            throw new Error(`Erro ao buscar orçamento ID ${id}`);
        }
        return response.json();
    }

    async function efetivarOrcamento(id) {
        const response = await fetch(`http://localhost:8080/efetivaOrcamento/${id}`);
        if (!response.ok) {
            throw new Error(`Erro ao efetivar orçamento ID ${id}`);
        }
        return response.json();
    }

    async function getCatalogoProdutos() {
        const response = await fetch(`http://localhost:8080/catalogoProdutos`);
        if (!response.ok) {
            throw new Error("Erro ao buscar catálogo de produtos");
        }
        return response.json();
    }

    async function chegadaEstoque(idProduto, qtdade) {
        const body = {
            idProduto: parseInt(idProduto),
            qtdade: parseInt(qtdade)
        };
        const response = await fetch(`http://localhost:8080/chegadaEstoque`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        if (!response.ok) {
            const erro = await response.text();
            throw new Error(`Erro ao registrar chegada de estoque: ${erro}`);
        }
        return response.json();
    }

    async function getDisponiveisCatalogo() {
        const response = await fetch(`http://localhost:8080/disponiveisCatalogo`);
        if (!response.ok) {
            throw new Error("Erro ao buscar produtos disponíveis no catálogo");
        }
        return response.json();
    }

    async function getDisponiveisInformados(idsProdutos) {
        const body = {
            idsProdutos: idsProdutos.map(id => parseInt(id))
        };
        const response = await fetch(`http://localhost:8080/disponiveisInformados`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        if (!response.ok) {
            throw new Error("Erro ao buscar produtos informados");
        }
        return response.json();
    }
    
    async function getTaxaConversao() {
        const response = await fetch(`http://localhost:8080/vendas/taxa-conversao`);
        if (!response.ok) {
            throw new Error("Erro ao buscar a taxa de conversão");
        }
        return response.json();
    }
    
    async function getOrcamentosEfetivadosPeriodo(dataInicial, dataFinal) {
        const url = `http://localhost:8080/orcamentosEfetivadosPeriodo?dataInicial=${dataInicial}&dataFinal=${dataFinal}`;

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error("Erro ao buscar orçamentos efetivados no período");
        }
        return response.json();
    }

    async function getVolumeVendasPeriodo(dataInicio, dataFim) {
        const url = `http://localhost:8080/vendas/volume?inicio=${dataInicio}&fim=${dataFim}`;

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error("Erro ao buscar o volume de vendas no período");
        }
        return response.json();
    }


    return {
        getProdutosDisponiveis,
        criarNovoOrcamento,
        buscarOrcamentoPorId,
        efetivarOrcamento,
        getCatalogoProdutos,
        chegadaEstoque,
        getDisponiveisCatalogo,
        getDisponiveisInformados,
        getTaxaConversao,
        getOrcamentosEfetivadosPeriodo,
        getVolumeVendasPeriodo
    };
})();
