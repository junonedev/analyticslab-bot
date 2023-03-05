package pro.analyticslab.bot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.analyticslab.bot.AnalyticsLab;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Logger logger = LoggerFactory.getLogger(Config.class);


    // PROPERTIES
    private static final Properties props = new Properties();

    public void loadProperties(@Nonnull String propsPath) throws IOException {
        props.load(AnalyticsLab.class.getClassLoader().getResourceAsStream(propsPath));
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
