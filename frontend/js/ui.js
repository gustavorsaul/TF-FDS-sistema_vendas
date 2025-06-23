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

    async function exibirOrcamento(orcamento, titulo = null) {
        const produtos = await API.getProdutosDisponiveis();
    
        const cabecalho = titulo 
            ? `<h2>${titulo}</h2>` 
            : `<h2>Orçamento ID: ${orcamento.id}</h2>`;
    
        const tabela = `
            ${cabecalho}
            <p><strong>Cliente:</strong> ${orcamento.nomeCliente}</p>
            <p><strong>Local:</strong> ${orcamento.estado} - ${orcamento.pais}</p>
            <p><strong>Data Criação:</strong> ${orcamento.dataCriacao}</p>
            <p><strong>Validade:</strong> ${orcamento.validade}</p>
    
            <table>
                <thead>
                    <tr>
                        <th>ID Produto</th>
                        <th>Descrição</th>
                        <th>Quantidade</th>
                        <th>Preço Unitário (R$)</th>
                        <th>Subtotal (R$)</th>
                    </tr>
                </thead>
                <tbody>
                    ${orcamento.itens.map(item => {
                        const idProduto = item.idProduto;
                        const qtdade = item.qtdade;
    
                        const produto = produtos.find(p => p.id === idProduto);
                        const descricao = produto ? produto.descricao : "Produto não encontrado";
                        const preco = produto ? produto.precoUnitario : 0;
                        const subtotal = preco * qtdade;
    
                        return `
                            <tr>
                                <td>${idProduto}</td>
                                <td>${descricao}</td>
                                <td>${qtdade}</td>
                                <td>${preco.toFixed(2)}</td>
                                <td>${subtotal.toFixed(2)}</td>
                            </tr>
                        `;
                    }).join('')}
                </tbody>
            </table>
    
            <p><strong>Custo Itens:</strong> R$ ${orcamento.custoItens.toFixed(2)}</p>
            <p><strong>Imposto Estadual:</strong> R$ ${orcamento.impostoEstadual.toFixed(2)}</p>
            <p><strong>Imposto Federal:</strong> R$ ${orcamento.impostoFederal.toFixed(2)}</p>
            <p><strong>Desconto:</strong> R$ ${orcamento.desconto.toFixed(2)}</p>
            <p><strong>Custo Consumidor:</strong> R$ ${orcamento.custoConsumidor.toFixed(2)}</p>
            <p><strong>Efetivado:</strong> ${orcamento.efetivado ? 'Sim' : 'Não'}</p>
    
            ${botaoVoltar()}
        `;
    
        document.getElementById('resultado').innerHTML = tabela;
    }

    function mostrarFormularioCriarOrcamento(produtos) {
        const tabelaProdutos = produtos.map(prod => `
            <tr>
                <td>${prod.id}</td>
                <td>${prod.descricao}</td>
                <td>${prod.precoUnitario.toFixed(2)}</td>
                <td><input type="number" id="qtd-${prod.id}" min="0" value="0" style="width:60px;"></td>
            </tr>
        `).join('');

        const formulario = `
            <h2>Criar Orçamento</h2>

            <label for="nome-cliente">Nome do Cliente:</label>
            <input type="text" id="nome-cliente" placeholder="Digite o nome">
            <br><br>

            <label for="pais">País:</label>
            <input type="text" id="pais" placeholder="Digite o país">
            <br><br>

            <label for="estado">Estado:</label>
            <input type="text" id="estado" placeholder="Digite o estado">
            <br><br>

            <h3>Selecione os produtos:</h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Descrição</th>
                        <th>Preço Unitário (R$)</th>
                        <th>Quantidade</th>
                    </tr>
                </thead>
                <tbody>
                    ${tabelaProdutos}
                </tbody>
            </table>
            <br>

            <button class="back-button" id="btn-criar-orcamento-final">Criar Orçamento</button>
            ${botaoVoltar()}
        `;

        document.getElementById('resultado').innerHTML = formulario;
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

    function exibirCatalogoProdutos(produtos) {
        const tabela = `
            <h2>Catálogo de Produtos</h2>
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

    function mostrarFormularioChegadaEstoque() {
        const html = `
            <h2>Chegada de Produtos no Estoque</h2>
            <label for="input-id-produto">ID do Produto:</label><br>
            <input type="number" id="input-id-produto" placeholder="Digite o ID do Produto"><br><br>
    
            <label for="input-quantidade">Quantidade:</label><br>
            <input type="number" id="input-quantidade" placeholder="Digite a quantidade"><br><br>
    
            <button class="back-button" id="btn-registrar-chegada">Registrar</button>
            ${botaoVoltar()}
        `;
        document.getElementById('resultado').innerHTML = html;
    }

    function exibirChegadaEstoque(itemEstoque) {
        const html = `
            <h2>Estoque Atualizado</h2>
            <p>Foram inseridos <strong>${itemEstoque.quantidade}</strong> unidades do produto <strong>${itemEstoque.produto.descricao}</strong> no estoque.</p>
    
            <table>
                <thead>
                    <tr>
                        <th>ID Produto</th>
                        <th>Descrição</th>
                        <th>Preço Unitário (R$)</th>
                        <th>Quantidade no Estoque</th>
                        <th>Estoque Mínimo</th>
                        <th>Estoque Máximo</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${itemEstoque.produto.id}</td>
                        <td>${itemEstoque.produto.descricao}</td>
                        <td>${itemEstoque.produto.precoUnitario.toFixed(2)}</td>
                        <td>${itemEstoque.quantidade}</td>
                        <td>${itemEstoque.estoqueMin}</td>
                        <td>${itemEstoque.estoqueMax}</td>
                    </tr>
                </tbody>
            </table>
    
            ${botaoVoltar()}
        `;
        document.getElementById('resultado').innerHTML = html;
    }
    
    function exibirDisponiveisCatalogo(produtos) {
        const tabela = `
            <h2>Produtos Disponíveis no Catálogo</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID Produto</th>
                        <th>Descrição</th>
                        <th>Quantidade Disponível</th>
                    </tr>
                </thead>
                <tbody>
                    ${produtos.map(prod => `
                        <tr>
                            <td>${prod.idProduto}</td>
                            <td>${prod.descricao}</td>
                            <td>${prod.quantidade}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
            ${botaoVoltar()}
        `;
    
        document.getElementById('resultado').innerHTML = tabela;
    }
    
    function mostrarFormularioDisponiveisInformados() {
        const html = `
            <h2>Consultar Disponibilidade por IDs</h2>
            <label for="input-ids">Informe os IDs dos produtos separados por vírgula:</label><br>
            <input type="text" id="input-ids" placeholder="Ex.: 10, 20, 30"><br><br>
    
            <button class="back-button" id="btn-consultar-disponiveis">Consultar</button>
            ${botaoVoltar()}
        `;
        document.getElementById('resultado').innerHTML = html;
    }
    
    function exibirDisponiveisInformados(produtos) {
        const tabela = `
            <h2>Produtos Consultados</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID Produto</th>
                        <th>Descrição</th>
                        <th>Quantidade Disponível</th>
                    </tr>
                </thead>
                <tbody>
                    ${produtos.map(prod => `
                        <tr>
                            <td>${prod.idProduto}</td>
                            <td>${prod.descricao}</td>
                            <td>${prod.quantidade}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
            ${botaoVoltar()}
        `;
    
        document.getElementById('resultado').innerHTML = tabela;
    }
    
    function exibirTaxaConversao(dados) {
        const html = `
            <h2>Taxa de Conversão de Orçamentos</h2>
            <p><strong>Total de Orçamentos:</strong> ${dados.totalOrcamentos}</p>
            <p><strong>Orçamentos Efetivados:</strong> ${dados.efetivados}</p>
            <p><strong>Taxa de Conversão:</strong> ${dados.taxaConversao.toFixed(2)}%</p>
            ${botaoVoltar()}
        `;
        document.getElementById('resultado').innerHTML = html;
    }
    
    return {
        exibirProdutos,
        exibirOrcamento,
        mostrarMensagem,
        mostrarFormularioCriarOrcamento,
        mostrarFormularioBuscarOrcamento,
        mostrarFormularioEfetivarOrcamento,
        exibirCatalogoProdutos,
        voltarTelaInicial,
        mostrarFormularioChegadaEstoque,
        exibirChegadaEstoque,
        exibirDisponiveisCatalogo,
        mostrarFormularioDisponiveisInformados,
        exibirDisponiveisInformados,
        exibirTaxaConversao
    };
})();