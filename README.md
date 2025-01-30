
# Simulador de Colônia de Formigas

## Descrição
Este projeto implementa uma simulação de uma colônia de formigas em Java, onde formigas navegam por um ambiente com obstáculos (tamanduás, lama e vento) tentando alcançar seus formigueiros.

## Características
- 3 formigueiros distribuídos no mapa
- Múltiplos tipos de obstáculos:
  - Tamanduá: remove a formiga do mapa
  - Lama: alterna o estado da formiga entre parada e em movimento
  - Vento: empurra a formiga para uma direção aleatória
- Sistema de filas nos formigueiros
- Estatísticas detalhadas da simulação

## Como Executar
1. Compile todos os arquivos .java
2. Execute a classe `Principal`
```java
javac src/*.java
java src.Principal