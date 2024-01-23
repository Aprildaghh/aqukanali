package Dao;

import Model.Intention;

import java.sql.*;
import java.time.LocalDate;

public class FileReader {

    private static FileReader uniqueInstance;
    private static Connection con;
    private final String username = "root";
    private final String password = "zxcasd45";
    private final String scheme = "";

    private FileReader()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+scheme, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static FileReader getInstance() {
        if(uniqueInstance == null)
        {
            uniqueInstance = new FileReader();
        }
        return uniqueInstance;
    }

    public void addIntention(Intention intention) throws SQLException {
        String sql = "";
        Statement stmt = null;
        ResultSet rs = null;
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
    }

    public Intention getIntentionByDate(LocalDate date) throws SQLException {
        Intention intention = null;
        String sql = "";
        Statement stmt = null;
        ResultSet rs = null;
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        if(rs.next())
        {
            intention = new Intention(date, null, null);
        }

        return intention;
    }

    public void updateIntention(Intention intention) throws SQLException {
        String sql = "";
        Statement stmt = null;
        ResultSet rs = null;
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
    }
    /*

            if(rs.next())
                totalCups = rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalCups;
     */


}
