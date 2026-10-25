# Logística da Cadeia de Suprimentos

API REST que liga o estoque, a frota e o rastreamento das entregas de uma distribuidora: cada entrega registrada baixa o estoque do produto e ocupa um veículo, e o sistema impede entregas que furariam o estoque mínimo ou usariam um caminhão que já está na rua. É feita para a equipe de logística que hoje controla essas três coisas em planilhas separadas.

Projeto Final da disciplina de Backend (Engenharia de Software, SATC). **Etapa 1: arquitetura do backend.**

## Índice

- [Integrantes](#integrantes)
- [O problema](#o-problema)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Entidades](#entidades)
- [Rotas](#rotas)
- [Como executar](#como-executar)
- [Situação do projeto](#situação-do-projeto)
- [Documentação](#documentação)

## Integrantes

| Nome | GitHub |
|---|---|
| Andrey Henrique João | [@andreyhenrique1](https://github.com/andreyhenrique1) |
| Davi Steiner Freitas | [@Steiner7](https://github.com/Steiner7) |
| Cauã Savi da Silva Rosa | [@CauaSavi](https://github.com/CauaSavi) |

## O problema

**Tema sorteado:** Logística e Cadeia de Suprimentos

Numa distribuidora pequena, o estoque, a frota e as entregas ficam em controles separados. O pessoal do depósito não sabe o que já saiu, o despacho manda para a rua um caminhão que ainda não voltou, e o estoque de segurança some sem ninguém perceber até faltar produto. O sistema junta as três coisas: registrar uma entrega já baixa o estoque e ocupa o veículo, e concluir ou cancelar a entrega devolve o veículo (e, no cancelamento, o produto) automaticamente.

**Quem usa o sistema:**

| Quem | O que faz no sistema |
|---|---|
| Almoxarife | Cadastra os produtos, com o estoque atual e o estoque mínimo, e acompanha o que está abaixo do mínimo |
| Gestor de frota | Cadastra os veículos, com o motorista e a rota, e vê quais estão disponíveis ou em rota |
| Despachante | Registra as entregas e marca cada uma como concluída ou cancelada |

**Regras de negócio.**

| # | Regra | Onde está no código | Rota que usa |
|---|---|---|---|
| R1 | Uma entrega não pode deixar o estoque do produto abaixo do estoque mínimo; quando passa, a quantidade sai do estoque | `EntregaService.criar` | `POST /entregas` |
| R2 | Só um veículo `DISPONIVEL` pode receber uma entrega; ao receber, ele passa a `EM_ROTA` | `EntregaService.criar` | `POST /entregas` |
| R3 | Só uma entrega `EM_TRANSITO` pode ser concluída ou cancelada; ao concluir ou cancelar, o veículo volta a `DISPONIVEL` | `EntregaService.concluir`, `EntregaService.cancelar` | `PUT /entregas/{id}/concluir`, `PUT /entregas/{id}/cancelar` |
| R4 | Cancelar uma entrega devolve a quantidade ao estoque do produto | `EntregaService.cancelar` | `PUT /entregas/{id}/cancelar` |

**Fora do escopo:** front-end, login e perfis de usuário, entregas com mais de um produto, cálculo de rota e frete, e rastreamento por GPS. O status do veículo também não tem manutenção: ele só alterna entre disponível e em rota.

## Tecnologias

| O quê | Qual |
|---|---|
| Linguagem | Java 25 |
| Framework | Spring Boot 3.5 (Web, Validation, Data JPA) |
| Banco de dados | PostgreSQL 16, em Docker |
| Migrations | Flyway |
| Documentação da API | springdoc-openapi (Swagger UI) |

## Arquitetura

A API é organizada em camadas. Uma requisição entra pelo Controller, passa pelo Service, que aplica as regras de negócio, e chega ao banco pelo Repository; a resposta faz o caminho de volta. O Mapper converte DTO em entidade e entidade em DTO.

**Mapa da estrutura.**

| Entidade | Migration | Model | Repository | DTOs | Mapper | Service | Controller |
|---|---|---|---|---|---|---|---|
| Produto | `V1__criar_produtos.sql`, pronta | `Produto`, pronta | `ProdutoRepository`, pronto | `ProdutoRequestDTO`, `ProdutoResponseDTO`, prontos | `ProdutoMapper`, implementado | `ProdutoService`, implementado | `ProdutoController`, em parte (falta `PUT` e `DELETE`) |
| Veiculo | `V2__criar_veiculos.sql`, pronta | `Veiculo`, `StatusVeiculo`, prontas | `VeiculoRepository`, pronto | `VeiculoRequestDTO`, `VeiculoResponseDTO`, prontos | `VeiculoMapper`, implementado | `VeiculoService`, implementado | `VeiculoController`, em parte (falta `PUT` e `DELETE`) |
| Entrega | `V3__criar_entregas.sql`, pronta | `Entrega`, `StatusEntrega`, prontas | `EntregaRepository`, pronto | `EntregaRequestDTO`, `EntregaResponseDTO`, prontos | `EntregaMapper`, implementado | `EntregaService`, implementado (R1 a R4) | `EntregaController`, implementado |

## Entidades

| Entidade | O que representa | Como se relaciona |
|---|---|---|
| Produto | Um produto do estoque, com a quantidade atual e o estoque mínimo de segurança | Sai em várias entregas |
| Veiculo | Um veículo da frota, com o motorista, a rota e se está disponível ou em rota | Leva várias entregas, uma de cada vez |
| Entrega | Uma entrega rastreada: o produto e a quantidade, o cliente, o veículo e a situação | Pertence a um produto e a um veículo |

Atributos, tipos e o diagrama das tabelas estão em [docs/modelo-de-dominio.md](docs/modelo-de-dominio.md).

## Rotas

### As que já funcionam

| Verbo | Caminho | O que faz | Regra | Sucesso | Erros |
|---|---|---|---|---|---|
| `POST` | `/produtos` | Cadastra um produto | Nenhuma | `201` | `400` |
| `GET` | `/produtos` | Lista os produtos | Nenhuma | `200` | Nenhum |
| `GET` | `/produtos/{id}` | Busca um produto | Nenhuma | `200` | `404` |
| `POST` | `/veiculos` | Cadastra um veículo | Nenhuma | `201` | `400` |
| `GET` | `/veiculos` | Lista os veículos | Nenhuma | `200` | Nenhum |
| `GET` | `/veiculos/{id}` | Busca um veículo | Nenhuma | `200` | `404` |
| `POST` | `/entregas` | Registra uma entrega | R1 e R2 | `201` | `400`, `404` |
| `GET` | `/entregas` | Lista as entregas | Nenhuma | `200` | Nenhum |
| `GET` | `/entregas/{id}` | Busca uma entrega | Nenhuma | `200` | `404` |
| `PUT` | `/entregas/{id}/concluir` | Conclui a entrega e libera o veículo | R3 | `200` | `400`, `404` |
| `PUT` | `/entregas/{id}/cancelar` | Cancela a entrega, devolve o estoque e libera o veículo | R3 e R4 | `200` | `400`, `404` |

Exemplo de requisição:

```http
POST /entregas
Content-Type: application/json

{
  "cliente": "Mercado Bom Preço",
  "destino": "Rua Henrique Lage, 500 - Criciúma",
  "quantidade": 30,
  "previsao": "2026-11-03",
  "produtoId": 1,
  "veiculoId": 1
}
```

Resposta, `201 Created` (o estoque caiu de 120 para 90 e o veículo saiu em rota):

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

### Erros

Todo erro sai no mesmo formato. Este é o `400` de mandar o mesmo veículo numa segunda entrega antes de a primeira terminar:

```json
{
  "status": 400,
  "erro": "Bad Request",
  "mensagem": "R2: o veículo 1 já está em rota e não pode receber outra entrega",
  "caminho": "/entregas",
  "timestamp": "2026-10-25T19:30:00",
  "campos": []
}
```

Um campo inválido também devolve `400`, com a lista `campos` dizendo qual campo corrigir. Um id que não existe devolve `404`.

### As planejadas

Estão no contrato, mas ainda não respondem.

| Recurso | O que vai fazer |
|---|---|
| `PUT /produtos/{id}`, `DELETE /produtos/{id}` | Corrigir o cadastro de um produto e remover um produto que nunca saiu em entrega |
| `PUT /veiculos/{id}`, `DELETE /veiculos/{id}` | Trocar motorista e rota de um veículo e remover um veículo que nunca fez entrega |

O contrato completo, com todas as rotas e os DTOs, está em [docs/contrato-da-api.md](docs/contrato-da-api.md).

## Como executar

Pré-requisitos: JDK 25, Docker Desktop aberto e Git.

```bash
git clone https://github.com/Steiner7/logistica-entregas-api.git
cd logistica-entregas-api

docker compose up -d          # sobe o PostgreSQL na porta 5437
./mvnw spring-boot:run        # Windows: .\mvnw.cmd spring-boot:run
```

Com a API no ar:

| Endereço | O que é |
|---|---|
| <http://localhost:8080/swagger-ui.html> | Swagger UI: as rotas, com **Try it out** para enviar requisições |
| <http://localhost:8080/v3/api-docs> | O contrato OpenAPI em JSON |

Para testar as rotas, use o Swagger UI ou o **Postman**. No Postman, importe todas as rotas de uma vez: **Import** → **Link** → `http://localhost:8080/v3/api-docs`.

Roteiro rápido para ver as regras funcionando: cadastre um produto (`POST /produtos`) e um veículo (`POST /veiculos`), registre uma entrega com os dois (`POST /entregas`) e tente registrar outra com o mesmo veículo (barrada pela R2) ou com uma quantidade que fure o estoque mínimo (barrada pela R1). Depois conclua ou cancele a primeira entrega (`PUT /entregas/1/concluir` ou `/cancelar`) e confira o produto e o veículo de novo.

Para parar: `Ctrl+C` na API e `docker compose stop` no banco. Para apagar o banco e rodar as migrations do zero: `docker compose down -v` e suba de novo.

## Situação do projeto

| Parte | Situação |
|---|---|
| Desenho | Completo: problema e regras neste README, modelo e contrato em `docs/` |
| Estrutura | As 3 entidades têm migration, entidade, Repository, DTOs, Mapper, Service e Controller |
| API e regras | `POST`, `GET` e `GET /{id}` funcionam para produtos, veículos e entregas; R1, R2, R3 e R4 estão implementadas |

**Ainda não funciona:** `PUT` e `DELETE` de produtos e de veículos.

## Documentação

| Documento | O que tem |
|---|---|
| [Modelo de domínio](docs/modelo-de-dominio.md) | Entidades, relacionamentos, diagrama das tabelas, migrations |
| [Contrato da API](docs/contrato-da-api.md) | Todas as rotas, os DTOs, exemplos de JSON e erros |
| [Autoavaliação](docs/autoavaliacao.md) | Os critérios da revisão do código, marcados pelo grupo |
