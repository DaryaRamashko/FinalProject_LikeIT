package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.Comment;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ErrorMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.TextMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.uncompleteddata.UncompletedCommentMemoryContainer;
import com.kolyadko.likeit.service.impl.CommentService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.impl.CommentActionValidator;
import com.kolyadko.likeit.validator.Validator;

/**
 * Created by DaryaKolyadko on 08.09.2016.
 */
public class EditCommentCommand extends ActionCommand {
    private static final String EXIT_PARAM_QUESTION_ID = "question";

    private static final String PARAM_COMMENT_ID = "comment";
    private static final String PARAM_TEXT = "text";

    private static final String SESSION_ATTR_UNCOMPLETED = "uncompleted";
    private static final String EDIT_COMMENT_ERROR_CHECK = "error.checkForm";
    private static final String EDIT_SUCCESS = "info.editComment.success";
    private static final String EDIT_PROBLEM = "info.editComment.problem";

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isAllowedAction(content)) {
            UncompletedCommentMemoryContainer container = initUncompletedComment(content);

            if (isInputDataValid(content)) {
                try {
                    CommentService commentService = new CommentService();
                    int commentId = Integer.parseInt(content.getRequestParameter(PARAM_COMMENT_ID));
                    boolean isAdmin = RequestContentUtil.isCurrentUserAdmin(content);
                    Comment comment = commentService.findById(commentId, isAdmin);

                    if (comment != null) {
                        comment.setText(container.getText());

                        if (commentService.updateComment(comment)) {
                            content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(EDIT_SUCCESS,
                                    MemoryContainerType.ONE_OFF));
                        } else {
                            content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(EDIT_PROBLEM));
                        }

                        return MappingManager.QUESTION_PAGE +
                                RequestContentUtil.generateQueryString(EXIT_PARAM_QUESTION_ID, comment.getQuestionId());
                    }
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(EDIT_COMMENT_ERROR_CHECK));
            }

            content.setSessionAttribute(SESSION_ATTR_UNCOMPLETED, container);
            return MappingManager.EDIT_COMMENT_PAGE + RequestContentUtil.getParamAsQueryString(content,
                    PARAM_COMMENT_ID);
        } else {
            content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(NOT_ALLOWED));
        }

        return MappingManager.HOME_PAGE;
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return Validator.isNumIdValid(content.getRequestParameter(PARAM_COMMENT_ID)) &&
                CommentActionValidator.isTextValid(content.getRequestParameter(PARAM_TEXT));
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        try {
            CommentService commentService = new CommentService();
            String commentId = content.getRequestParameter(PARAM_COMMENT_ID);
            Comment comment = commentService.findById(Integer.valueOf(commentId));
            return comment != null && allowedAction(content, comment.getAuthorId());
        } catch (ServiceException e) {
            return false;
        }
    }

    private UncompletedCommentMemoryContainer initUncompletedComment(RequestContent content) {
        return new UncompletedCommentMemoryContainer(content.getRequestParameter(EXIT_PARAM_QUESTION_ID),
                content.getRequestParameter(PARAM_TEXT));
    }
}