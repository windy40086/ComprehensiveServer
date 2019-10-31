package util;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil {
    public static void Close(Closeable ... args){
        for(Closeable arg:args){
            if(arg!=null){
                try {
                    arg.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
