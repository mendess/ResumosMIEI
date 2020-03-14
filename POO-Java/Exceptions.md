# Exceptions

Exceptions são uma ferramenta usada para realizar controlo de erros. Enquanto que
em `C` quase todos os erros que ocorrem dão origem a uma `Segmentation Fault`
genérica, quando trabalhamos com uma linguagem orientada a objetos é costume
usar exceptions que transmitem mais informação.

## Tipos de exceptions
A classe abstrata que todos os erros e exceções devem estender é
[Throwable][ThrowableDocs]. Depois desta temos 2 sub-tipos:

* [Error][ErrorDocs] Que são erros que muitas vezes não podem ser tratados, por
    exemplo:
    * [OutOfMemoryError][OutOfMemoryDocs]
* [Exception][Exception] Que são exceções do funcionamento normal e devem ser
    tratados. Estas ainda se sub-dividem em mais dois tipos:
    * [RuntimeExceptions][RuntimeExceptions]
    * _Checked exceptions_

    A única diferença entre estas é que as _Checked Exceptions_ tem de ser
    declaradas na assinatura do método, enquanto que as `RuntimeExceptions`
    não. Exemplos de `RuntimeExceptions` são:
    [NullPointerException][NullPointerDocs] e
    [ArrayOutOfBoundsException][ArrayOutOfBoundsDocs].

## Exceptions mais comuns
A exceção mais comum é [NullPointerException][NullPointerDocs] que ocorre sempre
que se tenta aceder a uma variável cujo valor seja `null`.

### Exemplo:
```java
1 public class ExampleClass{
2     public static void main(String[] args){
3         String s = null;
4         System.out.println(s.length());
5     }
6 }
```
Irá produzir o seguinte output no terminal:
```java
Exception in thread "main" java.lang.NullPointerException
    at ExampleClass.main(ExampleClass.java:4)
```
Como podemos ver o nome completo da exception é `java.lang.NullPointerException`,
a classe em que ocorreu e o método `ExampleClass.main` e o ficheiro e respetiva
linha de código `ExampleClass.java:4`

## Propagação de exceções por métodos
Quando uma exceção ocorre, se não for tratada, ira interromper a execução do
método imediatamente e subir até que atinja um ponto em que seja tratada ou que
não possa subir mais, o que normalmente faz com que o programa termine, como
foi o caso do exemplo anterior.

```java
  1 public class ExampleClass{
  2     public static void main(String[] args){
  3         String s = null;
  4         System.out.println("Hello");
  5         printStringLength(s);
  6         System.out.println("Still running");
  7     }
  8     public static void printStringLength(String s){
  9         System.out.print(s.length());
 10         System.out.println(" characters");
 11     }
 12 }
```

Irá produzir o seguinte output no terminal:

```java
Hello
Exception in thread "main" java.lang.NullPointerException
    at ExampleClass.printStringLength(ExampleClass.java:9)
    at ExampleClass.main(ExampleClass.java:5)
```

Como podemos ver a exceção gerada no método `printStringLength` originou na linha
9 e, como consequência, a linha 10 não foi executada. Da mesma forma a linha 6 da
 `main()` não foi executada pois a exceção foi propagada para esta. Como neste
ponto a exceção não foi tratada causou a interrupção por completo do programa.

## Try/catch
Para tratar uma exceção temos ao nosso dispor a sintaxe do `try{}catch()`.
```java
try{
    /* try block: código que possa produzir uma exceção */
} catch (NullPointerException e) {
    /* catch block: tratar exceção */
}
/* Resto do método */
```
Assim sempre que código dentro do `try` block lançar uma `NullPointerException`
ira imediatamente saltar para o `catch` block e depois continuar pelo `/*Resto do
método*/` sem que o interrompa.

Aplicando isto ao exemplo anterior:
```java
  1 public class ExampleClass{
  2     public static void main(String[] args){
  3         String s = null;
  4         System.out.println("Hello");
  5         printStringLength(s);
  6         System.out.println("Still running");
  7     }
  8     public static void printStringLength(String s){
  9         try{
 10             System.out.print(s.length());
 11         }catch(NullPointerException e){
 12             System.out.print("0");
 13         }
 14         System.out.println(" characters");
 15     }
 16 }
```
Irá produzir o seguinte output no terminal:
```
Hello
0 characters
Still running
```
Como podemos ver todas as linhas de código foram executadas apesar da exceção ter
sido lançada pela linha 10.

## Criar uma nova exceção
Muitas vezes a nossa aplicação tem os seus erros específicos que tem de tratar,
nesse caso será boa ideia criar as nossas próprias exceções.

Uma exceção é um objeto, e como todos os objetos é definido por uma classe.

```java
public class MyException extends Exception{

}
```
Isto é suficiente para criar uma exceção simples. A invocação do `super` está
implícita.

Para lançar esta exceção, assim como qualquer outra, usamos a _keyword_ `throw`.

```java
public method() throws MyException{
    /* code */
    if(someError){
        throw new MyException();
    }
    /* more code */
}
```
Um `throw` tem o mesmo impacto na execução que um `return`, ou seja, se um
`throw` for executado o método termina. Para além disto, se se tratar de uma
_Checked Exception_, tem de se sinalizar na assinatura do método que este lança
uma exceção, para que quem o use esteja ciente dessa possibilidade e possa
tratar o erro.

## A Classe Throwable
A classe [Throwable][ThrowableDocs] é a superclasse de todas as exceções e erros,
apenas classes que estendam `Throwable` podem ser lançadas por um `throw` e
tratadas por um `catch`.

A nossa exceção pode ter qualquer construtor que seja necessário, no entanto,
seguir os construtores implementados pela classe `Throwable` é o mais comum.

### Exemplo
```java
public class MyException extends Exception{

    public MyException(){ // Se não incluirmos este constructor a nossa
    }                     // exceção terá de ter sempre uma mensagem

    public MyException(String message){
        super(message);
    }
}
```
Fazendo assim uso de um dos [construtores][ThrowableMessageConst] da super
classe.

```java
public method() throws MyException{
    /* code */
    if(someError){
        throw new MyException("Exceção feia :(");
    }
    /* more code */
}
```

[NullPointerDocs]: https://docs.oracle.com/javase/8/docs/api/java/lang/NullPointerException.html
[ThrowableDocs]: https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html
[ThrowableMessageConst]: https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html#Throwable-java.lang.String-
[ErrorDocs]: https://docs.oracle.com/javase/8/docs/api/index.html?java/lang/Error.html
[OutOfMemoryDocs]: https://docs.oracle.com/javase/8/docs/api/index.html?java/lang/OutOfMemoryError.html
[RuntimeExceptions]: https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html
[ArrayOutOfBoundsDocs]: https://docs.oracle.com/javase/8/docs/api/java/lang/ArrayIndexOutOfBoundsException.html
