# Arrays
Arrays são umas das estruturas de dados mais simples (a seguir a tipos primitivos) a que temos acesso em C e muitas outras
 linguagens imperativas.

Consistem numa lista de tamanho **estático** onde podemos guardar elementos de um determinado tipo.
```C
    int array[10];
```
Acima está declarado um array de inteiros com 10 posições. Tal como as variaveis tem valores *aleatorios* quando não são inicializadas
 os arrays seguem o mesmo padrão. Não sabemos portanto que valores estão no array.

## Declaração e inicialização
Em `C` podemos declarar e inicializar um array de inteiros estático (iremos falar de arrays dinamicos mais a frente) de 3 maneiras:
 * Explicitamente declarar os seus conteudos
```C
    int array[] = {0,0,0,0};
```
 * Declarar o seu tamanho e o conteudo de todas as posições
```C
    int array[4] = {0};
```
 * Declarar o seu tamanho e manualmente alterar o seu conteudo
```C
    int array[4];
    int i;
    for(i = 0; i < 4; i++){
        array[i] = 0;
    }
```
Todos este exemplos produzem um array de 4 posições todas a 0.

The notar que todos estes metodos são equivalentes em termos de eficiência. De qualquer forma o PC tem
 de ir a todas as celulas e alterar o valor. Quer esse comportamento seja declarado explicita ou implicitamente

## Aceder e alterar os valores de um array
Isto é bastante simples, aliás já fiz uso desta syntax na secção anterior.

`int x = array[0];` guarda na variavel `x` o valor que está na posição `0` do array.

`array[0] = x;` guarda na posicão `0` do array o valor do `x`

Os indices validos de um array são o conjunto dos números inteiros desde `0` até ao seu tamanho menos 1, ou seja,
 para o array da secção anterior (`int array[4]`) os indices validos são 0,1,2,3.

Apesar da simplicidade desta funcionalidade, é no uso desta que são causados a maior parte dos erros! Se acedermos
 a um indice maior ou igual ao tamanho do array ou negativo estamos a aceder a memória para a qual não temos autorização
 e o programa "crasha" indicando o famoso `segmentation fault`.

## Arrays e funções
Passar um array a uma função é relativamente simples
```C
// funcao que recebe um array
void func(int array[], int N){
    /* code */
}

int main(){
    int array[4] = {0};
    func(array,4);
}
```
Nota que passei também o tamanho do array. Isto é bastante habitual visto que em C não é possivel saber o tamanho de um
array apenas percorrendo-o, temos então que "tomar conta" deste valor para evitar incorrer numa `segmentation fault`.

Outro promenor importantissimo é que, ao contrario de variaveis de tipos primitivos, alterar um array dentro de uma função
 tambem o altera fora da função.

Por exemplo, o seguinte codigo,
```C
void func(int array[], int N){
    array[0] = 1;
    N = 2;
}

int main(){
    int array[4] = {0};
    int n = 4;
    printf("Before: array[0] == %d and n == %d\n", array[0], n);
    func(array,n);
    printf("After:  array[0] == %d and n == %d\n", array[0], n);
    return 0;
}
```
produz o seguinte output
```
Before: array[0] == 0 and n == 4
After:  array[0] == 1 and n == 4
```

Isto deve se ao facto de o array ser na verdade um apontador para memória. Conceito que será aborado mais à frente.

## Strings
As strings em C são um caso particular dos arrays. Tem todas as propriedades de um array mas acrescentam algumas.
```C
    char string[] = "Hello World\n";
```
Podem ser declaradas desta forma (similar à primeira que vimos para o array de inteiros), no entanto, este array (ou string) é
*null terminated*. Ou seja, o ultimo caracter da string é um '\0'.

Isto permite-nos saber o comprimento da string "apenas olhando para ela"
```C
    int strlen(char string[]){
        int i = 0;
        while(string[i]!='\0'){
            i++;
        }
        return 0;
    }
```
Temos então aqui uma definição da `strlen`. Uma função que retorna o comprimento de uma string.
