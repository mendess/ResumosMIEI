---
---
# NOT FINISHED
---
---
# Hierarquia de classes

A hierarquia de classes tem imensas vantagens no que toca a manter a sanidade de quem programa.

**Nota:** É assumido que há um getter definido corretamente para todas as variaveis de
 instancia declaradas.

## Situação exemplo
Imagina que precisamos de implementar um programa com alunos e professores.
Poderiamos então implementar as seguintes classes.
```Java
public class Aluno {
	private String num;
	private String nome;
	private String email;
	private ArrayList<String> cadeiras;

	public Aluno(String num, String nome, String email, List<String> cadeiras){
		this.num = num;
		this.nome = nome;
		this.email = email;
		this.cadeiras = new ArrayList<>(cadeiras);
		//Um dos constructores de arraylist permite passar uma Collection
		//No entanto é preciso ter cuidado pois este constructor não
		// clona os elementos da lista, ou seja, so pode ser usado para
		// listas de objectos imutaveis
	}
	/* Getters, Setters, etc */
}

public class Prof {
	private String num;
	private String nome;
	private String email;
	private String departamento;

	public Prof(String num, String nome, String email, String departamento){
		this.num = num;
		this.nome = nome;
		this.email = email;
		this.departamento = departameto;
	}
	/* Getters, Setters, etc */
}
```
No entanto, temos assim muito codigo duplicado. Variaveis repetidas, getters, setters, se for o caso, tb teremos metodos iguais repetidos.

O equals, toString vão ser extremamento parecidos tambem.

## Classe que agrupa o codigo comum
Uma primeira solução para o nosso problema é então agrupar as partes comuns numa só classe.
```Java
public class Pessoa {
	private String num;
	private String nome;
	private String email;

	public Pessoa(String num, String nome, String email){
		this.num = num;
		this.nome = nome;
		this.email = email;
	}
	/* Getters, Setters, etc */
}
```
Resta-nos apenas relacionar as nossas antigas classes com esta.
Isto é muito simples. Mas tem alguns detalhes importantes a ter em conta,
que falarei a seguir.
```Java
public class Aluno extends Pessoa {
	private ArrayList<String> cadeiras;

	public Aluno(String num, String nome, String email, List<String> cadeiras){
		super(num,nome,email);
		this.cadeiras = new ArrayList<>(cadeiras);
	}
	/* Getters, Setters, etc */
}

public class Prof extends Pessoa {
	private String departamento;

	public Prof(String num, String nome, String email, String departamento){
		super(num,nome,email);
		this.departamento = departameto;
	}
	/* Getters, Setters, etc */
}
```
A primeira palavra ('keyword') nova aqui é `super`. Esta serve para fazer
referencia a simbolos (variaveis, metodos, etc) definidos na super classe.

Assim a linha `super(...)` refere-se ao constructor da superclasse e evita-se assim
 repetir codigo, visto que aquela parte do constructor é igual para todas as
 subclasses de `Pessoa`.

Podemos também usar o super para chamar metodos da superclasse. O classico exemplo é
o equals.
```Java
public class Aluno extends Pessoa {
	/* igual ao de cima */
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || this.getClass() != o.getClass())
			return false;
		Aluno that = (Aluno) o;
		return super.equals(o)
		    && this.cadeiras.equals(that.getCadeiras());
	}
}
```
### Implicações desta construção do codigo
Pessoa é uma classe como outra qualquer. Podemos instancia-la e chamar-lhe os
metodos que tem definido.
```Java
	Pessoa p = new Pessoa("D99999","JBB","god@ghci.com");
	String num = p.getNum(); // funciona
	ArrayList<String> cadeiras = p.getCadeiras(); // não funciona, este getter não
                                                     // esta definido na classe Pessoa
```
`Aluno` (e `Prof`) podem tambem ser instanciados. E podemos chamar metodos
 definidos na superclasse e metodos definidos na propria classe.
```Java
	Aluno a = new Aluno("A12345","Mendes","mendes@mymail.com", new ArrayList<>());
	String num = a.getNum(); // funciona. O getter esta definido na superclasse
				 // que esta extende.
	ArrayList<String> cadeiras = a.getCadeiras();
				// funciona. O getter esta definido nesta classe
```
Mas a mais interessante consequencia é a seguinte:
```Java
	Pessoa p = new Prof("D54321","Nestor","nestor@clone.com","DI");
	String num = p.getNum(); //funciona.
	String departamento = p.getDepartamento();
				// não funciona. Um objecto do tipo Pessoa não tem
				// este getter definido. Apesar do "verdadeiro" tipo
				// da classe ser Prof
	Prof prof = (Prof) p;
	String departamento = prof.getDepartamento();
				// funciona. Apos o cast a JVM já consegue econtrar
				// o metodo e chama-lo
```
Este "malabarismo" de casts pode dar a entender que instanciar objectos desta forma
é uma perda de tempo. Mas há uma muito boa razão para isto. Considere o seguinte
exemplo.
```Java
	ArrayList<Aluno> alunos = new ArrayList<>();
	alunos.add(new Aluno(...)); // funciona
	alunos.add(new Prof(...)); // não funciona

	ArrayList<Pessoa> pessoas = new ArrayList<>();
	pessoas.add(new Aluno(...)); // funciona. Alunos são Pessoas
	pessoas.add(new Prof(...)); // funciona. Profs são Pessoas
```
Aqui vemos uma das mais comuns utilizações deste paradigma. Outro exemplo é o seguinte
```Java
public class Cartao{
	private Pessoa p;
	public Cartao(Pessoa p){
		this.p = p;
	}
```
Esta classe pode ter, na sua variavel de instancia, uma `Pessoa`, um `Aluno` ou um
 `Prof`.

