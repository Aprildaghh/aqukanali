import Controller.MainController;
import Dao.IntentionDAO;
import Model.Entity.ContentEntity;
import Model.Entity.IntentionEntity;
import View.ConsoleView;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        IntentionDAO asd = IntentionDAO.getInstance();

        System.out.println(asd.getIntentionByDate(LocalDate.of(2002, 7, 15)).toString());


        // MainController main = new MainController();
        // main.start();
    }

    private static void addOrUpdateIntentionTest()
    {
        IntentionDAO dao = IntentionDAO.getInstance();

        IntentionEntity intention = new IntentionEntity(0, Date.valueOf(LocalDate.now()));
        ContentEntity c1 = new ContentEntity(false, "dsaasd", intention);
        ContentEntity c2 = new ContentEntity(true, "asdafs", intention);

        intention.add(c1);
        intention.add(c2);

        dao.addOrUpdateIntention(intention);
    }
}