# GDB

O `gdb` é o debugger * de facto * para C/C++ em linux. Permite inspecionar o que um programa esta a fazer num certo ponto de execução, inspecionar o valor das variaveis naquele momento, entre muitas outras coisas.

## Instalação

Ubuntu/Debian based
```
sudo apt-get update
sudo apt-get install gdb
```

Arch based
```
sudo pacman -Syu
sudo pacman -S gdb
```

Outros distros
  * Google it

## Casos de uso

Usamos o `gdb`, ou qualquer outro debugger, para:
  1. Analisar um programa que crashou (segmentation fault)
  2. Analisar memory leaks
  3. Inspecionar o estado de algum variavel durante a execução do mesmo

## Compilar

Para usar o `gdb` é preciso includir uma flag extra quando compilamos o nosso código com o `gcc`. A flag em questão `-g`. Desto modo o `gcc` sabe que é para compilar o programa em debug mode e includi informação extra no executavel.

Um exemplo com mais flags à mistura.
```
gcc -g -ansi code.c -o code
```

## Primeiros passos no GDB

Para usar o `gdb` é simples, assumindo que o terminal se encontra na diretoria do executavel:
```bash
gdb program
```
ou
```bash
gdb
```
```bash
(gdb) fie program
```
A ultima linha de código é dentro do terminal do `gdb`. O comando file faz load a um programa.

De facto, o `gdb` possui uma shell interativa com um conjunto de comandos bastante completos.
É fácil notar que estamos dentro do terminal do `gdb` pelo
```bash
(gdb)
```


