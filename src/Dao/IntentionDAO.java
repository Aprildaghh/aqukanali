package Dao;

import Model.Entity.ContentEntity;
import Model.Entity.IntentionEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

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

    public void addIntention(IntentionEntity intention) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // save intention to db
        Query intentionQuery = session.createQuery(
                    """
                            insert into IntentionEntity(intentionDate)
                            values ( :intentionDate )
                            """)
                    .setParameter("intentionDate", intention.getIntentionDate());
        intentionQuery.executeUpdate();

        // get intention id from db
        Query<IntentionEntity> intentionEntityQuery = session.createQuery(
                        "select id, intentionDate from IntentionEntity where intentionDate = :theDate"
                        , IntentionEntity.class)
                .setParameter("theDate", intention.getIntentionDate());
        IntentionEntity theIntention = intentionEntityQuery.getSingleResultOrNull();
        intention.setId(theIntention.getId());

        // save contents to db
        List<ContentEntity> contents = intention.getContents();
        for (ContentEntity content :
                contents) {
            session.saveOrUpdate(content);
        }



        transaction.commit();

    }

    public void updateIntention(IntentionEntity intention)
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        List<ContentEntity> contents = intention.getContents();
        for (ContentEntity content: contents) {
            session.merge(content);
        }

        transaction.commit();
    }

    public IntentionEntity getIntentionByDate(LocalDate date) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query<IntentionEntity> intentionEntityQuery = session.createQuery(
                        "select id, intentionDate from IntentionEntity where intentionDate = :theDate"
                , IntentionEntity.class)
                .setParameter("theDate",java.sql.Date.valueOf(date));

        IntentionEntity theIntention = intentionEntityQuery.getSingleResultOrNull();

        if(theIntention == null)
        {
            transaction.commit();
            return null;
        }

        Query<ContentEntity> contentEntityQuery = session.createQuery(
                "select id, contentCompletion, intentionContent from ContentEntity where intention = :intentionId",
                ContentEntity.class)
                        .setParameter("intentionId", theIntention);

        List<ContentEntity> contents = contentEntityQuery.getResultList();

        for (ContentEntity content : contents) theIntention.add(content);

        transaction.commit();

        return theIntention;
    }
}