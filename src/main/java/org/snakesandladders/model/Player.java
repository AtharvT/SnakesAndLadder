package org.snakesandladders.model;

public class Player {
    private final String name;
    private int position;

    public Player(String name, int startingPosition) {
        this.name = name;
        this.position = startingPosition;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

//    public void move(int steps) {
//        if (steps < 0) {
//            System.out.println(name + " moves back " + (-steps) + " steps.");
//        } else {
//            System.out.println(name + " moves forward " + steps + " steps.");
//        }
//        this.position += steps;
//        // You can add additional logic here, for example, to check if the player has won.
//    }
    public void setPosition(int position) {
        this.position = position;
    }

}
