# armazem-aventureiro

## 1. A Forja & o Frasco

Em **Valed'água**, cidade comercial costeira por onde passam aventureiros de todas as origens, existe um armazém conhecido por abastecer desde o viajante iniciante até o explorador veterano: **A Forja & o Frasco**.

Por trás do balcão está **Nilo Ferrabrasa**, um velho amante de histórias de aventura que, na juventude, partiu em jornada — mas voltou cedo demais, depois de uma flechada no joelho. Hoje, Nilo leva outros ao tesouro que não pôde possuir. Seus conselhos são levados a sério pelo entusiasmo e pelo conhecimento que acumulou sobre os mais variados tópicos do mundo.

O armazém reúne armas, armaduras, poções, acessórios mágicos e relíquias, organizados em **cinco classificações de raridade**: comum, incomum, raro, épico e lendário. Cada mercadoria tem seu próprio registro, com nome, raridade, preço e nível mínimo necessário para utilizá-la.

Para não perder o controle do estoque, Nilo mantém cuidadosamente o **livro de registros do armazém** — e é ele que esta API representa. Aqui, mercadorias podem ser cadastradas, consultadas, atualizadas e retiradas de circulação, sempre com as regras do Armazém.

## 2. Como compilar e rodar

### Requisitos

- **Java 17** ou superior.
- **Maven** — não precisa instalar: o projeto inclui o **Maven Wrapper** (`mvnw` / `mvnw.cmd`).

### Subir a aplicação

Na raiz do projeto:

**Windows (PowerShell ou CMD):**
```powershell
.\mvnw.cmd spring-boot:run
```

**macOS / Linux:**
```bash
./mvnw spring-boot:run
```

A aplicação sobe em **http://localhost:8080**. A primeira execução demora mais porque o Maven baixa as dependências.

### Console do H2

O banco é **em memória** (H2), então os dados são recriados a cada inicialização. O console fica disponível em:

- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:armazem`
- **User Name:** `sa`
- **Password:** (em branco)

### Rodar os testes

**Windows:**
```powershell
.\mvnw.cmd clean test
```

**macOS / Linux:**
```bash
./mvnw clean test
```

A saída deve terminar com **BUILD SUCCESS**.

## 3. Entidade e campos

A entidade `Item` representa uma mercadoria do Armazém. A tabela no banco se chama `item`.

| Campo | Tipo | Obrigatório | Regras |
|---|---|---|---|
| `id` | `Long` | Gerado | Chave primária, auto-incrementada |
| `nome` | `String` | Sim | Até 100 caracteres, único |
| `raridade` | `Enum` (`Raridade`) | Sim | `COMUM`, `INCOMUM`, `RARO`, `EPICO` ou `LENDARIO` |
| `preco` | `BigDecimal` | Sim | Maior ou igual a zero |
| `nivelMinimo` | `Integer` | Não | Entre 1 e 100 |

## 4. Rotas da API

| Método | Rota | Descrição | Status |
|---|---|---|---|
| `GET` | `/api/itens` | Lista todos os itens | `200` |
| `GET` | `/api/itens/{id}` | Busca um item por id | `200`, `404` |
| `POST` | `/api/itens` | Cria um item | `201`, `400`, `409` |
| `PUT` | `/api/itens/{id}` | Atualiza um item por completo | `200`, `400`, `404`, `409` |
| `DELETE` | `/api/itens/{id}` | Remove um item | `204`, `404` |

## 5. Exemplos de JSON

### POST `/api/itens` — criar (201)

Requisição:
```json
{
  "nome": "Espada Flamejante",
  "raridade": "EPICO",
  "preco": 1250.00,
  "nivelMinimo": 20
}
```

Resposta (201):
```json
{
  "id": 21,
  "nome": "Espada Flamejante",
  "raridade": "EPICO",
  "preco": 1250.00,
  "nivelMinimo": 20
}
```

### GET `/api/itens` — listar (200)

Resposta (200): lista com os 20 itens do catálogo inicial. Exemplo de um item:
```json
{
  "id": 1,
  "nome": "Poção de Cura Menor",
  "raridade": "COMUM",
  "preco": 25.00,
  "nivelMinimo": 1
}
```

### GET `/api/itens/{id}` — buscar por id (200, 404)

Resposta (200): um item, como no exemplo acima.

Resposta (404):
```json
{
  "status": 404,
  "erro": "Item não encontrado",
  "mensagens": [
    "Nilo procurou nas prateleiras, mas não encontrou esse item no catálogo."
  ]
}
```

### PUT `/api/itens/{id}` — atualizar (200, 400, 404, 409)

Requisição:
```json
{
  "nome": "Espada Flamejante Reforçada",
  "raridade": "LENDARIO",
  "preco": 1500.00,
  "nivelMinimo": 25
}
```

Resposta (200): o item atualizado, com o mesmo `id`.

### DELETE `/api/itens/{id}` — remover (204, 404)

Resposta (204): sem corpo.

Resposta (404):
```json
{
  "status": 404,
  "erro": "Item não encontrado",
  "mensagens": [
    "Nilo procurou nas prateleiras, mas não encontrou esse item no catálogo."
  ]
}
```

### Erro 400 — validação (400)

Requisição com `nome` vazio:
```json
{
  "nome": "",
  "raridade": "COMUM",
  "preco": 10.00,
  "nivelMinimo": 1
}
```

Resposta (400):
```json
{
  "status": 400,
  "erro": "Dados inválidos",
  "mensagens": [
    "Informe o nome do item"
  ]
}
```

### Erro 400 — raridade inexistente (400)

Requisição com `raridade` inválida:
```json
{
  "nome": "Item Teste",
  "raridade": "XYZ",
  "preco": 10.00,
  "nivelMinimo": 1
}
```

Resposta (400):
```json
{
  "status": 400,
  "erro": "Raridade Inválida",
  "mensagens": [
    "As raridades reconhecidas são: COMUM, INCOMUM, RARO, ÉPICO, LENDÁRIO"
  ]
}
```

### Erro 400 — JSON mal formado (400)

Requisição com JSON quebrado:
```
{"nome":
```

Resposta (400):
```json
{
  "status": 400,
  "erro": "JSON INVÁLIDO",
  "mensagens": [
    "Verifique a estrutura do JSON antes de entregá-lo"
  ]
}
```

### Erro 409 — nome duplicado (409)

Requisição com nome que já existe:
```json
{
  "nome": "Espada Flamejante",
  "raridade": "EPICO",
  "preco": 1250.00,
  "nivelMinimo": 20
}
```

Resposta (409):
```json
{
  "status": 409,
  "erro": "Nome duplicado",
  "mensagens": [
    "Esse item já está registrado no livro de Nilo: 'Espada Flamejante'. Cada mercadoria precisa de um nome exclusivo!"
  ]
}
```

## 6. Exemplos de cURL

> No Windows, use **`curl.exe`** (não `curl`, que ali é apelido de outro comando). Para o corpo JSON, grave-o num arquivo e use `--data-binary "@arquivo.json"`, fora da pasta do projeto.

### Listar itens

```powershell
curl.exe -i http://localhost:8080/api/itens
```

### Buscar item por id

```powershell
curl.exe -i http://localhost:8080/api/itens/1
```

### Criar item

Arquivo `criar.json`:
```json
{"nome":"Espada Flamejante","raridade":"EPICO","preco":1250.00,"nivelMinimo":20}
```

```powershell
curl.exe -i -X POST http://localhost:8080/api/itens -H "Content-Type: application/json" --data-binary "@criar.json"
```

### Atualizar item

Arquivo `atualizar.json`:
```json
{"nome":"Espada Flamejante Reforçada","raridade":"LENDARIO","preco":1500.00,"nivelMinimo":25}
```

```powershell
curl.exe -i -X PUT http://localhost:8080/api/itens/21 -H "Content-Type: application/json" --data-binary "@atualizar.json"
```

### Remover item

```powershell
curl.exe -i -X DELETE http://localhost:8080/api/itens/21
```

## 7. Evidências

As capturas de tela com as respostas de cada operação (incluindo os erros) estão na pasta [`evidencias/`](evidencias/).