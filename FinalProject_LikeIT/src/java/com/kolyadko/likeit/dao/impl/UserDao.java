package com.kolyadko.likeit.dao.impl;

import com.kolyadko.likeit.dao.AbstractDao;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.pool.ConnectionWrapper;
import com.kolyadko.likeit.type.GenderType;
import com.kolyadko.likeit.type.RoleType;
import com.kolyadko.likeit.type.StateType;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */
public class UserDao extends AbstractDao<String, User> {
    private static final String ALL_COLUMNS = "login, avatar_id, first_name, last_name, gender, password, birth_date, " +
            "sign_up_date, email, email_confirmed, role, state, rating, answer_num, question_num, comment_num, archive";
    private static final String INSERT_COLUMNS = "login, first_name, last_name, gender, password, birth_date, " +
            "sign_up_date, email";

    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM user";
    private static final String FIND_BY_ID = SELECT_ALL + "  WHERE login=?;";
    private static final String FIND_ALL_EXISTING = SELECT_ALL + "  WHERE archive='false';";
    private static final String CREATE = "INSERT INTO user (" + INSERT_COLUMNS + ") VALUES(" +
            StringUtils.repeat("?", ", ", INSERT_COLUMNS.split(",").length) + ");";

    public UserDao(ConnectionWrapper connection) {
        super(connection);
    }

    @Override
    public User findById(String login) throws DaoException {
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        try {
            preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(readEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            closeStatement(preparedStatement);
        }

        if (users.size() > 0) {
            return users.get(0);
        }

        return null;
    }

    @Override
    public ArrayList<User> findAll() throws DaoException {
        try {
            return findWithStatement(SELECT_ALL);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void create(User user) throws DaoException {
        //TODO avatar id
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getGender().name());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setDate(6, user.getBirthDate());
            preparedStatement.setDate(7, user.getSignUpDate());
            preparedStatement.setString(8, user.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            closeStatement(preparedStatement);
        }
    }

    public ArrayList<User> findAllExisting() throws DaoException {
        try {
            return findWithStatement(FIND_ALL_EXISTING);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected User readEntity(ResultSet resultSet) throws SQLException {
        User user = new User(resultSet.getString("login"));
        user.setAvatarId(resultSet.getInt("avatar_id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setGender(GenderType.getGenderType(resultSet.getString("gender")));
        user.setPassword(resultSet.getString("password"));
        user.setBirthDate(resultSet.getDate("birth_date"));
        user.setSignUpDate(resultSet.getDate("sign_up_date"));
        user.setEmail(resultSet.getString("email"));
        user.setEmailConfirmed(resultSet.getBoolean("email_confirmed"));
        user.setRole(RoleType.getRoleType(resultSet.getString("role")));
        user.setState(StateType.getStateType(resultSet.getString("state")));
        user.setRating(resultSet.getFloat("rating"));
        user.setAnswerNum(resultSet.getInt("answer_num"));
        user.setQuestionNum(resultSet.getInt("question_num"));
        user.setCommentNum(resultSet.getInt("comment_num"));
        user.setArchive(resultSet.getBoolean("archive"));
        return user;
    }
}