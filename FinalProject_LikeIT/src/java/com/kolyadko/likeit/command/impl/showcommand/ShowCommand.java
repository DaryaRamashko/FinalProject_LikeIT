package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.util.MappingManager;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public class ShowCommand implements Command {
    private String path;

    public ShowCommand(String path) {
        this.path = path;
    }

    @Override
    public String execute(RequestContent content) {
        return MappingManager.getInstance().getProperty(path);
    }
}