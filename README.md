# Snakes and Ladders Game

## Description
This project implements a classic Snakes and Ladders game in Java. It's designed to be run in a console/terminal and supports multiple players. The game includes a dynamic board setup, dice roll simulation, and automatic navigation of snakes and ladders.

## Key Features
- **Driver Application**: Asks for manual input or loads a JSON config as input.
- **Rules and Game Engine**: Drive the rules and games respectively, adhering to the Single Responsibility Principle.
- **Extensible and Modular Game Elements**: Includes Snake, Ladder, Mine, and Crocodile with a common interface and jump methods.
- **Dice Rolling Strategy**: Min, Max, Sum strategy for dice rolling implemented using the Strategy Design Pattern.
- **InputHandler**: Handles all manual inputs in case we are not loading from a config.
- **JUnit Tests**: For key driver classes like GameEngine, RulesEngine, and InputHandler.
- **Extensible Board**: With basic validations in place.
- **Turn Limit**: A limit of 100 turns to terminate the game safely.
- **Used Records** : Use of new Java 17 features for record for model classes like game entities

## Assumptions Made During Implementation
1. The game allows for a manual roll of dice per person, meaning each player can ask for a manual roll of dice.
2. Certain edge cases, such as when a crocodile leads to a snake/ladder or any other game element, are not handled.

Have attempted all requirements of the problem statement