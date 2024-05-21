import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class World {
    private Organism[][] grid;
    private int height;
    private int width;

    public World(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new Organism[height][width];
    }

    public void populate(int numAnts, int numDoodlebugs) {
        Random rand = new Random();
        int count = 0;
        while (count < numAnts) {
            int x = rand.nextInt(height);
            int y = rand.nextInt(width);
            if (grid[x][y] == null) {
                grid[x][y] = new Ant(x, y, this);
                count++;
            }
        }
        count = 0;
        while (count < numDoodlebugs) {
            int x = rand.nextInt(height);
            int y = rand.nextInt(width);
            if (grid[x][y] == null) {
                grid[x][y] = new Doodlebug(x, y, this);
                count++;
            }
        }
    }


    public void simulate() {
        Scanner scanner = new Scanner(System.in);
        boolean continueSimulation = true;

        while (continueSimulation) {
            printWorld();
            System.out.println("Do you want to continue simulation? (Y/N)");
            String input = scanner.nextLine().toUpperCase();

            if (input.equals("N")) {
                continueSimulation = false;
            } else if (input.equals("Y")) {
                step();
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }

    public void step() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Organism organism = grid[i][j];
                if (organism != null && !organism.hasMoved()) {
                    organism.move();
                }
            }
        }

        // Reset hasMoved flag after each step
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Organism organism = grid[i][j];
                if (organism != null) {
                    organism.setMoved(false);
                }
            }
        }
    }

    public void printWorld() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] != null) {
                    if (grid[i][j] instanceof Ant) {
                        System.out.print("O ");
                    } else if (grid[i][j] instanceof Doodlebug) {
                        System.out.print("X ");
                    }
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Organism getAt(int x, int y) {
        return grid[x][y];
    }

    public void move(int fromX, int fromY, int toX, int toY) {
        grid[toX][toY] = grid[fromX][fromY];
        grid[fromX][fromY] = null;
    }

    public void placeOrganism(Organism organism) {
        grid[organism.x][organism.y] = organism;
    }

    public void removeOrganism(Organism organism) {
        grid[organism.x][organism.y] = null;
    }

    public ArrayList<int[]> getEmptyAdjacentCells(int x, int y) {
        ArrayList<int[]> emptyCells = new ArrayList<>();
        if (isValidCoordinate(x - 1, y) && grid[x - 1][y] == null) {
            emptyCells.add(new int[]{x - 1, y});
        }
        if (isValidCoordinate(x + 1, y) && grid[x + 1][y] == null) {
            emptyCells.add(new int[]{x + 1, y});
        }
        if (isValidCoordinate(x, y - 1) && grid[x][y - 1] == null) {
            emptyCells.add(new int[]{x, y - 1});
        }
        if (isValidCoordinate(x, y + 1) && grid[x][y + 1] == null) {
            emptyCells.add(new int[]{x, y + 1});
        }
        return emptyCells;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < height && y >= 0 && y < width;
    }
}