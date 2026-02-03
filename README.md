
# T4 – Contador de Palavras Eficiente (Hash + AVL)

**Estruturas de Dados - UNIRIO ED 2025.2**

Contador de frequência usando **Hash** (O(1)) + **AVL** (O(log n))

## 🚀 Como Executar

**EXIGE exatamente 4 argumentos:**

```
java main.Main <texto.txt> <exclusoes.txt> <F|A> <C|D>
```
Exemplos

# Frequência decrescente
```
java main.Main teste.txt exclusoes.txt F D
```
# Frequência crescente
```
java main.Main teste.txt exclusoes.txt F C
```
# Alfabética A→Z
```
java main.Main teste.txt exclusoes.txt A C
```
# Alfabética Z→A
```
java main.Main teste.txt exclusoes.txt A D
```

📋 Passo a Passo
Coloque os arquivos na pasta:

teste.txt (texto)

exclusoes.txt (uma palavra por linha)

Compile:

```
javac *.java
```
Execute:

```
java main.Main teste.txt exclusoes.txt F D
```
🛠️ Estruturas
Hash: Contagem O(1)

LSE: Ordem descoberta O(n)

AVL: Ordenação balanceada O(log n)

Autores:
Gabriel Viola & Lucas Martins
