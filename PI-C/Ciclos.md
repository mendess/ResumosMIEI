# Ciclos

Quando queremos fazer muitas vezes a mesma coisa não convem faze-la à mão. Se tiveram
 Haskell no semestre passado devem se lembrar de recursividade como metodo para
 resolver este problema. No entanto, em programação imperativa, podemos utilizar
 ciclos para resolver estes problemas.

## While
O `while` é o mais simples destes.
```C
    while(condicao){
        instrucao1;
        instrucao2;
        etc;
    }
```
Enquanto a `condicao` for verdadeira o codigo é repetido.

Exemplo pratico:
```C
    int i = 0;
    while(i < 3){
        printf("%d\n",i);
        i++;
    }
```
Podemos até ler este código em linguagem natural da seguinte forma: "Enquanto o `i`
 for menor que 3, imprimir o valor do `i` e incrementar o `i`".

O output deste codigo é:
```
0
1
2
```

## For
O `for` é essencialmente uma extenção do `while`. Como podemos ver no exemplo prático
 anterior, inicializamos uma variavel para contar iterações (`i`), usamos essa mesma
 variavel na condição do `while`, e incrementamos a variavel no corpo do `while`.

O `for` permite-nos juntar estes 3 passos numa linha de codigo.
```C
    for(<expr1>; <expr2>; <expr3>){
        /* corpo */
    }
```
 * `<expr1>`: Instrucão que será executada uma vez, antes do código do `for`
 * `<expr2>`: O equivalente à condição do `while`. Se der `0` (falso) o ciclo termina
 * `<expr3>`: Instrução que será executada no fim do corpo do `for`

Exemplo prático:
```C
    int i;
    for(i = 3; i < 3; i++){
        printf("%d\n",i);
    }
```
Este codigo é equivalente ao anterior. O output é o mesmo.

## Uma nota sobre recursividade
A recursividade é válida em C, mas não é tão eficiente como um ciclo na maioria das
 situações. No entanto, torna alguns algoritmos mais simples de implementar
 (algoritmos sobre àrvores binarias por exemplo).

```C
void countTo(int i){
    if(i >= 0){
        countTo(i-1);
        printf("%d\n",i);
    }
}

int main(){
    countTo(2);
}
```
