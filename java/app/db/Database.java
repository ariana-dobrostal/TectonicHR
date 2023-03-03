package app.db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * The Database class represents the PostgreSQL database of the TectonicHR project.
 * It can create a user and a database using the configuration specified in the Config class.
 */
public class Database {
    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    private static Database currentInstance = null;

    public Database(String host, String port, String db, String user, String pass) {
        this.host = host;
        this.port = port;
        this.database = db;
        this.username = user;
        this.password = pass;
    }

    public static void setInstance(Database db) {
        currentInstance = db;
    }

    public static Database getInstance() {
        return currentInstance;
    }

    /**
     * Opens a connection of the database if it exists.
     *
     * @throws RuntimeException If something goes wrong while generating the connection.
     */
    public Connection connect() {
        try {
            String dbUrl = createUrl(host, port, database);
            System.out.println("connecting to " + dbUrl + " ...");
            return DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a URL which is used to establish a connection with a database.
     *
     * @param host String host part of the URL.
     * @param port String port part of the URL.
     * @param db   String database name part of the URL.
     * @return String URL.
     */
    private String createUrl(String host, String port, String db) {
        return "jdbc:postgresql://" + host + ":" + port + "/" + db;
    }


    /**
     * Runs and executes the queries withing the specified sql file.
     *
     * @param resourceFilePath A file that is used in creating the tables.
     * @throws RuntimeException If there were problems while opening the resource file.
     */
    public void runSqlFile(Connection connection, String resourceFilePath) {
        InputStream input = Database.class.getClassLoader().getResourceAsStream(resourceFilePath);
        if (input == null) {
            System.out.println("resourceFilePath");
            throw new RuntimeException("problem opening resource");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        String fileText = reader.lines().collect(Collectors.joining("\n"));
        String[] queries = fileText.split(";");

        for (String query : queries) {
            execute(connection, query);
        }
    }

    /**
     * Executes an sql query of the update type.
     *
     * @param sql String sql query to be executed.
     * @throws RuntimeException If something goes wrong while creating the statement.
     */
    private void execute(Connection connection, String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}