#### Atencao
```Java
	ArrayList<Pessoa> pessoas = new ArrayList<>();
	// Adicionam-se montes de cenas a lista
	Pessoa p = pessoas.get(42);
	Prof prof = (Prof) p; // Não funciona caso o tipo "verdadeiro" de p não seja Prof
```
Este codigo poderá lancar uma `ClassCastException`. Este tipo de erros não podem ser
 detetados pelo compilador mas quando ocorrem crasham o programa.

Para evitar isto simplesmente temos de fazer uma simples verificação.
```Java
	if(p instanceof Prof){
		Prof prof = (Prof) p;
		/* Fazer cenas de prof */
	}
```

## Classes Abstractas
Até agora tudo bem, mas há um problema com a nossa aplicação. Podem haver
 instancias de `Pessoa`, que não faz sentido no nosso contexto. (Ou se é Aluno ou se
 é Prof)

Este problema é facilmente resolvido adicionando uma palavra na declaração de `Pessoa`.
```Java
public abstract class Pessoa {
	/* Tudo igual */
}
```
Tudo que foi escrito acima deste paragrafo continua a ser verdade, excepto
`new Pessoa(...)`, isto é agora invalido.

Outra vantagem é que agora podemos definir metodos abstractos.
```Java
public abstract class Pessoa {
	public abstract irParaAula(Aula a);
}
```
Agora todas as classes que extendem `Pessoa` são obrigadas a implementar o metodo `irParaAula`.

Um metodo abstracto é declarado na classe abstracta e implementado nas subclasses da mesma. Isto é, na classe abstracta dizemos que todas as suas subclasses vão ter aquele comportamento (leia-se metodo) mas cada subclasse é que escolhe como o implementa.

**Um metodo abstracto é obrigatorio implementar**

Isto é util quando faz sentido que todas a subclasses implementem um certo comportamento mas este depende de fatores que não conhecemos na superclasse, por exemplo.

Em Java cada classe pode apenas extender uma superclasse. (C++, por exemplo, permite
 herança multipla)


## Interfaces
Finalmente temos as interfaces. Estas servem para resolver o problema de Java não permitir herança multipla.

Interfaces podem ser vistas como contratos, ou seja, uma classe que implementa uma interface compromete-se a implementar os metodos descritos na interface.

### Situação exemplo
Estamos a desenvolver uma api para reproduzir musica.
```Java
public class Radio {
    private ArrayList<Musica> musicas;
    public Radio(){
        this.musicas = new ArrayList<>();
    }

    public void queue(Musica musica){
        this.musica.add(musica);
    }

    public void play(){
        this.musicas.get(0).start();
        this.musicas.remove(0);
    }
}
```
Mas não queremos que restringir aos utilizadores da nossa api (outros programadores) a uma classe `Musica` definida por nós.

### Solução
Podemos então definir uma interface, um contrato. E as condições deste contrato são muito simples:
 * *"Se implementares estes metodos, eu posso reproduzir a tua musica"*

Definimos a interface então da seguinte forma:
```Java
public interface Musica{
    void start();
}
```
De notar que não coloquei um *access modifier* (`public`, `private`, etc.). Isto é porque todos os metodos definidos numa interface
 são `public` por defeito.

Assim um programador que queria usar a nossa api pode definir a sua classe de musica e utlizar o radio que nós já definimos.
```Java
public class MyMusic implements Musica{
    private byte[] song;

    public MyMusic(byte[] song){
        this.song = song;
    }

    public void start(){
        /*
        Reproduzir a musica
        */
    }
}
```

### Interfaces mais comuns
#### Collections
Nas `Collections` do Java temos varias interfaces que podemos utilizar.
Alguns exemplos
 * [List](link.to/list)
 * [Map](link.to/map)

Podemos utlizar estas interfaces para fazer o nosso codigo mais genérico.
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

E este sistema permite-nos também implementar as nossa proprias implementações de listas/maps/etc
```Java
public class DBMap implements Map<String,Cena>{
}
```
