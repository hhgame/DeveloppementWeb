package miage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Logger logger = new FileLogger();
        LoggerService logService = new LoggerService(logger);
        logService.logMessage("TEST: test of the logger service");

        try (var context = new AnnotationConfigApplicationContext("miage")) {
            LoggerService loggerService = context.getBean(LoggerService.class);
            loggerService.logMessage("TEST: logger injection");
        }
    }
}