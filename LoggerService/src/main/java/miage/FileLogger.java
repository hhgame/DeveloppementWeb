package miage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;


public class FileLogger implements Logger{
    public void log(String message) {
        try(PrintWriter out = new PrintWriter(new FileOutputStream("dev.log"))) {
            out.write(message);
            out.write('\n');
        } catch (FileNotFoundException e) {
            System.out.println("Error of logging file!");
        }
    }
}
