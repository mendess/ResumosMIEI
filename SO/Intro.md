# Contextualização

As histórias do Moura têm como objectivo justificar e contextualizar as decisões tomadas sobre a evolução dos Sistemas Operativos. Infelizmente, o resultado obtido é uma amálgama de informação com muito pouca utilidade.

Este capitulo tem como objectivos principais `resumir o contexto histórico` e `apresentar o papel do sistema operativo e sua definição`.

# Contexto Histórico

//TODO

# Papel do Sistema Operativo

O papel do sistema operativo pode ser visto de várias ópticas.

1. Gestor de recursos
  * O sistema operativo gere recursos lógicos, mapeando-os para os seus correspondentes físicos;
    * Ficheiros <-> Espaço em disco;
    * Processos <-> Processador;
    * Periféricos <-> virtuais Periféricos físicos.
  * Desta forma o sistema operativo torna os recursos lógicos **genéricos** e reutilizáveis;
  * Lista de recursos lógicos geridos pelo sistema operativo;
    1. Processos (estudados detalhadamente nos próximos capitulos);
    2. Memória virtual (estudada também mais a frente);
      * Gestão de espaços de endereçamento.
    3. Sistema de ficheiros;
    4. Periféricos;
      * Gere qualquer periférico através de `periféricos digitais`, obtendo um grau de abstracção.
    5. Utilizadores.
      * Identidade;
      * Privilégios.
2. Interface
  * Interface operacional;
    * Shell (bash, zsh, etc);
    * GUI (Linux, Apple, Windows).
  * System calls
    * Permitem executar operações associadas aos objectos do sistema;
    * Os objectos do sistema representa os recrusos lógicos e os seus metodos associados.
    * Fazem parte do modelo computacional dos sistemas opertativos.

3. Máquina virtual
  * O sistema operativo é uma maquina virtual sobre a máquina fisica.
  * Facilidade na sua utilização.

# Definição de Sistema Operativo

Como reparamos podemos ver o papel do Sistema Operativo de vários angulos levando a várias definições, todas correctas.

Podemos pensar no sistema operativo como uma máquina virtual que abstrai os detalhes da máquina física, disponibiliza também um conjunto de recursos lógicos e interfaces para os gerir e programar.
