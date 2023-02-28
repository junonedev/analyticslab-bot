package pro.analyticslab.bot;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public class Variables {
    private static final Logger logger = LoggerFactory.getLogger(Variables.class);


    public static final OffsetDateTime STARTUP = OffsetDateTime.now();
    public static final String SUPPORT_GUILD_ID = Config.getProperty("analyticslab.guild.id");
    public static final List<String> SUPPORT_LOCALES = List.of(Config.getProperty(
            "analyticslab.options.locales.support").split(","));
    public static final String PRIMARY_LOCALE = Config.getProperty("analyticslab.options.locales.primary");
    public static final List<String> OWNER_IDS = List.of(Config.getProperty("analyticslab.owners").split(","));
    public static final boolean REBOOT_DIED_SHARD = Objects.equals(
            Config.getProperty("analyticslab.options.rebootDiedShard"), "true");



    // translation system
    private static JsonObject json;


    /**
     * Loads json file with translations
     * @param translatePath translation path
     */
    public void loadTranslator(@Nonnull String translatePath) {
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(Objects.requireNonNull(
                Variables.class.getClassLoader().getResourceAsStream(translatePath)));

        json = gson.fromJson(reader, JsonObject.class);

        logger.info("Translator initialized");
    }


    private static JsonElement getTranslatedElement(String key, String locale) {
        String[] keys = (key + (locale == null ? "" : "." + locale)).split("\\.");

        JsonObject lastElement = json.get(keys[0]).getAsJsonObject();
        for (int i = 1; i < keys.length; i++) {
            JsonElement element = lastElement.get(keys[i]);

            if (element == null || (element.isJsonObject() && i + 1 == keys.length)) break;

            if (i + 1 == keys.length) {
                return element;
            } else {
                lastElement = element.getAsJsonObject();
            }
        }

        return null;
    }

    /**
     * Find translation by key & locale
     * @param locale Locale (optional)
     * @param key Translation key
     * @return String
     */
    public static String getMessageAsString(String key, String locale) {
        JsonElement element = getTranslatedElement(key, locale);
        return element == null ? "[translator error]" : element.getAsString();
    }

    /**
     * Find translation by key & locale
     * @param key Translation key
     * @return String
     */
    public static String getMessageAsString(String key) {
        return getMessageAsString(key, null);
    }
}
