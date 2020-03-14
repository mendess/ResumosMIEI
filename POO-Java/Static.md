# Metodos e Variaveis de Classe

Tal como as instâncias de uma classe as classes em si podem ter variáveis e
métodos, estes servem para definir dados e comportamento que seja igual para
todas as instâncias independentemente do seu estado interno

Para distinguir um variável ou método de classe dos de instância usa-se a
_keyword_ `static`

```java
public class MyClass{

    private static String foo = "Foo"; // Variável de classe

    public static String getFoo(){     // Método de classe
        return foo;
    }

    private String bar;                // Variável de instância

    public MyClass(){                  // Construtor
        this.bar = "Bar";
    }

    public String getBar(){            // Método de instância
        return this.bar;
    }
}
```
Pontos importantes a notar deste exemplo:

1. As variáveis de classe tem de ser inicializadas quando são declaradas,
para não conterem valores por defeito (`null` no caso dos objectos). Visto
não existe um "constructor de classe".
2. As variáveis de classe não podem ser referenciadas com a _keyword_ `this`
visto que não pertencem a nenhuma instância. Aliás a referencia `this` não
pode ser usada dentro de um contexto `static`, visto que não existe nenhuma
instância para referenciar.

#### Exemplo de como chamar os métodos
```java
public static void main(String[] args){
    MyClass mc = MyClass();
    String bar = mc.getBar();
    String foo = MyClass.getFoo();
}
```
Variáveis de classe podem ser vistos como variáveis globais do programa,
são criadas quando o programa é iniciado e existem durante todo o tempo
de vida do programa.

# Exemplo do comportamento da variável global
```java
public class MyClass{
    private static int count = 0;

    public static int getC(){
        return count;
    }

    public MyClass(){
        count++;
    }
}
```
```java
public static void main(String[] args){
    int a = -1;
    a = MyClass.getC();        // a == 0
    MyClass mc = new MyClass();
    a = MyClass.getC();        // a == 1
}
```
Como podemos ver por este exemplo, como o constructor altera o valor da
variável `static` esta modificação é visível independente do contexto.


# Singleton Pattern (extra matéria)
Por vezes queremos garantir que existe apenas uma instância da nossa classe
isto pode ser conseguido com métodos de classe.

```java
public class Highlander{
    private static Highlander instance = null;

    private static Highlander get(){
        if(instance == null) instance = new Highlander();
        return instance;
    }

    private Highlander(){

    }
}
```
O que isto implica é que não podem ser criadas instâncias novas porque o
construtor é `private` e para obter uma instância temos de usar o método
`static` `get()`, que irá retornar sempre a mesma instância.

## Interfaces
As interfaces também podem ter métodos static funcionando como qualquer
qualquer outro método de classe.
```java
public interface MyInterface{
    public static foo(){
        System.out.println("Bar");
    }
}
```
```java
public class Main{
    public static void main(String[] args){
        MyInterface.foo();
    }
}
```
