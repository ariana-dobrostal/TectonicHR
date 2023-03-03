package app;

import app.db.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbCreator {

    public static void main(String[] args) {
        Config config = Config.fromCommandLine(args);     

        Database myDb = new Database(config.DB_HOST, config.DB_PORT, config.DB_DB, config.DB_USER, config.DB_PASS);
        try (Connection connection = myDb.connect()) {
            myDb.runSqlFile(connection, "db/tables.sql");
            myDb.runSqlFile(connection, "db/data.sql");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates or recreates a database, all its tables (using file db/tables.sql), and fills them with data(using file db/data.sql).
     * Can be called multiple times.
     */
    public static void recreateDatabase(Connection connection, String database, String username, String password) {
        if (databaseExists(database, connection)) {
            dropDatabase(database, connection);
        }
        if (userExists(username, connection)) {
            System.out.println("user already exists");
        } else {
            createUser(connection, username, password);
        }
        createDatabase(database, username, connection);
    }

    /**
     * Creates a user who will be the owner of the database.
     *
     * @param user     String name of the user to be created.
     * @param password String password of the user to be created.
     */
    private static void createUser(Connection connection, String user, String password) {
        execute("CREATE USER " + user + " WITH PASSWORD '" + password + "' CREATEDB", connection);
        System.out.println("user created");
    }

    /**
     * Checks if the user exists.
     *
     * @param user String name of the user that is being tested.
     * @return True if it exists, otherwise false.
     * @throws RuntimeException If something goes wrong while creating the statement.
     */
    private static boolean userExists(String user, Connection connection) {
        try (Statement statement = connection.createStatement()) {
            String userExistsSql = "SELECT 1 FROM pg_roles WHERE rolname='" + user + "'";
            ResultSet resultSet = statement.executeQuery(userExistsSql);
            return resultSet.isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Creates a database with a specified owner.
     *
     * @param database String name of the database to be created.
     * @param owner    String name of the user who will be the owner of the database.
     */
    private static void createDatabase(String database, String owner, Connection connection) {
        execute("CREATE DATABASE " + database + " OWNER " + owner, connection);
        System.out.println("database " + database + " created");
    }

    /**
     * Checks if the database exists.
     *
     * @param database String name of the database that is being tested.
     * @return True if it exists, otherwise false.
     * @throws RuntimeException If something goes wrong while creating the statement.
     */
    private static boolean databaseExists(String database, Connection connection) {
        try (Statement statement = connection.createStatement()) {
            String userExistsSql = "SELECT 1 FROM pg_database WHERE datname='" + database + "'";
            ResultSet resultSet = statement.executeQuery(userExistsSql);
            return resultSet.isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Drops the specified database.
     *
     * @param database String name of the database to be dropped.
     */
    private static void dropDatabase(String database, Connection connection) {
        execute("DROP DATABASE " + database, connection);
        System.out.println("database dropped");
    }
    
    /**
     * Drops the given user.
     * @param user
     * @param connection
     */
    private static void dropUser(String user, Connection connection) {
        execute("DROP USER " + user, connection);
        System.out.println("user dropped");
    }

    /**
     * Executes an sql query of the update type.
     *
     * @param sql String sql query to be executed.
     * @throws RuntimeException If something goes wrong while creating the statement.
     */
    private static void execute(String sql, Connection connection) {
        // System.out.println("EXECUTE: " + sql);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
