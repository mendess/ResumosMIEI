# Collections
As collections de Java permitem-nos abstrair o comportamento habitual de guardar os nosso objetos em
 arrays, arvores, hastables, etc. Estes padrões de programação são tão comuns que não vale a pena estar
 sempre a definilos.

Neste documento vou falar de `Collection` e `Map`, que não é uma collection mas serve um proposito similar.
<!-- Arranjar esta frase -->

*Nota: Este resumo assume que os conceitos de [Hierarquia de Classes](./Hierarquia_de_classes.md) estão bem
 compreendidos.*

*Nota: Este resumo apenas apresenta uma reduzida lista de classes e metodos de forma
 exemplificativa. Existem muitas mais classes e metodos.*

## Hierarquia de classes das collections
A API das Collections disponibiliza as seguintes classes: (Existem mais, este é apenas um pequeno exemplo)
```Rust
                                  +-------------------------------+
                    +------------>|        Collection<E>          |<------+
                    |             +-------------------------------+       |
                    |                ^                                    |
                    |                |                                    |
                    |                |                                    |
               +---------+      +----------+                          +--------+
      ++======>| List<E> |      | Queue<E> |<=====++                  | Set<E> |<====++
      ||       +---------+      +----------+      ||                  +--------+     ||
      ||             ^^            ^^             ||                     ^^          ||
      ||             ||            ||             ||                     ||          ||
      ||             ||            ||             ||                     ||          ||
++==============++  ++===============++ ++==================++  ++============++   +--------------+
|| ArrayList<E> ||  || LinkedList<E> || || PriorityQueue<E> ||  || HashSet<E> ||   | SortedSet<E> |
++==============++  ++===============++ ++==================++  ++============++   +--------------+
                                                                       ^                  ^^
                                                                       |                  ||
                                                                       |                  ||
                                                          ++==================++   ++============++
                                                          || LinkedHashSet<E> ||   || TreeSet<E> ||
                                                          ++==================++   ++============++
Legenda:
++==++                        ^^  ==>
||  ||  -> Classe             ||       -> Implements
++==++                        ||

+----+                         ^ -->
|    |  -> Interface           |       -> Extends
+----+                         |
```

Podemos consultar a documentação de todas estas para saber que metodos temos disponiveis:

* [Collection][CollectionDocs]
* [List][ListDocs]
* [Queue][QueueDocs]
* [Set][SetDocs]
* [ArrayList][ArrayListDocs]
* [LinkedList][LinkedListDocs]
* [PriorityQueue][PriorityQueueDocs]
* [HashSet][HashSetDocs]
* [SortedSet][SortedSetDocs]
* [LinkedHashSet][LinkedHashSetDocs]
* [TreeSet][TreeSetDocs]

### Collection
A interface `Collection` garante que todas as classes que a implementam disponibilizam,
 entre outros, metodos para adicionar, verificar a existencia e remover elementos.

* `boolean add(E e)`
* `boolean remove(E e)`

Ambos retornando `true` se o estado da collection foi alterado.

* `boolean contains(Object o)`

Que retorna `true` case o objecto esteja contido na coleção.

### List
Esta interface que estende a `Collection` adiciona, entre outros, metodos com a noção de indice,
 muito analogo a um array:

* `int indexOf(Object o)` Que retorna o indice de um determinado objecto
* `E get(int index)` Que retorna o objecto que está num dado indice
* `boolean remove(int index)` Que remove um objecto num dado indice

### ArrayList
A implementação de lista mais usada é o ArrayList. Este exibe o comportamento de um array dinâmico.
 Disponibilizando assim metodos que esperamos encontrar numa lista que herda.

# Map
Um map, ou dicionario, server para guardar pares chave-valor. Efetivamente são uma Hash Table.

## Hierarquia de classes do Map
A API do Map disponibiliza as seguintes classes: (Existem mais, este é apenas um pequeno exemplo)
```Rust
                       +----------------------+
             ++=======>|       Map<K,V>       |<---------+
             ||        +----------------------+          |
             ||                                          |
    ++================++                        +-------------------+
    ||  HashMap<K,V>  ||                        |   SortedMap<K,V>  |
    ++================++                        +-------------------+
              ^                                          ^
              |                                          |
              |                                 +-------------------+
   ++====================++                     | NavigableMap<K,V> |
   || LinkedHashMap<K,V> ||                     +-------------------+
   ++====================++                              ^^
                                                         ||
                                                ++=================++
                                                ||   TreeMap<K,V>  ||
                                                ++=================++
```
Podemos consultar a documentação de todas estas para saber que metodos temos disponiveis:
 * [Map][MapDocs]
 * [HashMap][HashMapDocs]
 * [LinkedHashMap][LinkedHashMapDocs]
 * [SortedMap][SortedMapDocs]
 * [NavigableMap][NavigableMapDocs]
 * [TreeMap][TreeMapDocs]

### Map API
A interface `Map` garante que todas as classes que a implementam disponibilizam,
 entre outros, metodos para adicionar, verificar a existenciai, obter e remover
 elementos

 * `boolean put(K key, V value)`
 * `boolean containsKey(Object key)`
 * `V get(K key)`
 * `V remove(K key)`

Outros metodos importantes são:

 * `Set<K> keySet()` para obter o conjunto das chaves.
 * `Collection<V> values()` para obter uma coleção de valores do map.
 * `Set<Map.Entry<K,V>> entrySet()` que devolve um conjunto de pares chave-valor.

Importante notar que estes metodos podem ter fraco desempenho em algumas
 implementações da interface, por exemplo `HashMap`, devido à ineficiencia
 de interar sobre este tipo de estruturas. Por esta razão, Map não implementa
 `Iterable`.



[CollectionDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html
[ListDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/List.html
[QueueDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html
[SetDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/Set.html
[ArrayListDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
[LinkedListDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html
[PriorityQueueDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html
[HashSetDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html
[SortedSetDocs]:https://docs.oracle.com/javase/9/docs/api/java/util/SortedSet.html
[LinkedHashSetDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashSet.html
[TreeSetDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html
[MapDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/Map.html
[HashMapDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
[LinkedHashMapDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html
[SortedMapDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/SortedMap.html
[NavigableMapDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/NavigableMap.html
[TreeMapDocs]:https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html
