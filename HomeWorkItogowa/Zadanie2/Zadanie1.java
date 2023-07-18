import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;

class Toy {
    private String id;
    private String name;
    private int weight;

    public Toy(String id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}

public class Zadanie1 {
    private PriorityQueue<Toy> toys;

    public Zadanie1() {
        toys = new PriorityQueue<>((t1, t2) -> t2.getWeight() - t1.getWeight());
    }

    public void addToy(Toy toy) {
        toys.add(toy);
    }

    public Toy getRandomToy() {
        Random random = new Random();
        int totalWeight = toys.stream().mapToInt(Toy::getWeight).sum();
        int randomNumber = random.nextInt(totalWeight) + 1;

        int currentWeight = 0;
        for (Toy toy : toys) {
            currentWeight += toy.getWeight();
            if (randomNumber <= currentWeight) {
                return toy;
            }
        }

        return null;
    }

    public void simulateLottery(int iterations, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < iterations; i++) {
                Toy toy = getRandomToy();
                String result = toy != null ? toy.getId() : "No toy available";
                writer.write(result + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Zadanie1 zadanie1 = new Zadanie1();

        zadanie1.addToy(new Toy("1", "конструктор", 2));
        zadanie1.addToy(new Toy("2", "робот", 2));
        zadanie1.addToy(new Toy("3", "кукла", 6));

        zadanie1.simulateLottery(10, "lottery_results.txt");
    }
}
