package View;

import java.util.List;
import java.util.concurrent.TimeUnit;
import Exceptions.NullIntentionException;
import Model.Intention;

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
        try {
            TimeUnit.MICROSECONDS.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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

        str += "\nType 'e' for edit, 'm' for returning to main menu.\n";

        slowPrint(str);
    }

    public void showSpecificDateAsking()
    {
        String str = "Enter the date(dd-mm-yyyy):";
        slowPrint(str);
    }

    public void showGreeting()
    {
        String str = "Welcome to AQUKANALI, the todo app\n" +
                "say hello to continue...";

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
                2. Show a specific day's plan.
                3. Exit.""";

        slowPrint(str);
    }

    public void showIntentionCreation()
    {
        String str = """
                Enter your intentions for today
                For help type 'help'
                """;
        slowPrint(str);
    }

    public void showIntentionCreationHelp()
    {
        String str = """
                Enter your intentions one by one.
                Press enter to add new intention.
                For exiting creation simply type 'exit'.
                """;
        slowPrint(str);
    }

    public void showIntentionEditing()
    {
        String str = """
                Editing Intention...
                For help type 'help'
                """;
        slowPrint(str);
    }

    public void showIntentionEditingHelp()
    {
        String str = """
                Simply first type the intention number you want to change then rewrite the intention if you want.
                If you want to simply mark the intention as completed just type DONE after the intention number.
                If you want to create new intention start your intention with the next numerical intention number value.
                For exiting editing simply type 'exit'.
                Ex:
                    1. DONE
                    2. Changed Intention
                    3. New Intention
                    exit
                """;
        slowPrint(str);
    }

}
