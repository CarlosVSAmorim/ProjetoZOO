# ProjetoZOO

ProjetoZOO é um sistema de gerenciamento de zoológico desenvolvido em Java, utilizando Swing para a interface gráfica e SQLite para persistência de dados. O sistema permite gerenciar animais, usuários e cuidadores, além de gerar relatórios.


![Tela Inicial](https://i.imgur.com/sVsxejp.png)

---

## 🐾 Funcionalidades

### 📋 Gerenciamento de Animais
- Adicionar novos animais (nome, espécie)
- Atualizar informações de animais existentes
- Remover animais
- Visualizar lista de animais cadastrados
- Gerar relatório de animais

![Tela de Animais](imgs/tela_animais.png)

### 👤 Gerenciamento de Usuários
- Registrar novos usuários (username, password)
- Atualizar informações de usuários existentes
- Remover usuários
- Visualizar lista de usuários cadastrados
- Gerar relatório de usuários
- Autenticação com usuário padrão `admin` e senha `123`

![Tela de Usuários](imgs/tela_usuarios.png)

### 👷 Gerenciamento de Cuidadores
- Adicionar novos cuidadores (nome, CPF, telefone, email, especialidade, salário, data de contratação)
- Atualizar informações de cuidadores existentes
- Remover cuidadores
- Visualizar lista de cuidadores cadastrados
- Gerar relatório com resumo da folha de pagamento
- Validações: CPF, data futura e email

![Tela de Cuidadores](imgs/tela_cuidadores.png)

### 📑 Relatórios
- Geração de relatórios específicos para Animais, Usuários e Cuidadores
- Relatório geral consolidado
- Exportação dos relatórios para arquivo `.txt`

![Relatório](imgs/tela_relatorio.png)

### 🔁 Navegação
- Menu principal para acesso às seções de gerenciamento
- Botões "Voltar ao Menu" em todas as telas
- Funcionalidade de logout e sair da aplicação

![Menu Principal](imgs/menu_principal.png)

### Estrutura do Projeto

O projeto segue o padrão arquitetural Model-View-Controller (MVC):

*   **`model`**: Contém a lógica de negócios e a interação com o banco de dados.
    *   `AnimalModel.java`: Gerencia operações CRUD para animais.
    *   `UserModel.java`: Gerencia operações CRUD e autenticação para usuários.
    *   `CuidadorModel.java`: Gerencia operações CRUD para cuidadores, incluindo validações e formatação de dados.
    *   `MenuModel.java`: Define a estrutura e opções do menu principal.
*   **`view`**: Responsável pela interface gráfica do usuário.
    *   `AnimalView.java`: Tela para gerenciamento de animais.
    *   `UserView.java`: Tela para gerenciamento de usuários.
    *   `CuidadorView.java`: Tela para gerenciamento de cuidadores.
    *   `LoginView.java`: Tela de login.
    *   `MenuView.java`: Tela do menu principal.
    *   `ReportView.java`: Tela para exibição e exportação de relatórios.
    *   `RegisterView.java`: (Presente, mas não diretamente integrada no fluxo de login/registro do `UserController` para novos usuários além do padrão).
*   **`controller`**: Atua como intermediário entre o Model e a View, manipulando eventos do usuário e atualizando a View com dados do Model.
    *   `AnimalController.java`: Controla as interações da `AnimalView` e `AnimalModel`.
    *   `UserController.java`: Controla as interações da `UserView` e `UserModel`.
    *   `CuidadorController.java`: Controla as interações da `CuidadorView` e `CuidadorModel`.
    *   `MenuController.java`: Orquestra a navegação entre as diferentes Views e Controllers, gerencia o fluxo de login e logout, e consolida a geração de relatórios.
*   **`database`**: Contém a classe de conexão com o banco de dados.
    *   `Database.java`: (Observação: O `AnimalModel`, `UserModel` e `CuidadorModel` estabelecem suas próprias conexões com `zoo.db`, tornando esta classe `Database.java` redundante ou um resquício de uma implementação anterior. O sistema funciona com as conexões diretas nos modelos).
*   **`Main.java`**: Ponto de entrada da aplicação, responsável por inicializar todos os componentes (Modelos, Views, Controllers) e iniciar o fluxo do programa.

### Tecnologias Utilizadas

*   **Java Development Kit (JDK)**: Versão 22 (conforme `misc.xml`).
*   **Swing**: Biblioteca gráfica para a interface do usuário.
*   **SQLite**: Banco de dados leve e embarcado para persistência de dados.
    *   Driver JDBC para SQLite: `sqlite-jdbc-3.50.2.0.jar` (configurado em `sqlite_jdbc_3_50_2_0.xml`).

### Como Executar

1.  **Pré-requisitos:**
    *   Certifique-se de ter o JDK 22 (ou compatível) instalado em sua máquina.
    *   O driver JDBC para SQLite (`sqlite-jdbc-3.50.2.0.jar`) deve estar configurado no classpath do projeto. No contexto fornecido, ele é referenciado como `jar://$USER_HOME$/Desktop/sqlite-jdbc-3.50.2.0.jar!/`.
2.  **Compilação e Execução (via IDE - IntelliJ IDEA):**
    *   Abra o projeto em uma IDE como o IntelliJ IDEA.
    *   Verifique se as dependências (especialmente o driver SQLite JDBC) estão corretamente configuradas no módulo do projeto (`ProjetoZOO.iml`).
    *   Execute a classe `Main.java`.
3.  **Compilação e Execução (via Linha de Comando):**
    *   Navegue até o diretório `src` do projeto.
    *   Compile os arquivos Java:
        ```bash
        javac -d ../out -cp ".;path/to/sqlite-jdbc-3.50.2.0.jar" Main.java controller/*.java model/*.java view/*.java database/*.java
        ```
        (Substitua `path/to/sqlite-jdbc-3.50.2.0.jar` pelo caminho real do seu driver JDBC. Em sistemas Unix/Linux, use `:` em vez de `;` para o classpath).
    *   Navegue até o diretório `out`.
    *   Execute a aplicação:
        ```bash
        java -cp ".;path/to/sqlite-jdbc-3.50.2.0.jar" Main
        ```
        (Novamente, ajuste o classpath conforme seu sistema operacional).

### Banco de Dados

O banco de dados SQLite (`zoo.db`) será criado automaticamente na primeira execução do aplicativo, no mesmo diretório onde o JAR ou os arquivos `.class` são executados. Ele conterá as tabelas `animals`, `users` e `cuidadores`. Um usuário padrão `admin` com senha `123` será inserido na tabela `users` se ela estiver vazia.

### Contribuição

Sinta-se à vontade para fazer um fork do projeto, propor melhorias e enviar pull requests.

### Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
