# SIGSE — Sistema Integrado de Identificação e Chamada Silenciosa

Back-end do TCC **TechFy**: controle de acesso escolar com RFID e chamada visual no pátio, sem microfone nem caixa de som.

O objetivo é organizar a saída do **1º ao 5º ano** de forma silenciosa. O responsável (ou a inspetora) aproxima o cartão na portaria; o nome do aluno aparece na TV do pátio; a criança vai até o portão; a tag confirma a saída e some da tela.

## Como o sistema funciona

```
[ Cartão RFID ] → [ Leitor / ESP32 na portaria ]
                           │
                      HTTP (API)
                           ▼
              [ Servidor SIGSE + MySQL ]
                           │
                    WebSocket STOMP
                           ▼
              [ Painel digital no pátio ]
```

1. A secretaria cadastra turma, aluno, responsável e o UID do cartão RFID.
2. Na portaria, o leitor envia o UID para `POST /api/leituras` com `ponto: PORTARIA`.
3. O servidor valida o cartão, registra o horário e coloca o aluno na fila de chamada.
4. O painel da TV, conectado em `/ws` e inscrito em `/topic/patio`, recebe o evento `INSERIR`.
5. A tela mostra só o necessário pela LGPD: **primeiro nome + inicial do sobrenome + turma** (ex.: `Lucas S. - 3º Ano B`).
6. No portão, uma nova leitura com `ponto: PORTAO` confirma a saída, grava o evento e envia `REMOVER` para a TV.
7. Se o cartão foi esquecido, a inspetora usa `POST /api/chamadas/manual` com matrícula ou `alunoId`.

O mesmo cartão pode ser do **aluno** ou do **responsável**. Cartão de responsável com mais de um filho precisa de `alunoId` na leitura.

## O que este repositório é hoje

Este projeto é o **servidor central** (API REST + WebSocket + JPA/MySQL). Ainda não inclui:

- firmware do ESP32 / leitor
- tela React do pátio
- painel web da secretaria
- login JWT e perfis (admin, inspetor, professor)

## Stack

| Camada        | Tecnologia                          |
|---------------|-------------------------------------|
| Linguagem     | Java 21                             |
| Framework     | Spring Boot 4.1.1                   |
| API           | REST (`spring-boot-starter-webmvc`) |
| Tempo real    | WebSocket + STOMP                   |
| Persistência  | Spring Data JPA + MySQL             |
| Validação     | Bean Validation                     |

## Como o back-end está organizado

```
src/main/java/br/com/projetosigse/
  config/        CORS e WebSocket
  controller/    Endpoints REST
  dto/           Contratos JSON
  exception/     Erros da API
  model/         Entidades JPA
  repository/    Acesso ao banco
  service/       Regras (leitura, fila, cadastro)
  util/          Nome no painel (LGPD)
```

Arquivos-chave do tempo real:

- `config/WebSocketConfig.java` — endpoint `/ws`
- `service/PatioNotificador.java` — envio para `/topic/patio`
- `service/ChamadaService.java` — dispara `INSERIR` / `REMOVER`

## Como rodar

1. Instale **JDK 21+** e **MySQL**.
2. Ajuste usuário e senha em `src/main/resources/application.properties`. O banco `sigse` é criado automaticamente.
3. Suba a aplicação (`ProjetoSigseApplication`) no IntelliJ/Cursor ou:

```bash
./mvnw spring-boot:run
```

No Windows:

```bat
mvnw.cmd spring-boot:run
```

4. A API fica em `http://localhost:8080`.
5. Teste os endpoints no Postman. Passo a passo em [docs/API.md](docs/API.md).

## WebSocket do pátio

- Conexão: `ws://localhost:8080/ws` (STOMP; SockJS também está habilitado)
- Tópico: `/topic/patio`
- Eventos:

```json
{
  "acao": "INSERIR",
  "chamada": {
    "id": 1,
    "alunoId": 1,
    "nomeExibicao": "Lucas S.",
    "turma": "3º Ano B",
    "fotoUrl": null,
    "status": "AGUARDANDO"
  }
}
```

`acao` pode ser `INSERIR` ou `REMOVER`. Quem entra depois na TV também pode consultar `GET /api/chamadas` para montar a fila atual.

## Privacidade (LGPD)

O painel **não** exibe nome completo, CPF nem dados do responsável. Só primeiro nome, inicial do sobrenome, turma e foto opcional.

## Próximos passos

- Autenticação e perfis de acesso
- Interface da secretaria / inspetoria
- Painel visual do pátio
- Integração com o leitor RFID (ESP32)
