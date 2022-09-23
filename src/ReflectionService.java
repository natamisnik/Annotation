import catalog.RandomInt;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class ReflectionService {

    private static final String CLASS_EXTENSION = ".class";
    private static final String JAR_EXTENSION = ".jar";
    private static final String JAR_PREFIX = "jar:";

    public static List<Class<?>> fetchClassesFromPackage(
            String packageName) throws URISyntaxException, IOException {

        List<Class<?>> classes = new ArrayList<>();

        String packagePath = packageName.replace('.', '/');
        URL resource = ClassLoader.getSystemClassLoader().getResource(packagePath);

        if (resource != null) {
            if (resource.toString().startsWith(JAR_PREFIX)) {
                classes.addAll(fetchClassesFromJar(resource, packageName));
            } else {
                File file = new File(resource.toURI());
                classes.addAll(fetchClassesFromDirectory(file, packageName));
            }
        } else {
            throw new RuntimeException("Resource not found for package: " + packageName);
        }

        return classes;
    }

    private static List<Class<?>> fetchClassesFromDirectory(File directory, String packageName)
            throws IOException {

        List<Class<?>> classes = new ArrayList<>();

        String[] files = directory.list();
        for (String file : files) {

            String className = null;
            if (file.endsWith(CLASS_EXTENSION)&&!file.contains("RandomInt")) {
                className = packageName + '.'
                        + file.substring(0, file.lastIndexOf(CLASS_EXTENSION));
            }

            if (className != null) {
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException | NoClassDefFoundError e) {
                }
            }

            File subDir = new File(directory, file);
            if (subDir.isDirectory()) {
                classes.addAll(fetchClassesFromDirectory(subDir, packageName + '.' + file));
            }
        }

        return classes;
    }

    public static List<Class<?>> fetchClassesFromJar(URL resource, String packageName)
            throws IOException {

        String resourcePath = resource.getPath();

        String jarPath = resourcePath
                .replaceFirst("[.]jar[!].*", ".jar")
                .replaceFirst("file:", "")
                .replace(" ", "\\ ");
        jarPath = URI.create(jarPath).getPath();

        return fetchClassesInSamePackageFromJar(packageName, jarPath, null);
    }

    public static List<Class<?>> fetchClassesInSamePackageFromJar(
            String packageName, String jarPath, URLClassLoader urlClassLoader)
            throws IOException {

        List<Class<?>> classes = new ArrayList<>();

        String packagePath = packageName.replace('.', '/');

        try (JarFile jarFile = new JarFile(jarPath)) {

            Enumeration<JarEntry> en = jarFile.entries();
            while (en.hasMoreElements()) {
                JarEntry entry = en.nextElement();
                String entryName = entry.getName();

                if (entryName != null
                        && entryName.endsWith(CLASS_EXTENSION)
                        && entryName.startsWith(packagePath)
                        && !entryName.substring(packagePath.length() + 1).contains("/")) {

                    try {
                        String entryToLoad = entryName.substring(
                                        0, entryName.lastIndexOf(CLASS_EXTENSION))
                                .replace('/', '.');

                        Class<?> entryClass =
                                urlClassLoader == null ? Class.forName(entryToLoad)
                                        : urlClassLoader.loadClass(entryToLoad);

                        if (entryClass != null) {
                            classes.add(entryClass);
                        }
                    } catch (ClassNotFoundException | NoClassDefFoundError e) {
//                        logger.log(Level.SEVERE, "Cannot instantiate: {0} {1}",
//                                new Object[]{entryName, e.toString()});
                    }
                }
            }
        }

        return classes;
    }

    public Object setValuebyRandomInt (Class<?> clazz) throws Exception {

        Object obj = createNewInstance(clazz);

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(RandomInt.class)) {
                RandomInt randomInt = field.getDeclaredAnnotation(RandomInt.class);

                int i = (int) (Math.random() * (randomInt.max() - randomInt.min()) + randomInt.min());

                field.setAccessible(true);

                field.set(obj, i);
                field.setAccessible(false);
            }
        }

        return obj;

    }

    public Object createNewInstance(Class<?> clazz) throws Exception {

        Constructor ctors = clazz.getConstructor();
        return ctors.newInstance();

        }

    }
