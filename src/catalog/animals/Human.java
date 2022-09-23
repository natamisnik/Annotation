package catalog.animals;

import catalog.RandomInt;

public class Human {

            @RandomInt(max = 10)
          public int speed;

            @RandomInt(max = 110)
           public int age;

            @RandomInt(max = 220)
            public int height;

    @Override
    public String toString() {
        return "Human{" +
                "speed=" + speed +
                ", age=" + age +
                ", height=" + height +
                '}';
    }
}
