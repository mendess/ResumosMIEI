# MNOL - MATLAB
Nesta secção vamos explorar a parte de MATLAB lecionada em MNOL.

# Índice

* [Utilitários](#Utilitários)
* [Definir Funções](#Definir-Funções)
* [Resolver sistemas de equações](#Resolver-sistemas-de-equações)
* [Resolver equação](#Resolver-equação)
* [Estimar valores](#Estimar-valores)
  * [Polinómio Interpolador de Newton](#Polinómio-Interpolador-de-Newton)
  * [Spline](#Spline)
    * [Spline Cúbica](#Spline-Cúbica)
    * [Spline Cúbica Completa](#Spline-Cúbica-Completa)
    * [Segmento de Spline](#Segmento-de-Spline)
* [Valor do Integral](#Valor-de-Integral)
  * [Função](#Função)
  * [Valores](#Valores)
* [Mínimos Quadrados](#Mínimos-Quadrados)
    * [Polinómios Ortogonais](#Polinómios-Ortogonais)
    * [Modelo não polinomial linear](#Modelo-não-polinomial-linear)
* [Quasi-Newton](#Quasi-Newton)
    * [Sem derivadas](#Sem-derivadas)
    * [Com derivadas](#Com-derivadas)
* [Método de Nelder-Head](#Método-de-Nelder-Head)


- - - -

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

- - - -

# Resolver sistemas de equações
* definir a função
* utilizar o comando **fsolve**
```Matlab
op = optimset('tolfun',TolFun_Value,'tolx',TolX_Value);
x1 = Aproximação_inicial;
format long;
[x,fval,exitflag, output] = fsolve('fun2', x1, op)
```
NOTA: tolx é o critério de paragem

- - - -

# Resolver equação
* definir a função
* utilizar o comando **fsolve**
```Matlab
op = optimset('tolx',TolX_Value);
x1 = Aproximação_inicial;
format long;
[x,fval,exitflag, output] = fsolve('fun2', x1, op)
```
- - - -

# Estimar valores
## Polinómio Interpolador de Newton
* obter os grau + 1 pontos em torno do ponto x (por proximidade)
* calcular o polinómio interpolador
```Matlab
pol = polyfit(points_X, points_Y, grau);
```
* calcular um ponto nesse polinómio
```Matlab
format long;
polyval(pol, point_X)
```

## Spline
### Spline Cúbica
```Matlab
sp = spline(points_X, points_Y, x)
```

### Spline Cúbica Completa
```Matlab
sp = spline(points_X, [declive_inferior points_Y declive_superior], x)
```

NOTAS(apenas utilizar se for explicitado que é necessário):
* se tiver função e não tiver declives
  * derivar função
  * calcular declive para pontos
* se não tiver função nem declives
  * calcular declive dos primeiros dois e últimos dois
  * remover o segundo ponto e último ponto dos vetores

### Segmento de Spline
```Matlab
sp = spline(points_X, points_Y)
% OU
sp = spline(points_X, [declive_inferior points_Y declive_superior])
sp.coefs
```

- - - -

# Valor do Integral

## Função
```matlab
quad(fun,a,b)
quadl(fun,a,b)
quad(fun,a,b,error)
```
NOTA:
* fazer `[z, nf] = quad(@(x) x+1 , 1, 2, 0.00001)`
  * z -> result
  * nf -> número de avaliações da função

## Valores
```matlab
trapz(points_X, points_Y)
```


- - - -

# Mínimos Quadrados
## Polinómios Ortogonais
```matlab
x = [1.5 2 3 4]
y = [4.9 3.3 2 1.5]

[p,s] = polyfit(x,y,1)

polyval(p,2.5)

%Residuo do erro
(s.normr)^2
```

## Modelo não polinomial linear
```matlab
x = [1.5 2 3 4]
y = [4.9 3.3 2 1.5]

foo = @(c,x) c(1)./x + c(2).*x
[c,resnorm,residual,exitflag,out] = lsqcurvefit(foo,[1 1],x,y)

%Residuo do erro
resnorm
```

- - - -

# Quasi-Newton
Este método apenas calcula o mínimo. Assim, para calcular o máximo utilizamos a
seguinte formula:
```
max = -min(-f(x))
```

## Sem derivadas
```matlab
foo = @(x) 0.25*x(1)^4-0.5*x(1)^2+0.1*x(1)+0.5*x(2)^2
fminunc(foo)
% OU
[x,y,ef,out] = fminunc(@(x) 0.25*x(1)^4-0.5*x(1)^2+0.1*x(1)+0.5*x(2)^2,[-1 0.5])
```

> Se `ef < 0` não converge

> Se `ef = 0` atinge nº max iterações -> aumentar maxIter

> Se `ef > 0` converge

## Com derivadas
```matlab
function [f,g] = foo(x)
    f = 0.25*x(1)^4-0.5*x(1)^2+0.1*x(1)+0.5*x(2)^2;
    g(1) = x(1)^3 %derivada em x1
    g(2) = x(2)   %derivada em x2
end

op = optimset('gradobj','on')
[x,y,ef,out] = fminunc('foo',[-1 0.5],op)

```

### optimset options:
* 'tolfun',10
* 'tolx',10
* 'HessUpdate','DFP'
* 'MaxIter',10000
* 'MaxFunEvals',10000

- - - -

# Método de Nelder-Mead
```matlab
foo = @(x) max(abs(x(1)),abs(x(2)-1))

op = optimset('display','iter')

[x,y,ef,out] = fminsearch(foo,[1 1],op)
```
