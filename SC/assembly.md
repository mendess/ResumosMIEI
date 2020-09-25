# Assembly

**Assembly** é uma linguagem de programação de baixo nível (*low-level*). Programar e interpretar código maquina (binário) é algo bastante trabalhoso e de difícil compreensão para o ser humano. Consequentemente, foi criado o Assembly, uma vez que é uma
representação muito mais inteligível das instruções que um computador
executa. Além disso, a sua conversão para linguagém máquina é bastante simples devido à existência do **Assembler**. Esta linguagem é muitas vezes utilizada para aceder diretamente às instruções do processador, para a manipulação direta de *Hardware* e para corrigir problemas de *performance*.


Como já devem ter visto na explicação da [stack](stack.md), **push** e
**pop** são duas instruções básicas para colocar e remover elementos da
stack. Ambas são instruções em assembly que recebem um registo.

```push <reg>```
 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;   Coloca no 
topo da stack o conteúdo do registo.

```pop <reg>```
 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  Remove o valor que está
topo da stack e coloca-o no registo.  


## **mov**

A instrução **mov** copia o valor da fonte (*source*) para o destino. (A fonte e o destino não podem ser ambas na memória).

<br>

``` mov %eax, %ebx```  

 Copia o valor do conteúdo do registo **%eax** para o registo **%ebx**. (o registo %eax não é alterado).
 
<br>

```mov (%eax), %ebx```  

Copia o valor apontado pelo conteúdo do **%eax** para **%ebx**.
Nesta instrução, o que estiver no registo **%eax** é um apontador para um local de memória. O valor apontado será um operando num dado local da memória. Os `()` representam uma acesso à memória em que se obtem o valor apontado.

<br>

```mov %eax, (%ebx)```

Copia o valor do registo **%eax** para o local de memória apontado por **%ebx**.

<br>


```mov $0x10, %ebx ``` 

Guarda o valor (imediato) 0x10 ( 16 ) no registo **%ebx**.

<br>


```mov %eax, 0x4(%ebx)```

Nesta instrução é colocado o conteúdo do registo **%eax** no local apontado por `%ebx + 4`. Os parantêses no segundo operando indicam que o destino é na memória e que terá que ser feito um acesso à mesma. Assim, é extraído o conteudo do registo **%ebx** e é efetuado um deslocamento relativo a esse valor para determinar o local da memória destino.

## **leal**

A instrução **leal** (load effective address) tem algumas semelhanças com a instrução **mov**. Contudo, esta quando utiliza operandos que são apontadores não determina o valor apontado por eles. Na secção interior pode-se constatar que a utilização de `()` na instrução **mov** implica acesso à memória de modo a obter o valor apontado pelo endereço. Por outro lado, na instrução **leal** os parênteses não implicam acesso à memória. Desta forma, é possível realizar aritmética sobre os apontadores sem ter que ir à memória e ver para o que estes apontam.

<br>

```leal $0x10(%eax), %ebx```

Esta instrução soma o conteúdo de **%eax** com 16 (decimal) e coloca esse valor no registo **%ebx**.

```leal -4(%eax, %edx, 4), %ebx```

Nestea instrução é realizada esta operação: (%eax + %edx * 4 ) - 4 e o seu resultado é colocado no registo **%ebx**-


<br>


Existem muitas outras intruções de Assembly que podem ser consultas no [Instruction Set](http://gec.di.uminho.pt/lei/sc/InstrSet_IA32.pdf).