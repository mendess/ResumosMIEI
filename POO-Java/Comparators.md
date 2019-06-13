# Comparable e Comparators

Um comparator serve para definir um critério pelo qual se podem
 comparar duas classes. Este tipo de comparações são normalmente usadas
 ordenar elementos de uma lista ou inserir corretamente numa arvore de
 procura.

Durante este resumo vai ser varias vezes refereciada a seguinte classe:
```Java
public class Aluno{
    private String nome;
    private int nota;
    /* construtores e getter/setters */
}
```

### Comparators I

Para criar um comparator temos apenas de defninir uma classe que
 implemente [Comparator][comparatorDocs].

Se quisermos ordenar por ordem de notas, criamos o seguinte comparator.
```Java
public class AlunoComparator implements Comparator<Aluno>{
    public int compare(Aluno a1, Aluno a2){
        return Integer.compare(a1.getNota(), a2.getNota());
    }
}
```
Os tipos basicos (`int`, `float`, `double`, ...) já tem definidos na
respetiva classe metodos para os comparar.

#### Utilização

##### Metodo

Para o utilizar apenas temos de o passar a algum metodo ou construtor
que necessite de um.
```Java
List<Aluno> alunosPorNota = new ArrayList<>();
// inserir montes de alunos
alunosPorNota.sort(new AlunoComparator());
```
Neste caso o metodo [sort][sortDocs] ira ordenar por ordem crescente de notas
os alunos da lista.

Se quisermos ordenar pela ordem inversa podemos utilizar o metodo
[reversed][reversedDocs] disponibilizado por defeito em todos os `Comparator`s
```Java
alunosPorNota.sort(new AlunosComparator().reversed());
```

##### Construtor

Para criar um [SortedSet][sortedSetDocs], neste caso, temos de passar um comparator
 ao criar uma instancia.
```Java
SortedSet<Aluno> alunos = new TreeSet<>(new AlunoComparator());
```
Assim sempre que for adicionado um novo aluno a este [TreeSet][treeSetDocs] este
 irá ser inserido de forma ordenada.

### Comparators II

Em vez de criarmos uma classe nova para comparar objectos de uma outra
 classe podemos também criar comparators com `lambdas` e outros
 metodos pre-definidos.

#### Utilização

##### Metodo

Utilizando a classe da secção anterior, os alunos podem ser ordenados
 das seguintes formas.

 * Usando um comparator de inteiros pre-definido na classe comparator,
 ao qual temos apenas de passar um metodo que retorna o valor a
 comparar.
```Java
alunosPorNota.sort(Comparator.comparingInt(Aluno::getNota));
alunosPorNome.sort(Comparator.comparing(Aluno::getNome)); //O resultado do metodo tem de ser
                                                          //comparable (ver secção seguinte)
```
 * Usando um lambda que compare dois objectos.
```Java
alunosPorNota.sort((a1, a2) -> Integer.compare(a1.getNota(), a2.getNota()));
```

O primeiro pode também ser invertido. [<sup>\[1\]</sup>][extraNotes]
```Java
alunosPorNota.sort(Comparator.comparingInt(Aluno::getNota).reversed());
```

### Comparable ou "ordem natural"

Outro metodo de comparar objectos de uma classe é fazer essa classe comparavel.

##### Metodo

Podemos definir que, por defeito, os Alunos podem ser comparados por nome
implementando a interface [Comparable][comparableDocs], que obriga à implementação
do metodo [compareTo][compareToDocs].
```Java
public class Aluno implements Comparable<Aluno> {
    private String nome;

    public int compareTo(Aluno aluno){
        return this.nome.compareTo(aluno.getNome());
    }
} 
```
**Nota:** As strings são `Comparable`.

Podemos agora ter uma lista ordenada por nome da seguinte forma:
```Java
alunosPorNome.sort(Comparator.naturalOrder());
```

##### Construtor

Agora que `Aluno` é [Comparable][comparableDocs] podemos criar uma estrutura ordenada
sem nos preocuparmos com o comparator::
```Java
SortedSet<Aluno> alunos = new TreeSet<>();
```

## Extra Notes

1. Para inverter o lambda temos de expandi-lo para o que ele verdadeiramente é:
    ```Java
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
