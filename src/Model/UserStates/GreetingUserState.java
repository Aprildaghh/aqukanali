package Model.UserStates;

import Controller.MainController;
import Exceptions.UserStateException;

public class GreetingUserState implements UserState{

    private final MainController controller;

    public GreetingUserState(MainController mc)
    {
        this.controller = mc;
    }

    @Override
    public void leaving() {
        throw new UserStateException("Cannot change state from greeting to goodbye");
    }

    @Override
    public void intentionInspecting() {
        throw new UserStateException("Cannot change state from greeting to intentionInspecting");
    }

    @Override
    public void intentionEditing() {
        throw new UserStateException("Cannot change state from greeting to intentionEditing");
    }

    @Override
    public void mainMenu() {
        controller.setCurrentUserState(controller.getMainMenuUserState());
    }
}
