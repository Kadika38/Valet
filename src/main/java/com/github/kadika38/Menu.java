import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Menu extends MenuObject {
    private String openingLine;
    private LinkedHashMap<String, MenuObject> options;
    private ArrayList<String> optionStrings;
    private Scanner scanner;

    Menu(String openingLine, LinkedHashMap<String, MenuObject> options) {
        this.openingLine = openingLine;
        this.options = options;
        optionStrings = new ArrayList<String>();
        for (String key : this.options.keySet()) {
            optionStrings.add(key);
        }
        this.scanner = new Scanner(System.in);
    }

    public void open() {
        // print opening line
        System.out.println(this.openingLine);

        // print options to choose from
        int count = 1;
        for (String key : optionStrings) {
            System.out.println(count + ") " + key);
            count++;
        }
        
        // take in choice input
        boolean keepRunning = true;
        Integer choice = -1;
        while (keepRunning) {
            String choiceString = scanner.nextLine();
            if ("E".equals(choiceString)) {
                System.out.println("Exiting");
                return;
            }
            try {
                choice = Integer.parseInt(choiceString);
                if (choice <= 0 | choice > optionStrings.size()) {
                    throw new NumberFormatException();
                }
                keepRunning = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.  Input a number value that corresponds to one of the options provided, or E to exit this menu.");
            }
        }

        // invoke the users choice (either to open a Menu or run an Action)
        MenuObject optionChosen = null;
        // start by finding the MenuObject in options associated with the chosen option
        if (choice > 0 && choice <= options.keySet().size()) {
            count = 1;
            for (String key : optionStrings) {
                if (count == choice) {
                    optionChosen = options.get(key);
                    break;
                } else {
                    count++;
                }
            }
        } else {
            System.out.println("Error.  Closing menu.");
            return;
        }
        // open Menu or run Action
        if (optionChosen != null) {
            if (optionChosen instanceof Menu) {
                ((Menu) optionChosen).open();
            } else if (optionChosen instanceof Action) {
                ((Action) optionChosen).run();
            }
        } else {
            System.out.println("Error.  Closing Menu.");
            return;
        }
    }
}
