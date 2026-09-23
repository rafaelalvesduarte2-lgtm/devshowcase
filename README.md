# DevShowcase API

API REST desenvolvida em Java com Spring Boot para gerenciamento de perfis, projetos, tecnologias e feedbacks.

## Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Maven
* Postman

## Modelo de domínio

O projeto possui as seguintes entidades:

* **Profile** — perfil do desenvolvedor
* **Project** — projetos do perfil
* **Technology** — tecnologias utilizadas nos projetos
* **Feedback** — avaliações/comentários sobre os projetos

### Relacionamentos

* Profile 1:N Project
* Project N:N Technology
* Project 1:N Feedback

## Endpoints

### Profiles

**POST** `/api/profiles`

Cria um novo perfil.

**GET** `/api/profiles/{id}`

Busca um perfil pelo ID.

### Technologies

**POST** `/api/technologies`

Cadastra uma tecnologia.

**GET** `/api/technologies`

Lista todas as tecnologias.

### Projects

**POST** `/api/projects`

Cria um projeto associado a um perfil e às tecnologias.

**GET** `/api/projects`

Lista todos os projetos.

### Feedbacks

**POST** `/api/feedbacks`

Cria um feedback associado a um projeto.

## Validações

A API possui validações para os dados recebidos nos DTOs, incluindo:

* campos obrigatórios;
* formato de e-mail;
* URLs iniciadas com `http://` ou `https://`;
* notas de feedback entre 1 e 5.

Também existe tratamento global para erros de validação e recursos não encontrados.

## Execução do projeto

É necessário possuir Java 21 e PostgreSQL instalados.

Configure a conexão com o banco de dados e defina a variável de ambiente:

```text
DB_PASSWORD
```

Depois execute:

```powershell
.\mvnw.cmd spring-boot:run
```

A API será executada em:

```text
http://localhost:8080
```

## Banco de dados

O projeto utiliza PostgreSQL com o banco:

```text
devshowcase
```

As tabelas são criadas/atualizadas automaticamente pelo Hibernate.

## Testes

Os endpoints foram testados utilizando o Postman.
