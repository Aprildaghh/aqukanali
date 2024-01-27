package Dao;

import Entity.IntentionContentEntity;
import Model.Intention;
import jakarta.persistence.Entity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class IntentionDAO {

    private static IntentionDAO uniqueInstance;
    private static SessionFactory sessionFactory;

    private IntentionDAO()
    {
        sessionFactory = SessionFactoryMaker.getFactory();
    }

    public static IntentionDAO getInstance() {
        if(uniqueInstance == null)
        {
            uniqueInstance = new IntentionDAO();
        }
        return uniqueInstance;
    }

    public void addIntention(Intention intention) throws SQLException {

    }

    public Intention getIntentionByDate(LocalDate date) throws SQLException {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String theDate = String.valueOf(date.getYear()) + '-' + String.valueOf((date.getMonthValue() < 10 ? ("0" + String.valueOf(date.getMonthValue())) : String.valueOf(date.getMonthValue()))) + '-' + String.valueOf(date.getDayOfMonth());
        Query<Map> query = session.createQuery(
                "select ic.isCompleted as state, ic.intentionContent as content from IntentionContentEntity ic where ic.intentionId = (select i.intentionId from IntentionEntity i where i.theDate = date('"+theDate+"'))"
                , Map.class);

        List<Map> theIntentionContent = query.getResultList();


        transaction.commit();

        List<String> intentions = new ArrayList<>();
        List<Boolean> completions = new ArrayList<>();


        for(int i = 0; i < theIntentionContent.size(); i++)
        {
            HashMap<Boolean, String> hashMap = (HashMap<Boolean, String>) theIntentionContent.get(i);
            Set<Boolean> set = hashMap.keySet();

            intentions.add(hashMap.get(i));
            // completions.add(set.)
        }

        return new Intention(date, intentions, completions);
    }

    public void updateIntention(Intention intention) throws SQLException {

    }
}


/*

		// execute query and get result list
		List<Customer> result = query.getResultList();

		// return the results
		return result;
	}

 */