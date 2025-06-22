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

    
    return {
        configurarEventos
    };
})();
