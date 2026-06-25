# Blockchain em Java

Uma implementação simples de blockchain em linha de comando desenvolvida em Java, com persistência em JSON e suporte a múltiplos algoritmos de hash.

## Funcionalidades

- **Adicionar blocos** com conteúdo personalizado
- **Encadeamento por hash** — cada bloco armazena o hash do anterior, formando uma corrente
- **Verificação de integridade** — detecta violações diretas em blocos e quebras de vínculo entre blocos
- **Persistência** em arquivo JSON (via Gson) ou TXT
- **Algoritmos de hash intercambiáveis** — SHA-256 e MD5 (via Apache Commons Codec)
- **Menu interativo** no terminal

## Estrutura do Projeto

```
src/main/java/
├── Main.java                          # Ponto de entrada da aplicação
├── models/
│   └── Bloco.java                     # Modelo do bloco (id, conteúdo, hashes, timestamp)
├── service/
│   ├── AlgoritmoHash.java             # Interface para algoritmos de hash
│   └── BlockChainService.java         # Lógica central da blockchain
├── repository/
│   └── ArmazenadorRepository.java     # Interface para persistência
├── infra/
│   ├── security/
│   │   ├── GeradorSHA256.java         # Implementação SHA-256
│   │   └── GeradorMD5.java            # Implementação MD5
│   └── persistence/
│       ├── ArquivoJsonRepository.java # Persistência em JSON
│       └── ArquivoTxtRepository.java  # Persistência em TXT
└── terminal/
    └── BlockChainConsole.java         # Interface de linha de comando
```

## Pré-requisitos

- Java 21+
- Gradle (ou use o wrapper `gradlew`)

## Como Executar

```bash
# Compilar e executar
./gradlew build
./gradlew run
```

Ou diretamente:

```bash
./gradlew build
java -cp build/libs/blockchain-1.0-SNAPSHOT.jar Main
```

## Como Usar

Ao executar, um menu interativo é exibido:

```
===== MENU BLOCKCHAIN =====
1. Adicionar Novo Bloco
2. Visualizar Blockchain
3. Verificar Integridade da Rede
0. Sair
```

- **1** — Insere um novo bloco com o conteúdo informado
- **2** — Exibe todos os blocos da corrente
- **3** — Verifica se a corrente foi violada (hash inválido ou quebra de encadeamento)
- **0** — Encerra o programa

## Configuração

Em `Main.java` é possível alternar entre:

- **Persistência**: `ArquivoJsonRepository` (JSON) ou `ArquivoTxtRepository` (TXT)
- **Algoritmo de hash**: `GeradorSHA256` ou `GeradorMD5`

```java
// Exemplo: usar TXT + SHA-256
ArmazenadorRepository repository = new ArquivoTxtRepository("blockchain.txt");
AlgoritmoHash hash = new GeradorSHA256();
```

## Tecnologias

- Java 21
- Gradle
- Apache Commons Codec (hashing)
- Gson (serialização JSON)
