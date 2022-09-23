package catalog.animals;

import catalog.RandomInt;

public class Tiger {

    @RandomInt(max = 130)
    int speed;

    @RandomInt(max = 20)
    int age;

    @RandomInt(max = 220)
    int lenght;

    @Override
    public String toString() {
        return "Tiger{" +
                "speed=" + speed +
                ", age=" + age +
                ", lenght=" + lenght +
                '}';
    }
}
