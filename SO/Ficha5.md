# Pipes anónimos

Uma das formas que os processos têm para comunicar entre si é através de
ficheiros "normais", mas este meio de comunicação é muito lento, pois requer
que os dados sejam escritos para disco, para além de termos que lidar com
nomes, permissões, etc. 

Para evitar esses problemas podemos usar `pipes`.

Os `pipes`, neste caso `pipes anónimos`, são *buffers* em memória, que permitem 
que um processo comunique com outro processo, no caso dos pipes anónimos com
processos criados por ele próprio ou pelos seus filhos.

## Criação de pipes anónimos

Para criar um pipe anónimo usamos a função `int pipe(int pipefd[2])`, que
recebe como argumento um array de dois inteiros e que vão passar a atuar como os
extremos de leitura e de escrita do pipe.

A função retorna `0` em caso de sucesso e `-1` caso tenha ocorrido algum erro e não
tenha sido possível criar o pipe.

Exemplo de utilização da função `pipe`:
```C
#include <unistd.h>

int main() {
    int pipefd[2]; // o nome deste array pode ser qualquer coisa
    if(pipe(pipefd) < 0) {
        puts("Erro na criação do pipe!");
        exit(1);
    }
    // Neste momento:
    // pipefd[0] é o extremo de leitura do pipe
    // pipefd[1] é o extremo de escrita do pipe
}
```

## Comunicação através de pipes anónimos

A comunicação através de pipes anónimos é de apenas uma via, i.e., um processo 
escreve para o pipe e outro processo lê do pipe. O kernel assegura a sincronização
entre escritas e leituras.

Um processo que apenas lê de um pipe deve fechar o extremo de escrita, e vice-versa,
para evitar problemas como *deadlocks*.

#### Leitura

A tabela seguinte ilustra o comportamento da função `read(pipefd[0], buffer, BUFFERSIZE)`,
onde `pipefd[0]` é o extremo de leitura do pipe, tal como foi definido no exemplo acima.

| Há dados no pipe? | Extremo de escrita aberto | Extremo de escrita fechado |
|-------------------|-----|-----|
| Sim | Lê dados e escreve-os no buffer | Lê dados e escreve-os no buffer |
| Não | Bloqueia | Termina normalmente sem escrever nada no buffer |

Quando a função bloqueia, fica num estado suspenso, à espera de uma de duas coisas, 
ou que outro processo escreva no extremo de escrita, dando à função algo para ler,
ou que o extremo de escrita seja fechado em todos os outros processos com acesso ao pipe.

#### Escrita

A tabela seguinte ilustra o comportamento da função `write(pipefd[1], buffer, BUFFERSIZE)`,
onde `pipefd[1]` é o extremo de escrita do pipe, tal como foi definido no exemplo acima.

| Extremo de leitura está | Há espaço | Não há espaço |
|-------------------|-----|-----|
| Fechado | Mata escritor (SIGPIPE) | Mata escritor (SIGPIPE) |
| Aberto | Escreve do buffer para o pipe | Bloqueia |

O sinal `SIGPIPE` indica que tentámos escrever num pipe cujos extremos de leitura
estão fechados. Isto diz-nos que, para podermos escrever num pipe, este tem que
ter pelo menos um extremo de leitura aberto.

#### Exemplo de leitura e escrita num pipe

Neste exemplo, um processo-pai envia a um processo filho a mensagem "Bom dia",
que a imprime no terminal.

```C
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>

int main() {
    int pipefd[2];
    if(pipe(pipefd) < 0) {
        perror("Erro na criação do pipe!");
        exit(1);
    }

    if(fork() == 0) { // Processo-filho

        // O processo-filho não vai escrever no pipe, portanto podemos fechar o extremo de escrita.
        close(pipefd[1]);

        char buf[10];
        int bytes_read = read(pipefd[0], buf, 10); // Estamos a ler do extremo de leitura do pipe.
        close(pipefd[0]); // Podemos fechar o extremo de leitura pois já não precisamos de ler do pipe.

        write(STDOUT_FILENO, buf, bytes_read);
        _exit(0);
    } 

    else { // Processo-pai

        // O processo-pai não vai ler do pipe, portanto podemos fechar o extremo de leitura.
        close(pipefd[0]);

        char * str = "Bom dia";
        write(pipefd[1], str, strlen(str)); // Estamos a escrever para o extremo de escrita do pipe.
        close(pipefd[1]); // Podemos fechar o extremo de leitura pois não vamos voltar a escrever no pipe.

        wait(NULL);
    }

    return 0;
}
```

O *output* deste programa será:
> Bom dia

## Pipes para encadeamento de comandos

Um dos principais usos de pipes anónimos é no encadeamento de comandos. Se tivermos
um ficheiro `foo.txt` que contém:
```
Bom dia!
Hoje está um dia agradável.
Tenho que estudar Sistemas Operativos.
```
e quisermos obter o número de linhas neste ficheiro, podemos usamos o seguinte comando:
```bash
$ cat foo.txt | wc -l
```
que, na verdade, é a combinação de dois comandos, um que dá como *output* o conteúdo
do ficheiro `foo.txt` e outro que determina o número de linhas do *input* fornecido.

Aquele `|` permite-nos usar o *output* de um comando como *input* de outro. É 
possível emular esse comportamento em C com o uso de pipes.

Este exemplo mostra como tal é possível.
```C
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>

int main() {
    int pipefd[2];
    if(pipe(pipefd) < 0) {
        perror("Erro na criação do pipe!");
        exit(1);
    }


    if(fork() == 0) { // Primeiro filho, irá executar `cat foo.txt` e enviar o 
                      // seu output para o extremo de escrita do pipe.

        close(pipefd[0]); // Este filho não irá ler do pipe.

        dup2(pipefd[1], STDOUT_FILENO); // O standard output deste filho passa a
                                        // ser o extremo de escrita do pipe.

        close(pipefd[1]); // Este file descriptor já não é necessário, pois está
                          // agora duplicado no STDOUT_FILENO.
        
        execlp("cat", "cat", "foo.txt", NULL);
        _exit(1);
    }


    close(pipefd[1]); // Não iremos voltar a escrever no pipe, portanto podemos
                      // fechar o extremo de escrita.


    if (fork() == 0) { // Segundo filho, irá executar `wc -l` usando como input 
                       // o extremo de leitura do pipe.

        dup2(pipefd[0], STDIN_FILENO); // O standard input deste filho passa a 
                                       // ser o extremo de leitura do pipe.
        
        close(pipefd[0]); // Este file descriptor já não é necessário, pois está
                          // agora duplicado no STDIN_FILENO.
        
        execlp("wc", "wc", "-l", NULL);
        _exit(1);
    }

    close(pipefd[0]); // Também já podemos fechar o extremo de leitura.

    wait(NULL);
    wait(NULL);

    return 0;
}
```

O *output* deste programa será:
> 3