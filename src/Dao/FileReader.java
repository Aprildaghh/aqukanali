package Dao;

import Model.Intention;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private static FileReader uniqueInstance;
    private static Connection con;
    private final String username = "root";
    private final String password = "zxcasd45";
    private final String scheme = "aqukanali";

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

        List<String> intentions = intention.getIntentionList();

        String theDate = String.valueOf(LocalDate.now().getYear() + '-' + LocalDate.now().getMonthValue() + '-' + LocalDate.now().getDayOfMonth());
        String intentionSQL = "insert into intention(theDate) values(date('"+theDate+"'))";
        Statement stmt = null;
        ResultSet rs = null;
        stmt = con.createStatement();
        rs = stmt.executeQuery(intentionSQL);

        String intentionContentAbstractSQL = "insert into intentioncontent(intentionid, iscompleted, intentioncontent) values ((select intentionid from intention where intention.theDate = date('" + theDate + "')), 0, \"";

        for(int i = 0; i < intentions.size(); i++)
        {
            String intentionContentSQL = intentionContentAbstractSQL + intentions.get(i) + "\")";
            rs = stmt.executeQuery(intentionContentSQL);
        }
    }

    public Intention getIntentionByDate(LocalDate date) throws SQLException {
        Intention intention = null;
        String theDate = String.valueOf(date.getYear() + '-' + date.getMonthValue() + '-' + date.getDayOfMonth());
        String sql = "select intentioncontent.isCompleted as state, intentioncontent.IntentionContent as content from intentioncontent where intentioncontent.IntentionId = (select intention.IntentionId from intention where intention.thedate = date('"+theDate+"'));";
        Statement stmt = null;
        ResultSet rs = null;
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        List<String> intentions = new ArrayList<>();
        List<Boolean> completions = new ArrayList<>();
        boolean stillGoin = true;

        for(int i = 0; stillGoin; i++)
        {
            intentions.add(rs.getString(1));
            completions.add(rs.getBoolean(0));
            stillGoin = rs.next();
        }

        return new Intention(date, intentions, completions);
    }

    public void updateIntention(Intention intention) throws SQLException {
        LocalDate date = intention.getDate();
        String theDate = String.valueOf(date.getYear() + '-' + date.getMonthValue() + '-' + date.getDayOfMonth());
        List<String> intentionContents = intention.getIntentionList();
        List<Boolean> intentionCompletions = intention.getIntentionMarkList();
        Statement stmt = null;
        ResultSet rs = null;
        stmt = con.createStatement();

        for(int i = 0; i < intentionContents.size(); i++)
        {
            String sql = "update intentioncontent set intentioncontent.iscompleted = " + (intentionCompletions.get(i) ? '1' : '0') +
                    ", intentioncontent.intentioncontent = \"" + intentionContents.get(i) +
                    "\") where intentionid = (select intentionid from intention where intention.thedate = date('"+ theDate + "'))";

            rs = stmt.executeQuery(sql);
        }

    }

}
