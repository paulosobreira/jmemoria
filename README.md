# JMemória

Jogo da memória com cartas

- Pode ser jogado multiplayer local ou remoto(Rede local)
- Para se jogar de 2 na mesma maquina é preciso abrir 2 instancias do jogo onde uma delas é o Servidor/Cliente e a outra apenas cliente.

  1. Servidor > Iniciar Servidor (Normalemnete porta 8080 ou outra disponivel)
  2. Na textaera é exibido "Ok eu sou NomeDoPc/127.0.1.1 ouvindo na porta 8080"
  3. Menu > conectar e entre com ip informa na mensagem(127.0.1.1)
  4. Entre com o numero da porta(8080)
  5. Entre com seu Nome(Todos os joagdores devem informar nome unicos. O jogo anucia de quem é a vez de jogar)
  6. Servidor > Iniciar Jogo(O jogo monta o tabuleiro de cartas em todas as instâncias conectadas e inicia o jogo)

## Requerimentos

- Para executar o jogo é necessário Java 11
- Para rodar uma instância do jogo no Windows execute run.bat
- Para rodar uma instância do jogo no linux execute run.sh

## Informação técnica

- Construção Maven
  - mvn clean package
