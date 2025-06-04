
---

# Fundamentos de Desenvolvimento de Software

## Trabalho Final – 2025/1

### Implementando um sistema de vendas usando arquitetura limpa

---

## Objetivo

O objetivo deste é explorar os conceitos apresentados em aula, com ênfase em padrões de projeto, princípios SOLID e Arquitetura Limpa, no desenvolvimento do “backend” de um sistema de vendas.

---

## Execução

O trabalho deve ser desenvolvido em grupos de até 4 alunos, trabalhos individuais devem ser discutidos e aprovados pelo professor da turma (não é objetivo da disciplina a execução de trabalhos individuais).

---

## Enunciado

O trabalho proposto é a continuidade da implementação do sistema de vendas cujo desenvolvimento foi iniciado durante as aulas. Devem ser observados os requisitos (novos ou alterados) apresentados nesse documento.

---

## Novo enunciado

Uma loja online oferece um catálogo de produtos. Para cada produto, são armazenados o código, a descrição e o preço unitário atual. Os produtos disponíveis para venda estão estocados em um galpão, onde cada item possui um espaço reservado. Nesse espaço, define-se a quantidade máxima que pode ser armazenada, a quantidade mínima que deve ser mantida em estoque e a quantidade atual disponível.

Quando um cliente deseja adquirir um ou mais itens, ele realiza um pedido indicando, para cada produto, a quantidade desejada. O sistema gera um orçamento que contém: um código identificador, a data, o nome do cliente, a lista dos itens solicitados, o estado e o país de entrega, além do somatório dos custos dos itens, o valor dos impostos estadual/regional e federal/nacional, os valores de desconto aplicáveis e o valor final para o consumidor. Todos os orçamentos gerados são armazenados para consultas futuras.

Caso o cliente aprove o orçamento, ele pode solicitar a efetivação da venda informando o número do orçamento. Nesse momento, o sistema verifica a viabilidade do pedido, ou seja, se há estoque suficiente para atender à solicitação. Se houver disponibilidade, o sistema realiza a baixa dos itens no estoque e atualiza o status do orçamento para "efetivado". Aspectos relacionados a pagamento e entrega dos produtos não estão contemplados no escopo deste sistema.

Atualmente, a loja atende apenas determinados estados brasileiros. Contudo, existe a expectativa de expansão para todo o território nacional e, possivelmente, para outros países. Por esse motivo, torna-se necessário registrar o país e o estado/região no momento do pedido. Caso o local informado não esteja entre os atendidos, o pedido deve ser recusado.

A tributação varia conforme o estado/região e o país. Para cada pedido, são calculados obrigatoriamente um imposto estadual/regional e um imposto federal/nacional. Esses valores são sempre calculados antes da aplicação de eventuais descontos (ver detalhamento dos estados atendidos e suas respectivas regras de tributação no Anexo I).

A política atual de descontos prevê:

* 5% de desconto por item cuja quantidade solicitada seja superior a três unidades.
* 10% de desconto sobre o valor total do orçamento quando este contiver mais de dez itens.

Os descontos são cumulativos. Novas políticas de desconto podem ser criadas ou canceladas ao longo do tempo.

Os orçamentos possuem validade de 21 dias a partir da sua geração. Após esse prazo, não podem mais ser efetivados.

Adicionalmente, o sistema deverá contemplar um módulo gerencial, capaz de gerar, no mínimo, quatro consultas com dados consolidados, tais como: volume total de vendas por período, perfil de compras de um cliente, total de vendas por produto e taxa de conversão de orçamentos. Além disso, deverá ser implementado, obrigatoriamente, um relatório no formato texto, referente a uma quinta consulta. Este relatório deverá ser projetado para, futuramente, estar disponível também nos formatos HTML e XML. A definição das consultas ficará a critério do grupo.

---

## Previsão de “endpoints”

O sistema deve prever “endpoints” para:

* Retornar o catálogo de produtos.
* Solicitar orçamento para um pedido (lista de itens).
* Efetivar orçamento indicado se ainda for válido e houver produtos disponíveis.
* Informar a chegada de produtos no estoque.
* Retornar a quantidade disponível no estoque para todos os itens do catálogo.
* Retornar a quantidade disponível no estoque para uma lista de produtos informados.
* Retornar a lista de orçamentos efetivados em um determinado período (informar data inicial e data final).
* Retornar cada uma das consultas definidas pelo grupo.

---

## Instruções para o desenvolvimento do trabalho

O futuro sistema será composto por dois módulos:

a) Um módulo backend (REST e monolítico) capaz de atender as requisições de futuros módulos de frontend dentro da lógica de negócios proposta.

b) A construção de um módulo de frontend está fora do escopo deste trabalho, mas se o grupo conseguir fazer uma versão, mesmo que bastante inicial, será valorizada.

O objetivo do trabalho é desenvolver o módulo backend do sistema capaz de atender todos os requisitos descritos atendendo os princípios SOLID e a arquitetura CLEAN. Padrões de projeto deverão ser explorados sempre que adequado. O módulo deve ser escrito em Java usando a tecnologia Spring-Boot (outras opções de linguagem/tecnologia devem ser previamente acordadas com o professor). A escolha do SGBD para o armazenamento dos dados é livre (pode ser usado o H2 em memória), o uso de JPA é obrigatório. Um conjunto de dados de seed deve ser fornecido de forma a testar adequadamente o sistema.

O grupo deve fornecer um workspace que permita testar cada um dos endpoints desenvolvidos.

O trabalho pode ser desenvolvido individualmente ou em grupos de até 4 alunos. O projeto deverá ser “zipado” entregue via Moodle. A apresentação em aula é obrigatória para que o trabalho seja avaliado. Durante a apresentação o grupo deverá apresentar o sistema funcionando e responder questões sobre a arquitetura, padrões utilizados e outros aspectos de implementação.

Todos os alunos devem entregar, individualmente e em uma sala específica, uma autoavaliação contendo – qual foi sua participação na construção do trabalho e como vê a participação de cada um dos demais componentes do grupo.

Para a apresentação final todos os integrantes do grupo devem estar presentes e aptos a responder perguntas sobre o trabalho desenvolvido.

Trabalhos que configurem fraude acadêmica (mesmo entre turmas) não terão nota atribuída.

---

## Anexo I

### Tabela de estados brasileiros atendidos e cálculo de impostos correspondentes

| Estado Brasileiro     | Regra para cálculo do imposto estadual                                                                                                                                                                                       |
| --------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Rio Grande do Sul** | Orçamentos de menos de R\$ 100,00 são isentos. Para orçamentos maiores que R\$ 100,00 a alíquota é de 10% sobre o valor que ultrapassa R\$ 100,00.                                                                           |
| **São Paulo**         | Alíquota única de 12%.                                                                                                                                                                                                       |
| **Pernambuco**        | Alíquota única de 15% sobre todos os produtos menos aqueles considerados essenciais que tem uma alíquota de 5%. Produtos essenciais tem um "*" ao final da descrição do produto. Exemplos: “Farinha*”, “Banha\*”, “Arroz\*”. |

### Imposto federal:

* Alíquota única de 15% sobre o valor total do orçamento.

---
