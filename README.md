# Fundamentos de Desenvolvimento de Software

## Trabalho Final – 2025/1

### Implementando um sistema de vendas usando arquitetura limpa

---

## Objetivo

O objetivo deste trabalho é explorar conceitos de padrões de projeto, princípios SOLID e Arquitetura Limpa no desenvolvimento do "backend" de um sistema de vendas.

---

## Funcionalidades Desenvolvidas

**Gestão de Produtos e Estoque:**
- Catálogo de produtos com código, descrição e preço unitário
- Controle de estoque com quantidades máxima, mínima e atual por produto
- Registro de chegada de produtos no estoque

**Sistema de Orçamentos:**
- Geração de orçamentos com código, data, cliente, itens solicitados e local de entrega
- Cálculo automático de impostos estaduais e federais conforme regras específicas por estado
- Aplicação de descontos: 5% por item com mais de 3 unidades e 10% para orçamentos com mais de 10 itens
- Validação de orçamentos por 21 dias
- Verificação de disponibilidade de estoque na efetivação

**Cobertura Geográfica:**
- Atendimento a estados específicos do Brasil (RS, SP, PE)
- Validação de localização antes de processar pedidos
- Cálculo de impostos diferenciado por estado:
  - **Rio Grande do Sul:** Isento para orçamentos < R$ 100,00; 10% sobre valor que excede R$ 100,00
  - **São Paulo:** Alíquota única de 12%
  - **Pernambuco:** 15% geral; 5% para produtos essenciais (marcados com "*" na descrição)
  - **Imposto Federal:** 15% sobre valor total do orçamento

**Módulo Gerencial:**
- Consultas de volume de vendas por período
- Perfil de compras por cliente
- Total de vendas por produto
- Taxa de conversão de orçamentos
- Relatório de clientes que mais compraram (formato texto)

**Endpoints Disponíveis:**
- Catálogo de produtos
- Solicitação e efetivação de orçamentos
- Consulta e atualização de estoque
- Relatórios gerenciais

---

## Execução do Sistema

Para executar o sistema, utilize o seguinte comando:

```bash
mvn spring-boot:run
```

O sistema estará disponível após a inicialização do Spring Boot.

**Acesso ao Sistema:**
- **Interface Web:** Abra o arquivo `index.html` na pasta `frontend` no seu navegador
- **API REST:** Acesse via `localhost:8080` para fazer requisições JSON diretamente


