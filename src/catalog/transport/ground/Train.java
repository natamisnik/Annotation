package catalog.transport.ground;

import catalog.RandomInt;

public class Train {

    @RandomInt(max = 300)
    int speed;

    int weight;

    @Override
    public String toString() {
        return "Train{" +
                "speed=" + speed +
                ", weight=" + weight +
                '}';
    }
}
