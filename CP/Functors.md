# Functors

## Tipos de dados recursivos, algebricos e indutivos

O tipo de dados `A*` é recursivo se `A*` aparecer na definição do tipo.

Como ja vimos `A*` é isomorfico com `1 -|- A >< A*`, e `A*` aparece na sua definição.

Podemos também definir em Haskell como:

```haskell
-- L de Lista
data L a = Nil | Cons (a, L a)
-- a é o tipo da lista (int, double, etc)
```

Como o tipo `1 -|- A >< A*` combina tipos de dados diferentes (3 deles) é considerado um tipo de dados algébrico. Esta forma de exprimir listas (neste caso) permite analisar as listas de forma matématica e muito estruturada.


Por fim classificamos como indutivo pois a declaração do tipo explica como construir os habitantes do tipo (`Nil` ou `Cons (a, L a)`). Temos portanto dois construtores.

## Abstração dos tipos de dados

Continuando com o exemplo anterior, as funções que transformam um estado no outro são as funções `in` e `out` e são únicas.
`In` sugere que vamos para dentro do tipo (sintetiza-lo), `Out` indica que vamos para fora do tipo isto é que o vamos desconstruir (analisar).

Convém notar que apesar de termos chamado este tipo de dados `L` qualquer outro tipo de dados `X` definido por uma constante e um contrutor binário que aceite um `a` e um `X` é abstratamente igual (isomorfico). Apenas estamos a mudar o simbolos (continuamos a representar o mesmo).


## Tipos de dados indutivos

Como ja vimos em cima existem tipos de dados que são indutivos.
Vamos pegar num tipo de dados indutivo qualquer `T` e numa função `f : T -> B`.
