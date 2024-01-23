package Model.UserStates;

import Controller.MainController;
import Exceptions.UserStateException;

public class IntentionCreationUserState extends UserStateSubject implements UserState{

    private final MainController controller;

    public IntentionCreationUserState(MainController mc)
    {
        super(mc);
        controller = mc;
    }

    @Override
    public void leaving() {
        throw new UserStateException("Cannot change state from intentionCreation to leaving.");
    }

    @Override
    public void intentionInspecting() {
        throw new UserStateException("Cannot change state from intentionCreation to intentionInspecting.");
    }

    @Override
    public void intentionEditing() {
        throw new UserStateException("Cannot change state from intentionCreation to intentionEditing.");
    }

    @Override
    public void mainMenu() {
        controller.setCurrentUserState(controller.getMainMenuUserState());
        controller.update();
    }

    @Override
    public void intentionCreation() {
        throw new UserStateException("Cannot change state from intentionCreation to intentionCreation");
    }
}
