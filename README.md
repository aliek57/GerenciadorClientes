# Gerenciador de Clientes - Representante Comercial

Esse aplicativo foi desenvolvido para gerenciar os clientes de um representante comercial, permitindo o cadastro, o gerenciamento de visitas e a realização de pedidos. A aplicação utiliza a API **Room** do Android para armazenar os dados localmente e oferece funcionalidades como a seleção de cidades, registro de visitas e visualização das informações dos clientes.

## Funcionalidades

### Tela Inicial:
- O representante comercial pode selecionar a cidade a partir de um **Spinner** com lista de seleção.
- Após selecionar a cidade, uma lista de clientes daquela cidade é exibida.
- Na lista de clientes, há um botão para ligar diretamente para o cliente, que abre o aplicativo de telefonia do dispositivo.

### Cadastro de Novo Cliente:
- O aplicativo busca automaticamente as informações de endereço utilizando o serviço REST via [ViaCEP](https://viacep.com.br/ws/<CEP>/json).
- **Cidades** são mantidas em uma tabela à parte, identificadas pelo código do IBGE retornado pelo Web Service.
- Caso a cidade do cliente não exista no banco de dados local, ela será inserida automaticamente.

### Registro de Visita ao Cliente:
- O representante pode registrar a **data e hora** da visita.
- É possível adicionar uma **classificação de satisfação do cliente** (1 a 5 estrelas).
- O **valor do pedido** registrado (pode ser 0,00 se não houve pedido).
- Uma **observação** pode ser registrada para incluir impressões ou acordos feitos com o cliente.

### Listagem de Clientes por Cidade:
- Ao listar os clientes de uma cidade, os clientes com a **data da última visita mais antiga** serão exibidos primeiro.
- Clientes visitados nos últimos **7 dias** terão um **indicador visual** (fundo verde ou ícone) para destacar que já foram visitados.

## Tecnologias Utilizadas
- **Android SDK**: Para o desenvolvimento da interface e funcionalidades.
- **Room Database**: Para o armazenamento local de dados dos clientes, cidades e visitas.
- **Retrofit**: Para a integração com o serviço REST de CEP (ViaCEP).
- **Kotlin**: Para o desenvolvimento do aplicativo.

## Como rodar o projeto

### Pré-requisitos:
Antes de rodar o projeto, verifique se você possui as seguintes ferramentas instaladas:

- **Android Studio**: Para abrir e executar o projeto.
- **JDK (Java Development Kit)**: Necessário para compilar o código Android.
- **Emulador Android** ou **dispositivo físico**: Para rodar o aplicativo no seu ambiente de teste.

### Passos:
1. **Clone o repositório** para o seu computador:

   ```bash
   git clone git@github.com:aliek57/GerenciadorClientes.git

2. **Abra o projeto no Android Studio:**

  - Abra o Android Studio.
  - Selecione Open no menu e navegue até o diretório onde você clonou o repositório.
  - Abra o projeto no Android Studio.

3. **Instale as dependências:**

  - Aguarde o Android Studio sincronizar o Gradle e baixar as dependências do projeto.

4. **Configure o Emulador ou Conecte um Dispositivo Físico:**

  - Configure um emulador Android ou conecte seu dispositivo físico via USB.
  - Certifique-se de que a depuração USB esteja ativada no dispositivo.
  
5. **Execute o aplicativo:**

  - No Android Studio, clique no botão Run (ícone de play) para rodar o aplicativo no emulador ou dispositivo conectado.
  
Agora o aplicativo estará rodando no seu dispositivo ou emulador! 🙂

---

Desenvolvido para a disciplina de **Desenvolvimento de Aplicações Móveis 1**.
