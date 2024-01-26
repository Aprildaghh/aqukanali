package Controller;

import Dao.FileReader;
import Model.Intention;
import Model.UserStates.*;
import View.ConsoleView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class MainController {

    private final ConsoleView view;
    private final FileReader fileReader;
    private Intention currentIntention;

    private UserState currentUserState;
    private final UserState goodbyeUserState;
    private final UserState greetingUserState;
    private final UserState intentionEditingUserState;
    private final UserState intentionInspectingUserState;
    private final UserState mainMenuUserState;
    private final UserState intentionCreationUserState;

    public MainController()
    {
        this.view = ConsoleView.getInstance();
        this.fileReader = FileReader.getInstance();
        this.goodbyeUserState = new GoodbyeUserState(this);
        this.greetingUserState = new GreetingUserState(this);
        this.intentionEditingUserState = new IntentionEditingUserState(this);
        this.intentionInspectingUserState = new IntentionInspectingUserState(this);
        this.mainMenuUserState = new MainMenuUserState(this);
        this.intentionCreationUserState = new IntentionCreationUserState(this);
        currentUserState = greetingUserState;
    }

    public void setCurrentUserState(UserState userState)
    {
        currentUserState = userState;
    }
    public UserState getGoodbyeUserState() {
        return goodbyeUserState;
    }

    public UserState getGreetingUserState() {
        return greetingUserState;
    }

    public UserState getIntentionEditingUserState() {
        return intentionEditingUserState;
    }

    public UserState getIntentionInspectingUserState() {
        return intentionInspectingUserState;
    }

    public UserState getMainMenuUserState() {
        return mainMenuUserState;
    }
    public UserState getIntentionCreationUserState() { return intentionCreationUserState;}

    public void update()
    {
        if (currentUserState.equals(greetingUserState))
        {
            view.showGreeting();
        }
        else if (currentUserState.equals(mainMenuUserState))
        {
            view.showMainMenu();
        }
        else if(currentUserState.equals(intentionInspectingUserState))
        {
            if(currentIntention == null)
            {
                System.err.println("Error at showScreen(Intention) in MainController.java: Given intention is null.");
                return;
            }
            view.showExistingIntention(currentIntention);
        }
        else if(currentUserState.equals(intentionEditingUserState))
        {
            view.showIntentionEditing();
            intentionEditing();
        }
        else if(currentUserState.equals(goodbyeUserState))
        {
            view.showGoodbye();
            exit(0);
        }
        else if(currentUserState.equals(intentionCreationUserState))
        {
            view.showIntentionCreation();
            intentionCreation();
        }
    }

    public void start()
    {
        view.showGreeting();
        String input = null;
        while(true)
        {
            Scanner myObj = new Scanner(System.in);
            input = myObj.nextLine();

            if(Objects.equals(input, "hello")&& currentUserState.equals(greetingUserState))
                currentUserState.mainMenu();
            else if(Objects.equals(input, "1")&& currentUserState.equals(mainMenuUserState))
            {

                currentIntention = null;
                try {
                    currentIntention = fileReader.getIntentionByDate(LocalDate.now());
                } catch (SQLException e) {
                    System.err.println("Error at start() in MainController.java: Couldn't get data from db.");
                    currentUserState.mainMenu();
                    continue;
                }
                if(currentIntention == null)
                    currentUserState.intentionCreation();
                else
                    currentUserState.intentionInspecting();
            }
            else if(Objects.equals(input, "2")&& currentUserState.equals(mainMenuUserState))
            {
                view.showSpecificDateAsking();
                getDate();
            }
            else if(Objects.equals(input, "3") && currentUserState.equals(mainMenuUserState))
            {
                currentUserState.leaving();
            }
            else if(Objects.equals(input, "e") && currentUserState.equals(intentionInspectingUserState))
            {
                currentUserState.intentionEditing();
            }
            else if(Objects.equals(input, "m") && currentUserState.equals(intentionInspectingUserState))
            {
                currentUserState.mainMenu();
            }
        }
    }

    private void getDate()
    {
        // TODO: get the date from the user
        LocalDate theDate = null;
        int dd, mm, yyyy;

        while(true)
        {
            Scanner myObj = new Scanner(System.in);
            String[] dateParts = myObj.nextLine().split("-", 3);

            if(dateParts.length != 3)
            {
                System.err.println("Given date format is not correct! Ex: dd-mm-yyyy");
                continue;
            }

            dd = Integer.parseInt(dateParts[0]);
            mm = Integer.parseInt(dateParts[1]);
            yyyy = Integer.parseInt(dateParts[2]);

            if(dd < 1 || dd > 31)
            {
                System.err.println("Given day is not possible! It should be between 1 and 31");
                continue;
            }
            else if( mm < 1 || mm > 12)
            {
                System.err.println("Given month is not possible! It should be between 1 and 12");
                continue;
            }

            theDate = LocalDate.of(yyyy, mm, dd);
            break;
        }

        // search for intention for the date
        Intention theIntention = null;
        try {
            theIntention = fileReader.getIntentionByDate(theDate);
        } catch (SQLException e) {
            System.err.println("Error at getDate() in MainController.java: Intention couldn't found in db.");
            currentUserState.mainMenu();
            return;
        }

        // set currentIntention to that intention
        currentIntention = theIntention;

        // change state to intentionInspectingUserState
        currentUserState.intentionInspecting();
    }

    private void intentionCreation()
    {
        // get the intentions from user
        List<String> intentions = new ArrayList<>();

        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if(Objects.equals(input, "exit")) break;
            else if(Objects.equals(input, "help"))
            {
                view.showIntentionCreationHelp();
                continue;
            }

            intentions.add(input);
        }

        // store the intention to db
        Intention newIntention = new Intention(LocalDate.now(), intentions, null);
        try {
            fileReader.addIntention(newIntention);
        } catch (SQLException e) {
            System.err.println("Error at intentionCreation() in MainController.java: Couldn't save intention to the db.");
            currentUserState.mainMenu();
            return;
        }

        // set the currentIntention to the new intention
        currentIntention = newIntention;

        // get to the main screen state
        currentUserState.mainMenu();
    }

    private void intentionEditing()
    {
        // create a temp intention as a copy of the already existing intention
        LocalDate theDate = currentIntention.getDate();
        List<String> intentionList = currentIntention.getIntentionList();
        List<Boolean> intentionMarkList = currentIntention.getIntentionMarkList();

        // get the string from user
        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if(Objects.equals(input, "exit")) break;
            else if(Objects.equals(input, "help"))
            {
                view.showIntentionEditingHelp();
                continue;
            }

            int id = 0;
            for(int i = 0; i < input.length(); i++)
            {
                char c = input.charAt(i);
                if( c == '.')
                {
                    input = input.substring(i+2);
                    break;
                }
                id = (id * 10) + (c - '0');
            }

            id--;

            if(input.equals("DONE"))
            {
                intentionMarkList.set(id, true);
            }
            else if(id >= intentionList.size())
            {
                intentionList.add(input);
                intentionMarkList.add(false);
            }
            else
            {
                intentionList.set(id, input);
            }

        }


        // update the intention on db
        Intention tempIntention = new Intention(theDate, intentionList, intentionMarkList);
        try {
            fileReader.updateIntention(tempIntention);
        } catch (SQLException e) {
            System.err.println("Error at intentionEditing() in MainController.java: Couldn't update the intention on db.");
        }

        // set the currentIntention to the new intention
        currentIntention = tempIntention;

        // get main screen state
        currentUserState.mainMenu();
    }

}
