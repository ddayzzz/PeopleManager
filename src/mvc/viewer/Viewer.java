package mvc.viewer;

import container.IContainer;
import container.Person;

import java.awt.event.ActionListener;
import java.util.Collection;

public interface Viewer {

    void showCriticalMessage(String msg);
    void showInfoMessage(String msg);
    String getUserInput(String title, String text);
    Person getSelectedPerson();
    Person getNewPersonFromUserInput(boolean idEditable, Person oldPerson);
    void showPersonInfo(Person p);
    void showAllPersons(IContainer c);
    void changeTitleText(String title);
    void setSelectedPerson(Person person);
    boolean askUserYesOrNo(String text);
    boolean askToRefreshList();
    int showQueryPersonsResultAndGetSelectResult(String title, String text, Collection items);
    int askManyOptions(String title, String text, String[] optionsText);
    void addActionListenerToButtons(ActionListener actionListener);
    boolean needAddBuutonActionListener();
}
