package org.example.commands;

import org.example.commands.voids.ListCommand;
import org.example.commands.voids.algorithms.Statistics;

public class AddCommand {
    private ListCommand save;
    private ListCommand list;
    private ListCommand view;
    private ListCommand raport;
    private ListCommand color;
    private ListCommand info;


    public AddCommand(ListCommand save, ListCommand list, ListCommand view, ListCommand raport, ListCommand color, ListCommand info) {
        this.save = save;
        this.list = list;
        this.view = view;
        this.raport = raport;
        this.color = color;
        this.info = info;
    }

    public void save() {

        save.execute();
    }

    public void list() {

        list.execute();
    }

    public void view() {

        view.execute();
    }

    public void raport() {

        raport.execute();
    }

    public void color() {

        color.execute();
    }

    public void info() {

        info.execute();
    }
}
