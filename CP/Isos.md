# Isomorfismos

## Informação

Uma função `f` é definida como `f : A -> B`. Podemos pensar como um processo em que apenas conhecemos o tipo de entrada e o tipo de saida. Por outras palavras, `f` compromete-se a produzir um valor do tipo `B` se receber um valor do tipo `A`.


As funções podem ser vistas pelo forma como perdem ou matêm informação. 
Nos extremos deste espectro temos as funções `identidade` e `constante`.
Todas as outras funções encontram-se entre elas e programar é a arte de perder informação de forma controlada e precisa.


As funções perdem informação `confundido` o input ou seja, para inputs diferentes produzem o mesmo output.

## Funções injectivas

Funções injectivas são as funções que não confundem o seu input (input diferente, output **sempre** diferente).

## Funções sobrejectivas

Funções sobrejectivas são as funções que o seu cojunto de chegada é igual ao seu domínio.
Seja `f : A -> B`, para qualquer `b` pertencente a `B` existe um `a` tal que `f a = b` (sendo que `a` pertence a `A`).

## Isomorfismos

Funções iso, são funções que são injectivas e sobrejectivas. 
Isto é, funções que não perdem informação.

As funções isomorficas apenas reorganizam a informção, sendo possivel voltar ao estado orginial. Daqui conclui-se que admitem inversa.

## Exemplos

Vamos considerar a lista `A = [1,2,3,4]` uma função `f` que transforma a lista A em `(1, [2,3,4])` é isomorfica, visto ser possivel voltar ao estado anterior e nao perdemos nenhuma informação.

O par `B = (1, 3)` é um isomorfismo de `(3, 1)` visto que não se perde nenhuma informação (se não considerarmos a ordem) e pode-se voltar ao estado inicial.

## Haskell

```haskell

inListas = either (const []) (uncurry (:))

outListas [] = (Left ())
outListas (h:t) = (Right (h,t))

> outListas [1,2,3,4]
Right (1,[2,3,4])
> inListas Right (1, [2,3,4])
[1,2,3,4]
```

## Esquemas

### Listas

Vamos representar uma lista de valores do tipo `A` como `A*`. 
Uma representação de um insomorfismo de `A*` é `1 -|- A >< A*`.

O que é o `1`? E o `-|- A`?

O `1` representa um conjunto de valores que só tem um elemento. 
Isto é **NECESSÁRIO** para ser isomorfico, visto que se não admitisse apenas um elemento não saberiamos a que estado voltar.
Ao analisarmos o `outListas` vemos que tem dois casos possiveis, um **ou** outro (`1`). O `()` corresponde ao `1` em Haskell. Ou é uma lista vazia ou uma lista com elementos de `A` onde criamos um par com o primeiro elemento da lista e o resto da lista `A >< A*`.
