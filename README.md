# Validador de Senha API

Uma API web robusta e bem estruturada para validar senhas de acordo com critérios de segurança rigorosos.

## 📋 Visão Geral

Este projeto implementa uma solução para o desafio backend da ITI Digital. A aplicação expõe uma API REST que valida senhas baseado em regras pré-definidas, seguindo princípios de Clean Code, SOLID e boas práticas de arquitetura.

## 🔐 Regras de Validação

Uma senha é considerada **válida** quando atende TODAS as seguintes condições:

- ✅ Mínimo de **9 caracteres**
- ✅ Contém pelo menos **1 dígito** (0-9)
- ✅ Contém pelo menos **1 letra minúscula** (a-z)
- ✅ Contém pelo menos **1 letra maiúscula** (A-Z)
- ✅ Contém pelo menos **1 caractere especial** (!@#$%^&*()-+)
- ✅ **Nenhum caractere repetido** no conjunto
- ✅ **Nenhum espaço em branco**

### Exemplos

```
✅ VÁLIDA:   "AbTp9!fok"
❌ INVÁLIDA: ""                  (vazia)
❌ INVÁLIDA: "aa"                (muito curta)
❌ INVÁLIDA: "ab"                (muito curta)
❌ INVÁLIDA: "AAAbbbCc"          (caracteres repetidos)
❌ INVÁLIDA: "AbTp9!foo"         (caractere 'o' repetido)
❌ INVÁLIDA: "AbTp9!foA"         (caractere 'A' repetido)
❌ INVÁLIDA: "AbTp9 fok"         (contém espaço em branco)
```

## 🚀 Como Executar

### Pré-requisitos

- **Java 17+**
- **Maven 3.6+**

### Instalação e Execução

1. **Clone ou navegue para o diretório do projeto:**
   ```bash
   cd C:\Users\igorm\Downloads\CaseSenha
   ```

2. **Compile o projeto:**
   ```bash
   mvn clean compile
   ```

3. **Execute os testes:**
   ```bash
   mvn test
   ```

4. **Inicie a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

A aplicação estará disponível em: `http://localhost:8080`

## 📡 API Endpoints

### Validar Senha

**POST** `/api/v1/senhas/validar`

#### Request

```json
{
  "senha": "AbTp9!fok"
}
```

#### Response (Válida)

```json
true
```

#### Response (Inválida)

```json
false
```

#### Response (Erro)

```json
{
  "status": 400,
  "mensagem": "Corpo da requisição inválido",
  "erro": "Required request body is missing",
  "dataHora": "2023-10-01T12:00:00",
  "caminho": "/api/v1/senhas/validar"
}
```

#### Exemplos com cURL

```bash
# Senha válida
curl -X POST http://localhost:8080/api/v1/senhas/validar \
  -H "Content-Type: application/json" \
  -d '{"senha":"AbTp9!fok"}'

# Senha inválida (muito curta)
curl -X POST http://localhost:8080/api/v1/senhas/validar \
  -H "Content-Type: application/json" \
  -d '{"senha":"abc"}'
```

## 🏗️ Arquitetura e Design

### Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/casesenha/
│   │   ├── AplicacaoValidadorSenha.java          # Classe principal Spring Boot
│   │   ├── config/
│   │   │   └── ConfiguracaoAplicacao.java        # Configuração de beans
│   │   ├── controller/
│   │   │   └── ControladorValidacaoSenha.java    # Endpoints REST
│   │   ├── dto/
│   │   │   ├── RequisicaoValidacaoSenha.java     # DTO de requisição
│   │   │   ├── RespostaErro.java                 # DTO de resposta de erro
│   │   │   └── RespostaValidacaoSenha.java       # DTO de resposta (não usado)
│   │   ├── exception/
│   │   │   └── ControladorExcecaoGlobal.java     # Tratamento global de exceções
│   │   ├── service/
│   │   │   └── ServicoValidacaoSenha.java        # Lógica de negócio
│   │   └── validator/
│   │       ├── ValidadorSenha.java               # Interface do validador
│   │       ├── ValidadorSenhaComposto.java       # Implementação composta
│   │       └── regra/
│   │           ├── ExcecaoRegraValidacao.java     # Exceção customizada
│   │           ├── RegraValidacao.java           # Interface de regra
│   │           └── impl/
│   │               ├── RegraComCaractereEspecial.java
│   │               ├── RegraComDigito.java
│   │               ├── RegraComLetraMaiuscula.java
│   │               ├── RegraComLetraMinuscula.java
│   │               ├── RegraSemCaracteresRepetidos.java
│   │               ├── RegraSemEspacosEmBranco.java
│   │               └── RegraTamanhoMinimo.java
│   └── resources/
│       └── application.properties                 # Configurações
└── test/
    └── java/com/casesenha/
        ├── controller/
        │   └── ControladorValidacaoSenhaIntegracaoTest.java
        ├── service/
        │   └── ServicoValidacaoSenhaTest.java
        ├── validator/
        │   ├── ValidadorSenhaCompostoTest.java
        │   └── regra/
        │       └── impl/
        │           └── RegrasValidacaoTest.java
        └── resources/
            └── application.properties
```

### Decisões de Design

#### 1. **Padrão Strategy com Interface e Regras Compostas**
   - **Decisão:** Usar uma interface `ValidadorSenha` com implementação `ValidadorSenhaComposto` que combina múltiplas `RegraValidacao`
   - **Racional:** Permite trocar facilmente a estratégia de validação sem afetar o resto da aplicação. Facilita adição/remoção de regras individualmente. Favorece extensibilidade e testabilidade.
   - **SOLID:** Dependency Inversion Principle (DIP) - depender de abstrações, não de implementações concretas

#### 2. **Separação de Camadas**
   - **Controller:** Apenas coordena requisições HTTP
   - **Service:** Contém lógica de negócio e orquestra o validador
   - **Validator:** Responsável unicamente pela validação
   - **DTO:** Encapsula dados de entrada/saída
   - **Racional:** Facilita manutenção, testes e evolução do código
   - **SOLID:** Single Responsibility Principle (SRP)

#### 3. **Injeção de Dependência via Constructor**
   - **Decisão:** Usar injeção de dependência no construtor em vez de `@Autowired` em atributos
   - **Racional:** Torna dependências explícitas, facilita testes unitários e evita efeitos colaterais
   - **SOLID:** Dependency Inversion Principle

#### 4. **Regras de Validação Individuais**
   - **Decisão:** Quebrar a lógica de validação em regras menores e independentes
   - **Racional:** Cada regra tem uma responsabilidade clara, facilitando compreensão, manutenção e testes
   - **SOLID:** Single Responsibility Principle

#### 5. **DTOs para Request/Response**
   - **Decisão:** Criar DTOs específicos em vez de usar String diretamente
   - **Racional:** Facilita expansão futura (ex: adicionar mais campos), melhora documentação e permite validação de entrada

#### 6. **Versionamento de API**
   - **Decisão:** Usar `/api/v1/` no endpoint
   - **Racional:** Permite evoluir a API sem quebrar clientes existentes

#### 7. **Tratamento Global de Exceções**
   - **Decisão:** Usar `@RestControllerAdvice` para tratamento centralizado de erros
   - **Racional:** Padroniza respostas de erro e evita duplicação de código

#### 8. **Spring Boot com Maven**
   - **Decisão:** Usar Spring Boot e Maven
   - **Racional:** Framework maduro e com excelente suporte para criar APIs REST em Java. Maven é o padrão da indústria.

### Princípios SOLID Aplicados

- **S**RP: Cada classe tem uma única responsabilidade
- **O**CP: Aberto para extensão (novas implementações do `ValidadorSenha` ou novas `RegraValidacao`), fechado para modificação
- **L**SP: Implementações respeitam o contrato da interface
- **I**SP: Interfaces segmentadas (`ValidadorSenha` e `RegraValidacao` são pequenas e focadas)
- **D**IP: Depende de abstrações (`ValidadorSenha`, `RegraValidacao`), não de implementações concretas

## 🧪 Testes

### Testes Unitários
Localizado em: `src/test/java/com/casesenha/validator/regra/impl/RegrasValidacaoTest.java`

Cobre todas as regras de validação individualmente:
- Senhas válidas e inválidas para cada regra
- Casos extremos (null, vazio, etc.)
- Todos os caracteres especiais válidos

**Executar testes unitários das regras:**
```bash
mvn test -Dtest=RegrasValidacaoTest
```

### Testes do Validador Composto
Localizado em: `src/test/java/com/casesenha/validator/ValidadorSenhaCompostoTest.java`

Testa a combinação de regras:
- Senhas válidas
- Senhas inválidas (cada tipo de falha)
- Exemplos do desafio

**Executar testes do validador composto:**
```bash
mvn test -Dtest=ValidadorSenhaCompostoTest
```

### Testes de Serviço
Localizado em: `src/test/java/com/casesenha/service/ServicoValidacaoSenhaTest.java`

Testa a lógica de negócio:
- Integração com o validador
- Logging de resultados

**Executar testes de serviço:**
```bash
mvn test -Dtest=ServicoValidacaoSenhaTest
```

### Testes de Integração
Localizado em: `src/test/java/com/casesenha/controller/ControladorValidacaoSenhaIntegracaoTest.java`

Testa a API completa:
- Requisições com senhas válidas
- Requisições com senhas inválidas
- Tratamento de requisições nulas
- JSON inválido
- Códigos de status HTTP

**Executar testes de integração:**
```bash
mvn test -Dtest=ControladorValidacaoSenhaIntegracaoTest
```

**Executar todos os testes:**
```bash
mvn test
```

## 📊 Cobertura de Testes

O projeto contém **24+ testes** cobrindo:
- ✅ Todas as 7 regras de validação individualmente
- ✅ Combinação de regras no validador composto
- ✅ Lógica de negócio no serviço
- ✅ Integração HTTP completa
- ✅ Tratamento de erros
- ✅ Exemplos fornecidos no desafio

## 🔧 Tecnologias Utilizadas

| Tecnologia | Versão | Propósito |
|-----------|---------|----------|
| Java | 17 | Linguagem de programação |
| Spring Boot | 3.3.5 | Framework web |
| Spring Web | 3.3.5 | Criação de REST APIs |
| JUnit 5 | - | Framework de testes |
| AssertJ | - | Assertions mais legíveis |
| Mockito | - | Mocking para testes |
| Lombok | 1.18.34 | Redução de boilerplate |
| Maven | 3.6+ | Gerenciador de dependências |
| PITest | 1.15.8 | Testes de mutação |

## 📝 Premissas e Decisões

### Premissa 1: Caracteres Especiais Exatos
**Assunção:** Os caracteres especiais válidos são exatamente: `!@#$%^&*()-+`

**Racional:** O desafio especificava exatamente esses caracteres. Qualquer outro caractere especial (como `.`, `,`, `?`, etc.) torna a senha inválida.

### Premissa 2: Sem Caracteres Repetidos em TODO o Conjunto
**Assunção:** Se qualquer caractere aparecer mais de uma vez em toda a senha, ela é inválida.

**Racional:** O desafio diz "Não possuir caracteres repetidos dentro do conjunto", o que significa nenhum caractere pode aparecer mais de uma vez.

### Premissa 3: Espaços em Branco Não São Válidos
**Assunção:** Qualquer whitespace (espaço, tab, nova linha) torna a senha inválida.

**Racional:** O desafio menciona "Espaços em branco não devem ser considerados como caracteres válidos", portanto nem mesmo um espaço é permitido.

### Premissa 4: Null é Inválido
**Assunção:** Uma senha `null` é considerada inválida.

**Racional:** Não há senha a validar se o valor é nulo.

## 🎯 Qualidade de Código

- ✅ **Clean Code:** Nomes descritivos, métodos pequenos e focados, sem lógica complexa
- ✅ **SOLID:** Todos os 5 princípios aplicados
- ✅ **Documentação:** JavaDoc em todas as classes e métodos públicos
- ✅ **Testes:** Cobertura abrangente com testes unitários, integração e mutação
- ✅ **Extensibilidade:** Fácil adicionar novas regras ou estratégias de validação
- ✅ **Manutenibilidade:** Código claro e bem organizado em camadas

## 📈 Possíveis Evoluções Futuras

1. **Configuração de Regras:** Permitir customizar as regras de validação via arquivo de config
2. **Métricas de Força:** Retornar um score de força da senha (fraca, média, forte)
3. **Histórico de Validações:** Registrar tentativas de validação em banco de dados
4. **Rate Limiting:** Limitar requisições por IP
5. **Autenticação/Autorização:** Proteger endpoints com JWT ou OAuth2
6. **Suporte Multilíngue:** Internacionalizar mensagens de erro
7. **Cache:** Cachear resultados de validações (com cuidado de segurança)
8. **Validação Assíncrona:** Processar validações pesadas de forma assíncrona
9. **API de Consulta de Regras:** Endpoint para consultar quais regras estão ativas
10. **Swagger/OpenAPI:** Documentação automática da API

## 📄 Licença

Este projeto é fornecido como solução para o desafio backend da ITI Digital.

## 👤 Autor

Solução desenvolvida para o desafio backend ITI Digital.

---

**Desenvolvido com foco em qualidade, extensibilidade e manutenibilidade.**
