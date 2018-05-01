import java.util.ArrayList;

public class MyClass /*[extends SuperClass] [implements InterfaceClass, ...]*/{

    private int num;
    private String nome;
    private MyClass outraCena;
    private ArrayList<String> nomes;
    private ArrayList<MyClass> outrasCenas;

    public MyClass(){
        this.num = 0;
        this.nome = "";
        this.outraCena = new MyClass();
        this.nomes = new ArrayList<>();
        this.outrasCenas = new ArrayList<>();
    }

    public MyClass(int num, String nome, MyClass outraCena,
                    ArrayList<String> nomes, ArrayList<MyClass> outrasCenas){
        this.num = num;
        this.nome = nome;
        this.outraCena = outraCena.clone();
        this.nomes = new ArrayList<>(nomes);
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

    public MyClass getOutraCena(){
        return this.outraCena.clone();
    }

    public ArrayList<String> getNomes(){
        return new ArrayList<>(this.nomes);
    }

    public ArrayList<XClass> getOutrasCenas(){
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

    public void setNomes(ArrayList<String> nomes){
        this.nomes = new ArrayList<>(nomes);
    }

    public void setOutrasCenas(ArrayList<XClass> cenas){
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
