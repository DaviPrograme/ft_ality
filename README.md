# ft_ality

Este projeto foi inspirado na ideia de treinar e executar autômatos finitos para reconhecer combinações de teclas, como as usadas em movimentos de jogos de luta. O projeto simula um modo de treinamento onde combinações específicas resultam na execução de "golpes" ou ações predefinidas, aproximando a lógica de autômatos finitos de uma aplicação prática e interativa.
O principal objetivo é explorar conceitos fundamentais de linguagens formais e processamento de estados, aplicando-os em uma interface simples e funcional.

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

* Estados: Representam as condições atuais do sistema.
* Alfabeto de entrada: Um conjunto finito de símbolos.
* Estado inicial: O estado onde o processamento começa.
* Estados de aceitação: Estados que indicam reconhecimento de uma entrada válida.
* Função de transição: Define como o autômato muda de estado com base no símbolo atual.

No contexto deste projeto, os autômatos finitos são usados para reconhecer sequências de teclas que correspondem a combinações de movimentos, funcionando de maneira semelhante ao reconhecimento de palavras em uma linguagem.

## como Executar

Certifique-se de ter o Leiningen instalado.

Clone este repositório.

Execute o comando lein run no diretório raiz do projeto.

Insira combinações de teclas conforme o mapeamento exibido.

Configuração e Combinações

Mapeamento de Teclas

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

Combinações de Movimentos

Disabler - Left, Left + Front Punch
Teleport Slam - Down, Up
Shadow Toss - Right, Right + Back Punch
Weapon Steal - Down + Back Punch
Sweep Kick - Left + Back Kick

## Importância do Projeto

Este projeto permitiu explorar conceitos fundamentais como:

* Gramáticas formais e a hierarquia de Chomsky.

* Linguagens regulares e suas aplicações práticas.

* Estruturas de dados imutáveis e a abordagem funcional de programação.

Além disso, ele demonstrou como os autômatos finitos podem ser usados para processar entradas em tempo real, como as combinações de teclas de um jogo. Essa abordagem tem aplicações práticas em diversas áreas, como desenvolvimento de jogos, compiladores e sistemas de controle.

Conclusão

A implementação deste projeto foi uma oportunidade valiosa para unir teoria e prática, explorando os limites da programação funcional e do design de sistemas baseados em estados. Qualquer feedback ou sugestão será bem-vindo!
