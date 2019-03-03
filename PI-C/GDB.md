# GDB

O `gdb` é o debugger *de facto* para C/C++ em linux. Permite inspecionar o que um programa esta a fazer num certo ponto de execução, inspecionar o valor das variaveis naquele momento, entre muitas outras coisas.

## Casos de uso

Usamos o `gdb`, ou qualquer outro debugger, para:
  1. Analisar um programa que crashou (segmentation fault)
  2. Analisar memory leaks
  3. Inspecionar o estado de alguma variavel durante a execução do mesmo

## Compilar

Para usar o `gdb` é preciso includir uma flag extra quando compilamos o nosso código com o `gcc`. A flag em questão é `-g`. Desto modo o `gcc` sabe que é para compilar o programa em debug mode e includi informação extra no executavel.

Um exemplo com mais flags à mistura.
```
gcc -g code.c
```

## Primeiros passos no GDB

Para usar o `gdb` é simples, assumindo que o terminal se encontra na diretoria do executavel:
```bash
gdb nome_do_executavel
```
O `gdb` possui uma shell interativa com um conjunto de comandos bastante completos.

O terminal do `gdb` tem TAB completion para maior parte dos comandos e `help <comando>` para mais informações sobre qualquer comando.

## Comandos essenciais

Para executar o programa:
```bash
(gdb) run
```
Assumindo que o programa tem erros, a linha que levou o programa a crashar é apresentada após correr o programa juntamente com mais alguma informação.

Se suspeitarmos que uma instrução é a culpada podemos mandar para o programa antes de a executar. Para isso impomos `breakpoints`. O programa para de executar **ANTES** de executar a instrução do `breakpoint`.

Para colocarmos um `breakpoint` na linha 20:
```bash
(gdb) break 20
```
Ou simplesmente
```bash
(gdb) b 20
```
Se o nosso programa tiver vários ficheiros `.c` associados temos que especificar o ficheiro:
```bash
(gdb) b random.c:20
```

Podemos também por um `breakpoint` associado a uma função diretamente:
```C
int test(char a, void* c){
    // codigo
  }
```
```bash
(gdb) b test
```

Agora que temos vários `breakpoints` quando corrermos o programa com `run` ele vai parar no primeiro `breakpoint` que encontrar.

Podemos continuar para o próximo com o comando `continue`, ou executar o programa linha a linha com o comando `step`.

Semelhante ao `step` existe o `next`, que trata uma função (por exemplo) como uma linha de código só, executando-a e parando depois.

Estar sempre a escrever `step` ou `next` é repetitivo, ao carregar no ENTER o `gdb` executa o ultimo comando outra vez.

Para remover um `breakpoint` podemos usar o comando `del <num>` onde `num` é o número do `breakpoint`. `del` apenas remove todos os `breakpoints`

## Estado das variaveis

As vezes é interessante estudar o estado das nossas variáveis.

#### Print
O comando `print` seguido de uma variável, imprime o valor da mesma.
```bash
(gdb) print var
```
É possível formatar este valor para outra base numérica.

Estar sempre a verificar o estado de uma variável a mão também é aborrecido e o `gdb` oferece duas alternativas.

#### Display
O comando `display` repete o `print` sempre que o gdb pára, ou seja, quando para num breakpoint, no fim de um `next` ou `step`.
```bash
(gdb) display var
```
Para parar de fazer `display` a uma variável basta fazer `undisplay <num>` onde o num é indicado atrás do valor da variável impressa.

#### Watch
O comando `watch` é uma espécie de `breakpoint` que interrompe a execução do programa quando a variável que esta a ser observada é alterada, fazendo print do novo e antigo valor.
```bash
(gdb) watch var
```
Atenção que se várias variáveis tiverem o mesmo nome o `gdb` escolhe qual observar por localidade.

## Abreviaturas
Porque a preguiça não tem limites, os commandos mais utilizados tem uma abreviatura disponível:

| comando  | abreviatura |
| -------- | ----------- |
|`break`   | `b`         |
|`print`   | `p`         |
|`run`     | `r`         |
|`next`    | `n`         |
|`step`    | `s`         |
|`continue`| `c`         |

