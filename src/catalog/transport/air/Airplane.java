package catalog.transport.air;

import catalog.RandomInt;

public class Airplane {

    @RandomInt(max = 350)
    int speed;

    int weight;

    @Override
    public String toString() {
        return "Airplane{" +
                "speed=" + speed +
                ", weight=" + weight +
                '}';
    }
}
