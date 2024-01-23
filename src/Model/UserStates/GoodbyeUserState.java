package Model.UserStates;

import Controller.MainController;
import Exceptions.UserStateException;

public class GoodbyeUserState extends UserStateSubject implements UserState {

    private final MainController controller;

    public GoodbyeUserState(MainController mc){
        super(mc);
        controller = mc;
    }

    @Override
    public void leaving() {
        throw new UserStateException("Cannot change state from goodbye to goodbye");
    }

    @Override
    public void intentionInspecting() {
        throw new UserStateException("Cannot change state from goodbye to intentionInspecting");
    }

    @Override
    public void intentionEditing() {
        throw new UserStateException("Cannot change state from goodbye to intentionEditing");
    }

    @Override
    public void mainMenu() {
        throw new UserStateException("Cannot change state from goodbye to mainMenu");
    }

    @Override
    public void intentionCreation() {
        throw new UserStateException("Cannot change state from goodbye to intentionCreation");
    }
}
