# Password Validator API

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

**POST** `/api/v1/passwords/validate`

#### Request

```json
{
  "password": "AbTp9!fok"
}
```

#### Response (Válida)

```json
{
  "valid": true
}
```

#### Response (Inválida)

```json
{
  "valid": false
}
```

#### Exemplos com cURL

```bash
# Senha válida
curl -X POST http://localhost:8080/api/v1/passwords/validate \
  -H "Content-Type: application/json" \
  -d '{"password":"AbTp9!fok"}'

# Senha inválida (muito curta)
curl -X POST http://localhost:8080/api/v1/passwords/validate \
  -H "Content-Type: application/json" \
  -d '{"password":"abc"}'
```

## 🏗️ Arquitetura e Design

### Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/casesanha/
│   │   ├── PasswordValidatorApplication.java     # Classe principal Spring Boot
│   │   ├── config/
│   │   │   └── ApplicationConfig.java            # Configuração de beans
│   │   ├── controller/
│   │   │   └── PasswordValidationController.java # Endpoints REST
│   │   ├── dto/
│   │   │   ├── PasswordValidationRequest.java    # DTO de requisição
│   │   │   └── PasswordValidationResponse.java   # DTO de resposta
│   │   ├── service/
│   │   │   └── PasswordValidationService.java    # Lógica de negócio
│   │   └── validator/
│   │       ├── PasswordValidator.java            # Interface
│   │       └── StrictPasswordValidator.java      # Implementação
│   └── resources/
│       └── application.properties                # Configurações
└── test/
    └── java/com/casesanha/
        ├── validator/
        │   └── StrictPasswordValidatorTest.java  # Testes unitários
        └── controller/
            └── PasswordValidationControllerIntegrationTest.java
```

### Decisões de Design

#### 1. **Padrão Strategy com Interface**
   - **Decisão:** Usar uma interface `PasswordValidator` com implementação `StrictPasswordValidator`
   - **Racional:** Permite trocar facilmente a estratégia de validação sem afetar o resto da aplicação. Favorece extensibilidade e testabilidade.
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

#### 4. **Métodos Privados para Cada Validação**
   - **Decisão:** Quebrar a lógica de validação em métodos menores e reutilizáveis
   - **Racional:** Cada método tem uma responsabilidade clara, facilitando compreensão e manutenção
   - **SOLID:** Single Responsibility Principle

#### 5. **DTOs para Request/Response**
   - **Decisão:** Criar DTOs específicos em vez de usar String diretamente
   - **Racional:** Facilita expansão futura (ex: adicionar mais campos), melhora documentação e permite validação de entrada

#### 6. **Versionamento de API**
   - **Decisão:** Usar `/api/v1/` no endpoint
   - **Racional:** Permite evoluir a API sem quebrar clientes existentes

#### 7. **Spring Boot com Maven**
   - **Decisão:** Usar Spring Boot e Maven
   - **Racional:** Framework maduro e com excelente suporte para criar APIs REST em Java. Maven é o padrão da indústria.

### Princípios SOLID Aplicados

- **S**RP: Cada classe tem uma única responsabilidade
- **O**CP: Aberto para extensão (novas implementações do `PasswordValidator`), fechado para modificação
- **L**SP: Implementações respeitam o contrato da interface
- **I**SP: Interface segmentada (`PasswordValidator` é pequena e focada)
- **D**IP: Depende de abstrações (`PasswordValidator`), não de implementações concretas

## 🧪 Testes

### Testes Unitários
Localizado em: `src/test/java/com/casesanha/validator/StrictPasswordValidatorTest.java`

Cobre todos os cenários de validação:
- Senhas válidas
- Senhas nulas
- Senhas vazias
- Senhas muito curtas
- Falta de dígito
- Falta de letra minúscula
- Falta de letra maiúscula
- Falta de caractere especial
- Caracteres repetidos
- Espaços em branco
- Todos os caracteres especiais válidos
- Exemplos do desafio

**Executar testes unitários:**
```bash
mvn test -Dtest=StrictPasswordValidatorTest
```

### Testes de Integração
Localizado em: `src/test/java/com/casesanha/controller/PasswordValidationControllerIntegrationTest.java`

Testa a API completa:
- Requisições com senhas válidas
- Requisições com senhas inválidas
- Tratamento de requisições nulas
- Múltiplas senhas válidas e inválidas
- Códigos de status HTTP

**Executar testes de integração:**
```bash
mvn test -Dtest=PasswordValidationControllerIntegrationTest
```

**Executar todos os testes:**
```bash
mvn test
```

## 📊 Cobertura de Testes

O projeto contém **24+ testes** cobrindo:
- ✅ Todas as 7 regras de validação
- ✅ Casos extremos (null, vazio, exatamente 9 caracteres)
- ✅ Todos os caracteres especiais válidos
- ✅ Integração HTTP completa
- ✅ Exemplos fornecidos no desafio

## 🔧 Tecnologias Utilizadas

| Tecnologia | Versão | Propósito |
|-----------|---------|----------|
| Java | 17 | Linguagem de programação |
| Spring Boot | 3.1.5 | Framework web |
| Spring Web | 3.1.5 | Criação de REST APIs |
| JUnit 5 | - | Framework de testes |
| AssertJ | - | Assertions mais legíveis |
| Maven | 3.6+ | Gerenciador de dependências |

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
- ✅ **Testes:** Cobertura abrangente com testes unitários e de integração
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

## 📄 Licença

Este projeto é fornecido como solução para o desafio backend da ITI Digital.

## 👤 Autor

Solução desenvolvida para o desafio backend ITI Digital.

---

**Desenvolvido com foco em qualidade, extensibilidade e manutenibilidade.**

