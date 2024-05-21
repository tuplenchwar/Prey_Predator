import java.util.ArrayList;
import java.util.Random;

class Doodlebug extends Organism {
    private static final int BREED_THRESHOLD = 8;
    private static final int STARVE_THRESHOLD = 3;
    private static final int[] DIRECTIONS = {0, 1, 2, 3}; // 0: up, 1: right, 2: down, 3: left

    private int starveTime;

    public Doodlebug(int x, int y, World world) {
        super(x, y, world);
        this.starveTime = 0;
    }

    @Override
    public void move() {
        Organism prey = findPrey();
        if (prey != null) {
            eat(prey);
            incrementBreedTime();
        } else {
            shuffleArray(DIRECTIONS);
            for (int dir : DIRECTIONS) {
                int[] newCoords = getNewCoordinates(dir);

                if (isValidMove(newCoords[0], newCoords[1])) {
                    world.move(x, y, newCoords[0], newCoords[1]);
                    x = newCoords[0];
                    y = newCoords[1];
                    incrementBreedTime();
                    setMoved(true);
                    if (isReadyToBreed(BREED_THRESHOLD)) {
                        breed();
                    }
                    starveTime++;
                    if (starveTime >= STARVE_THRESHOLD) {
                        world.removeOrganism(this);
                    }
                    return;
                }
            }
            setMoved(false);
        }
    }


    private Organism findPrey() {
        if (isAdjacent(x - 1, y, Ant.class)) {
            return world.getAt(x - 1, y);
        } else if (isAdjacent(x, y + 1, Ant.class)) {
            return world.getAt(x, y + 1);
        } else if (isAdjacent(x + 1, y, Ant.class)) {
            return world.getAt(x + 1, y);
        } else if (isAdjacent(x, y - 1, Ant.class)) {
            return world.getAt(x, y - 1);
        }
        return null;
    }

    private void eat(Organism prey) {
        world.removeOrganism(prey);
        world.move(x, y, prey.x, prey.y);
        x = prey.x;
        y = prey.y;
        starveTime = 0; // Reset starve time
    }

    private void breed() {
        ArrayList<int[]> emptyCells = world.getEmptyAdjacentCells(x, y);
        if (!emptyCells.isEmpty()) {
            int[] coords = emptyCells.get(new Random().nextInt(emptyCells.size()));
            world.placeOrganism(new Doodlebug(coords[0], coords[1], world));
            resetBreedTime();
        }
    }

    private int[] getNewCoordinates(int direction) {
        int newX = x, newY = y;

        if (direction == 0) {
            newX--;
        } else if (direction == 1) {
            newY++;
        } else if (direction == 2) {
            newX++;
        } else {
            newY--;
        }

        return new int[]{newX, newY};
    }

    private void shuffleArray(int[] array) {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private boolean isAdjacent(int newX, int newY, Class<? extends Organism> type) {
        if (isValidCoordinate(newX, newY)) {
            Organism adjacent = world.getAt(newX, newY);
            return adjacent != null && type.isInstance(adjacent);
        }
        return false;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < world.getHeight() && y >= 0 && y < world.getWidth();
    }
}
