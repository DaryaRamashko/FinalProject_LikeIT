package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.service.impl.UserService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public class ShowUserListPageCommand extends ShowDefaultContentCommand {
    private static final String ATTR_USER_LIST = "userList";

    public ShowUserListPageCommand() {
        super(MappingManager.USER_LIST_PAGE);
    }

    @Override
    public String execute(RequestContent content) {
        UserService userService = new UserService();

        try {
            ArrayList<User> userList = userService.findAll(RequestContentUtil.isCurrentUserAdmin(content));
            content.setRequestAttribute(ATTR_USER_LIST, userList);
        } catch (ServiceException e) {
            LOG.error(e);
            content.setSessionAttribute(EXCEPTION, new ObjectMemoryContainer(e, MemoryContainerType.ONE_OFF));
            return MappingManager.getInstance().getProperty(MappingManager.ERROR_PAGE_500);
        }

        return super.execute(content);
    }
}