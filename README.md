# Setup profile whith AI

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Status do Projeto](https://img.shields.io/badge/Status-Archived-lightgrey)

## 💬 Descrição

Esta é uma API, desenvolvida em Java 21 utilizando o SpringBoot 3.4.0 e gradle como gestor de dependências.
O objetivo deste projeto é disponibilizar dois endpoints para que o usuário, sendo eles:
1 - Transcrição de um arquivo pdf e montagem de um prompt para ser enviado a API do chatGpt.
2 - Envio desse prompt e devolução de uma resposta do chatGpt.

A biblioteca Apache PDFBox foi utilizada para converter pdf em textos do tipo String.

Essa aplicação foi sugerida em um hackathon, onde o objetivo é usar inteligência artificial para diminuição de trabalhos manuais.

## 🚀 Instrução de instalação

Para desenvolvimento do projeto, foi utilizada a IDE intellij, da JetBrains. Demais instalações necessárias:
1. Java 21
2. MySql (com database de nome tk_teste para rodar a migração no build)
3. Gradle (caso deseje abrir o projeto em outra IDE que não o tenha por padrão)

## 💻 Instalação

Para importar o projeto, abra-o a partir do arquivo build.gradle e antes de sua execução, configure a variável de ambiente `OPENAI_API_KEY`, sendo igual a chave configurada no seu usuário da OpenAI.

## 🔗 Instruções de uso

Para acessar os endpoints, aós buildar o projeto, basta importar no postman a coleção disponibilizada no repositório. Selecione a aba "Body" e adicione uma "Key" de nome `file`. Ela deve ser do tipo "File" e selecione um arquivo do tipo ".pdf" na sessão "Value".


