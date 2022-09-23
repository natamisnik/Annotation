package catalog.transport.ground;

import catalog.RandomInt;

public class Car {

    @RandomInt(max = 200)
    int speed;

    int weight;

    @Override
    public String toString() {
        return "Car{" +
                "speed=" + speed +
                ", weight=" + weight +
                '}';
    }
}
