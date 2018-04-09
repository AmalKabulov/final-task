package by.epam.util;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationProcessor {

    public static List<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation, String pkgName) {
        return PackageScanner.getClassesForPackage(pkgName)
                .stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }




}
