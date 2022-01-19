package ua.edu.sumdu.j2se.chepiha.tasks.interfaces;

import ua.edu.sumdu.j2se.chepiha.tasks.types.ListState;

public interface Observer {
    void notification(ListState state);
}
