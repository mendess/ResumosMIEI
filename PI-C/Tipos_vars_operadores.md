# Tipos, variaveis e operadores

## Tipos

C tem vários tipos de dados simples. Estes são chamados tipo de dados primitivos. Outros tipos de dados mais complexos podem ser construidos a partir destes.

1. `char` um caracter ASCII;
2. `int` inteiro;
3. `float` um número real;
4. `double` um número real mais preciso;

Estes são os tipos mais comuns em C, mas ainda temos algums 'derivados' deles:
1. `long double` um número real ainda mais preciso
  * Implementação obscura, não usar.
2. `short int` um inteiro mais pequeno;
3. `long int`  um inteiro maior;
4. `unsigned int` um inteiro sem ser negativo;

Podem estar a pensar, onde é que estão os booleanos?!?

Pois bem, em C 'não existem booleanos' propriamente ditos.
Utiliza-se o `0` como valor para `false` e tudo o resto para `true`.


Podem também reparar que não falamos do tamanho de cada tipo. Na verdade o C apenas define um tamanho minimo para cada tipo de dados.

## Variaveis

Variaveis são 'caixas' que guardam valores.
A declaração de uma variavel, reserva e da um nome a uma area na memoria, que vai guardar um valor de um tipo especifico.
C é uma linguagem fortemente tipada. Isto é, cada variavel tem um tipo declarado e bem definido.

A forma de declarar uma variavel em C é:
```c
tipo nome;
```
Mais concretamente:
```c
int x;
```
Cria uma variavel chamada `x` do tipo `int`. `x` corresponde a uma area na memmoria que pode guardar um `int`.

Como podemos atribuir valores a variaveis?

É simples:
```c
int y;
y = 5;

// ou

int x = 5;
x = 3; // o valor de uma var pode mudar
```
**Notar que ao fazermos `int x;` o espaço de memoria reservado nao é limpo**
Ou seja, até atribuirmos um valor, `x` pode conter lixo.

### Alguns detalhes

1. As variaveis em C são case sensitive;
2. As variaveis em C podem começar com um `_`, ou uma letra. Podem conter números;
    * Pratica comum `int ponto;`;
    * Não recomendado numerar variaveis, começar com `_`.

3. Podemos declarar variaveis do mesmo tipo seguidas
```c
float x, y, z;
```
## Operadores

O operador para atribuir um valor a uma variavel é o `=`.

Os operadores para comparar variaveis (ou variaveis e valores):
1. `==` Para igualdade;
2. `!=` Para desigualdade;
3. `>` Maior do que;
4. `>=` Maior ou igual do que;
5. `<` Menor do que;
6. `<=` Menor ou igual do que.

Operadores matemáticos
1. `+` Soma;
2. `-` Subtração;
3. `*` Multiplicação;
4. `/` Divisão;
5. `%` Resto da divisão.

Operadores lógicos
1. `!` Negação;
2. `||` Or;
3. `&&` And;

### Operadores que manipular variaveis

Estar sempre a fazer
```c
int x = 5
// ...
x = x + 5;
```
quando queremos aumentar (ou diminuir) o valor da nossa variavel é cansativo, podemos simplesmente fazer:
```c
int x = 5
// ...
x += 5;
```
Pode-se ler como, `x` toma o valor actual de `x` mais 5.
Isto funciona para todas as operações matemáticas.


Caso do `++` e do `--`

```c
int x = 5;

x++; // x tem o valor de 6 agora

// equivalente a fazer
x = x + 1;
```
Por analogia
```c
int x = 5;

x--; // x tem o valor de 4 agora

// equivalente a fazer
x = x - 1;
```

Podemos utilizar estes operadores antes da variavel, mas o aumento ou decrescimo é feito antes de avaliar a variavel.
```c
int x = 5;
int y = 3;

x = y++;
// x tem valor 3
// y tem valor 4
```
Mas...
```c
int x = 5;
int y = 3;

x = ++y;
// x tem valor 4
// y tem valor 4
```
