import Controller.MainController;
import Dao.IntentionDAO;
import View.ConsoleView;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        IntentionDAO asd = IntentionDAO.getInstance();

        try {
            System.out.println(asd.getIntentionByDate(LocalDate.of(2002, 7, 15)).toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // MainController main = new MainController();
        // main.start();
    }
}