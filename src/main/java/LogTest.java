import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class LogTest {

    private static Logger logger = LogManager.getLogger();
    public static MatterMostLogger http;

    public static void main(String [] args) throws Exception{
        http = new MatterMostLogger();
        http.setErrorType("warn");
        http.sendPost();
        logger.error("This is an error message");
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.fatal("This is a fatal message");

    }
}
