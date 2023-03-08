package pro.analyticslab.bot.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Translator {
    private static final Logger logger = LoggerFactory.getLogger(Translator.class);
    private static final HashMap<String, JsonObject> JSONs = new HashMap<>();


    /**
     * Loads json file with translations
     * @param translatePath translation path
     */
    public Translator initializeLocale(@Nonnull String key, @Nonnull String translatePath) {
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(Objects.requireNonNull(
                Variables.class.getClassLoader().getResourceAsStream(translatePath)));

        JSONs.put(key, gson.fromJson(reader, JsonObject.class));

        logger.info(key + " translations initialized");
        return this;
    }


    private static JsonElement getTranslatedElement(String key, String locale) {
        String[] keys = (key + (locale == null ? "" : "." + locale)).split("\\.");

        JsonObject json = JSONs.get(locale);
        if (json == null)
            throw new IllegalArgumentException("Unsupported locale: no " + locale + " file");

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

        logger.warn("No translation for " + Arrays.toString(keys) + " was found");
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
        return element == null ? "[translator error: 404]" : element.getAsString();
    }
}
