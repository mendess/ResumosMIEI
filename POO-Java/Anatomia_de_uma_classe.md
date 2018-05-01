# Anatomia de uma classe

Notas:
 * Código entre [ ] é opcional. Ou seja, fica ao teu critério se é necessário.
 * As palavras 'MyClass', 'XClass', 'SuperClass' e 'InterfaceClass' são apenas exemplificativas.


### Declaração

Nesta declaração indicamos o nome da nossa class, se extende uma classe e se, e quais, interfaces implementa.
```Java
public class MyClass [extends SuperClass] [implements InterfaceClass, ...] {
```

### Variáveis de instância
Estas devem ser sempre `private` para garantir o encapsulamento do estado
interno do objeto.
```Java
    private int num;
    private String nome;
    private XClass outraCena;
    private ArrayList<String> nomes;
    private ArrayList<XClass> outrasCenas;
```
### Construtores
Os contrutores permitem instanciar novos objetos da classe. Devem inicializar todos as variáveis de instância.

#### Construtor vazio
Este construtor inicializa as variáveis de instância com valores por defeito.
```Java
    public MyClass(){
        this.num = 0;
        this.nome = "";
        this.outraCena = new XClass();
        this.nomes = new ArrayList<>();
        this.outrasCenas = new ArrayList<>();
    }
```
#### Construtor parameterizado
Este construtor recebe como parâmetro os valores que as variáveis de instância
 devem tomar.

*(Nota: Uma classe pode ter mais variáveis de instância do que os parâmetros
 passados neste tipo de construtor, caso, por exemplo, uma destas tenha um
 valor derivado de outras variáveis.)*

As diferentes atribuições feitas neste construtor são explicadas através dos
 getters/setters mais à frente.
```Java
    public MyClass(int num, String nome, XClass outraCena,
                    ArrayList<String> nomes, ArrayList<XClass> outrasCenas){
        this.num = num;
        this.nome = nome;
        this.outraCena = outraCena.clone();
        this.nomes = new ArrayList<>(nomes);
        this.outrasCenas = new ArrayList<>();
        for(XClass cena: outrasCenas){
            this.outrasCenas.add(cena.clone());
        }
    }
```
#### Construtor de cópia
Este construtor permite criar uma cópia exata de outra instância deste objeto.

*(Nota: Este construtor assume que todos os getters clonam corretamente as
 variáveis de instância que retornam.)*
```Java
    public MyClass(MyClass myClass){
        this.num = myClass.getNum();
        this.nome = myClass.getNome();
        this.outraCena = myClass.getOutraCena();
        this.nomes = myClass.getNomes();
        this.outrasCenas = myClass.getOutrasCenas();
    }
```
### Getters
Os getters permitem aceder às variáveis de instância de uma instância da nossa
 classe. Devem ser `public` apenas os que queremos que seja possível aceder.

#### Get de uma variável de tipo primitivo
As variáveis são 'passed by value', ou seja, o seu valor é copiado. Logo, este get é simples.

(Irá ficar mais claro mais a frente.)
```Java
    public int getNum(){
        return this.num;
    }
```
#### Get de um objeto imutável
Uma string é imutável, logo retornar um apontador para este objeto que pertence ao estado interno
no nosso objeto não tem problema.
```Java
    public String getNome(){
        return this.nome;
    }
```
#### Get de um objeto mutável
Um objeto mutável deve ser clonado para manter o encapsulamento. Se este objeto fosse alterado
fora da instância que o retornou, implicaria alterar o estado interno da mesma instância.

(Porque a variável é 'passed by value' e esta, na verdade, é um apontador.)
```Java
    public XClass getOutraCena(){
        return this.outraCena.clone();
    }
```
#### Get de uma lista de objetos imutáveis
Tem que se criar uma lista nova. Apesar de cada objeto individual da lista ser imutável, a lista
em si, não é imutável. Logo, se retornarmos a lista diretamente, novos valores podem ser adicionados
à mesma, alterando o estado interno da instância a partir do exterior.

Para isto, podemos fazer uso do construtor da `ArrayList` que recebe uma `Collection` e copia os valores.

