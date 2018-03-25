# Pointers
Um apontador é apenas isso. Uma variavel que aponta, neste caso, para um endereço
 em memoria. Este são estremamente poderosos mas também perigosos

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
 1. `x` é do tipo `int` e tem o valor `2`
 2. `x_ptr` é um apontador para um `int`
 3. `x_ptr` está agora a apontar para o endereço da variavel `x`
 4. O valor da variavel apontada por `x_ptr` passa a ser 4;
 5. Este `printf` imprime `X == 4` no ecra.

## Pointers usados com funções PRECISO DE UM TITULO MELHOR
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
 ou seja, o seu valor é copiado para ser passado para a função. Por outro lado, `y` é
 *"passed by reference"*, ou seja, é passado um apontador para a variavel que é local à
 `main`, podendo então a `func` alterar o valor desta.

## Arrays
Os arrays em C são na verdade apontadores para o primeiro indice do array. Os arrays são guardados
 em memória da seguinte forma.

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


