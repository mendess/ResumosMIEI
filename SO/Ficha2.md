# Processos

Se ficheiros são a primeira primitiva do sistema, processos são a segunda (que
também são ficheiros, mas explico isso mais a frente)

Um processo é um programa animado, nada mais. Para o executar o sistema
operativo carrega-o para RAM e passa o controlo para o processo. Mas estes
processos tem de estar organizados de alguma forma.

## PID
Cada processo tem um id único, chamado `pid`, que o identifica e todos os
processos podem consultar o seu com `getpid`.
```c
pid_t getpid(void);
```
## Parenting 101
Outra parte importante da organização dos processos é a noção de pai e filho,
quando um processo quer criar outro processo tem de fazer um filho usando a
chamada `fork` e tal como o `pid` do próprio, um processo pode consultar o `pid`
do seu pai com `getppid`.
```c
pid_t getppid(void);
```
Importante notar que um processo não pode consultar a qualquer momento os `pid`s
dos seu filhos, mas há outras formas de conseguir isto.

## Forking
Para criar um processo novo, em sistemas Unix like, faz se uso da *system call*
fork. Quando um processo faz `fork` o sistema operativo cria uma copia de toda a
memoria [<sup>\[1\]</sup>][cowSpace] do *forking process* para um novo, e de
seguida retoma a execução dos dois.

**Exemplo**
```c
#include <unistd.h>
#include <stdio.h>

int main(void) {
    puts("Ola, estou sozinho!");
    fork();
    puts("Ola, não estou sozinho.");
    return 0;
}
```
O output deste programa é o seguinte:
```
Ola, estou sozinho!
Ola, não estou sozinho.
Ola, não estou sozinho.
```
### Utilidade do fork
```c
pid_t fork();
```
Outra implicação do fork, é que este "retorna duas vezes", uma no processo pai e
outra no processo filho e o valor de retorno é diferente para cada um destes.

Para o pai, o `fork` retorna o `pid` do processo filho criado, para o filho
retorna `0` para que este saiba que é o filho. Com isto podemos distinguir o
filho do pai.

**Exemplo**
```c
#include <unistd.h>
#include <stdio.h>

int main(void) {
    puts("Ola, estou sozinho!");
    pid_t filho;
    if ((filho = fork()) != 0) {
        printf(
            "Sou o pai, tenho pid %d, e o meu filho tem pid: %d\n",
            getpid(),
            filho
        );
    } else {
        printf(
            "Sou o filho, tenho pid %d, e o meu pai tem pid: %d\n",
            getpid(),
            getppid()
        );
    }
    puts("Ola, não estou sozinho.");
    return 0;
}
```
O output deste programa não é definido, porque a ordem pela qual os prints
acontecem não determinística. (Escrevam e compilem o programa para verificar).

Algo que é determinístico, no entanto, é que ambos vai escrever `Ola, não estou
sozinho`, isto porque enquanto que os ramos do `if` são exclusivos, todo o resto
não é. Isto é importante ter em conta para que não escrever código que faz
coisas a mais. Principalmente é importante para não criar *fork bombs*
acidentalmente.
```c
#include <unistd.h>
#include <stdio.h>

int main(void) {
    while(1) fork();
}
```
Este programa, quando executa cria um filho, que por si vai criar mais filhos
que vão criar mais filhos, etc... Até que o o número de `pid`s esgota e a
maquina fica inutilizável porque precisas de processos para matar processos.

## Wait e Exit status
Quando um processo termina retorna um *exit status*, pode servir para comunicar
ao seu pai que um erro ocorreu, `0` significando que não ocorreram erros e
diferente de `0` significa que um erro ocorreu.

Isto pode ser observado na *shell*:
```sh
$ cd ~
$ echo "$?" # a variável especial $? serve para verificar
$           # o exit status do ultimo programa
0           # correu tudo bem
$ cd pasta_que_nao_existe
bash: cd: pasta_que_nao_existe: No such file or directory
$ echo "$?"
1           # ocorreu um erro
```
Em C isto traduz se num conjunto de funções e um return especial. Primeiro o
return da `main` é o *exit status*.
```c
int main(void) {
    return 1;
}
```
este número, apesar de "ser um `int`", tem de ser um número positivo entre 0 e
255 (inclusive), qualquer número que não esteja dentro deste intervalo vai ser
reinterpretado dessa forma, por exemplo, `-1` é `255` porque apenas os *low 8
bits* do número são considerados.

### exit vs \_exit
As outras duas formas de terminar um processo com um *exit status* são com as
funções exit. A `exit` é mais complexa, como explicado em `man 3 exit`:

- Todas as funções registadas com `atexti` e `on_exit` são chamadas
- Todos os streams do `stdio` são flushed e fechados.
- Ficheiros temporários são removidos.

Finalmente `exit` chama `_exit`. Por isso, `_exit` é mais abrupta enquanto que
`exit` termina o processo normalmente.

### Wait, zombies e órfãos
Criar processos filhos vem com responsabilidades da parte do processo pai.

Para esperar que um processo filho termine temos as funções:
```c
pid_t wait(int* status);
pid_t waitpid(pid_t pid, int* status, int options);
```
Ambas retornam o `pid` do processo que terminou, e colocam no `status` o *exit
status* desse processo. Em caso de erro `-1` é retornado.

