# Assembly

**Assembly** é uma linguagem de programação de baixo nível (*low-level*). Programar e interpretar código maquina (binário) é algo bastante trabalhoso e de difícil compreensão para o ser humano. Consequentemente, foi criado o Assembly, uma vez que é uma
representação muito mais inteligível das instruções que um computador
executa. Além disso, a sua conversão para linguagém máquina é bastante simples devido à existência do **Assembler**. Esta linguagem é muitas vezes utilizada para aceder diretamente às instruções do processador, para a manipulação direta de *Hardware* e para corrigir problemas de *performance*.


Como já devem ter visto na explicação da [stack](stack.md), **push** e
**pop** são duas instruções básicas para colocar e remover elementos da
stack. Ambas são instruções em assembly que recebem um registo.

```push <reg>```
 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ; Coloca no 
topo da stack o conteúdo do registo.

```pop <reg>```
 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ; Remove o valor que está
topo da stack e coloca-o no registo.

## mov

A instrução *mov* copia o valor da fonte (*source*) para o destino.

```mov %eax, %ebx```

 Copia o valor do conteúdo do registo **%eax** para o registo **%ebx**. (o registo %eax não é alterado).

```mov (%eax), %ebx```  Copia o valor apontado pelo conteúdo do **%eax** para **%ebx**.

Nesta instrução, o que estiver no registo **%eax** é um apontador para um local de memória. O valor apontado será um operando num dado local da memória. Os `()` representam uma acesso à memória em que se obtem o valor apontado.