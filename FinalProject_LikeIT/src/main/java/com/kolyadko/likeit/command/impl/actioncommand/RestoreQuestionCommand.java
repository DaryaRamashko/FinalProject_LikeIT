package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ErrorMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.TextMemoryContainer;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

/**
 * Created by DaryaKolyadko on 31.08.2016.
 */
public class RestoreQuestionCommand extends SimpleActionCommand {
    private static final String RESTORE_SUCCESS = "info.restoreQuestion.success";
    private static final String RESTORE_PROBLEM = "info.restoreQuestion.problem";

    public RestoreQuestionCommand() {
        super("question");
    }

    @Override
    protected void serviceCall(RequestContent content) throws CommandException {
        QuestionService questionService = new QuestionService();
        int questionId = Integer.parseInt(content.getRequestParameter(paramId));

        try {
            if (questionService.restoreQuestionFromArchive(questionId)) {
                content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(RESTORE_SUCCESS,
                        MemoryContainerType.ONE_OFF));
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(RESTORE_PROBLEM));
            }

            resultPage = MappingManager.QUESTION_PAGE + RequestContentUtil.getParamAsQueryString(content,
                    paramId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}