package Model.UserStates;

import Controller.MainController;
import Exceptions.UserStateException;

public class IntentionEditingUserState extends UserStateSubject implements UserState{

    private final MainController controller;

    public IntentionEditingUserState(MainController mc)
    {
        super(mc);
        controller = mc;
    }

    @Override
    public void leaving() {
        throw new UserStateException("Cannot change state from intentionEditing to leaving");
    }

    @Override
    public void intentionInspecting() {
        controller.setCurrentUserState(controller.getIntentionInspectingUserState());
        notifyObserver();

    }

    @Override
    public void intentionEditing() {
        throw new UserStateException("Cannot change state from intentionEditing to intentionEditing");
    }

    @Override
    public void mainMenu() {
        controller.setCurrentUserState(controller.getMainMenuUserState());
        notifyObserver();

    }

    @Override
    public void intentionCreation() {
        throw new UserStateException("Cannot change state from intentionEditing to intentionCreation");
    }
}