Enquanto que o `wait` retorna mal encontre um processo filho que tenha
terminado.

O `waitpid` por outro lado, permite um ter mais controlo sobre quando retornar.

O parâmetro `pid` pode tomar valores que não `pid`s, mas para além de `-1` (que
significa "qualquer processo"), estes saem fora da matéria da cadeira
[<sup>\[2\]</sup>][processGroups].

Ao parâmetro `options` pode ser passado `0` para não ativar nenhuma opção ou
as opções especificadas na *man page*, das quais a mais útil é `WNOHANG` que
permite ao `waitpid` não bloquear caso nenhum processo filho tenha terminado.

O que acontece ao processo filho se não fizermos wait?

Enquanto o processo pai existir os processos filhos que já terminaram são
designados de *zombies*, o sistema operativo mantém apenas a informação
necessária para que o processo possa ser *waited for* mais tarde. Mesmo assim,
enquanto o *zombie* existir uma entrada na tabela de processos continua a
existir e se esta tabela encher não é possível criar novos processos.

No caso de o pai terminar, normalmente, sem ter feito *wait* de todos os seus
filhos, estes tornam se órfãos e passam a ser filhos do processo `init` que tem
sempre `pid` 1.

O `init` é o primeiro processo que arranca sempre que o sistema operativo
inicia, tem sempre `pid` 1 e é o "pai de todos". Ele encarrega-se de
periodicamente chamar o `wait` para todos os seus filhos de forma a evitar ter
*zombies*, desta forma também serve como um *fall back plan* para processos
órfãos não se tornarem *zombies* mais tarde.

## Exit status

Quando um programa termina retorna uma valor entre `0` e `255`, e o pai desse
processo pode verificar esse número usando as macros `WIFEXITED` e
`WEXITSTATUS`. Estas tem de ser usadas em conjunto para evitar *undefined
behaviour*, sendo que `WEXITSTATUS` só pode ser usada caso `WIFEXITED` retornar
`1` (`true`).

```c
int status;
wait(&status);
if(WIFEXITED(status)) {
    puts("Child exited normally");
    int exit_status = WEXITSTATUS(status);
    printf("Exit status: %d\n", status);
}
```
A razão para a ter de ser verificar se o processo terminou normalmente é que
este pode ter terminado por receber um sinal, por exemplo, `SIGSEGV`
(segmentation fault), e neste caso o valor que vem em `status` não é um exit
status valido.

## Exemplos

### Procurar em paralelo.
```c
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <assert.h>

#define SIZE 1000 * 1024 * 1024
#define LINES 2

static char BIG_DATA[LINES][SIZE] = {0};

int child(size_t line) {
    for(size_t j = 0; j < SIZE; j++) {
        if (BIG_DATA[line][j] == 'x') {
            printf("Found it at: (%zu,%zu)\n", line, j);
            return EXIT_SUCCESS;
        }
    }
    return EXIT_FAILURE;
}

int main(int argc, char const* argv[]) {
    assert(argc > 2); // Can't bother handling lack of args
    // Putting the 'x' in the array to find it.
    BIG_DATA[atoi(argv[1]) % 2][atoi(argv[2])] = 'x';

    pid_t children[LINES];
    // Iniciar cada um dos filhos quer irão procurar o 'x'
    for (size_t i = 0; i < LINES; i++) {
        pid_t const pid = fork();
        if(pid == 0) {
            return child(i);
        } else {
            // Guardar o pid de cada um para mais tarde...
            children[i] = pid;
        }
    }

    for(int i = 0; i < LINES; i++) {
        int status;
        // ... esperar por cada um para saber se encontraram ou não o 'x'
        pid_t const p = waitpid(children[i], &status, 0);
        // O waitpid tem de retornar o pid do processo que foi encontrado.
        // Logo se o `p` != `children[i]` então ocorreu algum erro ao esperar.
        // Apenas se pode consultar o exit status se o processo terminou normalmente
        // Logo WIFEXITED(status)
        if (p == children[i] && WIFEXITED(status)) {
            if(WEXITSTATUS(status) == EXIT_SUCCESS) {
                printf("Process #%d found the 'x'\n", i);
            } else {
                printf("Process #%d didn't find the 'x'\n", i);
            }
        } else {
            printf("Something went wrong with process #%d\n", i);
        }
    }
}
```

## Extra notes

1. Apesar da semântica do fork ser que toda a memoria do processo é copiada, na
   verdade nenhuma é copiada, apenas quando um dos processos a tenta alterar é
   que esta é copiada e só depois é que é alterada. Esta técnica tem o nome de
   *copy on write*.

2. Como explicado na man page do `waitpid` este pode operar sobre *process
   groups*, por defeito todos os processos pertencem ao mesmo grupo do processo
   pai, mas por exemplo a grande maioria das *shells* colocam os processos que o
   utilizador inicia em grupos diferentes da própria *shell*. Os grupos são
   importantes para a propagação de sinais, sendo possível enviar
   [sinais](./Ficha7_Signals.md) a um grupo e não apenas a um processo.


[cowSpace]: ./Ficha2.md#extra-notes
[processGroups]: ./Ficha2.md#extra-notes
