package utility;

import java.io.*;

/**
 * Logger Singleton used to write stack traces of exceptions to a file called err.log
 */
public class Logger {
    private static Logger instance;
    private static PrintWriter writer;

    private Logger() {}

    public static Logger getInstance() {
        if(instance == null) {
            instance = new Logger();
            try {
                writer = new PrintWriter("err.log");
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public static void log(String s) {
        if(writer != null)
            writer.append(s);
    }

    public static PrintWriter getWriter() {
        if(writer == null) {
            try {
                writer = new PrintWriter("err.log");
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return writer;
    }

    public static void close() {
        writer.close();
    }
}
