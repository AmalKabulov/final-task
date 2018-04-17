package by.epam.processor.util;

public class ClassUtil {

    public static StackTraceElement getCallerMethodInfo() {

        return Thread.currentThread().getStackTrace()[3];
    }
}
