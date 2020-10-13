# Introducão

# Utilitários
> Alterar a precisão
```matlab
format long  % 15 casas decimais
format short %  5 casas decimais
```

> Funções importantes
* ones(n) -> matriz de 1s tamanho nxn
* zeros(n) -> matriz nula de ordem n
* eye(n) -> matriz identidade de ordem n
* ()’ -> transposta da matriz
* det () -> determinante da matriz
* rank() -> característica da matriz
* inv() -> inversa da matriz
* diag()-> diagonal da matriz
* triu(), tril() -> matriz triangular superior/inferior da matriz
* norm(,1), norm(,inf)-> normas (1 e inf) da matriz
* A \ b -> resolução do sistema linear

```
>> b=[1; 3; -6]
>> A=[1 -3 4; 2 5 -2; 3 8 10]
>> A\b %sistema linear Ax=b
ans =
2.2703
-0.6216
-0.7838
>> norm(A,1) %norm
```

- - - -

# Definir Funções
```Matlab
function [output] = function_name(input)
output = operation_on_input
```
NOTA: definir na Function Window

## Exemplos
* definir uma função "normal"
```Matlab
function [f] = fun1(x)
f = x^2 + x +1
```
* definir um sistema de equações
```Matlab
function [f] = fun2(x)
f(1) = x
f(2) = x^2
```

NOTA: é possível definir lambdas com `@(x) operation_on_x`
