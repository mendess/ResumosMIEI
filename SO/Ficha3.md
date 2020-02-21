# Exec

Agora que conseguimos criar novos processos podemos também executar novos
programas.

Para isto vamos fazer uso da família de funções `exec`.

Estas tem todas o mesmo comportamento geral mas chegam a este de maneiras
ligeiramente diferentes que veremos mais a frente.

Quando se executa um programa todo a memória do processo actual é substituída
pela do novo programa, isto implica stack, heap, e instruções. De forma mais
simples, o processo que chama a função transforma-se completamente noutro.

Isto tem algumas implicações, por exemplo, código que vem a seguir a um `exec`
só executa caso o mesmo falhe.

As 4 principais "implementações" deste `exec` variam em:

##### Onde vão procurar o executável (primeiro argumento)
- Caminho absoluto: O executável tem de estar na *current working directory* ou
    o caminho para este tem de ser absoluto;

- No PATH: `PATH` é um variável de ambiente que define uma lista separada por
    `:` de directorias onde executáveis podem devem ser procurados.
    - `$ echo $PATH | tr ':' '\n'` para ver o `PATH` actual.

##### Como recebem os argumentos que devem passar para o programa (resto dos argumentos)

- Numa lista de argumentos, terminada por `NULL`;
- Num array de `char*`, terminado por `NULL`.

Os nomes destas funções são os seguintes:

|           |Absolute|  Path (p) |
|   :---:   | :----: |   :---:   |
| **List (l)**  | `execl`  |   `execlp`  |
| **Array (v)** | `execv`  |   `execvp`  |

Outro paralelo importante de salientar é que os argumentos passados a estas
funções são os mesmo que serão mais tarde passados para a `main` do programa
executado sem qualquer alteração, logo deve de seguir as mesmas convenções:
- O primeiro argumento (`argv[0]`) é o nome do próprio programa.
- Todos os outros parâmetros não tem restrições
- A lista é terminada por `NULL`.

O facto do primeiro argumento do `exec` e o primeiro argumento do programa serem
ambos o nome deste (na maior parte dos casos) faz com que este seja repetido,
como se pode ver nos exemplos seguintes.

## Exemplos
Todos estes exemplos são equivalentes
```c
#include <unistd.h>

int main() {
    execl("/bin/ls", "ls", "-l", NULL);
}
```
```c
#include <unistd.h>

int main() {
    execlp("ls", "ls", "-l", NULL);
}
```
```c
#include <unistd.h>

int main() {
    char *const args[] = { "ls", "-l", NULL };
    execv("/bin/ls", args);
}
```
```c
#include <unistd.h>

int main() {
    char *const args[] = { "ls", "-l", NULL };
    execvp("ls", args);
}
```

## Fork + Exec

`fork` + `exec` é um idioma muito comum em Unix para criar novos processos que
executam programas diferentes. Como o `exec` substitui a imagem atual do
processo em memória pela imagem do novo, não é conveniente fazê-lo no "processo
principal".

Um exemplo de uma shell simples (e com muitos problemas e falhas) pode ser feita
com este padrão.
```c
#include <stdio.h>
#include <unistd.h>

// retorna uma linha do ficheiro pedido
char* read_line(int fd);

int main(void) {
    char* line;
    while ((line = read_line(0)) != NULL) {
/* 1 */ if(fork() == 0) {
/* 2 */      execlp(line, line, NULL);
/* 3 */      perror("Couldn't execute the command");
/* 4 */      _exit(1);
        }
        int status;
        wait(&status);
        if (!WIFEXITED(status) || WEXITSTATUS(status) != 0) {
            printf("Error: %d\n", status);
        }
        free(line);
    }
}
```
Esta *shell* básica tem alguns problemas, por exemplo, não permite passar
argumentos aos programas (`ls -l` não funciona), mas isso não é importante.

A parte importante deste exemplo são as quatro linhas comentadas:

1. O fork é usado para criar um processo filho que será substituído pelo novo
   programa.
2. O `execlp` tenta executar o programa pedido, se isto for possível, mais
   nenhum código deste programa vai ser executado pelo processo filho.
3. Por isso é que necessário salvaguardar para quando o `exec` falha, a prática
   normal é imprimir uma mensagem de erro...
4. ... e terminar o processo filho. A escolha de `_exit` em vez de `exit` neste
   local é para evitar que o processo filho faça mais para além de do esperado.
   (Ver o resumo sobre [Fork](./Ficha2.md), para ver a diferença entre as duas)

Este idioma vai ser expandido, adicionando mais capacidades, nos próximos
resumos, pelo que é preciso compreendê-lo bem.


## Extra notes
- Reza a lenda que no inicio os programas `mv`, `cp` e `rm` eram na verdade o
    mesmo binário, olhando para o `argv[0]` para determinar o que tinham de
    fazer:
    - `mv`: Copiar + Apagar o original
    - `cp`: Copiar
    - `rm`: Apagar o original
