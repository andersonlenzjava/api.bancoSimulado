# API de um banco simulado

## Introdução 

<p>- Este Projeto apresenta uma API Rest desenvolvida com o auxilio do Spring Boot, e a linguagem Java, para atender os requisitos de um sistema de um pequeno banco virtual, com cliente, tipo de conta, gerente, agência e transações. Com o intuito de exercitar a pratica de montagem de sistemas, com a modelagem da relação entre as entidades, e construção de APIs com todos os beans comuns a um sistema com arquitetura Rest. </p>

## Índice: 

<a href="#API-de-um-banco-simulado">API de um banco simulado</a></br>
<a href="#Introdução">Introdução</a></br>
<a href="#%EF%B8%8Ftécnicas-e-tecnologias-utilizadas">Técnicas e Tecnologias utilizadas</a></br>
<a href="#Funcionalidades">Funcionalidades</a></br>
<a href="#Etapas">Etapas</a></br>
<a href="#status-4344">Status: 43/44</a></br>
<a href="#Como-utilizar">Como utilizar</a></br>
<a href="#Um-melhor-detalhamento-do-uso-desta-API-é-apresentado-no-video-deste-link">VÍDEO do projeto</a></br>
<a href="#Considerações">Considerações</a></br>

Na operacionalização do sistema obedecer a seguinte sequência:
## ✔️Técnicas e Tecnologias utilizadas

*  Java </br>
*   Spring Framework</br>
*  Spring Boot </br>
*  Spring Boot Validation</br>
*  Spring Data JPA</br>
*  Maven</br>
*  Lombok</br>
*  Flyway</br>
*  PostgresSQL</br>
*  Postman </br>
*  Programação Orientada a Objetos</br>
*  Docker</br>


## 📃Funcionalidades

<p>O sistema Banco Simulado é caracterizado por possuir em seu domínio a possibilidade de cadastro de Agência, Cliente, Conta, Gerente e Transação.</br> 
- O Cliente será cadastrado com os atributos de uma Pessoa sendo nome, cpf e dataNascimento. </br>
- A Agência será cadastrada com o seu numero, nome, rua, cep, numeroPredio.</br>
- O Gerente será cadastrado com os atributos de uma Pessoa, a Agência a qual ele é vinculado, juntamente com o seu Status de trabalho que pode ser : Ferias ou Trabalhando. </br>
- A conta será cadastrada com o seu numero, o saldo inicial zero, a Agência a qual ele é vinculada, o Cliente que é o titular, e o tipo de conta podendo ser : Corrente, Poupança ou Estudante. </br>
- Caso um Cliente, Agência ou Conta já existam é informado uma mensagem de erro ao usuário. </br>
- Cada Transação será registrada com o seu valor, a data da transação, a conta operadora, a conta destino e o tipo de operação podendo ser: </br>
Deposito, Saque, Transferência, onde no Deposito e Saque a conta operador e destino são a mesma conta. </p>

<p>	A operação de deposito só é realizada se o valor a ser depositado é positivo, caso não seja é retornado uma mensagem de erro ao usuário. </br>
A operação de saque só é realizada se o valor do saque for positivo, caso não for é lançado uma exception que é traduzida em uma mensagem de erro ao usuário.</br>
A operação de transferência será composta por duas sub-rotinas de saque e depósito, sendo realizado as suas respectivas verificações. </br>
O sistema permite deletar uma transação, para isto é necessário informar uma chave de segurança que é o CPF do gerente informado via JSON juntamente no corpo da requisição. </br>
É possível detalhar uma transação por seu id, listar as transações pelo numero de uma conta, e listar transações maiores que um valor informado na URL da requisição. </p>

## Etapas:

- [x] Modelagem do diagrama de entidades e suas relações.

<img src="https://github.com/andersonlenzjava/api.bancoSimulado/blob/master/Diagrama_UML_BancoSimulado.JPG">

- [x] domain da API com:
  - [x] Agencia
  - [x] Pessoa
  - [x] Cliente
  - [x] Conta
  - [x] TipoConta
  - [x] Gerente
  - [x] StatusTrabalho
  - [x] Transacao
  - [x] TipoOperacao

