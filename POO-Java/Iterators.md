# Iterators
Em Java iterar sobre uma coleção pode ser feito de muitas formas diferentes. Cabe-nos a nós encontrar a mais indicada
 para o nosso problema.

Relembro que ler a documentação começa a ser, cada vez mais, crucial para escrever bom código. A maior parte das operações
 que são necessárias já estão definidas, não vale a pena *"reinventar a roda"*.

---

Ao longo deste documento:
 * `list` é um objeto do tipo `List<ListElem>`
 * `ListElem` é um elemento da lista.

# O 'for' que todos conhecemos
De certeza já viste/escreveste um `for` assim.
```java
for(int i=0; i<list.size(); i++){
    ListElem l = list.get(i);
    l.doStuff();
}
```
Este método funciona para a maior parte dos casos quando temos de iterar por um array (ArrayList), mas assume muito
sobre o funcionamento da estrutura/classe.

# Foreach
Collections que implementem [Iterable][iterable] podem ser iteradas com este estilo de `for`, chamado "foreach".

```java
for(ListElem l: list){
    l.doStuff();
}
```
Pode até ser lido, em linguagem natural, "For each `ListElem l` in list do \<this\>"

Alternativamente, este código pode ser implementado da seguinte forma, recorrendo ao uso de um lambda
 *(mais sobre estes numa [secção][ItInternos] mais à frente)*:
```java
list.foreach(l -> l.doStuff());
```

Mas este `for` tem, potencialmente, um problema: _"Temos sempre de percorrer a lista toda,
 visto que não temos a condição de paragem explícita"_. [<sup>\[1\]</sup>][extraNotes]

# Iteradores externos
Aqui entram os iteradores externos. [Iterable][iterable], como já referi acima,
 é uma [interface][interfaceMD], e esta garante que classes que a implementam têm o
 método `iterator()` que retorna um `Iterator` sobre a collection.

Como podemos ver pelos [javaDocs][iterator] `Iterator` implementa 3 métodos muito simples.
```java
boolean hasNext()
```
Que retorna `true` se o iterador não chegou ao fim da lista.
```java
E next()
```
Que retorna um elemento da lista onde o iterador se encontra e avança o iterador para o próximo elemento. [<sup>\[2\]</sup>][extraNotes]
```java
void remove()
```
Que remove da lista o último elemento que o `next()` retornou.

Vamos então pôr isto em prática.
```java
Iterator<ListElem> it = list.iterator();
while(it.hasNext()){
    ListElem l = it.next();
    l.doStuff();
    if(l.isSomething()){
        it.remove();
    }
}
```

Podemos então aqui alterar o código para que o ciclo acabe quando uma condição se verificar.
```java
boolean flag = true;
Iterator<ListElem> it = list.iterator();
while(flag && it.hasNext()){
    ListElem l = it.next();
    l.doStuff();
    if(l.isSomething()){
        it.remove();
    }
    if(someCondition){
        flag = false;
    }
}
```

Como cada classe `Iterable` implementa o seu próprio método `iterator()` podemos ter a certeza que estamos a
iterar de forma correta sobre a `Collecion` (O `foreach` também garante isto).

# Iteradores Internos
Os iteradoes internos tentam emular programação funcional para iterar sobre as `Collections`.

Estas implementam (desde o Java 8) o método [stream][streamMethod] que retorna um [Stream][streamDocs]
 da `Collection` e sobre este podemos fazer uma imensas operações.

Importante notar que, como acontece em Programação Funcional, os streams apresentam Imutabilidade, ou seja,
 enquanto que nos [iteradores externos][iteradoresExternos] podíamos remover elementos da `Collection` enquanto
 iterávamos sobre estes, com streams isto não é possível. Podemos, no entanto, criar uma lista sem os elementos
 que queremos remover e substituímos a lista antiga com a nova.

*(Nota: A lista é "imutável" apenas no sentido em não é possível alterar que elementos que a lista original tem,
 mas podemos alterar os objetos nela contidos e isto vai afetar a lista original, bem como todas as instâncias do
 objeto em questão. Vou tentar explicar isto melhor com alguns exemplos mais a frente)*

A estrutura usual de uma iteração usando `stream` é a seguinte:
```java
    list.stream()
        .operacões_sobre_a_estrutura()
        .converter_de_Stream_para_o_tipo_necessário();
```
Não vale a pena listar todas as operações mas vou apresentar alguns exemplos.

### Exemplo 1: map
Um caso muito frequente é querermos transformar uma lista de `A`s numa lista de `B`s.

