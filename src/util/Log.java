package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static StackTraceElement[] stes = Thread.currentThread().getStackTrace();

    public static void d(String log) {
        System.out.print(sdf.format(new Date()));
        System.out.print(" D/"+stes[2].getClassName()+": ");
        System.out.println(log);
    }

    public static void e(String log) {
        System.err.print(sdf.format(new Date()));
        System.err.print(" E/: ");
        System.err.println(log);
    }
}
