# Autoavaliação

Os 10 critérios que serão avaliados na revisão do código, descritos em `Aula 10-11/AVALIACAO.md`, no repositório da disciplina. Para cada um, a marca do grupo e onde está a evidência.

- ✅ atende
- ◐ atende em parte (diga o que falta)
- ❌ não atende

| Critério | Marca | Evidência |
|---|---|---|
| P1 A API sobe seguindo o README | ✅ | `README.md`, seção "Como executar" |
| P2 Cada classe no pacote da sua camada | ✅ | `src/main/java/api/` |
| P3 Migrations e `@Entity` de todas as tabelas, e a API sobe | ✅ | `V1__criar_produtos.sql`, `V2__criar_veiculos.sql`, `V3__criar_entregas.sql`; `Produto`, `Veiculo`, `Entrega` |
| P4 DTOs de entrada e de saída de todas as entidades | ✅ | `ProdutoRequestDTO`/`ProdutoResponseDTO`, `VeiculoRequestDTO`/`VeiculoResponseDTO`, `EntregaRequestDTO`/`EntregaResponseDTO` |
| P5 Repository, Mapper, Service e Controller de todas, com o comentário no topo, e o mapa da estrutura | ✅ | `ProdutoRepository`, `VeiculoRepository`, `EntregaRepository`, os três Mappers, Services e Controllers, e `README.md`, seção "Arquitetura" |
| P6 API simples: `POST`, `GET` e `GET /{id}`, com `400` e `404` | ✅ | `ProdutoController` (e também `VeiculoController` e `EntregaController`) |
| P7 No mínimo 3 regras de negócio implementadas, com `400` quando violadas | ✅ | `EntregaService`: R1 e R2 em `POST /entregas`, R3 em `PUT /entregas/{id}/concluir` e `/cancelar`, R4 em `PUT /entregas/{id}/cancelar` |
| P8 As regras atendem ao problema e ao tema | ✅ | `README.md`, seção "O problema" |
| P9 Código igual ao desenho | ✅ | `docs/modelo-de-dominio.md` e `docs/contrato-da-api.md` |
| P10 README completo e bem escrito | ✅ | `README.md`, conferido no GitHub |

**Pontos que o grupo espera no projeto:** 4,0

Acreditamos que todos os critérios foram atendidos; deixamos 4,0 por cautela, para a revisão do professor confirmar.

A apresentação vale os outros 5,0 pontos: são 3 perguntas, feitas na Aula 11.
