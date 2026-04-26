# FIPE Search - Redis Cache Optimization 🚀

![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

Este é um projeto de demonstração focado em **Arquitetura de Alta Performance** e **Resiliência**, construído com Spring Boot. O objetivo principal é demonstrar a implementação de um sistema de consulta de preços de veículos (baseado na Tabela FIPE) que utiliza **Cache Distribuído com Redis** para otimizar drasticamente o tempo de resposta e reduzir a carga no banco de dados relacional.

## 🛠️ Tecnologias Utilizadas

O projeto foi construído utilizando o padrão "Gold Standard" do ecossistema Java/Spring, focando em features modernas e boas práticas:

*   **Java 25**: Utilizando os recursos mais recentes da linguagem (como `records` para DTOs e blocos de texto).
*   **Spring Boot 4.0.6**: Framework base para injeção de dependências, auto-configuração e servidor web embarcado.
*   **Spring Data JPA / Hibernate**: ORM para mapeamento das entidades e consultas avançadas (JPQL com projeção direta para DTOs).
*   **Spring Data Redis**: Integração fluida com o servidor Redis para gerenciamento de cache distribuído.
*   **PostgreSQL**: Banco de dados relacional robusto e escalável, utilizando `UUID` nativo para chaves primárias.
*   **Serialização Nativa Java**: Utiliza a serialização padrão (`java.io.Serializable`) para armazenar os objetos em cache no Redis.
*   **Swagger / OpenAPI 3 (Springdoc)**: Documentação interativa e gerada automaticamente para a API REST.

## 🏗️ Arquitetura e Fluxo de Dados

A arquitetura foi desenhada para suportar alta concorrência. O fluxo de uma requisição de busca de preço (ex: `GET /fipe/reference?modelId=...&modelYear=2024`) segue o padrão **Cache-Aside**:

1.  **Controller (`FipeController`)**: Recebe a requisição HTTP, valida os parâmetros e chama o Service.
2.  **Service (`FipeService`)**: É o coração da lógica de negócio e do cache. O método de busca está anotado com `@Cacheable(value = "fipe", keyGenerator = "customKeyGenerator")`.
3.  **Camada de Cache (Redis Interceptor)**:
    *   **Cache Hit:** O Spring verifica no Redis se a chave customizada (ex: `fipe::a111...:2024`) existe. Se existir, o objeto binário é desserializado para o `record` `FipeResponseDto` e retornado imediatamente (Tempo de resposta < 5ms). O banco de dados nem fica sabendo da requisição.
    *   **Cache Miss:** Se a chave não existir ou tiver expirado (TTL de 24 horas), o fluxo continua para o banco de dados. Um log de `DEBUG` é emitido avisando do "Cache Miss".
4.  **Repository (`ReferenceRepository`)**: Executa uma query JPQL altamente otimizada que faz os `JOINs` necessários (Models e Makes) e já projeta o resultado diretamente no `FipeResponseDto`, economizando alocação de memória (evita carregar Entidades gerenciadas pelo Hibernate à toa).
5.  **Atualização do Cache**: Após o banco retornar o dado, o Spring intercepta o retorno, serializa o DTO de forma binária nativa e salva no Redis de forma transparente.

### Diferenciais Arquiteturais:
*   **KeyGenerator Customizado**: As chaves no Redis são legíveis para humanos, não hashes binários, facilitando o debug no `redis-cli`.
*   **Bloqueio de Caches Dinâmicos**: A configuração `disableCreateOnMissingCache()` impede a criação acidental de caches não mapeados, protegendo a memória do Redis.

## 🚀 Como Rodar o Projeto

### Pré-requisitos
Você precisará ter instalado na sua máquina:
*   **Java JDK 25** (ou compatível com a versão definida no `pom.xml`).
*   **Maven** (ou use o `mvnw` embutido).
*   **PostgreSQL**: Rodando na porta padrão `5432` com um banco chamado `fipesearchredis` e credenciais `meurer/meurer` (ou altere no `application.yaml`).
*   **Redis**: Rodando na porta padrão `6379`. (Recomendado usar Docker: `docker run -d -p 6379:6379 redis`).

### Passo a Passo

1.  **Clone e Acesse o Repositório:**
    Navegue até o diretório raiz do projeto (onde está o `pom.xml`).

2.  **Configuração de Dados Simulados:**
    O projeto conta com um script `src/main/resources/data.sql` que é executado automaticamente (devido à configuração `spring.sql.init.mode=always`). Ele popula o banco de dados com diversas marcas (Toyota, Honda, Volkswagen, etc.), modelos e referências de preço simuladas.
    *(Nota: A geração das chaves estrangeiras usa funções nativas do Postgres como `gen_random_uuid()`).*

3.  **Inicie a Aplicação:**
    Execute o comando Maven para rodar o Spring Boot:
    ```bash
    # No Windows
    .\mvnw.cmd spring-boot:run

    # No Linux/Mac
    ./mvnw spring-boot:run
    ```

4.  **Acesse a Documentação (Swagger UI):**
    Com a aplicação rodando, abra o seu navegador e acesse a interface interativa do Swagger para testar os endpoints:
    👉 `http://localhost:8080/swagger-ui/index.html`

### Testando o Cache no Redis

1. Faça uma requisição pela primeira vez através do Swagger.
2. Verifique os logs da aplicação; você deverá ver a mensagem de *Cache Miss* e a query SQL do Hibernate sendo executada.
3. Faça exatamente a mesma requisição novamente.
4. Verifique os logs: Nenhuma query SQL será executada! O retorno foi quase instantâneo via Redis.
5. (Opcional) Abra o seu terminal, conecte-se ao Redis via `redis-cli` e rode o comando `KEYS *` para ver as chaves dos seus dados cacheados!
