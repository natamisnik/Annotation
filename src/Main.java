
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Insert package:  ");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String packagename = reader.readLine();
        ReflectionService service = new ReflectionService();

        List<Class<?>> classes1 = service.fetchClassesFromPackage(packagename);

        List<Object> objects = new ArrayList<>();

        for (Class cl:classes1
        ) {

         objects.add(service.setValuebyRandomInt(cl));

        }

        for (Object o:objects
             ) {
            System.out.println(o.toString());
        }
    }
}














