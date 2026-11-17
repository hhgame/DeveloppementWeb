package miage;
import org.springframework.stereotype.Component;

@Component
public class LoggerService {
    private Logger logger;
    public LoggerService(Logger logger) {
        this.logger = logger;
    }
    public void logMessage(String message) {
        this.logger.log(message);
    }
}
