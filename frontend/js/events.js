const Events = (() => {

    function configurarEventos() {
        document.getElementById('btn-produtos')
            .addEventListener('click', carregarProdutos);

        document.getElementById('btn-novo-orcamento')
            .addEventListener('click', criarOrcamento);

        document.getElementById('btn-buscar-orcamento')
            .addEventListener('click', () => {
                UI.mostrarFormularioBuscarOrcamento();
            });

        document.getElementById('btn-efetivar-orcamento')
            .addEventListener('click', () => {
                UI.mostrarFormularioEfetivarOrcamento();
            });

        // Event delegation global
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

    async function criarOrcamento() {
        try {
            const itens = [
                { idProduto: 10, qtdade: 2 },
                { idProduto: 20, qtdade: 1 }
            ];
            const orcamentoCriado = await API.criarNovoOrcamento(itens);
            const orcamentoCompleto = await API.buscarOrcamentoPorId(orcamentoCriado.id);

            UI.exibirOrcamento(orcamentoCompleto, `Orçamento ${orcamentoCompleto.id} criado com sucesso`);
        } catch (error) {
            UI.mostrarMensagem(`Erro ao criar orçamento: ${error.message}`, true);
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

            UI.exibirOrcamento(orcamentoCompleto, `Orçamento ${orcamentoCompleto.id} efetivado com sucesso`);
        } catch (error) {
            UI.mostrarMensagem(`Erro ao efetivar orçamento: ${error.message}`, true);
        }
    }

    return {
        configurarEventos
    };
})();
