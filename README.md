# IntentsCompose

Projeto desenvolvido como atividade para a disciplina de Programação para Dispositivos Móveis. O objetivo deste repositório é recriar a aplicação de estudos de Intents (originalmente desenvolvida em XML) utilizando o paradigma declarativo do **Jetpack Compose**.

## 🚀 Funcionalidades

O aplicativo demonstra o uso prático e a comunicação via **Intents** no ecossistema Android:

* **Intents Explícitas:** Navegação entre a `MainActivity` e a `ParameterActivity`, demonstrando a passagem de parâmetros bidirecional (`putExtra`) e a captura de resultados utilizando `rememberLauncherForActivityResult`.
* **Intents Implícitas:**
    * `ACTION_VIEW`: Abertura de URLs no navegador padrão do sistema.
    * `ACTION_DIAL`: Abertura do discador nativo com número de telefone pré-preenchido.
    * `ACTION_CALL`: Realização de chamadas telefônicas diretas.
* **Permissões em Tempo de Execução:** Implementação do fluxo de solicitação de permissão (`CALL_PHONE`) exigido pelas versões mais recentes do Android para realizar a ação de ligação direta.

## 🛠️ Tecnologias Utilizadas

* Kotlin
* Jetpack Compose (Material 3)
* Android SDK

## 👨‍💻 Autor

Heitor Lemes Caldas