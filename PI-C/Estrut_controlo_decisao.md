# Estruturas de controlo e decisão

## If e If else
O `if` (bem como o `if else`) é usado para executar código, ou não, com base numa condição lógica.

Tanto como `if` e o `if else` estão presentes em `C`.

Separamos o `if` nestes dois casos pois, ao contrário do Haskell por exemplo, um `if` não tem que ser seguido por um `else`.

### Estrutura do If

O `if` em si tem dois casos.
1. Segundo de chavetas: Executa um bloco de código caso a condição do `if` se verifique;
2. Sem chavetas: Executa apenas a primeira linha de código caso a condição do `if` se verifique.

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

if(x==0) // mudou a condição!
// não temos {  }
x += 3; // x = x + 3
y--; // y = y - 1


Qual é o valor do x e do y?
X tem valor 4
Y tem valor -1
```
Como o `if` não tem chavetas a unica linha de código condicionada pelo `if` é `x += 3;`.
Visto que `x != 0` a condição do `if` é falsa e `x += 3;` nunca corre!

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
  x += 3; // não fica mais obvio que é afectado pelo if?
}

y--; // y = y - 1
```

Recomendamos então usarem sempre chavetas mesmo sendo estas redundante. Nunca fase incial é a melhor estratégia.

### Else

Vamos acrescentar o `else` agora.
```C
int x = 4;
int y = 0;

if(x==0)
  x += 3; // x = x + 3
else
  y--; // y = y - 1

Valor do x e y?
X

Qual é o valor do x e do y?
X tem valor 4
Y tem valor -1
```
Tal como o `if`, o `else` pode, ou não ser seguido de chavetas e as mesma regras se aplicam.
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
