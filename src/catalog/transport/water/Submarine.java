package catalog.transport.water;

import catalog.RandomInt;

public class Submarine {

    @RandomInt(max = 150)
    int speed;

    int weight;

    @Override
    public String toString() {
        return "Submarine{" +
                "speed=" + speed +
                ", weight=" + weight +
                '}';
    }
}
