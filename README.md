<div align="center">
  
# :apple: Orgs
  
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white)

App de simulação de um e-commerce de produtos naturais.

</div>

## :wrench: Funcionalidades do projeto
O projeto permite acessar a tela de login, cadastro, autenticação de usuário, com uso do sensor de biometria, e também, sair do App caso esteja autenticado. Além disso, possibilita a criação de produto e listagem dos produtos de cada usuário. Além disso, mantém os comportamentos de editar e remover produtos.


![Amostra das funcionalidades do App](https://github.com/RaphaelDaSilvaDev/Ecoleta_NLW_Rocketset/assets/66075182/8bdc2e40-f8a7-4356-9b4f-ca3befd7764c)

## ✔️ Técnicas e tecnologias utilizadas

- `Fluxo de autenticação com DataStore`: armazenar tipos primitivos via preferences, como por exemplo, o id do usuário autenticado
- `Migration`: permitir que o App evolua cada vez que as entidades do Room são modificadas, pois modificam também o schema do banco de dados
- `Coroutines e Flow`: utilizados para fazer a comunicação com o Room e o DataStore
- `StateFlow`: permitir a alteração do valor do Flow fora do builder, como por exemplo, atualizar o valor ao coletar novos valores de um outro Flow.
- `Activity base`: compartilhar código comum entre as Activities, como por exemplo, código de autenticação que permite acessar o usuário logado, deslogar do App e verificar se o usuário está ou não logado
- `Relacionamento no Room`: configurar entidade para identificar a qual registro ela pertence, como por exemplo, um produto que pertence a um usuário
- `Sensor de Biometroa`: além de fazer o login com usuário e senha, é possível usar a biometria para logar, sem necessidade de colocar a senha, caso já tenha um usuário logado anteriormente.

## 🛠️ Abrir e rodar o projeto

Após baixar o projeto, você pode abrir com o Android Studio. Para isso, na tela de launcher clique em:

Open an Existing Project (ou alguma opção similar) Procure o local onde o projeto está e o selecione (Caso o projeto seja baixado via zip, é necessário extraí-lo antes de procurá-lo) Por fim clique em OK O Android Studio deve executar algumas tasks do Gradle para configurar o projeto, aguarde até finalizar. Ao finalizar as tasks, você pode executar o App 🏆

---

<div align="center">
Feito por Raphael da Silva 🚀 <br/>
  Curso Desenvolvimento Android Nativo Alura
</div>
