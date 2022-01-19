package ua.edu.sumdu.j2se.chepiha.tasks.interfaces;

import ua.edu.sumdu.j2se.chepiha.tasks.types.ListState;

public interface Observable {
    void registerObserver(Observer o);
    void notifyObservers(ListState state);
}