Pessoa é utilizado como elemento de Cliente e Gerente.</br>
StatusTrabalho, TipoConta, TipoOperacao são enumerações.</br>

Para cada uma das entidades da API foi implementado os seguintes componentes:</br>
  - [x] Implementação da entidade
  - [x] Implementação do Repository
  - [x] Implementação do DTO Response
  - [x] Implementação do DTO Register

Para a entidade Gerente foi implementado também:</br>
- [x] GerenteDelTransacaoRegister

Para a entidade Transacao há também:</br>
  - [x] DepositarRegister
  - [x] SacarDepositarResponse
  - [x] SacarRegister

Controller
  - [x] AgenciaController
  - [x] ClienteController
  - [x] ContaController
  - [x] GerenteController
  - [x] TransacaoController

Para os controles Agencia, Cliente, Conta e Gerente foram implementadas os seguintes endpoints para as requisições HTTP compondo  CRUD:</br>
Ex: Para a Agencia.</br>
  - [x] @RequestMapping("/agencias") => Endereço URL básico:
  - [x] @GetMapping => Listar todas as Agencias.
  - [x] @GetMapping("/{id}") => Listar uma Agencia pelo id.
  - [x] @PostMapping => Cadastrar uma Agencia, com as informações no corpo da mensagem via JSON.
  - [x] @PutMapping("/{id}") => Atualizar uma Agencia pelo id, com as informações no corpo da mensagem via JSON.
  - [x] @DeleteMapping("/{id}") => Deletar uma Agencia pelo id.

Service

Para cada um dos endpoints das controllers foram montados métodos dentro das services para acessar o banco de dados </br> através dos Repositorys, e juntamente com os métodos das entidades, completar o CRUD e regras de negócio.</br>

Para a TransacaoService foram implementados algumas regras de negócio:</br>
  - [x] depositar
  - [x] sacar
  - [x] transferir
  - [x] deletarTransacao
  - [x] detalharPorId
  - [x] listarPorConta
  - [x] listarTransacaoMaiorQue

  - [x] implementação da configuração do banco de dados
  - [x] implementação do controle de versões do banco de dados com flyway
  - [x] implementação do tratador de erros
  - [x] ItemJaExisteException
  - [x] SaldoInsuficienteException
  - [x] liberação do cors com o WebConfig
  - [ ] deploy no heroku 

## Status: 43/44

## Como utilizar:

#### Carregamento do projeto

 <p>Neste momento para utilizar o sistema é necessário rodar o sistema offline dentro de alguma IDE, através do Spring Boot.</p>
   <p><strong>Etapas:</strong></p>
     - Download do projeto e descompactar </br>
     - Fazer a atualização das dependências com o Maven</br>
     - Fazer a configuração do banco de dados de sua preferência</br>
     - Criar o banco de dados </br>
     - Configurar a API a este banco de dados</br>
     - Rodar o projeto com a app.properties em spring.jpa.hibernate.ddl-auto=create</br>
     - Em seguida colocar spring.jpa.hibernate.ddl-auto=none</br>
     - Abrir a collection de endpoints com o software que gerencia requisições PostMan</br>

#### Na operacionalização do sistema obedecer a seguinte sequência:

Na operacionalização do sistema obedecer a seguinte sequência  na collection de endpoints junto ao arquivo do postman em anexo:</br>
Cadastrar todas as entidades:</br>
- Agencia</br>
- Gerente</br>
- Cliente</br>
- Conta</br>
- conforme os exemplos em JSON apresentados na collection de endpoints.</br>
Testar as operações de:</br>
- Sacar</br>
- Depositar</br>
- Fazer uma transferência (com duas contas criadas)</br>
- Listar as Transacoes maiores que</br>
- Listar uma transacao por um id</br>
- Deletar uma transacao (com o cpf do Gerente)</br>

## Um melhor detalhamento do uso desta API é apresentado no video deste link.

https://youtu.be/LezD2SevfAA </br>

## Considerações:

<p>Neste sistema foi possível implementar o CRUD dos ingredientes, dos items e dos pedidos, bem como as regras de negócio estabelecidas pelos requisitos do cliente. </p>
