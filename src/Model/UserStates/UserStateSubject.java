package Model.UserStates;

import Controller.MainController;

public abstract class UserStateSubject {

    private MainController observer;

    public UserStateSubject(MainController observer)
    {
        if(observer != null) this.observer = observer;
    }

    public void notifyObserver()
    {
        observer.update();
    }
}
