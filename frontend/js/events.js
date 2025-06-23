const Events = (() => {

    function configurarEventos() {
        document.getElementById('btn-produtos')
            .addEventListener('click', carregarProdutos);

        document.getElementById('btn-novo-orcamento')
            .addEventListener('click', prepararCriacaoOrcamento);

        document.getElementById('btn-buscar-orcamento')
            .addEventListener('click', () => {
                UI.mostrarFormularioBuscarOrcamento();
            });

        document.getElementById('btn-efetivar-orcamento')
            .addEventListener('click', () => {
                UI.mostrarFormularioEfetivarOrcamento();
            });

        document.addEventListener('click', (e) => {
            if (e.target && e.target.id === 'btn-voltar') {
                UI.voltarTelaInicial();
            }
            if (e.target && e.target.id === 'btn-buscar-orcamento-final') {
                buscarOrcamento();
            }
            if (e.target && e.target.id === 'btn-efetivar-orcamento-final') {
                efetivarOrcamento();
            }
            if (e.target && e.target.id === 'btn-criar-orcamento-final') {
                criarOrcamento();
            }
        });

        document.getElementById('btn-catalogo-produtos')
        .addEventListener('click', async () => {
            try {
                const produtos = await API.getCatalogoProdutos();
                UI.exibirCatalogoProdutos(produtos);
            } catch (error) {
                UI.mostrarMensagem(`Erro ao buscar catálogo: ${error.message}`, true);
            }
        });

        document.getElementById('btn-chegada-estoque')
        .addEventListener('click', () => {
            UI.mostrarFormularioChegadaEstoque();
        });

        document.addEventListener('click', (e) => {
        if (e.target && e.target.id === 'btn-registrar-chegada') {
            confirmarChegadaEstoque();
        }
        });

        document.getElementById('btn-disponiveis-catalogo')
        .addEventListener('click', async () => {
            try {
                const produtos = await API.getDisponiveisCatalogo();
                UI.exibirDisponiveisCatalogo(produtos);
            } catch (error) {
                UI.mostrarMensagem(`Erro ao buscar produtos disponíveis no catálogo: ${error.message}`, true);
            }
        });

        document.getElementById('btn-disponiveis-informados')
        .addEventListener('click', () => {
            UI.mostrarFormularioDisponiveisInformados();
        });

        document.addEventListener('click', (e) => {
            if (e.target && e.target.id === 'btn-consultar-disponiveis') {
                consultarDisponiveisInformados();
            }
        });

        document.getElementById('btn-taxa-conversao')
        .addEventListener('click', async () => {
            try {
                const dados = await API.getTaxaConversao();
                UI.exibirTaxaConversao(dados);
            } catch (error) {
                UI.mostrarMensagem(`Erro ao buscar taxa de conversão: ${error.message}`, true);
            }
        });

        document.getElementById('btn-efetivados-periodo')
            .addEventListener('click', () => {
                UI.mostrarFormularioOrcamentosEfetivadosPeriodo();
            });
        
        document.addEventListener('click', (e) => {
            if (e.target && e.target.id === 'btn-consultar-efetivados-periodo') {
                consultarOrcamentosEfetivadosPeriodo();
            }
        });
    }

    async function carregarProdutos() {
        try {
            const produtos = await API.getProdutosDisponiveis();
            UI.exibirProdutos(produtos);
        } catch (error) {
            UI.mostrarMensagem(`Erro ao buscar produtos: ${error.message}`, true);
        }
    }

    async function prepararCriacaoOrcamento() {
        try {
            const produtos = await API.getProdutosDisponiveis();
            UI.mostrarFormularioCriarOrcamento(produtos);
        } catch (error) {
            UI.mostrarMensagem(`Erro ao buscar produtos: ${error.message}`, true);
        }
    }

    async function criarOrcamento() {
        try {
            const nomeCliente = document.getElementById('nome-cliente').value.trim();
            const pais = document.getElementById('pais').value.trim();
            const estado = document.getElementById('estado').value.trim();

            if (!nomeCliente || !pais || !estado) {
                UI.mostrarMensagem("Por favor, preencha todos os dados do cliente e localização.", true);
                return;
            }

            const produtos = await API.getProdutosDisponiveis();
            const itens = produtos.map(prod => {
                const qtd = parseInt(document.getElementById(`qtd-${prod.id}`).value);
                return qtd > 0 ? { idProduto: prod.id, qtdade: qtd } : null;
            }).filter(item => item !== null);

            if (itens.length === 0) {
                UI.mostrarMensagem("Selecione pelo menos um produto com quantidade maior que zero.", true);
                return;
            }

            const dadosOrcamento = {
                nomeCliente,
                pais,
                estado,
                itens
            };

            const orcamentoCriado = await API.criarNovoOrcamento(dadosOrcamento);
            const orcamentoCompleto = await API.buscarOrcamentoPorId(orcamentoCriado.id);

            UI.exibirOrcamento(orcamentoCompleto, `Orçamento ${orcamentoCompleto.id} criado com sucesso!`);
        } catch (error) {
            if (error.message.includes("Localização não atendida")) {
                UI.mostrarMensagem(
                    `No momento não atendemos o estado ${document.getElementById('estado').value.trim()}. Em breve teremos expansão para este estado!`,
                    true
                );
            } else {
                UI.mostrarMensagem(`Erro ao criar orçamento: ${error.message}`, true);
            }
        }
    }

    async function buscarOrcamento() {
        const input = document.getElementById('input-id-orcamento');
        const id = input.value.trim();

        if (!id) {
            UI.mostrarMensagem("Por favor, insira um ID válido.", true);
            return;
        }

        try {
            const orcamento = await API.buscarOrcamentoPorId(id);
            UI.exibirOrcamento(orcamento);
        } catch (error) {
            UI.mostrarMensagem(`Erro ao buscar orçamento: ${error.message}`, true);
        }
    }

    async function efetivarOrcamento() {
        const input = document.getElementById('input-id-efetivar');
        const id = input.value.trim();

        if (!id) {
            UI.mostrarMensagem("Por favor, insira um ID válido.", true);
            return;
        }

        try {
            const orcamentoEfetivado = await API.efetivarOrcamento(id);
            const orcamentoCompleto = await API.buscarOrcamentoPorId(orcamentoEfetivado.id);

            UI.exibirOrcamento(orcamentoCompleto, `Orçamento ${orcamentoCompleto.id} efetivado com sucesso!`);
        } catch (error) {
            UI.mostrarMensagem(`Erro ao efetivar orçamento: ${error.message}`, true);
        }
    }

    async function confirmarChegadaEstoque() {
        const idProduto = document.getElementById('input-id-produto').value.trim();
        const qtdade = document.getElementById('input-quantidade').value.trim();
    
        if (!idProduto || !qtdade || qtdade <= 0) {
            UI.mostrarMensagem("Por favor, insira um ID de produto e uma quantidade válida.", true);
            return;
        }
    
        try {
            const resultado = await API.chegadaEstoque(idProduto, qtdade);
            UI.exibirChegadaEstoque(resultado);
        } catch (error) {
            UI.mostrarMensagem(`Erro ao registrar chegada de estoque: ${error.message}`, true);
        }
    }    

    async function consultarDisponiveisInformados() {
        const input = document.getElementById('input-ids').value.trim();
    
        if (!input) {
            UI.mostrarMensagem("Por favor, insira ao menos um ID de produto.", true);
            return;
        }
    
        const ids = input.split(',')
            .map(id => id.trim())
            .filter(id => id !== '' && !isNaN(id))
            .map(Number);
    
        if (ids.length === 0) {
            UI.mostrarMensagem("IDs inválidos. Informe IDs separados por vírgula.", true);
            return;
        }
    
        try {
            const produtos = await API.getDisponiveisInformados(ids);
            UI.exibirDisponiveisInformados(produtos);
        } catch (error) {
            UI.mostrarMensagem(`Erro ao buscar produtos: ${error.message}`, true);
        }
    }

    async function consultarOrcamentosEfetivadosPeriodo() {
        const dataInicial = document.getElementById('data-inicial').value;
        const dataFinal = document.getElementById('data-final').value;

        if (!dataInicial || !dataFinal) {
            UI.mostrarMensagem("Por favor, preencha ambas as datas.", true);
            return;
        }

        try {
            const orcamentos = await API.getOrcamentosEfetivadosPeriodo(dataInicial, dataFinal);
            UI.exibirOrcamentosEfetivadosPeriodo(orcamentos);
        } catch (error) {
            UI.mostrarMensagem(`Erro ao buscar orçamentos: ${error.message}`, true);
        }
    }

    
    return {
        configurarEventos,
        confirmarChegadaEstoque,
        consultarDisponiveisInformados,
        consultarOrcamentosEfetivadosPeriodo
    };
})();
