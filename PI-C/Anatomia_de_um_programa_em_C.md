# Anatomia de um programa


## Hello World

```c
#include <stdio.h>

// Comentário
/*
  Também
  um
  comentário
  */

int main() {
    printf("Hello, World!\n");

    return 0;
}
```
Este é o clássico programa HelloWorld escrito em C.

## Comentários

Tudo o que vem a seguir a `//` é um comentário em C. Tudo o que se encontra entre `/* */` é um comentário também.
  * `// Comment` Comentários de uma linha;
  * `/* Comment2.0 */` Comentários de várias linhas.

## Includes

Várias funções em C ja estão definidas, para as utilizarmos temos apenas que as importar.

Em C isso é feito utilizando a diretiva, de pré-processamento, `#include` seguida do ficheiro que contém a função pretendida entre  `<  >`.
Os ficheiros `.h`, header files, são os ficheiros importados que contêm todas as assinaturas das nossas funções (aprofundado mais à frente).

Visto que pretendemos escrever "Hello, World" no ecrã, temos que ter uma função que passe o nosso texto para o terminal (standard output). Essa função já existe em C e chama-se `printf`, mas para a usarmos temos que incluir (importar) o ficheiro que a define (tecnicamente é onde se encontra a sua assinatura). A função `printf` encontra-se no ficheiro `stdio.h`. Stdio é uma abreviatura para Standard Input Output.

Mas como sabemos onde se encontram as funções que queremos usar e como usá-las?
A resposta é simples, documentação.

Portanto `#include <stdio.h>` importa várias funções, sendo uma delas a `printf`, podemos então usar livremente esta função.


## Main e introdução as funções

A próxima linha de código é uma função muito importante, a função `main`.

A função `main` tem a particularidade de ser sempre executada quando corremos os nossos programas, além do mais é sempre a primeira!
Mas que raio é uma função em C?!?! Uma função em C é um conjunto de codigo C que é executado quando a função é chamada. Podemos pensar que sempre que a função `main` é chamada, tudo o que está entre chavetas é executado.

Isto leva-nos a mais duas definições:
  * Assinatura da função;
  * Corpo da função.

### Assinatura de uma função

A assinatura de uma função é o que a caracteriza, o caso da nossa `main` a sua assinatura é:
```c
int main()
/* o mesmo que
int main(null)
*/
```
Daqui podemos inferir que a função main devolve um `int` e não recebe argumentos.

De facto todas as assinaturas de funções seguem este esquema:
```c
tipo nome(tipo1 argumento1, tipo2 argumento2, tipo2 argumento3, [etc]);
```

### Corpo de uma função

O corpo de uma função, a sua implementação, é todo escrito entre chavetas. É aqui que o seu comportamento é definido.

No caso da nossa função `main` apenas chama a função `printf` e retorna 0.

### Como chamamos uma função?

Como podemos ver pelo nosso programa, chamamos uma função simplesmente usando o seu nome e passando os argumentos necessários.
```c
printf("Hello, World!\n");

/*
Aqui a função printf recebe um argumento:
 "Hello, World!\n"
*/
```

## Return
Como a nossa `main` é do tipo int, tal como já vimos nas assinaturas de funções, tem que devolver um int.
Esta operação de "devolver" é feita com o `return` em C. Como neste caso não tem grande significado, fazemos simplesmente return 0.

