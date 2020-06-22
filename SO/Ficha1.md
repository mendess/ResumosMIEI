# Open and Close

A primeira primitiva com que vamos trabalhar são ficheiros.

Em UNIX, *tudo são ficheiros*, fazendo destas funções as mais importantes
para interagir com o sistema operativo.

## API:

### Open

```c
int open(const char* path, int oflag);
int open(const char* path, int oflag, int permissions);
// `permissions` é normalmente chamado `mode`
```

#### Parâmetros

O `open` recebe o caminho para o ficheiro que se pretende abrir, o `path`, junto
com as flags `oflag` para indicar o modo de abertura, e são passadas da seguinte
forma.

```c
open("filename", O_FLAG1 | O_FLAG2 | ...);
```

As flags
- `O_RDONLY`: Abrir apenas para leitura
- `O_WRONLY`: Abrir apenas para escrever
- `O_RDWR`: Abrir para ler e para escrever

Indicam o modo de abertura e não podem ser misturadas umas com as outras, por
exemplo, `open("file", O_RDONLY | O_WRONLY)` está errado.

As outras flags que podem ser passadas indicam opções.
- `O_CREAT`: Cria o ficheiro se ele não existir.
- `O_APPEND`: Começa a escrever no fim do ficheiro em vez de no início.
- `O_TRUNC`: Apaga todo o conteúdo do ficheiro.
- etc, consultar a man page `man 2 open` para saber mais.

O parâmetro opcional `permissions`, permite especificar as permissões com que o
ficheiro deve ser criado (caso a flag `O_CREAT` seja especificada), este é o
mesmo número octal que pode ser passado para o `chmod` para especificar as novas
permissões do ficheiro.

**Nota:** Em C um número octal é começado por um `0`.

Por exemplo, para criar um ficheiro com permissões para leitura e escrita para o
dono e só leitura para todos os outros utilizadores, chama-se o `open` da
seguinte forma.

```c
open("file", O_CREAT | O_WRONLY, 0644);
```

**Nota:** Se não forem especificadas as permissões quão é criado um ficheiro as
permissões com que ele é criado não são especificadas, podendo ser qualquer
coisa.

#### Valor de retorno
O valor de retorno pode ter 2 significados.

Se for:
- `≥ 0`: um *file descriptor* que pode mais tarde ser passado a varias funções
  para interagir com o ficheiro aberto
- `= -1`: Indica que ocorreu algum erro

### Read
```c
ssize_t read(int fd, void* buf, size_t count);
```

#### Parâmetros
O `fd` é um *file descriptor* do ficheiro de onde ler.

O `buf` é um buffer/array para onde o conteúdo vai ser lido.

O `count` é quantos bytes no máximo devem ser lidos para dentro do buffer.

#### Valor de retorno
O valor de retorno pode ter 3 significados.

Se for
- `> 0`: Indica quantos bytes foram lidos
- `= 0`: Indica que o ficheiro terminou
- `= -1`: Indica que ocorreu algum erro

### Write
```c
ssize_t write(int fd, void const* buf, size_t count);
```

#### Parâmetros
O `fd` é um *file descriptor* de ficheiro para onde escrever.

O `buf` é o buffer/array onde ir buscar os dados que devem ser escritos.

O `count` é quantos bytes do `buf` devem ser escritos

#### Valor de retorno
O valor de retorno pode ter 2 significados.

Se for
- `≥ 0`: Indica quantos bytes foram escritos
- `= -1`: Indica que ocorreu algum erro

### Close
```
int close(int fd);
```
Fechar ficheiros é importante por varias razões, as mais importantes sendo:
- *Flushing*, não é garantido que mal um `write` é executado que o texto seja
  escrito em disco. Ao fechar o ficheiro correctamente temos essa garantia.
- Cada processo tem um máximo de *file descriptor* que pode ter abertos ao mesmo
  tempo.

#### Parâmetros
O `fd` a fechar

#### Valor de retorno
- ` 0`: Se foi fechado com sucesso.
- `-1`: Se ocorreu um erro.


## Standard Input, Output e Error
Todos os processos arrancam com 3 ficheiros abertos.
- `stdin`: Que é o standard input
- `stdout`: Que é o standard output
- `stderr`: Que é o standard error

E estes tem os *file descriptors* 0, 1 e 2, respectivamente, associados a cada
um.

Estes ficheiros, normalmente, apontam todos para o terminal em que o processo
foi iniciado.

O propósito destes ficheiros é permitir ao processo comunicar com o ambiente em
que se encontra (isto fará mais sentido mais a frente).

Por exemplo, para escrever um "Hello, World!" no terminal podemos usar a *system
call* `write`.

```c
#include <unistd.h>

int main(void) {
    char* s = "Hello, World!\n";
    write(1, s, strlen(s));
}
```

O standard error é deve ser usado para comunicar erros, mas no fundo é igual ao
standard output.


## Exemplo

Programa que copia no máximo 10 bytes de um ficheiro para outro. Tendo o cuidado
de verificar se algum erro ocorre e terminar o programa nesse caso.
```c
#include <unistd.h>
#include <fcntl.h>

int main(void) {
    int const source = open("file1", O_RDONLY);
    if (source == -1) {
        perror("Error opening file1");
        return 1;
    }
    int const dest = open("file2", O_WRONLY | O_CREAT | O_TRUNC, 0644);
    if (dest == -1) {
        perror("Error opening file2");
        return 1;
    }
    char buf[10];
    size_t const amount_read = read(source, buf, 10);
    if (amount == -1) {
        perror("Error reading file1");
        return 1;
    }
    size_t const amount_written = write(dest, buf, amount_read);
    if (amount == -1) {
        perror("Error writting file2");
        return 1;
    }
    if (amount_written < amount_read) {
        perror("Couldn't write everything");
        return 1;
    }
    close(source);
    close(dest);
}
```

## Nota sobre performance
As system calls `read` e `write` são das mais lentas e mais utilizadas pelo que
é preciso ter cuidado e tentar reduzir o número de vezes que são chamadas.

## Extra notes

- Os *magic numbers* `0`, `1` e `2` para o `stdin`, `stdout` e `stderr`
    respetivamente, podem ser substituidos pelas macros `STDIN_FILENO`,
    `STDOUT_FILENO` e `STDERR_FILENO` respectivamente.

- Sempre que uma função mecionadas assima retorna um erro, o tipo de erro que
    ocorreu pode ser visto na variavel global `errno`. `man errno` para aprender
    a usar a variavel.

## Extra reading

(https://fasterthanli.me/blog/2019/reading-files-the-hard-way/)[Reading files]
(https://fasterthanli.me/blog/2019/reading-files-the-hard-way-2/)[the hard]
(https://fasterthanli.me/blog/2019/reading-files-the-hard-way-3/)[way]

