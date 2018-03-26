# Revisões

Vamos apenas rever aplicação parcial de funções, o que é uma função curried e uncurried.

# Currying

`Currying` ou `aplicação parcial de uma função` é o acto de se aplicar uma função sem passar todos os argumentos que ela pede.
Vamos ver um exemplo.

Por simplicidade vamos definir uma função básica.
```haskell
mySumInt :: Int -> Int -> Int
mySumInt x y = x + y
```
A partir do tipo da função, para alguem iniciado em Haskell, não é muito intuitivo que esta função recebe dois `Ints` e devolve outro.

Isto acontece porque na realidade as funções em Haskell recebem apenas um argumento.
Funções com vários argumentos, vão aplicando o primeiro argumento (o mais a esquerda) e devolve uma nova função que pede menos um argumento.
```haskell
> :type mySumInt
mySumInt :: Int -> Int -> Int

> :type mySumInt 3
mySumInt 3 :: Int -> Int
```
Como podemos ver, ao aplicar a `mySumInt` a apenas um `Int`, temos uma nova função que aceita apenas um `Int` e devolve o resultado da soma desse `Int` com `3`.
Ou seja, o primeiro argumento da `mySumInt` foi substituido por `3` e temos uma função nova que soma um inteiro qualquer a `3`.

```haskell
mySumInt 3 4

(mySumInt 3) 4

3 + 4
```
Ou por tipos
```haskell
> :type mySumInt
mySumInt :: Int -> Int -> Int

> :type mySumInt 3
mySumInt 3 :: Int -> Int

> :type mySumInt 3 4
mySumInt 3 4:: Int
```
