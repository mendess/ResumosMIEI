# The stuff Nestor doesn't tell you

Neste "resumo" vou esclarecer umas coisas importantes sobre Programação
Orientada a Objectos.


# Getters and Setters

O lixo dos getters/setters devem ser evitados ao máximo. Alias se a tua classe
for
```java
class Foo {
    private String bar;
    String getBar() { return bar; }
    void setBar(String bar) { this.bar = bar; }
}
```
O quão menos encapsulado ficava se `bar` fosse simplesmente `public`? É a mesma
coisa! Há duas principais razões para encapsulamento:

- Mudar a representação interna do objecto não devia ser uma breaking change que
    implica mudar o codigo que o usa.
    - Se eu quiser mudar `Foo` para ter um `int` em vez de uma `String` vou ter
    de mudar o `getter` e o `setter`. Logo isto não está encapsulado. É uma breaking change.
- Impedir uso incorrecto do objecto.
    - Nem preciso de explicar esta, se podes directamente alterar os campos do
        objecto nada te impede de o fazer mal.

Efectivamente este código e equivalente a `public String bar`. Mas deu mais
trabalho e não se conseguiu nada.

Mas as solução e fazer tudo `public`? Não, é fazer coisas que fazem sentido e
mais nada.

Por exemplo, pegando no clássico `Ponto`.

```java
class Ponto {
    private int x;
    private int y;
    public Ponto(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
```

Qual parece ser o mais intuitivo de usar?
```java
public move(int x, int y) {
    this.x += x;
    this.y += y;
}
```
Ou

```java
public setX(int x) {
    this.x = x;
}
public setY(int y) {
    this.y = y;
}
```
?

Agora podias dizer qualquer coisa como: "Mas e se eu quiser que o ponto passe a
ter (x,y) coordenadas e não quero fazer as contas para o mover ate ao sitio". E
para isso relembro te que tens um construtor: `ponto = new Ponto(x, y);`.
Setters não servem para nada em 99% dos casos.

Claro que há situações em que um `setter` faz sentido mas são muito raras e
normalmente dá para fazer uma API melhor. Olhem para os métodos do `ArrayList` por
exemplo? Quantos setters e que aquilo tem?


### Artigo

- [Getter and setters are evil](https://www.yegor256.com/2014/09/16/getters-and-setters-are-evil.html)


## Herança

"Prefer composition over inheritance whenever possible"
    -- Some smart person probably.

Herança é considerado um dos maiores erros de programação orientada a objectos.
Eu podia falar aqui de todas as maneiras em que herança é má ideia, mas há gente
mais inteligente que eu que já falou sobre isso.

### Artigos
- [Why Extends is Evil](https://www.javaworld.com/article/2073649/why-extends-is-evil.html)
- [Inheritance is a Procedural Technique for Code Reuse](https://www.yegor256.com/2016/09/13/inheritance-is-procedural.html)

# Clones

_boy oh boy_
