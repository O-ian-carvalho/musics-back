
## Como Executar

1. Clone este repositório:
   ```sh
   git clone https://github.com/https://github.com/O-ian-carvalho/musics-back
   ```
2. Acesse o diretório do projeto:
  
3. Instale as dependências (caso utilize Maven, por exemplo):
   ```sh
   mvn clean install
   ```
4. Para rodar a aplicação localmente, use o seguinte comando:
   ```sh
   mvn spring-boot:run
   ```

A API estará disponível em:
```
http://localhost:8080/
```

## Build para Produção

Para gerar um arquivo `.jar` executável:
```sh
mvn clean package
```
O arquivo será gerado na pasta `target/`. Para executá-lo:
```sh
java -jar target/nome-do-arquivo.jar
```


