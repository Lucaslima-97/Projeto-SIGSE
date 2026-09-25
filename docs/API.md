# API SIGSE — como acessar e testar no Postman

Base URL: `http://localhost:8080`

A aplicação precisa estar rodando e o MySQL ligado. No Postman, use **Body → raw → JSON** nas requisições POST/PUT.

Não há login ainda: qualquer cliente na rede local pode chamar a API.

## Ordem sugerida de teste

Cadastre turma → aluno → (opcional) responsável e vínculo → cartão → leitura na portaria → fila → saída no portão.

### 1. Criar turma

`POST /api/turmas`

```json
{
  "nome": "3º Ano B",
  "serie": 3,
  "turno": "TARDE",
  "anoLetivo": 2026
}
```

`serie` deve ser de 1 a 5. Guarde o `id` da resposta.

`GET /api/turmas` lista todas. `GET /api/turmas/{id}` e `PUT /api/turmas/{id}` consultam e atualizam.

### 2. Criar aluno

`POST /api/alunos`

```json
{
  "nome": "Lucas Silva",
  "email": null,
  "cpf": null,
  "dataNascimento": "2016-03-12",
  "matricula": "2026001",
  "fotoUrl": null,
  "ativo": true,
  "turmaId": 1
}
```

Filtros: `GET /api/alunos?nome=Lucas` ou `GET /api/alunos?turmaId=1`.

### 3. Responsável e vínculo (opcional)

`POST /api/responsaveis`

```json
{
  "nome": "Maria Silva",
  "telefone": "11999999999",
  "parentesco": "MAE"
}
```

`POST /api/responsaveis/vinculos`

```json
{
  "alunoId": 1,
  "responsavelId": 1
}
```

### 4. Cadastrar cartão RFID

`POST /api/cartoes`

Cartão do aluno:

```json
{
  "uid": "AABBCCDD",
  "tipo": "ALUNO",
  "ativo": true,
  "alunoId": 1
}
```

Cartão do responsável:

```json
{
  "uid": "11223344",
  "tipo": "RESPONSAVEL",
  "ativo": true,
  "responsavelId": 1
}
```

O `uid` é o código que o leitor Mifare envia. No Postman, use qualquer string para simular.

### 5. Leitura na portaria (chamada silenciosa)

`POST /api/leituras`

```json
{
  "uid": "AABBCCDD",
  "ponto": "PORTARIA"
}
```

Se o cartão for de responsável com vários filhos:

```json
{
  "uid": "11223344",
  "ponto": "PORTARIA",
  "alunoId": 1
}
```

Isso coloca o aluno na fila e publica `INSERIR` no WebSocket.

### 6. Ver a fila do pátio

`GET /api/chamadas`

Retorna só chamadas com status `AGUARDANDO`. O `nomeExibicao` já vem no formato LGPD (`Lucas S.`).

### 7. Liberação manual (esqueceu o cartão)

`POST /api/chamadas/manual`

```json
{
  "matricula": "2026001"
}
```

Ou `{ "alunoId": 1 }`.

### 8. Confirmar saída no portão

Mesmo endpoint de leitura, com outro ponto:

`POST /api/leituras`

```json
{
  "uid": "AABBCCDD",
  "ponto": "PORTAO"
}
```

Alternativa sem RFID: `POST /api/chamadas/{id}/saida` (use o `id` da chamada).

A TV recebe `REMOVER` e o aluno some da fila.

### 9. Histórico

`GET /api/eventos/aluno/{alunoId}`

Registra chamada na portaria, saída no portão e liberação manual.

## Mapa rápido

| Método | Caminho | Uso |
|--------|---------|-----|
| POST | `/api/turmas` | Cadastrar turma |
| GET | `/api/turmas` | Listar turmas |
| PUT | `/api/turmas/{id}` | Atualizar turma |
| POST | `/api/alunos` | Cadastrar aluno |
| GET | `/api/alunos` | Listar / buscar |
| PUT | `/api/alunos/{id}` | Atualizar aluno |
| POST | `/api/responsaveis` | Cadastrar responsável |
| POST | `/api/responsaveis/vinculos` | Ligar aluno ao responsável |
| POST | `/api/cartoes` | Vincular UID RFID |
| POST | `/api/leituras` | Leitor na portaria ou portão |
| GET | `/api/chamadas` | Fila ativa do pátio |
| POST | `/api/chamadas/manual` | Chamada sem cartão |
| POST | `/api/chamadas/{id}/saida` | Confirmar saída pelo id |
| GET | `/api/eventos/aluno/{id}` | Relatório de acesso |

## Erros comuns

| Situação | O que conferir |
|----------|----------------|
| Connection refused | A aplicação não está rodando na porta 8080 |
| Erro de banco / Communications link failure | MySQL parado ou senha diferente do `application.properties` |
| 404 Cartão RFID não encontrado | UID diferente do cadastrado (o sistema grava em maiúsculas) |
| 422 Cartão RFID inativo | `ativo` do cartão está `false` |
| 422 Responsável com mais de um aluno | Envie `alunoId` na leitura |

## WebSocket (não é REST)

No Postman, o fluxo do TCC se testa pelas URLs acima. O painel da TV usa:

- endpoint STOMP: `/ws`
- tópico: `/topic/patio`

Detalhes no [README principal](../README.md).
