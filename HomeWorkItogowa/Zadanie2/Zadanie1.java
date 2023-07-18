import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Toy {
    private String id;
    private String name;
    private int quantity;
    private double weight;

    public Toy(String id, String name, int quantity, double weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void reduceQuantity() {
        quantity--;
    }
}

public class Zadanie1 {
    private List<Toy> toyList;
    private List<Toy> prizeList;

    public Zadanie1() {
        toyList = new ArrayList<>();
        prizeList = new ArrayList<>();
    }

    public void addToy(Toy toy) {
        toyList.add(toy);
    }

    public void updateWeight(String toyId, double weight) {
        for (Toy toy : toyList) {
            if (toy.getId().equals(toyId)) {
                toy.setWeight(weight);
                break;
            }
        }
    }

    public void runLottery() {
        Random random = new Random();
        double totalWeight = 0;

        // Вычисляем общий вес всех игрушек
        for (Toy toy : toyList) {
            totalWeight += toy.getWeight();
        }

        while (!toyList.isEmpty()) {
            double randomNumber = random.nextDouble() * totalWeight;
            double weightSum = 0;

            for (Toy toy : toyList) {
                weightSum += toy.getWeight();
                if (randomNumber <= weightSum) {
                    prizeList.add(toy);
                    toy.reduceQuantity();
                    totalWeight -= toy.getWeight();
                    break;
                }
            }

            toyList.remove(prizeList.get(prizeList.size() - 1));
        }

        savePrizeList();
    }

    public void savePrizeList() {
        try (FileWriter fileWriter = new FileWriter("prize_list.txt")) {
            for (Toy toy : prizeList) {
                fileWriter.write(toy.getId() + " - " + toy.getName() + "\n");
            }
            System.out.println("Список призовых игрушек сохранен в файле 'prize_list.txt'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Zadanie1 lottery = new Zadanie1();

        // Добавление игрушек
        Toy toy1 = new Toy("1", "Мяч", 5, 30);
        Toy toy2 = new Toy("2", "Кукла", 3, 20);
        Toy toy3 = new Toy("3", "Машинка", 4, 50);

        lottery.addToy(toy1);
        lottery.addToy(toy2);
        lottery.addToy(toy3);

        // Обновление веса игрушки
        lottery.updateWeight("2", 25);

        // Запуск розыгрыша и сохранение списка призовых игрушек
        lottery.runLottery();
    }
}
