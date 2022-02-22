package foodpanda;

import java.sql.*;

public class OracleConnect {
    private Connection connection;
    private static final String host = "127.0.0.1";
    private static final String dbname = "orcl";
    private static final String username = "c##foodpanda";
    private static final String password = "c##foodpanda";
    private static final String port = "1521";

    public OracleConnect()  {

        try {
            String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + dbname;
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database! "+ e);
            e.printStackTrace();
        }

    }

    public int updateDB(String query) throws Exception {
        Statement statement = this.connection.createStatement();
        return statement.executeUpdate(query);
    }

    public ResultSet searchDB(String query) throws Exception {
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(query);
    }

    public void close() throws Exception {
        this.connection.close();
    }

    public Connection getConnection() {
        return connection;
    }
}