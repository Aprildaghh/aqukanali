package Controller;

import Dao.FileReader;
import Model.UserStates.*;
import View.ConsoleView;

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

    public void start()
    {
        while(true)
        {
            
        }
    }

    private void getInput()
    {

    }

    private void processInput()
    {

    }
}
