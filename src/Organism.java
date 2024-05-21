import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Organism {
    protected int x;
    protected int y;
    protected int breedTime;
    protected boolean hasMoved;
    protected World world;

    public Organism(int x, int y, World world) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.breedTime = 0;
        this.hasMoved = false;
    }

    public void move() {
        // To be implemented in derived classes
    }

    protected boolean isValidMove(int newX, int newY) {
        return newX >= 0 && newX < world.getHeight() && newY >= 0 && newY < world.getWidth() && world.getAt(newX, newY) == null;
    }


    protected void incrementBreedTime() {
        breedTime++;
    }

    protected boolean isReadyToBreed(int breedThreshold) {
        return breedTime >= breedThreshold;
    }

    protected void resetBreedTime() {
        breedTime = 0;
    }

    protected boolean hasMoved() {
        return hasMoved;
    }

    protected void setMoved(boolean moved) {
        hasMoved = moved;
    }
}