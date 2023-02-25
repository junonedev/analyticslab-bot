package pro.analyticslab.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Properties;

public class Props {
    private static final Logger logger = LoggerFactory.getLogger("Props");
    private static final Properties props = new Properties();


    /**
     * Initialize properties module
     * @param filePath Path starts from `/resources` directory
     */
    public void load(@Nonnull String filePath) throws IOException {
        props.load(App.class.getClassLoader().getResourceAsStream(filePath));
        logger.info("Property file initialized");
    }

    /**
     * Get secret environment from `.properties` file
     * @param key Property key
     * @return String
     */
    public static String getProperty(@Nonnull String key) {
        String value = props.getProperty(key);

        if (value == null) {
            logger.warn(key + " property returns null value");
        }

        return value;
    }
}
