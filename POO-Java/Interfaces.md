# Interfaces
As interfaces servem para definir tipos de dados, para "agrupar" classes
diferentes sob um tipo comum, com um comportamento comum.

Interfaces podem ser vistas como contratos, ou seja, uma classe que
implementa uma interface compromete-se a implementar os métodos descritos na
interface.

### Situação exemplo
Estamos a desenvolver uma API para reproduzir musica.
```Java
public class Radio {
    private ArrayList<Musica> musicas;
    public Radio(){
        this.musicas = new ArrayList<>();
    }

    public void queue(Musica musica){
        this.musicas.add(musica);
    }

    public void play(){
        this.musicas.get(0).start();
        this.musicas.remove(0);
    }
}
```
Mas não queremos restringir aos utilizadores da nossa api (outros
programadores) a uma classe `Musica` definida por nós.

### Solução
Podemos então definir uma interface, um contrato. E as condições deste
contrato são muito simples:
 * *"Se implementares estes metodos, eu posso reproduzir a tua musica"*

Definimos a interface então da seguinte forma:
```Java
public interface Musica{
    void start();
}
```
De notar que não coloquei um *access modifier* (`public`, `private`, etc.).
Isto é porque todos os métodos definidos numa interface são `public` por
defeito.

Assim um programador que queria usar a nossa API pode definir a sua classe
de musica e utlizar o radio que nós já definimos.
```Java
public class MyMusic implements Musica{
    private byte[] song;

    public MyMusic(byte[] song){
        this.song = song;
    }

    public void start(){  // < Implementação do método que a interface obriga a implementar
        /*
        Reproduzir a musica
        */
    }
}
```

### Interfaces mais comuns
#### Collections
Nas `Collections` do Java temos várias interfaces que podemos utilizar.
Alguns exemplos são:
 * [List](https://docs.oracle.com/javase/8/docs/api/java/util/List.html)
 * [Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)

Podemos utlizar estas interfaces para fazer o nosso código mais genérico.
```Java
public class MyClass{
    private List<String> nomes;

    public MyClass(){
        this.nomes = new ArrayList<>();
    }

    public MyClass(List<String> nomes){
        this.nomes = nomes;
    }
}
```
Esta classe aceita qualquer tipo de lista.

E este sistema permite-nos também implementar as nossa próprias
implementações de listas/maps/etc...

```Java
public class DBMap implements Map<String,Cena>{
}
```

## Default Methods
Com o Java 8 foram introduzidos o metodos default. A ideia por traz destes é
definir comportamento por defeito para as classes que implementam a nossa
interface.

```Java
public interface Musica{
    void start();
    void stop();
    default void play10Seconds(){
        start();
        TimeUnit.SECONDS.sleep(10);
        stop();
    }
}
```

Assim todas as classes que implementem esta interface tem também este
método. Este pode ser `Overriden` para alterar o seu comportamento.

## Interfaces Funcionais

**TODO**
