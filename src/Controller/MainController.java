package Controller;

import Dao.FileReader;
import Exceptions.UserStateException;
import Model.Intention;
import Model.UserStates.*;
import View.ConsoleView;

import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class MainController {

    private final ConsoleView view;
    private final FileReader fileReader;

    private UserState currentUserState;
    private final UserState goodbyeUserState;
    private final UserState greetingUserState;
    private final UserState intentionEditingUserState;
    private final UserState intentionInspectingUserState;
    private final UserState mainMenuUserState;

    public MainController()
    {
        this.view = ConsoleView.getInstance();
        this.fileReader = FileReader.getInstance();
        this.goodbyeUserState = new GoodbyeUserState(this);
        this.greetingUserState = new GreetingUserState(this);
        this.intentionEditingUserState = new IntentionEditingUserState(this);
        this.intentionInspectingUserState = new IntentionInspectingUserState(this);
        this.mainMenuUserState = new MainMenuUserState(this);
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

    /*
    TODO: use observer design pattern between states and controller. when the state changes
    let the controller know then use the appropriate function of ConsoleView
     */
    public void start()
    {
        Intention currentIntention = null;
        String input = null;
        while(true)
        {
            if (currentUserState.equals(greetingUserState))
            {
                if(Objects.equals(input, "hello"))
                {
                    try{ currentUserState.mainMenu(); } catch( UserStateException ignored ) {}
                    continue;
                }
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
                    continue;
                }
                view.showExistingIntention(currentIntention);
            }
            else if(currentUserState.equals(intentionEditingUserState))
            {
                view.showIntentionCreation();
                intentionCreation();
            }
            else if(currentUserState.equals(goodbyeUserState))
            {
                view.showGoodbye();
                exit(0);
            }

            Scanner myObj = new Scanner(System.in);
            input = myObj.nextLine();
        }
    }

    private void intentionCreation()
    {

    }

}
