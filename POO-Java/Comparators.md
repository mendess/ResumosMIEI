# Comparable e Comparators

Um comparator serve para definir um critério pelo qual se podem
 comparar duas classes. Este tipo de comparações são normalmente usadas
 ordenar elementos de uma lista ou inserir corretamente numa árvore de
 procura.

Durante este resumo vai ser varias vezes referenciada a seguinte classe:
```java
public class Aluno{
    private String nome;
    private int nota;
    /* construtores e getter/setters */
}
```

### Comparators I

Para criar um comparator temos apenas de definir uma classe que
 implemente [Comparator][comparatorDocs].

Se quisermos ordenar por ordem de notas, criamos o seguinte comparator.
```java
public class AlunoComparator implements Comparator<Aluno>{
    public int compare(Aluno a1, Aluno a2){
        return Integer.compare(a1.getNota(), a2.getNota());
    }
}
```
Os tipos básicos (`int`, `float`, `double`, ...) já tem definidos na
respetiva classe métodos para os comparar.

#### Utilização

##### Método

Para o utilizar apenas temos de o passar a algum método ou construtor
que necessite de um.
```java
List<Aluno> alunosPorNota = new ArrayList<>();
// inserir montes de alunos
alunosPorNota.sort(new AlunoComparator());
```
Neste caso o método [sort][sortDocs] ira ordenar por ordem crescente de notas
os alunos da lista.

Se quisermos ordenar pela ordem inversa podemos utilizar o método
[reversed][reversedDocs] disponibilizado por defeito em todos os `Comparator`s
```java
alunosPorNota.sort(new AlunosComparator().reversed());
```

##### Construtor

Para criar um [SortedSet][sortedSetDocs], neste caso, temos de passar um comparator
 ao criar uma instância.
```java
SortedSet<Aluno> alunos = new TreeSet<>(new AlunoComparator());
```
Assim sempre que for adicionado um novo aluno a este [TreeSet][treeSetDocs] este
 irá ser inserido de forma ordenada.

### Comparators II

Em vez de criarmos uma classe nova para comparar objetos de uma outra
 classe podemos também criar comparators com `lambdas` e outros
 métodos pré-definidos.

#### Utilização

##### Metodo

Utilizando a classe da secção anterior, os alunos podem ser ordenados
 das seguintes formas.

 * Usando um comparator de inteiros pré-definido na classe comparator,
 ao qual temos apenas de passar um método que retorna o valor a
 comparar.
```java
alunosPorNota.sort(Comparator.comparingInt(Aluno::getNota));
alunosPorNome.sort(Comparator.comparing(Aluno::getNome)); //O resultado do método tem de ser
                                                          //comparable (ver secção seguinte)
```
 * Usando um lambda que compare dois objetos.
```java
alunosPorNota.sort((a1, a2) -> Integer.compare(a1.getNota(), a2.getNota()));
```

O primeiro pode também ser invertido. [<sup>\[1\]</sup>][extraNotes]
```java
alunosPorNota.sort(Comparator.comparingInt(Aluno::getNota).reversed());
```

### Comparable ou "ordem natural"

Outro método de comparar objetos de uma classe é fazer essa classe comparável.

##### Metodo

Podemos definir que, por defeito, os Alunos podem ser comparados por nome
implementando a interface [Comparable][comparableDocs], que obriga à implementação
do método [compareTo][compareToDocs].
```java
public class Aluno implements Comparable<Aluno> {
    private String nome;

    public int compareTo(Aluno aluno){
        return this.nome.compareTo(aluno.getNome());
    }
} 
```
**Nota:** As strings são `Comparable`.

Podemos agora ter uma lista ordenada por nome da seguinte forma:
```java
alunosPorNome.sort(Comparator.naturalOrder());
```

##### Construtor

Agora que `Aluno` é [Comparable][comparableDocs] podemos criar uma estrutura ordenada
sem nos preocuparmos com o comparator::
```java
SortedSet<Aluno> alunos = new TreeSet<>();
```

## Extra Notes

1. Para inverter o lambda temos de expandi-lo para o que ele verdadeiramente é:
    ```java
    alunosPorNome.sort((new Comparator<Aluno>() {
        @Override
        public int compare(Aluno a1, Aluno a2){
            return a1.getNome().compareTo(a2.getNome());
        }
    }).reversed());
    ```
    A isto chama-se uma classe anónima, que faz uso da [interface
    funcional][interfaceFuncional] `Comparator`.


[extraNotes]: ./Comparators.md#extra-notes
[interfaceFuncional]: ./Interfaces.md#interfaces-funcionais
[sortedSetDocs]: https://docs.oracle.com/javase/8/docs/api/java/util/SortedSet.html
[treeSetDocs]: https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html
[comparableDocs]: https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
[comparatorDocs]: https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html
[compareToDocs]: https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html#compareTo-T-
[sortDocs]: https://docs.oracle.com/javase/8/docs/api/java/util/List.html#sort-java.util.Comparator-
[reversedDocs]: https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#reversed--
