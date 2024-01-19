package Controller;

import Dao.FileReader;
import View.ConsoleView;

public class MainController {

    private final ConsoleView view;
    private final FileReader fileReader;

    public MainController()
    {
        this.view = ConsoleView.getInstance();
        this.fileReader = FileReader.getInstance();
    }
}
