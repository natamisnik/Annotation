package catalog.animals;

import catalog.RandomInt;

public class Turtle {

    @RandomInt(max = 35)
    int speed;

    @RandomInt(max = 200)
    int age;

    @RandomInt(max = 260)
    int lenght;

    @Override
    public String toString() {
        return "Turtle{" +
                "speed=" + speed +
                ", age=" + age +
                ", lenght=" + lenght +
                '}';
    }
}
