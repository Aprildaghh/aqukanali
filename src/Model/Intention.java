package Model;

import Exceptions.EmptyIntentionStringException;
import Exceptions.NullIntentionException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Intention {

    private LocalDate intentionDate;
    private List<String> intentionList;
    private List<Boolean> intentionMarkList;

    public Intention(LocalDate date)
    {
        intentionDate = date;
        this.intentionList = new ArrayList<>();
    }

    public Intention(LocalDate date, List<String> intentions, List<Boolean> intentionMarkList)
    {
        intentionDate = date;
        this.intentionList = new ArrayList<>();
        for(int i = 0; i < intentions.size(); i++)
        {
            intentionList.add(intentions.get(i));
            intentionMarkList.add(intentionMarkList.get(i));
        }
    }

    public void markAsCompleted(int id) throws NullIntentionException {
        if(id < 0 || id >= intentionList.size())
            throw new NullIntentionException("There are no intentions with the given id");

        intentionMarkList.set(id, true);
    }

    public void addIntention(String str) throws EmptyIntentionStringException {
        if(str.isEmpty())
            throw new EmptyIntentionStringException("The intention string is empty");

        intentionList.add(str);
        intentionMarkList.add(false);
    }

    public void deleteIntention(int id) throws NullIntentionException {
        if(id < 0 || id >= intentionList.size())
            throw new NullIntentionException("There are no intentions with the given id");
        intentionList.remove(id);
        intentionMarkList.remove(id);
    }

    public void updateIntention(int id, String str) throws NullIntentionException, EmptyIntentionStringException {
        if(id < 0 || id >= intentionList.size())
            throw new NullIntentionException("There are no intentions with the given id");
        if(str.isEmpty())
            throw new EmptyIntentionStringException("The intention string is empty");

        intentionList.set(id, str);
    }

    public List<String> getIntentionList() { return List.copyOf(intentionList);}
    public List<Boolean> getIntentionMarkList() { return List.copyOf(intentionMarkList);}
    public LocalDate getDate() { return intentionDate; }
    public Intention copy()
    {
        Intention newIntention = new Intention(intentionDate, intentionList, intentionMarkList);
        return newIntention;
    }
}
