

public class Simulation {
    public static void main(String[] args) {
        World world = new World(5, 5);
        world.populate(20, 2); // 100 ants and 5 doodlebugs
        world.simulate();
    }
}