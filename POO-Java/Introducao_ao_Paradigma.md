# Introdução ao paradigma

* **Disclaimer:** Devido a varios factores muita da sintaxe não vai ser completamente explicada neste documento.
No entanto, lendo todos os resumos essas "falhas" serão preenchidas *

O que é que significa programar "orientado aos objectos"?

Este paradigma coloca o como principal entidade principal do raciocinio os objectos, logo convem definir o que é um objecto!

## Objectos
Os objectos, resumidamente, são  Dados + Comportamento. Uma boa analogia para o mundo fisico é, por exemplo, uma maquina que
 vende chocolates. Tem dados: Quantos chocolates tem, e tem comportamento: recebe moedas, dá chocolates.

Mas como traduzimos isto para codigo?

Para fazermos uma maquina de chocolates primeiro temos de a "planear" ou "fazer o molde". Assim nasce a noção de classe.

(Esta cadeira faz uso de Java por isso essa é a linguagem que iremos usar nos nossos exemplos)
```Java
public class MaquinaChocolates{

}
```

Começamos assim a construir a nossa classe que servirá de molde para as nossas maquinas de chocolates. Vamos então dar-lhe os tais
dados + comportamento

```Java
public class MaquinaChocolates{
    private int dinheiro;
    private String[] chocolates;
    private int numChocolates;

    /* Constructor omitido */

    public String comprar(int dinheiroRecebido){
        if(dinheiroRecebido > 10){
            String vendido = this.chocolates[this.numChocolates];
            this.numChocolates--;
            this.dinheiro += dinheiroRecebido;
            return vendido;
        }
        return null;
    }
}
```
Os dados (variáveis de instância) são declarados no inicio da classe e o comportamento (metodos) declarado a seguir.

Podemos então agora criar **instâncias** da nossa classe.
```Java
    MaquinaChocolates maquina = new MaquinaChocolates();
```
Esta instância representa agora em memória os dados e comportamento que definimos na declaração da classe. E para evocar este comportamento
 temos de lhe "enviar uma mensagem".
```Java
    String chocolate = maquina.comprar(11);
```
Esta linha de codigo ira invocar o metodo e alterar os dados **desta** instância.
