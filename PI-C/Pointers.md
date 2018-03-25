# Pointers
Um apontador é apenas isso. Uma variavel que aponta, neste caso, para um endereço
 em memoria. Estes são extremamente poderosos mas também perigosos.

## Sintaxe
Com apontadores vem dois "novos" simbolos: `*` e `&`.

* O `*` é usado para aceder ao valor apontado pela variavel.
* O `&` é usado para aceder ao endereço de memória da variavel.

Para além disto o `*` é também usado para indicar que o tipo de alguma coisa é
 um apontador. Por exemplo:
```C
int main(){
    int x = 2;  // [1]
    int *x_ptr; // [2]

    x_ptr = &x; // [3]
    *x_ptr = 4; // [4]

    printf("X == %d\n",x); // [5]
}
```
 1. `x` é do tipo `int` e tem o valor `2`;
 2. `x_ptr` é um apontador para um `int`; [<sup>\[1\]</sup>][extraNotes]
 3. `x_ptr` está agora a apontar para o endereço da variavel `x`;
 4. O valor da variavel apontada por `x_ptr` passa a ser 4, aceder ao valor de um apontador chama-se _diferenciar o apontador_;
 5. Este `printf` imprime `X == 4` no ecra;

## Passagem por referencia e passagem por cópia
Se quisermos que um variavel local seja alterada por uma função podemos passar um apontador
 para esta.

```C
void func(int x, int *y_ptr){
    x = 2;
    *y_ptr = 2;
}

int main(){
    int x = 4;
    int y = 4;
    func(x, &y);
    printf("x: %d, y: %d\n",x,y);
    return 0;
}
```
Este código imprime no ecra `x: 4, y: 2`. Isto porque a variavel `x` é *"passed by value"*,
 ou seja, o seu valor é copiado para ser passado para a função. Que é como quem diz, uma nova variavel local é criada dentro da função `func(x)` e toma o valor passado quando a função é chamada. O `x` da main e o `x` da func são variaveis diferentes, apenas têm o mesmo valor.
Por outro lado, `y` é *"passed by reference"*, ou seja, é passado um apontador para a variavel que é local à `main`, podendo então a `func` alterar o valor desta.

## Arrays
Os arrays em C são na verdade apontadores para o primeiro indice do array. Os elementos de um array são guardados
 em memória de forma contigua, ou seja, o segundo elemento do array começa no bit a seguir ao ultimo bit
 do primeiro elemento, e assim sucessivamente.

*Assuma nesta secção que o nosso sistema guarda inteiros com quatro bits.*

`int array[] = {1,2,3};`

Será guaradado em memoria da seguinte forma

```
 mem   endereço
+----+
|0001| 0x101
|0010| 0x102
|0011| 0x103
+----+
```

na verdade `array` é uma varaivel do tipo `int *` e, analogamente pode ser declarado
 dessa forma: `int *array = {1,2,3}`, é equivalente.

**Escrever `int array[]` é exatamente igual a escrever `int *array`**

Sabendo que um array é na verdade um apontador para o primeiro elemento temos então que:
```C
    printf(" array == %p\n",array);
    printf("*array == %d\n",*array);
```
irá produzir o seguinte output:
```
 array == 0x101
*array == 1
```

## Aritmética de apontadores
Aritmética de apontadores permite escrever código mais sucinto mas, se for mal usada, por fazer com que este fique mais dificil de ler
 e, consequentemente, de corrigir.

Continuando com o exemplo anterior do nosso array, podemos aceder ao apontador do segundo elemento do array:
```C
    printf("array+1 == %p\n",array+1);
```
Output:
```
array+1 == 0x102
```

E seguindo o padrão sintatico das secções anteriores podemos também usar esta aritmética para aceder ao segundo elemento
 do array:
```C
    printf("  array[1] == %d\n",array[1]);
    printf("*(array+1) == %d\n",*(array+1));
```
Output:
```
  array[1] == 2
*(array+1) == 2
```

## Matrizes aka Arrays de Arrays
*Para esta secção assuma vou usar* `[` *e* `﹇` *para representar o inicio de um array,* `]` *e* `﹈` *para o fim de um array.
 Também vou usar* `·->` *para simbolizar um apontador.*

Um matriz é, na verdade, um array de arrays.
```
int matrix[][] = {{1,2,3},{4,5,6},{7,8,9}};
﹇
[ 1, 2, 3 ]
[ 4, 5, 6 ]
[ 7, 8, 9 ]
﹈
```
e, se um array é na verdade um apontador para o primeiro elemento, então uma matriz é um array de apontadores.
```C
int *matriz[] = {{1,2,3},{4,5,6},{7,8,9}};
﹇
·-> 1
·-> 4
·-> 7
﹈
```
Que pode ser lido como "Array de apontadores para `int`"

O resto dos elementos de cada array estão armazenados contiguamente na memória mas, sem usar aritmética de apontadores
 (nem acessos como `matriz[x][y]`), na verdade cada posição do "array principal" apenas aponta para o primeiro elemento
 de cada um dos seus arrays.

Mas se um array é um apontador para o primeiro elemento, então:

```C
int **matriz = {{1,2,3},{4,5,6},{7,8,9}};
·
|
V
·-> 1
```
Que pode ser lido como "Apontador para um apontador de `int`"

Ou seja, uma matriz é na verdade um apontador para um apontador.

**Relembro que todas estas maneiras de declarar a matriz são sintaticamente equivalentes!
 Fazem exatamente a mesma coisa. É apenas uma prespetiva diferente de olhar para as coisas.**

`int matriz[][] === int *matrix[] === int **matrix`

## Extra Notes
 1. Esta sintaxe para declarar um apontador é flexivel, isto é, `int *x`, `int* x` e `int * x` são todas equivalentes.

[extraNotes]: https://github.com/Mendess2526/ResumosMIEI/blob/writing/PI-C/Pointers.md#extra-notes
