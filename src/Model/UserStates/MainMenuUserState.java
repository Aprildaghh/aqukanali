package Model.UserStates;

import Controller.MainController;
import Exceptions.UserStateException;

public class MainMenuUserState extends UserStateSubject implements UserState{

    private final MainController controller;

    public MainMenuUserState(MainController mc)
    {
        super(mc);
        controller = mc;
    }

    @Override
    public void mainMenu() {
        throw new UserStateException("Cannot change state from mainMenu to mainMenu");
    }

    @Override
    public void leaving() {
        controller.setCurrentUserState(controller.getGoodbyeUserState());
        notifyObserver();

    }

    @Override
    public void intentionInspecting() {
        controller.setCurrentUserState(controller.getIntentionInspectingUserState());
        notifyObserver();

    }

    @Override
    public void intentionEditing() {
        controller.setCurrentUserState(controller.getIntentionEditingUserState());
        notifyObserver();

    }

    @Override
    public void intentionCreation() {
        controller.setCurrentUserState(controller.getIntentionCreationUserState());
        notifyObserver();
    }
}
