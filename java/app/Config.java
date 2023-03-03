package app;

import app.db.Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * The Config class configures the properties fields which the Database will use for creating a user and a database.
 */
public class Config {
    public String DB_HOST;
    public String DB_PORT;

    

    public String DB_DB;
    public String DB_USER;
    public String DB_PASS;

    /**
     * Static method to create a Config object using the command line.
     * If no arguments of value are present, the default constructor is used.
     * <p>
     * Example of use: $java -cp my.jar app.Aplikacija -config application.properties
     *
     * @param args Array of string arguments passed from the command line.
     * @return A Config object.
     * @throws RuntimeException If the file name is missing after -config.
     */
    public static Config fromCommandLine(String[] args) {
        List<String> list = Arrays.asList(args);
        int index = list.indexOf("-config");
        if (index >= 0) {
            if (index + 1 < list.size()) {
                String filePath = list.get(index + 1);
                System.out.println("Using config file: " + filePath);
                return new Config(filePath);
            } else {
                throw new RuntimeException("Missing file name after -config");
            }
        } else {
            System.out.println("Using default config file!");
            return new Config();
        }
    }

    /**
     * Creates a configuration using the application.properties file inside the project.
     */
    public Config() {
        InputStream input = Database.class.getClassLoader().getResourceAsStream("application.properties");
        setProperties(input);
    }

    /**
     * Creates a configuration using the specified file path to the wanted properties file.
     *
     * @throws RuntimeException If the input stream is unable to be created.
     */
    public Config(String filePath) {
        try (InputStream input = new FileInputStream(filePath)) {
            setProperties(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Sets the needed properties.
     *
     * @param input InputStream that is used for extracting properties.
     * @throws RuntimeException If the properties are unable to be loaded or not all required fields have been initialized.
     */
    private void setProperties(InputStream input) {
        try {
            Properties properties = new Properties();
            if (input == null) {
                System.out.println("Unable to get properties.");
                return;
            }
            properties.load(input);

            DB_HOST = properties.getProperty("database.host");
            DB_PORT = properties.getProperty("database.port");

            DB_DB = properties.getProperty("database.db");
            DB_USER = properties.getProperty("database.user");
            DB_PASS = properties.getProperty("database.pass");

            if (DB_HOST == null || DB_PORT == null ||  DB_DB == null || DB_USER == null || DB_PASS == null) {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
