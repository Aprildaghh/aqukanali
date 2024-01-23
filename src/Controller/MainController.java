package Controller;

import Dao.FileReader;
import Model.Intention;
import Model.UserStates.*;
import View.ConsoleView;

import java.sql.SQLException;
import java.time.LocalDate;
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
        // TODO: get the string from user

        // TODO: alter the string to create an intention


        // store the intention to db
        Intention newIntention = new Intention(null, null, null);
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
        Intention tempIntention = currentIntention.copy();

        // TODO:get the string from user

        // TODO:alter the string to alter the intention

        // update the intention on db
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
