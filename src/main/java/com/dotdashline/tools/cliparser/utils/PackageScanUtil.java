package com.dotdashline.tools.cliparser.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*
* @author Raymond Tsang
* @author Steven Liang
*
* @since 0.1
*/
public class PackageScanUtil {

    /**
     * Scans all classes accessible from the context class loader which belong
     * to the given package and subpackages.
     *
     * @param packageName
     *            The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }



    /**
     * Recursive method used to find all classes in a given directory and
     * subdirs.
     *
     * @param directory
     *            The base directory
     * @param packageName
     *            The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(
                        Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    /**
     * Returns a map of classes for a target annotation class from a collection
     * of packages.
     * 
     * @param annotatedClass
     * @param packageNames
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Map<Annotation, Class<?>> findAnnotatedClasses(Class<? extends Annotation> annotatedClass,
            String... packageNames) throws ClassNotFoundException, IOException {

        Map<Annotation, Class<?>> ret = new HashMap<>();

        // for each package get all the classes and filter out the one that
        // matches the given annotation
        Arrays.asList(packageNames).stream().forEach(p -> {
            try {
                PackageScanUtil.getClasses(p).stream().filter(x -> x.isAnnotationPresent(annotatedClass))
                        .forEach(x -> ret.put(x.getAnnotation(annotatedClass), x));
            } catch (ClassNotFoundException | IOException e) {
                // do nothing
            }
        });
        return ret;
    }

}
