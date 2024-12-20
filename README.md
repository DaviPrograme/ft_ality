# ft_ality


<p align="center">
  <img src="MK3.gif" alt="Descrição do GIF">
</p>


Este projeto tem como objetivo implementar autômatos finitos para reconhecer combinações de teclas, inspirando-se nos movimentos de jogos de luta. Ele simula um modo de treinamento onde combinações específicas acionam "golpes" ou ações predefinidas, conectando a lógica de autômatos finitos a uma aplicação prática e interativa. Além disso, busca explorar conceitos fundamentais de linguagens formais e processamento de estados, aplicando-os em uma interface funcional e acessível.
## Objetivos do Projeto

Este projeto tem como principais objetivos:

* **Formalizar e compreender gramáticas formais:** Gramáticas são a base para definir linguagens e, neste projeto, ajudam a construir as regras que o autômato deve seguir.
  
* **Explorar a hierarquia de Chomsky:** Essa classificação de linguagens ajuda a posicionar as linguagens regulares, utilizadas no projeto, dentro de um contexto mais amplo de linguagens formais.
  
* **Implementar e trabalhar com linguagens regulares:** Linguagens regulares são aquelas que podem ser representadas por expressões regulares e reconhecidas por autômatos finitos.

* **Construir autômatos finitos:** Esses modelos são usados para reconhecer sequências de teclas e movimentos no contexto de um jogo.

* **Estruturar o projeto em módulos funcionais:** Incentivar o uso de boas práticas na programação funcional, como modularidade, funções puras e imutabilidade.

Cada um desses objetivos contribui para a formação de habilidades importantes tanto em teoria da computação quanto em programação funcional aplicada.

## O que são Autômatos Finitos?

Autômatos finitos são modelos matemáticos usados para representar e manipular linguagens regulares. Eles consistem em:

* **Estados:** Representam as condições atuais do sistema.
* **Alfabeto de entrada:** Um conjunto finito de símbolos.
* **Estado inicial:** O estado onde o processamento começa.
* **Estados de aceitação:** Estados que indicam reconhecimento de uma entrada válida.
* **Função de transição:** Define como o autômato muda de estado com base no símbolo atual.

No contexto deste projeto, os autômatos finitos são usados para reconhecer sequências de teclas que correspondem a combinações de movimentos, funcionando de maneira semelhante ao reconhecimento de palavras em uma linguagem.

## 🛠️ Tecnologias

* [Clojure](https://clojure.org/) - Linguagem de programação
* [Leinigen](https://leiningen.org/) - Ferramenta de Gerenciamento de projetos

## como Executar

Para executar o projeto voce preisa executar o seguinte comando:

```bash
lein run [arquivo.grm]
```

O arquivo de configuração que o programa espera receber  é um similar a este [aqui](grammars/noob.grm):

```bash
w - Up
s - Down
a - Left
d - Right
j - Front Punch
i - Back Punch
k - Front Kick
l - Back Kick
o - Block
u - Switch Stance
q - Throw
________________________________________
Disabler - Left, Left + Front Punch
Teleport Slam - Down, Up
Shadow Toss - Right, Right + Back Punch
Weapon Steal - Down + Back Punch
Sweep Kick - Left + Back Kick
```

Na prirmeira seção do arquivo de configuração especificamos as teclas que podem ser usadas e seus respectvos golpes.
Na segunda seção estamos especificando os nomes dos golpes especiais e seus respectivos combos. Uma coisa que acho que vale destacar é que o prorgrama consegue distinguir teclas que foram pressionadas em sequencia daquuelas que foram pressionadas simultaneamente. ;)

## Importância do Projeto

Este projeto permitiu explorar conceitos fundamentais como:

* [Gramáticas formais](https://pt.wikipedia.org/wiki/Gram%C3%A1tica_formal#:~:text=Uma%20gram%C3%A1tica%20formal%20%C3%A9%20um,como%20um%20gerador%20de%20linguagem.) e a [hierarquia de Chomsky](https://pt.wikipedia.org/wiki/Hierarquia_de_Chomsky).

* [Linguagens regulares](https://pt.wikipedia.org/wiki/Linguagem_regular#:~:text=As%20linguagens%20regulares%20s%C3%A3o%20utilizadas,requer%20mem%C3%B3ria%20para%20ser%20reconhecida.) e suas aplicações práticas.

* Estruturas de dados imutáveis e a abordagem funcional de programação.

Além disso, ele demonstrou como os autômatos finitos podem ser usados para processar entradas em tempo real, como as combinações de teclas de um jogo. Essa abordagem tem aplicações práticas em diversas áreas, como desenvolvimento de jogos, compiladores e sistemas de controle.
