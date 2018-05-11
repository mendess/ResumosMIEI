# Exceptions

Exceptions são uma ferramenta usada para realizar controlo de erros. Enquanto que
em `C` quase todos os erros que ocorrem dão origem a uma Segmentation Fault
genérica, quando trabalhamos com uma linguagem orientada a objectos é costume
usar exceptions que transmitem mais informação.

## Exceptions mais communs
A exceção mais commum é [NullPointerException][NullPointerDocs] que ocorre sempre
que se tenta aceder a uma variavel cujo valor seja `null`.

### Exemplo:
```Java
1 public class ExampleClass{
2     public static void main(String[] args){
3         String s = null;
4         System.out.println(s.length());
5     }
6 }
```
Irá produzir o seguinte output no terminal:
```Java
Exception in thread "main" java.lang.NullPointerException
    at ExampleClass.main(ExampleClass.java:4)
```
Como podemos ver o nome completo da exception é `java.lang.NullPointerException`,
a classe em que ocorreu e o metodo `ExampleClass.main` e o ficheiro e respetiva
linha de código `ExampleClass.java:4`

## Propagação de exceções por metodos
Quando uma exceção ocorre, se não for tratada, ira interromper a execução do
metodo imediatamente e subir até que atinja um ponto em que seja tratada ou que
não possa subir mais, o que normalmente faz com que o programa termine, como
foi o caso do exemplo anterior.

```Java
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
```Java
Hello
Exception in thread "main" java.lang.NullPointerException
    at ExampleClass.printStringLength(ExampleClass.java:9)
    at ExampleClass.main(ExampleClass.java:5)
```

Como podemos ver a exceção gerada no metodo `printStringLength` originou na linha
9 e, como consequência, a linha 10 não foi executada. Da mesma forma a linha 6 da
 `main()` não foi executada pois a exceção foi propagada para esta. Como neste
ponto a exceção não foi tratada causou a interrupeção por completo do programa.

## Try/catch
Para tratar uma exceção temos ao nosso dispor a sintaxe do `try{}catch()`.
```Java
try{
    /* try block: codigo que possa produzir uma exceção */
catch(NullPointerException e){
    /* catch block: tratar exceção */
}
/* Resto do metodo */
```
Assim sempre que codigo dentro do `try` block lançar uma `NullPointerException`
ira imediatamente saltar para o `catch` block e depois continuar pelo `/*Resto do
metodo*/` sem que o interrompa.

Aplicando isto ao exemplo anterior:
```Java
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
Muitas vezes a nossa aplicação tem os seus erros especificos que tem de tratar,
nesse caso será boa ideia criar as nossas próprias exceções.

Uma exceção é um objecto, e como todos os objectos é definido por uma classe.

```Java
public class MyException extends Throwable{

    public MyException(){
    }
}
```
Isto é suficiente para criar uma exceção simples. A invocação do `super` está
implicita.

Para lancar esta exceção, assim como qualquer outra, usamos a _keyword_ `throw`.

```Java
public method() throws MyException{
    /* code */
    if(someError){
        throw new MyException();
    }
    /* more code */
}
```
Um `throw` tem o mesmo impacto na execução que um `return`, ou seja, se um
`throw` for executado o metodo termina. Para além disto tem de se sinalizar
na assinatura do metodo que este lança uma exceção, para que quem o use esteja
ciente dessa possibilidade e possa tratar o erro.

## A Classe Throwable
A classe [Throwable][ThrowableDocs] é a superclasse de todas as exceções, apenas
classes que extendam `Throwable` podem ser lançadas por um `throw` e tratadas
por um `catch`.

A nossa exceção pode ter qualquer construtor que seja necessário, no entanto,
seguir os construtores implementados pela classe `Throwable` é o mais comun.

### Exemplo
```Java
public class MyException extends Throwable{

    public MyException(){
    }

    public MyException(String message){
        super(message);
    }
}
```
Fazendo assim uso de um dos [construtores][ThrowableMessageConst] da super
classe.

[NullPointerDocs]: https://docs.oracle.com/javase/8/docs/api/java/lang/NullPointerException.html
[ThrowableDocs]: https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html
[ThrowableMessageConst]: https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html#Throwable-java.lang.String-
