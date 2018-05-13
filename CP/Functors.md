# Functors

## Tipos de dados recursivos, algebricos e indutivos

O tipo de dados `A*`é recursivo se `A*` aparecer na definição do tipo.

Como ja vimos `A*` é isomorfico com `1 -|- A >< A*`, e `A*` aparece na sua definição.

Podemos também definir em Haskell como:
```haskell
-- L de Lista
data L a = Fim | Cons (a, L a)
-- a é o tipo da lista (int, double, etc)
```

Como o tipo `1 -|- A >< A*` combina tipos de dados diferentes (3 deles) é considerado um tipo de dados algébrico. Esta forma de exprimir listas (neste caso) permite analisar as listas de forma matématica e muito estruturada.


Por fim classificamos como indutivo pois a declaração do tipo explica como construir os habitantes do tipo (`Nil` ou `Cons (a, L a)`). Temos portanto dois construtores.

## Functores abstração


