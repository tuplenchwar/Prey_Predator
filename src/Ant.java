import java.util.ArrayList;
import java.util.Random;

class Ant extends Organism {
    private static final int BREED_THRESHOLD = 3;
    private static final int[] DIRECTIONS = {0, 1, 2, 3}; // 0: up, 1: right, 2: down, 3: left

    public Ant(int x, int y, World world) {
        super(x, y, world);
    }

    @Override
    public void move() {
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
                return;
            }
        }
        setMoved(false);
    }

    private void breed() {
        ArrayList<int[]> emptyCells = world.getEmptyAdjacentCells(x, y);
        if (!emptyCells.isEmpty()) {
            int[] coords = emptyCells.get(new Random().nextInt(emptyCells.size()));
            world.placeOrganism(new Ant(coords[0], coords[1], world));
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
}