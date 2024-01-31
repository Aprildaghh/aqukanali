package Dao;

import Model.Entity.IntentionEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.time.LocalDate;

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

    public void addOrUpdateIntention(IntentionEntity intention) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.persist(intention);

        transaction.commit();

    }

    public IntentionEntity getIntentionByDate(LocalDate date) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query<IntentionEntity> query = session.createQuery(
                "select id, intentionDate from IntentionEntity where intentionDate = :theDate"
                , IntentionEntity.class);

        query.setParameter("theDate", java.sql.Date.valueOf(date));


        IntentionEntity theIntention = query.getSingleResult();

        transaction.commit();

        return theIntention;
    }
}