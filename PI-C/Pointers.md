# Pointers
Um apontador é apenas isso. Uma variável que aponta, neste caso, para um endereço
em memoria. Estes são extremamente poderosos mas também perigosos.

## Sintaxe
Com apontadores vem dois "novos" símbolos: `*` e `&`.

* O `*` é usado para aceder ao valor apontado pela variável.
* O `&` é usado para aceder ao endereço de memória da variável.

Para além disto o `*` é também usado para indicar que o tipo de alguma coisa é
um apontador. Por exemplo:

```c
int main(){
    int x = 2;  // [1]
    int* x_ptr; // [2]

    x_ptr = &x; // [3]
    *x_ptr = 4; // [4]

    printf("X == %d\n",x); // [5]
}
```

 1. `x` é do tipo `int` e tem o valor `2`;
 2. `x_ptr` é um apontador para um `int`; [<sup>\[1\]</sup>][extraNotes]
 3. `x_ptr` está agora a apontar para o endereço da variável `x`;
 4. O valor da variável apontada por `x_ptr` passa a ser 4, aceder ao valor de
    um apontador chama-se _diferenciar o apontador_, mas o termo mais usado é o
    inglês _dereference_;
 5. Este `printf` imprime `X == 4` no ecrã;

**Nota:** a o local onde o `*` é colocado na declaração de um tipo não é
relevante. Todos os exemplos seguintes querem dizer o mesmo:

```c
int* a;
int *a;
int * a;
int /* sou um apontador */ * /* chamado 'a' */ a;
```

## Passagem por referencia e passagem por cópia

Se quisermos que um variável local seja alterada por uma função podemos passar
um apontador para esta.

```c
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
Este código imprime no ecrã `x: 4, y: 2`. Isto porque a variavel `x` é *"passed
by value"*, ou seja, o seu valor é copiado para ser passado para a função. Que é
como quem diz, uma nova variável local é criada dentro da função `func` e
toma o valor passado quando a função é chamada. O `x` da `main` e o `x` da
`func` são variáveis diferentes, apenas têm o mesmo valor.
Por outro lado, `y` é *"passed by reference"*, ou seja, é passado um apontador
para a variável que é local à `main`, podendo então a `func` alterar o valor
desta.

## Arrays

Os arrays em C podem ser tratados como apontadores para o primeiro elemento do
array. Os elementos de um array são guardados em memória de forma contigua, ou
seja, o segundo elemento do array começa no bit a seguir ao ultimo bit do
primeiro elemento, e assim sucessivamente.

*Assuma nesta secção que o nosso sistema guarda inteiros com quatro bits.*

`int array[] = {1,2,3};`

Será guardado na stack da seguinte forma

```
 mem   endereço
+----+
|0001| 0x101
|0010| 0x102
|0011| 0x103
+----+
```

Sabendo que um array pode ser tratado como um apontador para o primeiro elemento
podemos fazer isto:

```c
    // imprimir o endereço do primeiro elemento
    printf(" array == %p\n", array);
    // imprimir o primeiro elemento
    printf("*array == %d\n", *array);
```
irá produzir o seguinte output:
```
 array == 0x101
*array == 1
```

## Aritmética de apontadores

Aritmética de apontadores permite escrever código mais sucinto mas, se for mal
usada, por fazer com que este fique mais difícil de ler.

Continuando com o exemplo anterior do nosso array, podemos aceder ao apontador
do segundo elemento do array:

```c
    printf("array + 1 == %p\n", array + 1);
```

Output:
```
array + 1 == 0x102
```

E seguindo o padrão sintáctico das secções anteriores podemos também usar esta
aritmética para aceder ao segundo elemento do array:
```c
    printf("*(array + 1) == %d\n", *(array + 1));
    // que é exactamente igual a usar o operador de indexação []
    printf("  array[1]   == %d\n", array[1]);
```
Output:
```
*(array + 1) == 2
  array[1]   == 2
```

## Passar arrays como parâmetro de uma função

Como vimos um array pode ser tratado como um apontador para o primeiro, e como
tal não tem mais informação que essa. Por esta razão, (quase) sempre que
passamos um array por parâmetro de uma função temos de também passar o tamanho
deste.

```c
void print_ints(int* ints, size_t n_ints) {
    for (size_t i = 0; i < n_ints; ++i) {
        printf("%d\n", ints[i]);
    }
}

int main() {
    int arr[] = {1, 2, 3};
    print_ints(arr, 3);
}
```

## Memoria dinâmica

Outra fonte de apontadores é memoria dinâmica, também conhecida como a _heap_.
Esta serve para nos dar mais controlo sobre o tempo de vida de uma variável e
varias outras coisas.

Para esta secção vamos apenas ver como criar arrays com memoria dinâmica.

```c
int* a = malloc(sizeof(int) * 10); // [1]

for (size_t i = 0; i < 10; ++i) {
    a[i] = i * i; // [2]
}

