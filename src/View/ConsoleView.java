package View;

import java.util.List;
import java.util.concurrent.TimeUnit;
import Exception.NullIntentionException;
public class ConsoleView {

    private static ConsoleView uniqueInstance;

    private ConsoleView(){}

    public static ConsoleView getInstance() {
        if(uniqueInstance == null)
        {
            uniqueInstance = new ConsoleView();
        }
        return uniqueInstance;
    }

    private void slowPrint(String s)
    {
        for(int i = 0; i < s.length(); i++)
        {
            System.out.print(s.charAt(i));
            try {
                TimeUnit.MICROSECONDS.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.print('\n');
    }

    public void showExistingIntention(Intention intention)
    {
        if(intention == null)
            throw new NullIntentionException("Given intention is null!");

        List<String> intentionList = intention.getIntentionList();
        List<Boolean> intentionMarkList = intention.getIntentionMarkList();
        String str = intention.getDate().toString() + "\n";

        for(int i = 0; i < intentionList.size(); i++)
        {
            str += (i+1) + ". " + ((intentionMarkList.get(i) ? "(DONE) " : "(NOT COMPLETED) ")) + intentionList.get(i) + "\n";
        }

        slowPrint(str);
    }

    public void showGreeting()
    {
        String str = "Welcome to AQUKANALI, the todo app\n" +
                "press any button to continue...";

        slowPrint(str);
    }

    public void showGoodbye()
    {
        String str = """
                Thank you for using our service!
                Have a great day!
                """;
        slowPrint(str);
    }

    public void showMainMenu()
    {
        String str = """
                What would you want today?
                1. Show today's plan, if there's not create one.
                2. Show a specific day's plan.""";

        slowPrint(str);
    }

    public void showIntentionCreation()
    {
        String str = "Enter your intentions for today(put '&' between intentions):";
        slowPrint(str);
    }

}
