Snakes and Ladders Game
Description
This project implements a classic Snakes and Ladders game in Java. It's designed to be run in a console/terminal and supports multiple players. The game includes a dynamic board setup, dice roll simulation, and automatic navigation of snakes and ladders.

Key Features implemented
1. Driver Application which asks for manual input or loads a json config as input
2. Rules and Game engine which drive the rules and games respectively adhering to Single Responsibility Principle
3. Extensible and Modular Game Elements including Snake, Ladder, Mine and Crocodile with common interface and jump methods.
4. Min, Max, Sum strategy for dice rolling implemented using Strategy Design Pattern.
5. InputHandler to handle all manual inputs in case we are not loading from config
6. Junit tests for key driver classes like GameEngine, RulesEngine and InputHandler
7. Extensible Board with basic validations in place
8. A limit of 100 turns to terminate the game safely.

Assumptions taken while implementing the project:
Since I was not fully sure of the manual override requirement. I am taking a manual roll of dices per person. Which means each player can
ask for a manual roll of dice.
