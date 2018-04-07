# Anatomia de uma classe

Notas:
 * Código entre [ ] é opcional. Ou seja fica ao teu criterio se é necessário.
 * As palavras 'MyClass', 'XClass', 'SuperClass' e 'InterfaceClass' sao apenas exemplicficativos


### Declaração

Nesta declaração indicamos o nome da nossa class, se extende uma classe e se, e quais, interfaces implementa.
```Java
public class MyClass [extends SuperClass] [implements InterfaceClass, ...] {
```

### Variaveis de instancia
Estas devem ser sempre `private` para garantir o encapsulamento do estado
interno do objecto.
```Java
	private int num;
	private String nome;
	private XClass outraCena;
	private ArrayList<String> nomes;
	private ArrayList<XClass> outrasCenas;
```
### Constructores
Os contrutores permitem instanciar novos objectos da classe. Devem inicializar todos as variaveis de instancia.

#### Constructor vazio
Este constructor inicializa as variaveis de instancia com valores por defeito.
```Java
	public MyClass(){
		this.num = 0;
		this.nome = "";
		this.outraCena = new OutraCena();
		this.nomes = new ArrayList<>();
		this.outrasCenas = new ArrayList<>();
	}
```
#### Constructor parameterizado
Este constructor recebe como parametro os valores que as variaveis de instancia
 devem tomar.

*(Nota: Uma classe pode ter mais variaveis de instacia do que os parametros
 passados neste tipo de constructor, caso, por exemplo, uma destas tenha um
 valor derivado de outras variaveis)*

As diferentes atribuições feitas neste constructor são explicadas atravez dos
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
#### Constructor de copia
Este constructor permite criar uma copia exata de outra instancia deste objecto

*(Nota: Este constructor assume que todos os getters clonam corretamente as
 variaveis de instancia que retornam)*
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
Os getters permitem aceder as variaveis de instancia de uma instancia da nossa
 classe. Devem ser `public` apenas os que queremos que seja possivel aceder.

#### Get the uma variavel de tipo primitivo
As variaveis são 'passed by value' ou seja o seu valor é copiado. Logo este get é simples

(isto fica mais claro mais a frente)
```Java
	public int getNum(){
		return this.num;
	}
```
#### Get de um objecto imutavel
Uma string é imutavel logo retornar um apontador para este objecto que pertence ao estado interno
no nosso objecto não tem problema.
```Java
	public String getNome(){
		return this.nome;
	}
```
#### Get de um objecto mutavel
Um objecto mutavel deve ser clonado para manter o encapsulamento. Se este objecto fosse alterado
fora da instancia que o retornou, implica alterar o estado interno da mesma instancia.

(porque o variavel é 'passed by value' e esta, na verdade, é um apontador)
```Java
	public XClass getOutraCena(){
		return this.outraCena.clone();
	}
```
#### Get de uma lista de objectos imutaveis
Tem de se criar uma lista nova. Apesar de cada objecto individual da lista ser imutavel a lista
em si não é emutavel. Logo se retornarmos a lista diretamente, novos valores podem ser adicionados
à mesma, alterando o estado interno da instancia a partir do exterior.

Para isto podemos fazer uso do construtor da `ArrayList` que recebe uma `Collection` e copia os valores.

**_ATENÇÃO SÓ PUDEMOS USAR ESTE CONSTRUCTOR PARA LISTAS DE OBJECTOS IMUTAVEIS_**
```Java
	public List<String> getNomes(){
		return new ArrayList<>(this.nomes);
	}
```
#### Get de uma lista de objectos mutaveis
Como no get anterior tem de ser criada uma nova lista. Mas o elementos ao ser adicionados à mesma
tem de ser clonados.
```Java
	public List<XClass> getOutrasCenas(){
		ArrayList<XClass> newOCenas = new ArrayList<>();
		for(XClass cena: this.outrasCenas){
			newOCenas.add(cena.clone());
		}
		return newOCenas;
	}
```
### Setters
Os setters seguem o mesmo principio dos getters. Objectos imutaveis e tipos primitivos
não tem de ser clonados. O resto sim.
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

	public void setOutrasCenas(List<String> cenas){
		ArrayList<XClass> newCenas = new ArrayList<>();
		for(XClass cena: cenas){
			newCenas.add(cena.clone());
		}
		this.outrasCenas = newCenas;
	}
```
*Nota: setters de uma lista nem sempre fazem sentido. Dependendo do contexto, pode fazer mais
sentido implementar métodos que adicionem ou removam elementos as listas*

### Métodos "obrigatorios" de definir.
Estes métodos devem ser definidos para todas as classes que sejam criadas. Salvo exceções em que
sejam que sejam inuteis.

#### equals
O equals é o mais importante e raramente é inutil.
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
Analise do código:
 1. Compara-se os apontadores. Se forem iguais sabemos que o objecto é o mesmo.
 2. Verifica-se se o objecto é null ou se as classes entre eles são diferentes. Qualquer uma destas
    indica que nao são iguais.
 3. Faz-se o cast para se poder chamar chamar métodos.
 4. Verifica-se se todos os elementos da classe são iguais.
    * Para tipos primitivos podemos comparar normalemte `==`.
    * Para classes chamamos o `equals` das mesmas.


#### toString
O toString é importante para efeitos de debug. Pode tb ser adaptado para aplicações de terminal.
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
#### clone
O clone deve ser implementado porque o Nestor diz que sim. Objectos imutaveis não devem
 implementar este metodo.
```Java
	public MyClass clone(){
		return new MyClass(this);
	}
```


---
Lista dos principais objectos imutaveis disponiveis:
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
