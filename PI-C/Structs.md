#Structs
Estruturas permitem-nos guardar tipos de dados mais complexos que `int` ou
`arrays`. Permitem definir tipos complexos e associar dados.

## Declaração
Uma struct é declarada com a *keyword* do mesmo nome. Por exemplo, se quiser
guardar um registo de uma pessoa posso declarar a seguinte estrutura:
```C
struct pessoa{
    int idade
    char* nome;
};
```
Podemos então usar esta esturtura no nosso codigo:
```C
int main(){
    struct pessoa p;
    
    p.idade = 18;
    
    char* nome = "Jbb";
    p.nome = nome;

    printf("Idade: %d, Nome: %s", e.idade, e.nome);
    return 0;
}
```
### Typedef
A keyword `typedef` permite-nos dar "alcunhas" aos nossos tipos, por exemplo 
se fizermos `typedef int inteiro` podemos escrever:
```C
typedef int inteiro;

int main(){
    inteiro i = 0;
}
```
Esta keyword é muito util quando usada em conjunto com structs para que não
tenhamos de repetir a palavra `struct` tantas vezes.
```C
struct pessoa{
    int idade;
    char* nome;
};

typedef struct pessoa Pessoa;

int main(){
    Pessoa p;  // Usamos os typedef em vez do "nome completo" do tipo
    /* resto do código */
}
```
Isto pode ser ainda "comprimido" para isto:
```C
typedef struct pessoa{
    int idade;
    char* nome;
}Pessoa;
```
Que é equivalente.

## Estruturas dentro de esturturas
As estruturas são apenas uma coleção de campos relacionados e se podemos
guardar um `int` podemos também guardar outra estrutura.

Imagine-se que queremos guardar a data de nascimento da nossa `Pessoa`.
```C
typedef struct data{
    int dia;
    int mes;
    int ano;
}Data;

typedef struct pessoa{
    int idade;
    char* nome;
    Data nascimento;
}Pessoa;

int main(){
    Pessoa p;
    p.idade = 18;
    p.nascimento.dia = 25;
    p.nascimento.mes = 4;
    p.nascimento.ano = 1997;

    return 0;
}
```

## Passar por copia vs referencia
Quando passamos uma instância da nossa estrutura para uma função podemos
faze-lo por copia ou por referencia. Este tem as mesmas consequencias de
passar qualquer outra variavel por copia ou referencia.

Uma diferença importante é que se passarmos as esturtura por copia esta vai
ter de ser completamente copiada para a função que a recebe tendo um
desempenho menor.

Por esta razão é costume declarar apontadores para as esturturas e trabalhar
com estes.

Por exemplo:
```C
int main(){
    Pessoa* p = malloc(sizeof(Pessoa)); // Alocamos memória para a
                                               // estrutura
    (*p).idade = 18;

    /* pointer para um int para comparação */

    int* i = malloc(sizeof(int));
    *i = 18;
}
```
Porque estamos a usar um apontador temos de aceder ao valor apontado (*p) para
alterarmos o conteudo. Mas como esta sintaxe é muito chata de escrever existe
uma maneira equivalente de o fazer: `p->idade`.

Pondo isto em pratica:
```C
void birthday(Pessoa p){
    p.idade += 1;
}
void birthday_prt(Pessoa* p){
    p->idade += 1;
}
int main(){
    Pessoa p;
    p.idade = 18;
    printf("%d\n", p.idade);

    birthday(p);
    printf("%d\n", p.idade);

    birthday_prt(&p);
    printf("%d\n", p.idade);

    return 0;
}
```
Este programa terá o seguinte output:
```
18
18
19
```
### Typedef II
No caso de querer-mos sempre com um apontador para a nossa estrutura podemos
mudar o `typedef` para nos facilitar a vida.
```C
struct pessoa{
    int idade;
    char* nome;
}

typedef struct pessoa * Pessoa;

int main(){
    Pessoa p = malloc(sizeof(struct pessoa));
    p->idade = 18;

    return 0;
}
```
Diferenças importantes a notar com este typedef é que, apesar de nao estar
 explicito, o tipo `Pessoa` é um apontador, pelo que o `sizeof` tem de
 "medir" o tamanho da esturura para alocar tamanho certo.

Este `typedef` pode novamente ser abreviado para:
```C
typedef struct pessoa{
    int idade;
    char* nome;
} * Pessoa;
```
Que é equivalente.