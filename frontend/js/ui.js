const UI = (() => {

    function mostrarMensagem(mensagem, isError = false) {
        const resultado = document.getElementById('resultado');
        resultado.innerHTML = `
            <p style="color:${isError ? 'red' : 'black'};">${mensagem}</p>
            ${botaoVoltar()}
        `;
    }

    function exibirProdutos(produtos) {
        const tabela = `
            <h2>Produtos Disponíveis</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Descrição</th>
                        <th>Preço Unitário (R$)</th>
                    </tr>
                </thead>
                <tbody>
                    ${produtos.map(prod => `
                        <tr>
                            <td>${prod.id}</td>
                            <td>${prod.descricao}</td>
                            <td>${prod.precoUnitario.toFixed(2)}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
            ${botaoVoltar()}
        `;
        document.getElementById('resultado').innerHTML = tabela;
    }

    function exibirOrcamento(orcamento, titulo = null) {
        const cabecalho = titulo 
            ? `<h2>${titulo}</h2>` 
            : `<h2>Orçamento ID: ${orcamento.id}</h2>`;

        const tabela = `
            ${cabecalho}
            <table>
                <thead>
                    <tr>
                        <th>ID Produto</th>
                        <th>Descrição</th>
                        <th>Quantidade</th>
                        <th>Preço Unitário (R$)</th>
                    </tr>
                </thead>
                <tbody>
                    ${orcamento.itens.map(item => {
                        const idProduto = item.produto ? item.produto.id : item.idProduto;
                        const descricao = item.produto ? item.produto.descricao : "-";
                        const preco = item.produto ? item.produto.precoUnitario : 0;
                        const quantidade = item.quantidade ?? item.qtdade;

                        return `
                            <tr>
                                <td>${idProduto}</td>
                                <td>${descricao}</td>
                                <td>${quantidade}</td>
                                <td>${preco.toFixed(2)}</td>
                            </tr>
                        `;
                    }).join('')}
                </tbody>
            </table>

            <p><strong>Custo Itens:</strong> R$ ${orcamento.custoItens.toFixed(2)}</p>
            <p><strong>Imposto:</strong> R$ ${orcamento.imposto.toFixed(2)}</p>
            <p><strong>Desconto:</strong> R$ ${orcamento.desconto.toFixed(2)}</p>
            <p><strong>Custo Consumidor:</strong> R$ ${orcamento.custoConsumidor.toFixed(2)}</p>
            <p><strong>Efetivado:</strong> ${orcamento.efetivado ? 'Sim' : 'Não'}</p>

            ${botaoVoltar()}
        `;

        document.getElementById('resultado').innerHTML = tabela;
    }

    function mostrarFormularioBuscarOrcamento() {
        const formulario = `
            <h2>Buscar Orçamento</h2>
            <label for="input-id-orcamento">ID do Orçamento:</label>
            <input type="number" id="input-id-orcamento" placeholder="Digite o ID">
            <br><br>
            <button class="back-button" id="btn-buscar-orcamento-final">Buscar</button>
        `;
        document.getElementById('resultado').innerHTML = formulario;
    }

    function mostrarFormularioEfetivarOrcamento() {
        const formulario = `
            <h2>Efetivar Orçamento</h2>
            <label for="input-id-efetivar">ID do Orçamento:</label>
            <input type="number" id="input-id-efetivar" placeholder="Digite o ID">
            <br><br>
            <div class="button-group">
                <button class="back-button" id="btn-efetivar-orcamento-final">Efetivar</button>
                <button class="back-button" id="btn-voltar">Voltar</button>
            </div>
        `;
        document.getElementById('resultado').innerHTML = formulario;
    }

    function botaoVoltar() {
        return `<br><button class="back-button" id="btn-voltar">Voltar</button>`;
    }

    function voltarTelaInicial() {
        document.getElementById('resultado').innerHTML = `
            <p>Clique nos botões da barra lateral para navegar pelas funcionalidades.</p>
        `;
    }

    return {
        exibirProdutos,
        exibirOrcamento,
        mostrarMensagem,
        mostrarFormularioBuscarOrcamento,
        mostrarFormularioEfetivarOrcamento,
        voltarTelaInicial
    };
})();