**_ATENÇÃO: SÓ PUDEMOS USAR ESTE construtor PARA LISTAS DE objetos IMUTÁVEIS_**
```Java
    public ArrayList<String> getNomes(){
        return new ArrayList<>(this.nomes);
    }
```
Ou
```Java
    public ArrayList<String> getNomes(){
        return this.nomes.clone();
    }
```
#### Get de uma lista de objetos mutaveis
Como no get anterior, tem que ser criada uma nova lista. Mas, os elementos ao serem adicionados à mesma,
têm que ser clonados.
```Java
    public ArrayList<XClass> getOutrasCenas(){
        ArrayList<XClass> newOCenas = new ArrayList<>();
        for(XClass cena: this.outrasCenas){
            newOCenas.add(cena.clone());
        }
        return newOCenas;
    }
```
### Setters
Os setters seguem o mesmo princípio dos getters. Objetos imutáveis e tipos primitivos
não têm que ser clonados, tudo o resto sim.
```Java
    public void setNum(int num){
        this.num = num;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setOutraCena(XClass outraCena){
        this.outraCena = outraCena;
    }

    public void setNomes(ArrayList<String> nomes){
        this.nomes = new ArrayList<>(nomes);
    }

/*  OU assim
    public void setNomes(ArrayList<String> nomes){
        this.nomes = nomes.clone();
    }
*/

    public void setOutrasCenas(ArrayList<XClass> cenas){
        ArrayList<XClass> newCenas = new ArrayList<>();
        for(XClass cena: cenas){
            newCenas.add(cena.clone());
        }
        this.outrasCenas = newCenas;
    }
```
*Nota: Setters de uma lista nem sempre fazem sentido. Dependendo do contexto, pode fazer mais
sentido implementar métodos que adicionem ou removam elementos às listas.*

### Métodos "obrigatórios" de definir.
Estes métodos devem ser definidos para todas as classes que sejam criadas. Salvo exceções em que
sejam inúteis.

#### equals
O equals é o mais importante, e raramente é inútil.
```Java
    public boolean equals(Object o){
/*[1]*/ if(this == o) return true;

/*[2]*/ if(o == null || this.getClass() != o.getClass())
            return false;

/*[3]*/ MyClass that = (MyClass) o;
/*[4]*/ return this.num == that.getNum()
            && this.nome.equals(that.getNome())
            && this.outraCena.equals(that.getOutraCena())
            && this.nomes.equals(that.getNomes())
            && this.outrasCenas.equals(that.getOutrasCenas());
    }
```
Análise do código:
 1. Compara-se os apontadores. Se forem iguais, sabemos que o objeto é o mesmo.
 2. Verifica-se se o objeto é null ou se as classes entre eles são diferentes. Qualquer uma destas
    indica que não são iguais.
 3. Faz-se o cast para se poder chamar métodos.
 4. Verifica-se se todos os elementos da classe são iguais.
    * Para tipos primitivos podemos comparar normalmente com `==`.
    * Para classes chamamos o `equals` das mesmas.

#### toString
O toString é importante para efeitos de debug. Pode também ser adaptado para aplicações de terminal.
```Java
    public String toString(){
        StringBuffer sb = new StringBuffer("MyClass: ");
        sb.append("Num: ").append(this.num).append(", ");
        sb.append("Nome: ").append(this.nome).append(", ");
        sb.append("Outra Cena: ").append(this.outraCena).append(", ");
        sb.append("Nomes: ").append(this.nomes).append(", ");
        sb.append("Outras Cenas: ").append(this.outrasCenas).append(", ");
        return sb.toString();
    }
```
#### Clone
O clone deve ser implementado porque o Nestor diz que sim. Objetos imutáveis não devem
 implementar este método.
```Java
    public MyClass clone(){
        return new MyClass(this);
    }
```


---
Lista dos principais objetos imutáveis disponíveis:
 * Integer
 * Float
 * Double
 * Char
 * Boolean
 * String
 * LocalDate
 * LocalTime
 * LocalDateTime
(existem mais)

---
Todo o código pode ser visto no [Anexo](ANEXOS/Anatomia_de_uma_classe.java)
