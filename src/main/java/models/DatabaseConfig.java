package models;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final String PROPERTIES_FILE = "db.properties";
    private Properties properties;

    public DatabaseConfig() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getJdbcURL() {
        return properties.getProperty("jdbc.url");
    }

    public String getJdbcUsername() {
        return properties.getProperty("jdbc.username");
    }

    public String getJdbcPassword() {
        return properties.getProperty("jdbc.password");
    }
}

