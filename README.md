# dicionario-lemaf

**Autor**: Andreas A. Adelino

Pré-requisitos:

- Apache Maven 3.5.0
- JDK 1.8
- Git 

==========================================

_Passos para instalação e execução do programa:_

1. Clone o repositório em alguma pasta em seu computador usando o git.
> git clone https://github.com/andreas-andrade/dicionario-lemaf.git

2. Pelo terminal, navegue até a pasta do projeto e execute o comando Maven:
> mvn clean compile assembly:single

Será gerado um arquivo .jar na pasta "/target".

_Executando o aplicativo:_
 
1. Execute o arquivo no terminal com o comando: 
> java -jar target/dicionario-api-1.0-SNAPSHOT-jar-with-dependencies.jar 

2. Com o programa executando, informe qualquer palavra e dê ENTER para executar a busca.

3. Digite ":q" para encerrar.