Assumindo que `ListElem` implementa `int getId()`, podemos converter uma lista de `ListElem` numa lista de `Integer`.
```java
    List<Integer> ids = list.stream()
   /*1*/.map(l -> l.getId())
   /*2*/.collect(Collectors.toList);
```
Analisando passo a passo:
 1. [map][mapMethod] transforma os elementos da lista de acordo com a função que lhe é passada. Em geral, esta função
 será um lambda. Lambdas são relativamente simples, este `l -> l.getId()`, por exemplo, quer dizer: "para cada `l`
 chama e guarda o resultado de `getId()` como elemento da lista", no contexto do `map`. [<sup>\[3\]</sup>][extraNotes]
 2. [collect][collectMethod] *coleciona* o resultado numa lista, visto que o resultado das nossas operações é um
 `Stream<Integer>` e nós precisamos de uma `List<Integer>`. Para chamar o método `collect()` temos de lhe passar
  o [Collector][collectors] que este deve usar. [<sup>\[4\]</sup>][extraNotes]

Ficamos assim com uma lista com os Ids, esta nova lista independente da original.

### Exemplo 2: filter
Outra das aplicações mais frequentes de streams é a filtragem de uma lista.

Assumindo que `ListElem` implementa `int getValue()`, podemos então filtrar todos os elementos com valor inferior
 a `x`.

```java
public List<ListElem> getAbove(int x){
    return this.list.stream()
        .filter(l -> l.getValue() > x)
        .collect(Collectors.toList());
}
```

Este método irá então retornar uma lista dos `ListElem` com valor superior a `x` mas **atenção!**, pode, se `ListElem` não for
 imutável, ter o defeito de não garantir o [encapsulamento][getListMutaveis] da classe que implementa este método.
 Podemos, no entanto, resolver este problema facilmente, usando o `map`.

```java
public List<ListElem> getAbove(int x){
    return this.list.stream()
        .filter(l -> l.getValue() > x)
        .map(l -> l.clone())
        .collect(Collectors.toList());
}
```

## Method References
Quando o lambda que passamos a um destes métodos apenas chama outro método, como é o exemplo do `l -> l.getId()` podemos
 utilizar uma `Method Reference` com a seguinte sintaxe: `<Class>::<method>`

Olhando para o [Exemplo 1][ItInternosEx1] novamente, o código sofreria a seguinte alteração.
```java
    List<Integer> ids = list.stream()
        .map(ListElem::getId)
        .collect(Collectors.toList);
```

## Lambda mais complexos
Por vezes o código que temos de implementar é muito complexo para ser escrito numa só linha. Nestes casos podemos, "expandir"
 o lambda para que seja mais legível o que estamos a fazer.

```java
public List<ListElem> getAbove(int x){
    return this.list.stream()
        .filter(l -> {
                int i = l.getValue();
                if(someCondition(i)){
                    return true;
                }else{
                    if(someOtherCondition(i)){
                        return false;
                    }else{
                        return true;
                    }
                }
        })
        .collect(Collectors.toList());
}
```

---
# Extra Notes
1. Tecnicamente podemos colocar um `if` que faça `break` para sair da lista antes de a percorrer toda
 mas os stores são contra isto, justificando que fica menos legível. (Pessoalmente acho que depende e tem de ser visto caso a caso)
   ```java
        for(ListElem l: list){
            if(someCondition()) break;
            l.doStuff();
        }
    ```
2. O `E` no tipo de retorno do `next()` deve-se a este depender do tipo de `Iterator` quando este é declarado.
   Por exemplo, o `next()` de um `Iterator<String>` vai retornar `String`. A isto chamam-se genéricos e saem muito
   fora do ambito do que é esperado nesta disciplina. (Logo é uma cena fixe de pesquisar quando tiveres tempo ;) )
3. Existem também "especializações" do `map` como o [mapToInt][mapToIntMethod] que retorna um [IntStream][IntStream] em
   vez de um `Stream` normal. Sobre este podemos fazer [somatório][sumMethod], [médias][averageMethod], etc.
   ```java
   int totalValue = list.stream()
        .mapToInt(l -> l.getId())
        .sum();
   ```
4. A Api dos `Collectors` e dos `Streams` é muito extensa e depende de muitas classes. No entanto está cheia de
   exemplos que ajudam a sua compreensão

[extraNotes]: ./Iterators.md#extra-notes
[iterable]: https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html
[ItInternos]: ./Iterators.md#iteradores-internos
[iterator]: https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
[interfaceMD]: ./Hierarquia_de_classes.md#interfaces
[streamMethod]: https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#stream--
[streamDocs]: https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
[iteradoresExternos]: ./Iterators.md#iteradores-externos
[mapMethod]: https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#map-java.util.function.Function-
[collectMethod]: https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#collect-java.util.stream.Collector-
[collectors]: https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html
[getListMutaveis]: ./Anatomia_de_uma_classe.md#get-de-uma-lista-de-objectos-mutaveis
[ItInternosEx1]: ./Iterators.md#exemplo-1-map
[mapToIntMethod]: https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#mapToInt-java.util.function.ToIntFunction-
[IntStream]: https://docs.oracle.com/javase/8/docs/api/java/util/stream/IntStream.html
[sumMethod]: https://docs.oracle.com/javase/8/docs/api/java/util/stream/IntStream.html#sum--
[averageMethod]: https://docs.oracle.com/javase/8/docs/api/java/util/stream/IntStream.html#average--
