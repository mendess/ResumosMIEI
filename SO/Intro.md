# Contextualização

As histórias do Moura têm como objectivo justificar e contextualizar as decisões tomadas sobre a evolução dos Sistemas Operativos. Infelizmente, o resultado obtido é uma amálgama de informação com muito pouca utilidade.

Este capitulo tem como objectivos principais *resumir o contexto histórico* e *apresentar o papel do sistema operativo e sua definição*.

# Contexto Histórico

Os primeiros sistemas informáticos não tinham sistema operativo, nem nada semelhante, apenas quando o hardware começou a melhorar se começou a pensar em optimizações a nível do software. Foram criados os primeiros programas que simplificaram as operações do computador.

Estes programas, chamados de `monitores de controlo` permitem ao utilizador carregar programas para a memória, editá-los e verificar a sua execução.

Cada utilizador tem um determinado tempo atribuído só para si, podem utilizar o computador exclusivamente.

**Durante maior parte do tempo o computador está parado a espera do input do utilizador ou de operações Input/Output.**


Seguiram-se então os sistemas de `tratamento em batch`. Sendo que **maior parte do tempo de processador era gasto em operações I/O (espera activa)**, a solução foi separar os periférico de I/O.
Começaram também a surgir os primeiros dispositivos de memória secundária.

Um computador auxiliar lia para a banda magnética os programas a executar, quando o trabalho em curso terminasse o sistema operativo ia a lista de trabalho e seleccionava o próximo a executar. Alem disso, em vez de imprimir o resultado dos programas, os ficheiros são enviados para uma impressora quando acabam de executar.

Os periféricos avisam o processador no fim da sua exeução, através de interrupções.

## Escalonamento

Este sistema simples ja permite mecanismos de optimização de gestão do computador. Um utilizador pode indicar a duração máxima de uma operação, ou os recursos utilizados. As operações poderão ser escolhidas com base no tempo de execução.

## Multiprogramação

O mecanismo de interrupções permite multiplexar o processador.
A capacidade de alternar a execução **não** é limitada a `um programa e periféricos I/O` mas pode ser estendida a **vários programas em memória**.

Um programa que queira aceder a um ficheiro em disco fica bloqueada enquanto o controlador de disco actua. Durante este tempo outro programa pode ser executado pelo processador.
Desta forma conseguimos optimizar a utilização do processador.

## Memória virtual

//TODO


# Papel do Sistema Operativo

O papel do sistema operativo pode ser visto de várias ópticas.

## 1. Gestor de recursos
O sistema operativo gere recursos lógicos, mapeando-os para os seus correspondentes físicos;
  * Ficheiros <-> Espaço em disco;
  * Processos <-> Processador;
  * Periféricos virtuais <-> Periféricos físicos.

Desta forma o sistema operativo torna os recursos lógicos **genéricos** e reutilizáveis;

Lista de recursos lógicos geridos pelo sistema operativo;
 1. Processos (estudados detalhadamente nos próximos capitulos);
 2. Memória virtual (estudada também mais a frente);
    * Gestão de espaços de endereçamento.
 3. Sistema de ficheiros;
 4. Periféricos;
    * Gere qualquer periférico através de `periféricos digitais`, obtendo um grau de abstracção.
 5. Utilizadores.
    * Identidade;
    * Privilégios.

## 2. Interface
 * Interface operacional;
   * Shell (bash, zsh, etc);
   * GUI (Linux, Apple, Windows).
* System calls
   * Permitem executar operações associadas aos objectos do sistema;
   * Os objectos do sistema representa os recrusos lógicos e os seus metodos associados.
   * Fazem parte do modelo computacional dos sistemas opertativos.

## 3. Máquina virtual
 * O sistema operativo é uma maquina virtual sobre a máquina fisica.
 * Facilidade na sua utilização.

# Definição de Sistema Operativo

Como reparamos podemos ver o papel do Sistema Operativo de vários angulos levando a várias definições, todas correctas.

Podemos pensar no sistema operativo como uma máquina virtual que abstrai os detalhes da máquina física, disponibiliza também um conjunto de recursos lógicos e interfaces para os gerir e programar.
