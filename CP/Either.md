# Either (mesmo codominio)

À semelhança do que aconteceu com o `split`, onde combinamos funções com o mesmo domínio, é possivel combinar funções com o mesmo codominio (contradominio).

Tendo duas funções, `f` e `g` e tal que `f : A -> C` e `g : B -> C`, podemos representar estas funções da seguinte forma:

![Mesmo codominio](./images/either/samecodomain.jpg)

Aplicamos `f` caso apareça um valor do tipo `A` e aplicamos `g` caso apareça um valor do tipo `B`. O símbolo utilizado para representar isto é `[f, g]`.


Podemos combinar os dominios destas duas funções, para isso é necessário uma marca a indicar de que tipo (conjunto) vem o valor (se do `A` ou do `B`). Para marcar de que tipo o valor vem usamos injeções.

![Simbolo](./images/either/inject.jpg)

Em Haskell já existe um tipo de dados que serve perfeitamente para fazer estas injeções. O `Either` representa ou um valor ou outro, marcando-os com `Left` ou `Right`.
```haskell
data Either a b = Left a | Right b
```

`i1` e `i2` usados nos diagramas são apenas alcunhas para `Left` e `Right`. Surge então este diagrama:

![Diagrama](./images/either/diagrama.jpg)

## Haskell

Como vimos o `Either` ja esta definido em Haskell.

```haskell
-- Apenas mudamos os nomes para bater certo com os diagramas
i1      = Left
i2      = Right
```

# Either (codominio diferente)

Quando duas funções não partilham o mesmo codominio também é possivel combina-las.
Segue `f : A -> C` e `g : B -> D`. Injetando os codomino temos `C+D`:

![Diferente codominio](./images/either/difcodomain.jpg)

Seguindo-se então este digrama:

![Diagrama2](./images/either/difdiagrama.jpg)

Daqui concluimos que `f` é igual a `i1 . f` e `g` a `i2 . g`.


![Diagrama3](./images/either/diffim.jpg)

Mais uma vez existe uma notação especial para este caso, `f -|- g`.

## Haskell

```haskell
(-|-) :: (a -> b) -> (c -> d) -> Either a c -> Either b d
f -|- g = either (i1 . f) (i2 . g)
````
