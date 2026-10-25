# Contrato da API

O contrato é o que o cliente da API enxerga: rotas, JSON e status.

## Convenções

| Item | Valor |
|---|---|
| Endereço base | `http://localhost:8080` |
| Formato | JSON, na requisição e na resposta |
| Datas | `yyyy-MM-dd` |
| Erros | Sempre no formato `ErroDTO` (ver [Erros](#erros)) |
| Documentação viva | `http://localhost:8080/swagger-ui.html` |

## Rotas

Coluna **Funciona**: `sim` para as rotas que já respondem nesta etapa.

### Produtos

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `GET` | `/produtos` | Lista os produtos | Nenhuma | lista de `ProdutoResponseDTO` | `200` | Nenhum | sim |
| `GET` | `/produtos/{id}` | Busca um produto | Nenhuma | `ProdutoResponseDTO` | `200` | `404` | sim |
| `POST` | `/produtos` | Cadastra um produto | `ProdutoRequestDTO` | `ProdutoResponseDTO` | `201` | `400` | sim |
| `PUT` | `/produtos/{id}` | Atualiza nome e estoque mínimo. O `estoqueAtual` enviado é ignorado: depois do cadastro, ele só muda pelas entregas (R1 e R4) | `ProdutoRequestDTO` | `ProdutoResponseDTO` | `200` | `400`, `404` | não |
| `DELETE` | `/produtos/{id}` | Remove um produto sem entregas | Nenhuma | Nenhuma (sem corpo) | `204` | `400`, `404` | não |

### Veículos

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `GET` | `/veiculos` | Lista os veículos | Nenhuma | lista de `VeiculoResponseDTO` | `200` | Nenhum | sim |
| `GET` | `/veiculos/{id}` | Busca um veículo | Nenhuma | `VeiculoResponseDTO` | `200` | `404` | sim |
| `POST` | `/veiculos` | Cadastra um veículo (nasce `DISPONIVEL`) | `VeiculoRequestDTO` | `VeiculoResponseDTO` | `201` | `400` | sim |
| `PUT` | `/veiculos/{id}` | Atualiza motorista e rota | `VeiculoRequestDTO` | `VeiculoResponseDTO` | `200` | `400`, `404` | não |
| `DELETE` | `/veiculos/{id}` | Remove um veículo sem entregas | Nenhuma | Nenhuma (sem corpo) | `204` | `400`, `404` | não |

### Entregas

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `GET` | `/entregas` | Lista as entregas | Nenhuma | lista de `EntregaResponseDTO` | `200` | Nenhum | sim |
| `GET` | `/entregas/{id}` | Busca uma entrega | Nenhuma | `EntregaResponseDTO` | `200` | `404` | sim |
| `POST` | `/entregas` | Registra uma entrega | `EntregaRequestDTO` | `EntregaResponseDTO` | `201` | `400`, `404` | sim |

### Rotas que não são CRUD

As que nascem das regras de negócio do README.

| Verbo | Caminho | O que faz | Regra | Sucesso | Erros |
|---|---|---|---|---|---|
| `POST` | `/entregas` | Registra a entrega, baixa o estoque do produto e coloca o veículo `EM_ROTA` | R1 e R2 | `201` | `400`, `404` |
| `PUT` | `/entregas/{id}/concluir` | Marca a entrega como `ENTREGUE` e libera o veículo | R3 | `200` | `400`, `404` |
| `PUT` | `/entregas/{id}/cancelar` | Marca a entrega como `CANCELADA`, devolve a quantidade ao estoque e libera o veículo | R3 e R4 | `200` | `400`, `404` |

## DTOs

O que entra não é igual ao que sai: o cliente não envia `id` nem campos que a API decide (`status`, `dataConclusao`, os campos calculados).

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `ProdutoRequestDTO` | entrada | `nome`, `estoqueAtual`, `estoqueMinimo` | `nome` obrigatório, até 50 (`@NotBlank`, `@Size`); os dois estoques obrigatórios e zero ou mais (`@NotNull`, `@PositiveOrZero`) |
| `ProdutoResponseDTO` | saída | `id`, `nome`, `estoqueAtual`, `estoqueMinimo`, `disponivelParaEntrega`, `abaixoDoMinimo` | Nenhuma |
| `VeiculoRequestDTO` | entrada | `descricao`, `motorista`, `rota` | os três obrigatórios (`@NotBlank`); até 50, 50 e 100 caracteres (`@Size`) |
| `VeiculoResponseDTO` | saída | `id`, `descricao`, `motorista`, `rota`, `status` | Nenhuma |
| `EntregaRequestDTO` | entrada | `cliente`, `destino`, `quantidade`, `previsao`, `produtoId`, `veiculoId` | `cliente` e `destino` obrigatórios (`@NotBlank`, `@Size`); `quantidade` maior que zero (`@Positive`); `previsao` hoje ou depois (`@FutureOrPresent`); todos obrigatórios |
| `EntregaResponseDTO` | saída | `id`, `cliente`, `destino`, `quantidade`, `status`, `previsao`, `dataConclusao`, `produto` (objeto), `veiculo` (objeto) | Nenhuma |

## Exemplos

### `POST /produtos`

Requisição:

```json
{
  "nome": "Palete de água mineral 500 ml",
  "estoqueAtual": 120,
  "estoqueMinimo": 20
}
```

Resposta, `201 Created`:

```json
{
  "id": 1,
  "nome": "Palete de água mineral 500 ml",
  "estoqueAtual": 120,
  "estoqueMinimo": 20,
  "disponivelParaEntrega": 100,
  "abaixoDoMinimo": false
}
```

### `POST /veiculos`

Requisição:

```json
{
  "descricao": "Caminhão VW Delivery - ABC1D23",
  "motorista": "Carlos Souza",
  "rota": "Criciúma - Tubarão - Laguna"
}
```

Resposta, `201 Created`:

```json
{
  "id": 1,
  "descricao": "Caminhão VW Delivery - ABC1D23",
  "motorista": "Carlos Souza",
  "rota": "Criciúma - Tubarão - Laguna",
  "status": "DISPONIVEL"
}
```

### `POST /entregas`

Requisição:

```json
{
  "cliente": "Mercado Bom Preço",
  "destino": "Rua Henrique Lage, 500 - Criciúma",
  "quantidade": 30,
  "previsao": "2026-11-03",
  "produtoId": 1,
  "veiculoId": 1
}
```

Resposta, `201 Created`. O estoque do produto caiu de 120 para 90 e o veículo saiu em rota:

```json
{
  "id": 1,
  "cliente": "Mercado Bom Preço",
  "destino": "Rua Henrique Lage, 500 - Criciúma",
  "quantidade": 30,
  "status": "EM_TRANSITO",
  "previsao": "2026-11-03",
  "dataConclusao": null,
  "produto": {
    "id": 1,
    "nome": "Palete de água mineral 500 ml",
    "estoqueAtual": 90,
    "estoqueMinimo": 20,
    "disponivelParaEntrega": 70,
    "abaixoDoMinimo": false
  },
  "veiculo": {
    "id": 1,
    "descricao": "Caminhão VW Delivery - ABC1D23",
    "motorista": "Carlos Souza",
    "rota": "Criciúma - Tubarão - Laguna",
    "status": "EM_ROTA"
  }
}
```

## Erros

Todo erro sai no mesmo formato. Este é o `404` de `GET /produtos/999`:

```json
{
  "status": 404,
  "erro": "Not Found",
  "mensagem": "Produto não encontrado: 999",
  "caminho": "/produtos/999",
  "timestamp": "2026-10-25T19:30:00",
  "campos": []
}
```

No `400` de validação, a lista `campos` traz um item por campo inválido: `{"campo": "quantidade", "mensagem": "Quantidade precisa ser maior que zero"}`.

| Status | Quando acontece neste projeto |
|---|---|
| `400` | JSON ilegível, data fora do formato `yyyy-MM-dd` ou campo inválido no DTO de entrada |
| `404` | Id de produto, veículo ou entrega que não existe (inclusive `produtoId` e `veiculoId` no `POST /entregas`) |
| `400` | Regra de negócio violada: a `mensagem` começa com a regra que barrou o pedido (`R1:` a `R4:`) |
