# Stack

A stack é uma estrutura de dados baseada no princípio *Last In First Out*, ou seja, o último valor a ser colocado é o primeiro a ser removido. Desta forma, é uma estrutura semelhante a uma *pilha*. Novos valores são sempre colocados no topo da pilha e quando se pretende remover alguma coisa terá que ser o que estiver no topo. Esta adição e remoção é feita através de duas instruções básicas em *Assembly*: **push** e **pop**. 

## Push

A instrução **push** adiciona um valor ao topo da **stack**.

Analisemos a instrução `push %eax` :

* O **%esp** (stack pointer) é decrementado, uma vez que, ao ser adicionado um valor, o topo da stack altera-se.

* O valor que se encontra no registo **%eax** é colocado no local apontado pelo novo **%esp**. 


Desta forma, realizar estas duas intruções  é equivalente a realizar uma só instrução **push**:

   ```sub 4, %esp```

  ```mov %eax, (%esp)```

Porque é que é uma subtração e não uma adição? Como devem ter visto na explicação da memória, os endereços "crescem de cima para baixo". Desta forma, se acrescentamos um valor no topo, o apontador terá que diminuir. Porque é que subtraimos 4 unidades? Deve-se ao facto de estarmos a lidar com uma arquitetura **IA-32** em que adicionar um valor significa adicionar **4 bytes** de informação. 

## Pop

A instrução **pop** retira um valor do topo da stack e coloca-o no registo indicado. Contudo, o valor que se encontra no topo da stack não é exatamente removido. Aquilo que se verifica é que é alterado o apontador para o topo da pilha (**%esp**), incrementando-o 4 unidades. A explicação do **pop** é análoga à do **push** e esta instrução é equivalente a realizar estas duas instruções:

```mov (%esp), %eax```

```add %esp, 4```
