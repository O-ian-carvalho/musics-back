## Equipe

* Ian Carvalho Santos Conceição
* João Paulo Alves de Carvalho
* Enzo Franklin Alves e Souza
* Jonathas Sampaio Mascarenhas Almeida

---

## Como Executar

### 1. Clone este repositório:

```sh
git clone https://github.com/O-ian-carvalho/musics-back
```

### 2. Acesse o diretório do projeto:

```sh
cd musics-back
```

### 3. Configure o banco de dados MySQL:

Certifique-se de que o MySQL esteja instalado e em execução. Em seguida, crie um banco de dados para o projeto:

```sql
CREATE DATABASE musicdb;
```

Depois, configure o arquivo `application.properties` (ou `application.yml`) com suas credenciais de acesso, exemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/musicdb
spring.datasource.username=root
spring.datasource.password=suasenha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 4. Instale as dependências:

```sh
mvn clean install
```

### 5. Execute a aplicação localmente:

```sh
mvn spring-boot:run
```

---

## Acesso à API

A API estará disponível em:

```
http://localhost:8080/swagger-ui/index.html#/
```

---

## Exemplo de requisição

### Endpoint: `POST /musicas`

**Modelo do corpo da requisição (JSON):**

```json
{
  "nome": "string",
  "lancamento": "2025-06-10T15:05:30.894Z",
  "duracaoEmSegundos": 180,
  "generoId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "artistaId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "albumId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
}
```

> **⚠️ Observação:** Os IDs utilizados acima são apenas exemplos. Antes de cadastrar uma música, **é necessário criar previamente as entidades relacionadas (gênero, artista e álbum)**, pois há relacionamento entre elas.

