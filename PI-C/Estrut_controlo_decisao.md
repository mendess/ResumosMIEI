# Estruturas de controlo e decisão

## If e If else
O `if` (bem como o `if else`) é usado para executar código com base numa condição lógica.

Tanto como `if` e o `if else` estão presentes em `C`.

Separamos o `if` nestes dois casos pois, ao contrário do Haskell por exemplo, um `if` não tem que ser seguido por um `else`.

### Estrutura do If

O `if` em si tem dois casos.
1. Seguido de chavetas: Executa um bloco de código caso a condição do `if` se verifique;
2. Sem chavetas: Executa apenas a primeira linha de código caso a condição do `if` se verifique.

```C
if(condição){
  linhaDeCodigo1;
  linhaDeCodigo2;
}
```

Vamos ver alguns exemplos:
```C
int x = 4;
int y = 0;

if(x==4){
  x += 3; // x = x + 3
  y--; // y = y - 1
}

Qual é o valor do x e do y?
X tem valor 7
Y tem valor -1
```
Como o `if` tem chavetas e como `x==4` é verdadeiro, as duas linhas de código são executadas.

Sem chavetas:
```C
int x = 4;
int y = 0;

if(x==4) // mudou a condição!
        // não temos {  }
x += 3; // x = x + 3
y--; // y = y - 1


Qual é o valor do x e do y?
X tem valor 4
Y tem valor -1
```
Como o `if` não tem chavetas a unica linha de código condicionada pelo `if` é `x += 3;`.
Visto que `x == 0`é falso, `x += 3;` nunca corre!

De facto, o código como está escrito não é muito simpático para a leitura. Podia, E DEVIA, ser melhorado atravez de identação e/ou `{  }`, apesar destas não serem precisas.
```C
int x = 4;
int y = 0;

if(x==0)
  x += 3; // não fica mais obvio que é afectado pelo if?

y--; // y = y - 1
```
Melhor ainda...
```C
int x = 4;
int y = 0;

if(x==0){
  x += 3;
}

y--; // y = y - 1
```

Recomendamos então usarem sempre chavetas mesmo sendo estas redundantes. Nunca fase incial é a melhor estratégia. Se não colocarmos chavetas e mais tarde alterarmos
 adicionarmos codigo dentro do `if`, podemos nos esquecer destas e o codigo não faz o que esperamos. É um dos tipos de erros mais dificil de detetar
 (falo por experiência!).

### Else

Vamos acrescentar o `else` agora.
```C
int x = 4;
int y = 0;

if(x==0)
  x += 3; // x = x + 3
else
  y--; // y = y - 1

Qual é o valor do x e do y?
X tem valor 4
Y tem valor -1
```
Tal como o `if`, o `else` pode ser seguido de chavetas e as mesma regras se aplicam.
Quando é seguido de chavetas, corre esse bloco do cógido.
Quando não é seguido de chavetas corre apenas uma linha.
```C
int x = 4;
int y = 0;

if(x==0){
  x += 3; // x = x + 3
}else{
  y--; // y = y - 1
  x--;
}

// ou

if(x==0){
  x += 3; // x = x + 3
}
else{
  y--; // y = y - 1
  x--;
}
```
O estilo das chavetas é puramente pessoal, dentro dos limites da sanidade mental.

### Else if
Uma estrutura de controlo que resulta da composição das duas anteriores é o `else if`.

Se quisermos implementar um menu teremos de tomar uma decisão diferente conforme a opção que o utilizador escolher.
```C
void menu(char opcao){
    if(opcao == 'Y'){
        yes();
    }else if(opcao == 'N'){
        nao();
    }else{
        reset();
    }
}
```
Este codigo é equivalente ao seguinte.
```C
void menu(char opcao){
    if(opcao == 'Y'){
        yes();
    }
    if(opcao == 'N'){
        nao();
    }
    if(opcao != 'Y' && opcao != 'N'){
        reset();
    }
}
```
Mas este é muito mais chato de escrever e de ler. E caso o nosso menu tiver 10 opções diferentes o ultimo `if` irá ser gigantesco.

Para além disso, o segundo excerto de codigo é menos eficiente, visto que o nosso codigo fara muitas mais comparações. Enquanto que no 1º excerto se o primeiro `if`
 der `True` apenas o corpo deste é executado. Por outro lado, no 2º excerto, apesar de já termos entrado no primeiro `if` o computador vai ter de fazer todas as
 outras comparações.
