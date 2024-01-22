package Model.UserStates;

import Controller.MainController;
import Exceptions.UserStateException;

public class MainMenuUserState implements UserState{

    private final MainController controller;

    public MainMenuUserState(MainController mc)
    {
        controller = mc;
    }

    @Override
    public void mainMenu() {
        throw new UserStateException("Cannot change state from mainMenu to mainMenu");
    }

    @Override
    public void leaving() {
        controller.setCurrentUserState(controller.getGoodbyeUserState());
    }

    @Override
    public void intentionInspecting() {
        controller.setCurrentUserState(controller.getIntentionInspectingUserState());
    }

    @Override
    public void intentionEditing() {
        controller.setCurrentUserState(controller.getIntentionEditingUserState());
    }
}
