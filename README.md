# dicionario-lemaf

**Autor**: Andreas A. Adelino

Pré-requisitos:

- Maven
- JDK 1.8

==========================================

Passos para instalação e execução do programa:

1. Depois de ter feito clone do repositório, navegue até a pasta do projeto e pelo terminal, execute o comando Maven:
> mvn clean compile assembly:single

2. Será gerado um arquivo .jar na pasta "/target". Execute o arquivo no terminal com o comando: 
> java -jar target/dicionario-api-1.0-SNAPSHOT-jar-with-dependencies.jar 

3. Com o programa executando, informe qualquer palavra e dê ENTER para executar a busca.

4. Digite ":q" para encerrar.