free(a); // [3]
```

### Aumentar o comprimento do array

Uma das vantagens de utilizar memoria dinâmica é que podemos facilmente
"alterar" o comprimento do array depois dele ser criado, com a função `realloc`:

```c
int* a = malloc(sizeof(int) * 10);

for (size_t i = 0; i < 10; ++i) {
    a[i] = i * i;
}

a = realloc(a, sizeof(int) * 20); // [1]

for (size_t i = 10; i < 20; ++i) { // [2]
    a[i] = i * i;
}

free(a); // [3]
```

Alterando o exemplo anterior, depois de inicializar o array podemos aumentá-lo:
 1. O `realloc` recebe o pointer antigo e o novo tamanho que nós queremos para o
    nosso array, pode ser maior ou menor que o original. O que o `realloc` irá
    fazer é alocar um array novo com o novo tamanho e copiar o elementos do
    array antigo para o novo, que retorna.
 2. Ao preencher o array não temos de preencher os que já estavam preenchidos,
    visto que o `realloc` fez esse trabalho por nós. Logo podemos começar o loop
    em `10`.
 3. Libertar a memoria funciona da mesma forma, não temos de libertar o `a` que
    passamos ao `realloc` visto que ele faz isso por nós. Só temos de libertar o
    novo que ele retornou. Mantendo assim a regra: para cada `malloc` tem de
    haver um `free`.

### Bonus: calloc

O `calloc` é praticamente idêntico ao `malloc`, mas a memoria retornada por este
segundo vem com todos os bits a 0. Para alem disto este recebe os elementos da
multiplicação separados, primeiro o número de elementos e depois o tamanho
destes.

```c
// com malloc
int* a = malloc(sizeof(int) * 10);
memset(a, 0, sizeof(int) * 10); // preencher com 0 todos os bits

// com calloc
int* a2 = calloc(10, sizeof(int));
```

Tirando estes aspetos de alocação, inicialização e destruição, estes arrays
podem ser usados da mesma forma que os anteriores que eram alocados na stack.

## Matrizes aka Arrays de Arrays
*Para esta secção assuma que* `[` *e* `﹇` *são usados para representar o inicio
de um array,* `]` *e* `﹈` *para o fim de um array.

É usado* `·->` *para simbolizar um apontador.*

Um matriz é, na verdade, um array de arrays.

Para criar uma matriz só temos de seguir o mesmo padrão de criação de arrays.

Para criar a seguinte matriz na heap.
```c
﹇
[ 1, 2, 3 ],
[ 4, 5, 6 ],
[ 7, 8, 9 ]
﹈
```

Podemos fazer o seguinte.

```c
int** matriz = malloc(sizeof(int*) * 3); // [1]

for (size_t i = 0; i < 3; ++i) {
    matriz[i] = malloc(sizeof(int) * 3); // [2]
    for (size_t j = 0; j < 3; ++j) {
        matriz[i][j] = (i * 3) + j // [3]
    }
}
```

1. Alocamos espaço para um array de 3 pointers. Para isso passamos ao `malloc` 3
   vezes o tamanho de um pointer.
2. Depois inicializamos cada um desses pointers como o apontador para um novo
   array de 3 `int` como já fizemos na secção anterior.
3. Por fim preenchemos cada um dos arrays com os números.

No fim o que acontece em memória é o seguinte:

```c
matriz
·
|
v
﹇
·-> [ 1, 2, 3 ]
·-> [ 4, 5, 6 ]
·-> [ 7, 8, 9 ]
﹈
```

Esta não é a unica forma de criar uma matriz na heap, aliás, esta é a forma mais
intuitiva mas também mais lenta. Dependendo do que se vai fazer com a matriz,
esta pode ser toda alocada de uma so vez: `int* matriz = malloc(sizeot(int) * linhas * colunas)`.
Se fizermos isto o compilador não nos vai ajudar com a syntax `matriz[i][j]`,
pois para ele isto é apenas um array e não uma matriz, mas se fizermos o
trabalho dele por ele conseguimos dar a volta a este problema:
`*(matriz + (i * colunas) + j)`.


# Matrizes na stack (extra)

Ao criar uma matriz na stack desta forma, não vamos ter um array de apontadores,
porque todos os elementos estão guardados consecutivamente como se fosse na
verdade um array de N * M.

```c
int matriz[][3] = { {1,2,3}, {4,5,6}, {7,8,9} };
﹇
[ 1, 2, 3 ],
[ 4, 5, 6 ],
[ 7, 8, 9 ]
﹈
```

E, se um array é na verdade um apontador para o primeiro elemento, então uma
matriz pode ser vista um apontador para a primeira linha (primeiro array).

```c
// esta sintaxe é estranha, eu sei, mas praticamente nunca a vão ter de utilizar
// mas fica o tease para os que quiserem ir mais fundo

int (*matriz2)[3] = matriz;

matriz2 ·-> [ 1, 2, 3 ]
```

Pode ser lido como "apontador para array de 3 elementos", ou, "array de arrays de
3 elementos", ou ainda, "matriz com linhas de tamanho 3"
