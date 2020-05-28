# Projeto Undead Survival

# Equipe
* Gabriel Costa Kinder - 234720

# Descrição Resumida do Projeto
Undead Survival é um jogo celular de sobrevivência e estratégia baseado em turnos.

# Vídeo do Projeto
[Breve video explicativo do projeto](https://drive.google.com/open?id=14vn7SfmNX47Of_C4Yxt0i782d_3f-4OM)

# Diagrama Geral de Componentes

## Componente Enemy/Player - Board

Este é o diagrama compondo componentes bases para o jogo:

![Diagrama de componentes Enemy/Player](EnemyPlayerComp.png)

# Componente EnemyX

![Componente EnemyX](EnemyXComp.png)

## Interfaces

Interfaces associadas a esse componente:

![Interfaces EnemyX](EnemyInt.png)

# Componente Player

![Player](PlayerComp.png)

## Interfaces

Interfaces associadas a esse componente:

![Diagrama Interfaces](diagrama-interfaces.png)

# Componente Board

![Board](BoardComp.png)

## Interfaces

Interfaces associadas a esse componente:

![Diagrama Interfaces](diagrama-interfaces.png)

# Detalhamento das Interfaces

## Interface IEnemy
Interface geral para todos os inimigos.

Método | Objetivo
-------| --------
getHealth() | Retorna a vida do inimigo.
decreaseHealth(int) | Decresce a vida do inimigo pelo valor recebido.
move() | Move o inimigo em direção ao jogador
