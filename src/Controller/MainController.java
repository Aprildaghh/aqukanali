package Controller;

import Dao.IntentionDAO;
import Model.Entity.ContentEntity;
import Model.Entity.IntentionEntity;
import Model.UserStates.*;
import View.ConsoleView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class MainController {

    private final ConsoleView view;
    private final IntentionDAO intentionDAO;
    private IntentionEntity currentIntention;

    private UserState currentUserState;
    private final UserState goodbyeUserState;
    private final UserState greetingUserState;
    private final UserState intentionEditingUserState;
    private final UserState intentionInspectingUserState;
    private final UserState mainMenuUserState;
    private final UserState intentionCreationUserState;

    public MainController()
    {
        this.view = ConsoleView.getInstance();
        this.intentionDAO = IntentionDAO.getInstance();
        this.goodbyeUserState = new GoodbyeUserState(this);
        this.greetingUserState = new GreetingUserState(this);
        this.intentionEditingUserState = new IntentionEditingUserState(this);
        this.intentionInspectingUserState = new IntentionInspectingUserState(this);
        this.mainMenuUserState = new MainMenuUserState(this);
        this.intentionCreationUserState = new IntentionCreationUserState(this);
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
    public UserState getIntentionCreationUserState() { return intentionCreationUserState;}

    public void update()
    {
        if (currentUserState.equals(greetingUserState))
        {
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
                currentUserState.mainMenu();
                return;
            }
            else if (currentIntention.getContents() == null)
            {
                System.err.println("Error at showScreen(Intention) in MainController.java: Given intention's contents are null");
                currentUserState.mainMenu();
                return;
            }

            view.showExistingIntention(currentIntention);
        }
        else if(currentUserState.equals(intentionEditingUserState))
        {
            view.showIntentionEditing();
            intentionEditing();
        }
        else if(currentUserState.equals(goodbyeUserState))
        {
            view.showGoodbye();
            exit(0);
        }
        else if(currentUserState.equals(intentionCreationUserState))
        {
            view.showIntentionCreation();
            intentionCreation();
        }
    }

    public void start()
    {
        view.showGreeting();
        String input = null;
        while(true)
        {
            Scanner myObj = new Scanner(System.in);
            input = myObj.nextLine();

            if(Objects.equals(input, "hello")&& currentUserState.equals(greetingUserState))
                currentUserState.mainMenu();
            else if(Objects.equals(input, "1")&& currentUserState.equals(mainMenuUserState))
            {
                currentIntention = intentionDAO.getIntentionByDate(LocalDate.now());

                if(currentIntention == null)
                    currentUserState.intentionCreation();
                else
                    currentUserState.intentionInspecting();
            }
            else if(Objects.equals(input, "2")&& currentUserState.equals(mainMenuUserState))
            {
                view.showSpecificDateAsking();
                getDate();
            }
            else if(Objects.equals(input, "3") && currentUserState.equals(mainMenuUserState))
            {
                currentUserState.leaving();
            }
            else if(Objects.equals(input, "e") && currentUserState.equals(intentionInspectingUserState))
            {
                currentUserState.intentionEditing();
            }
            else if(Objects.equals(input, "m") && currentUserState.equals(intentionInspectingUserState))
            {
                currentUserState.mainMenu();
            }
        }
    }

    private void getDate()
    {
        // get the date from the user
        LocalDate theDate = null;
        int dd, mm, yyyy;

        while(true)
        {
            Scanner myObj = new Scanner(System.in);
            String[] dateParts = myObj.nextLine().split("-", 3);

            if(dateParts.length != 3)
            {
                System.err.println("Given date format is not correct! Ex: dd-mm-yyyy");
                continue;
            }

            dd = Integer.parseInt(dateParts[0]);
            mm = Integer.parseInt(dateParts[1]);
            yyyy = Integer.parseInt(dateParts[2]);

            if(dd < 1 || dd > 31)
            {
                System.err.println("Given day is not possible! It should be between 1 and 31");
                continue;
            }
            else if( mm < 1 || mm > 12)
            {
                System.err.println("Given month is not possible! It should be between 1 and 12");
                continue;
            }

            theDate = LocalDate.of(yyyy, mm, dd);
            break;
        }

        // search for intention for the date and set currentIntention to that intention
        currentIntention = intentionDAO.getIntentionByDate(theDate);

        // change state to intentionInspectingUserState
        currentUserState.intentionInspecting();
    }

    private void intentionCreation()
    {
        // get the intentions from user
        IntentionEntity newIntention = new IntentionEntity(java.sql.Date.valueOf(LocalDate.now()));
        newIntention.setId(0);

        List<ContentEntity> contents = new ArrayList<>();
        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if(Objects.equals(input, "exit")) break;
            else if(Objects.equals(input, "help"))
            {
                view.showIntentionCreationHelp();
                continue;
            }

            ContentEntity content = new ContentEntity(false, input, newIntention);
            contents.add(content);
        }
        newIntention.setContents(contents);

        // store the intention to db
        intentionDAO.addIntention(newIntention);

        // set the currentIntention to the new intention
        currentIntention = newIntention;

        // get to the main screen state
        currentUserState.mainMenu();
    }

    private void intentionEditing()
    {

        // get the string from user
        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if(Objects.equals(input, "exit")) break;
            else if(Objects.equals(input, "help"))
            {
                view.showIntentionEditingHelp();
                continue;
            }

            int id = 0;
            for(int i = 0; i < input.length(); i++)
            {
                char c = input.charAt(i);
                if( c == '.')
                {
                    input = input.substring(i+2);
                    break;
                }
                id = (id * 10) + (c - '0');
            }

            id--;

            if(input.equals("DONE"))
            {
                currentIntention.getContents().get(id).setContentCompletion(true);
            }
            else if(id >= currentIntention.getContents().size())
            {
                currentIntention.add(new ContentEntity(false, input, currentIntention));
            }
            else
            {
                currentIntention.getContents().get(id).setIntentionContent(input);
            }

        }


        // update the intention on db
        intentionDAO.updateIntention(currentIntention);

        // get main screen state
        currentUserState.mainMenu();
    }

}
