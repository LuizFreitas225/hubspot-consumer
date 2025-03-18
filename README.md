# HubSpot Consumer API

## Descrição

Este projeto consiste em uma API RESTful desenvolvida em Java 17 utilizando Spring Boot, com o objetivo de integrar-se à API do HubSpot. A aplicação implementa autenticação via OAuth 2.0 (Authorization Code Flow), criação de contatos no CRM do HubSpot e recebimento de notificações via webhooks. Além disso, utiliza Docker Compose para gerenciamento do banco de dados.

## Funcionalidades Implementadas

1. **Geração da URL de Autorização**: Endpoint responsável por gerar e retornar a URL de autorização para iniciar o fluxo OAuth com o HubSpot.
2. **Processamento do Callback OAuth**: Endpoint que recebe o código de autorização fornecido pelo HubSpot e realiza a troca pelo token de acesso.
3. **Criação de Contatos**: Endpoint que realiza a criação de um contato no CRM do HubSpot através da API.
4. **Recebimento de Webhook para Criação de Contatos**: Endpoint que escuta e processa eventos do tipo "contact.creation" enviados pelo webhook do HubSpot

## Mais Sobre o Desenvolvimento

O projeto foi construído obedecendo ao modelo MVC. Todos os pontos de entrada da API possuem um contrato bem definido, o que impossibilita a entrada de dados indesejados ou a execução de códigos injetados. Além disso, há um sistema de logs bem estruturado que permite acompanhar o avanço de cada uma das operações realizadas pela API.

Adicionalmente, implementei um handler de exceptions que trabalha juntamente com exceções personalizadas, fornecendo mensagens e status específicos para cada situação, garantindo um retorno padronizado para a API. Vale ressaltar que os problemas relacionados à inexistência ou invalidez do token são tratados de forma a redirecionar o cliente para a URL de autorização.

Em uma visão geral, o JPA foi utilizado no contexto dos repositories, enquanto o RestTemplate serviu como facilitador para acesso e estruturação dos dados retornados dos serviços do HubSpot. Para garantir a segurança do aplicativo, os dados de acesso ao HubSpot são coletados a partir do application.yml e parametrizados em variáveis de ambiente. Por se tratar de um aplicativo temporário, alterei e disponibilizei tudo dentro do arquivo como forma de facilitar.

## Melhorias Futuras

- **Implementação do Rate Limiting**: Utilizar a biblioteca Bucket4j para implementar o controle de taxa, garantindo que não sejam excedidos os limites de requisições à API do HubSpot.
- **Gerenciamento de Tokens com Redis**: Implementar o uso do Redis para armazenar tokens de acesso e refresh tokens, permitindo um gerenciamento mais eficiente e seguro das sessões.
- **Refatoração da Camada de Serviço**: Separar as responsabilidades de acesso a serviços externos, movendo o uso do RestTemplate para classes dedicadas, seguindo as boas práticas.
- **Desenvolvimento de Testes Unitários**: Escrever testes unitários utilizando JUnit e Mockito para assegurar a qualidade e a confiabilidade do código.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação utilizada no desenvolvimento da aplicação.
- **Spring Boot**: Framework que simplifica a criação de aplicações Java, proporcionando uma configuração simplificada e diversas funcionalidades integradas.
- **Maven**: Ferramenta de automação de compilação utilizada para gerenciamento de dependências e construção do projeto.
- **Docker Compose**: Utilizado para orquestrar e gerenciar o ambiente de containers, incluindo o banco de dados.
- **ngrok**: Ferramenta utilizada para expor a aplicação local na web, facilitando o desenvolvimento e testes de webhooks.

## 

## Como Executar o Projeto

1. **Pré-requisitos**:
    - Java 17 instalado.
    - Maven instalado.
    - Docker e Docker Compose instalados.
2. **Clonar o Repositório**:

    ```bash
    
    git clone https://github.com/LuizFreitas225/hubspot-consumer.git
    
    ```

3. **Subir o Banco de Dados com Docker Compose**:

    ```bash
    docker-compose up -d
    
    ```

4. **Construir e Executar a Aplicação**:

    ```bash
    mvn clean install
    mvn spring-boot:run
    
    ```

5. Expor API na Web (Neste caso eu utilizei o ngrok):

    ```bash
    ngrok http 8080
    
    ```


Anote o URL fornecido pelo ngrok e configure-o como o endpoint de webhook no HubSpot.

## Como usar?

Após executar o projeto, você pode acessar a documentação gerada pelo OpenAPI na seguinte URL:

http://localhost:8080/swagger-ui/index.html#/

Além disso, disponibilizei na pasta raiz do projeto os seguintes arquivos:

- `hubspot-consumer.postman_environment.json`
- `HubSpot-Consumer-API.postman_collection.json`

Esses arquivos podem ser utilizados no Postman ou no Insomnia para facilitar o acesso aos recursos da HubSpot Consumer.

**Observação:** Certifique-se de que o redirecionamento automático esteja permitido em seu navegador ou ferramenta de API para garantir o funcionamento correto dos endpoints que realizam redirecionamentos para a URL de autorização.