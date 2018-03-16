# Iterators
Então achas que sabes usar [Collections](Collections.md). Mas será que sabes manipula-las a sério!

---

Ao longo deste documento:
 * `list` é um objecto do tipo `List<ListElem>`
 * `ListElem` é um elemento da lista
 * `map` é um objecto do tipo `Map<String, MapElem>`
 * `MapElem` é um valor do map.

# O 'for' que todos conhecemos
De certeza já viste/escreveste um `for` assim.
```Java
for(int i=0; i<list.size(); i++){
    ListElem l = list.get(i);
    l.doStuff();
}
```
Isto esta muito bem e tal mas é muito entediante, para além de que assume muito sobre o funcionamento
da estrutura.

# Foreach
Collections que implementem [Iterable][iterable] podem ser iteradas com este estilo de `for`, chamado "foreach".

```Java
for(ListElem l: list){
    l.doStuff();
}
```
Pode até ser lido, em liguagem natural, "For each ListElem l in list do this"

Mas este `for` tem, potencialmente, um problema: _"Temos sempre de percorrer a lista toda,
 visto que não temos a condição de paragem explicita"_. [1][extraNotes]

# Iteradores externos
Aqui entram os iteradores externos. [Iterable][iterable], como já referi acima,
 é uma [interface][interfaceMD], e esta garante que classes que a implementam têm o
 metodo `iterator()` que retorna um `Iterator` sobre a collection.

Como podemos ver pelos [javaDocs][iterator] `Iterator` implementa 3 metodos muito simples.
```Java
boolean hasNext()
```
Que retorna `true` se o iterador não chegou ao fim da lista.
```Java
E next()
```
Que retorna um elemento da lista onde o iterador se encontra e avança o iterador para o proximo elemento. [2][extraNotes]
```Java
void remove()
```
Que remove da lista o ultimo elemento que o `next()` retornou.

Vamos então por isto em pratica.
```Java
Iterator<ListElem> it = list.iterator();
while(it.hasNext()){
    ListElem l = it.next();
    l.doStuff();
    if(l.isSomething()){
        it.remove();
    }
}
```

Podemos então aqui alterar o codigo para que o ciclo acabe quando uma condição se verificar.
```Java
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

Como cada classe `Iterable` implementa o seu proprio metodo `iterator` podemos ter a certeza que estamos a
iterar de forma correcta sobre a `Collecion` (O foreach também garante isto).

# Iteradores Internos
Lembram-se de Haskell? Isto é parecido. As Collections implementam todas (desde o Java 8) o metodo [stream][streamMethod]
 que retorna um [Stream][streamDocs] e sobre este podemos fazer uma imensidão de coisas.

---
**TO BE CONTINUED**

---


# Extra Notes
1. Tecnicamente podemos colocar um `if` que faça `break` para sair da lista antes de a precorrer toda
 mas os stores são contra isto, justificando que fica menos legivel. (Pessoalmente acho que depende e tem de ser visto caso a caso)
   ```Java
        for(ListElem l: list){
            if(someCondition()) break;
            l.doStuff();
        }
    ```
2. Aquele `E` no tipo de retorno de `next()` é porque depende do tipo de iterator quando este é declarado.
   Por exemplo, o `next()` de um `Iterator<String>` vai retornar `String`. Isto chamam-se genéricos e saem muito
   fora do ambito do que é esperado de aprender nesta disciplina. (Logo é uma cena fixe de pesquisar quando tiveres tempo ;) )

[extraNotes]: https://github.com/Mendess2526/ResumosMIEI/blob/master/POO-Java/Iterators.md#extra-notes
[iterable]: https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html
[iterator]: https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
[interfaceMD]: https://github.com/Mendess2526/ResumosMIEI/blob/master/POO-Java/Hierarquia_de_classes.md#interfaces
[streamMethod]: https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#stream--
[streamDocs]: https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
