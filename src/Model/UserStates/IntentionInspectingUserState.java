package Model.UserStates;

import Controller.MainController;
import Exceptions.UserStateException;

public class IntentionInspectingUserState implements UserState{

    private final MainController controller;

    public IntentionInspectingUserState(MainController mc)
    {
        controller = mc;
    }

    @Override
    public void leaving() {
        throw new UserStateException("Cannot change state from intentionInspecting to leaving");
    }

    @Override
    public void intentionInspecting() {
        throw new UserStateException("Cannot change state from intentionInspecting to intentionInspecting");
    }

    @Override
    public void intentionEditing() {
        controller.setCurrentUserState(controller.getIntentionEditingUserState());
    }

    @Override
    public void mainMenu() {
        controller.setCurrentUserState(controller.getMainMenuUserState());
    }
}
