public class MyClass /*[extends SuperClass] [implements InterfaceClass, ...]*/{

	private int num;
	private String nome;
	private XClass outraCena;
	private ArrayList<String> nomes;
	private ArrayList<XClass> outrasCenas;

	public MyClass(){
		this.num = 0;
		this.nome = "";
		this.outraCena = new OutraCena();
		this.nomes = new ArrayList<>();
		this.outrasCenas = new ArrayList<>();
	}

	public MyClass(int num, String nome, XClass outraCena,
			ArrayList<String> nomes, ArrayList<XClass> outrasCenas){
		this.num = num;
		this.nome = nome;
		this.outraCena = outraCena.clone();
		this.nomes = new ArrayList<>();
		for(String nome: nomes){
			this.nomes.add(nome);
		}
		this.outrasCenas = new ArrayList<>();
		for(XClass cena: outrasCenas){
			this.outrasCenas.add(cena.clone());
		}
	}

	public MyClass(MyClass myClass){
		this.num = myClass.getNum();
		this.nome = myClass.getNome();
		this.outraCena = myClass.getOutraCena();
		this.nomes = myClass.getNomes();
		this.outrasCenas = myClass.getOutrasCenas();
	}

	public int getNum(){
		return this.num;
	}

	public String getNome(){
		return this.nome;
	}

	public XClass getOutraCena(){
		return this.outraCena.clone();
	}

	public List<String> getNomes(){
		ArrayList<String> newNomes = new ArrayList<>();
		for(String nome: this.nomes){
			newNomes.add(nome);
		}
		return newNomes;
	}

	public List<XClass> getOutrasCenas(){
		ArrayList<XClass> newOCenas = new ArrayList<>();
		for(XClass cena: this.outrasCenas){
			newOCenas.add(cena.clone());
		}
		return newOCenas;
	}

	public void setNum(int num){
		this.num = num;
	}

	public void setNome(String nome){
		this.nome = nome;
	}

	public void setOutraCena(XClass outraCena){
		this.outraCena = outraCena;
	}

	public void setNomes(List<String> nomes){
		ArrayList<Nomes> newNomes = new ArrayList<>();
		for(String nome: nomes){
			newNomes.add(nome);
		}
		this.nomes = newNomes;
	}

	public void setOutrasCenas(List<String> cenas){
		ArrayList<XClass> newCenas = new ArrayList<>();
		for(XClass cena: cenas){
			newCenas.add(cena.clone());
		}
		this.outrasCenas = newCenas;
	}

	/*
	 *
	 * Metodos
	 *
	 */

	// Metodos obrigatorios

	public boolean equals(Object o){
		if(this == o) return true;

		if(o == null || this.getClass() != o.getClass())
			return false;

		MyClass that = (MyClass) o;
		return this.num == that.getNum()
		    && this.nome.equals(that.getNome())
		    && this.outraCena.equals(that.getOutraCena())
		    && this.nomes.equals(that.getNomes())
		    && this.outrasCenas.equals(that.getOutrasCenas());
	}

	public String toString(){
		StringBuffer sb = new StringBuffer("MyClass: ");
		sb.append("Num: ").append(this.num).append(", ");
		sb.append("Nome: ").append(this.nome).append(", ");
		sb.append("Outra Cena: ").append(this.outraCena).append(", ");
		sb.append("Nomes: ").append(this.nomes).append(", ");
		sb.append("Outras Cenas: ").append(this.outrasCenas).append(", ");
		return sb.toString();
	}

	public MyClass clone(){
		return new MyClass(this);
	}
}
