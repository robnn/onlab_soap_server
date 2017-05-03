package dal;


import dal.exception.CouldNotConnectException;
import dal.exception.NotConnectedException;
import model.Comment;
import model.Institute;
import model.Subject;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 4/6/17.
 */
public class OnlabDal implements DataAccesLayer {

    private static OnlabDal instance = null;
    private Connection connection;
    private static final String driverName = "oracle.jdbc.driver.OracleDriver";
    private static final String databaseUrl = "jdbc:oracle:thin:@172.17.0.2:1521:xe";

    private void checkConnected() throws NotConnectedException {
        if (connection == null) {
            throw new NotConnectedException();
        }
    }

    public static OnlabDal getInstance() {
        if(instance==null){
            instance = new OnlabDal();
        }
        return  instance;
    }

    @Override
    public void connect(String username, String password) throws CouldNotConnectException, ClassNotFoundException {
        try {
            if (connection == null || !connection.isValid(30)) {
                if (connection == null) {
                    // Load the specified database driver
                    Class.forName(driverName);
                } else {
                    connection.close();
                }
                // Create new connection and get metadata
                connection = DriverManager.getConnection(databaseUrl, username, password);
            }
        } catch (SQLException e) {
            throw new CouldNotConnectException();
        }
    }

    @Override
    public ArrayList<Institute> allInstituteQuery() {

        ArrayList<Institute> result = new ArrayList<>();
        try {
            checkConnected();
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rset = stmt.executeQuery("SELECT institute_id, name FROM INSTITUTE ")) {
                    while (rset.next()) {
                        Institute j = new Institute();
                        j.setId(rset.getLong("institute_id"));
                        j.setName(rset.getString("name"));
                        result.add(j);
                    }
                }
                //instituteList.toBuilder().
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (NotConnectedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Institute byIdQuery(long id) {
        Institute institute = new Institute();
        try {
            String query = "SELECT institute_id, name FROM INSTITUTE "
                    + "WHERE institute_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet rset = preparedStatement.executeQuery();
            rset.next();

            institute.setId(rset.getLong("institute_id"));
            institute.setName(rset.getString("name"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return institute;
    }

    @Override
    public void addInstituteQuery(Institute institute) {
        try {
            String query = "INSERT into INSTITUTE (name) VALUES"
                    + "(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, institute.getName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User registerQuery(User u) {
        User toreturn = new User();
        try {
            String idQuery = "SELECT MAX(USER_ID) AS MAXID FROM USER_TABLE";
            PreparedStatement idstatement = connection.prepareStatement(idQuery);
            ResultSet resultSet = idstatement.executeQuery();
            resultSet.next();
            long id = resultSet.getLong("MAXID") +1;
            String query = "INSERT into USER_TABLE (user_id, user_name, real_name, institute_id, password, role) VALUES"
                    + "(?,?,?,?,?, 'ROLE_USER')";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1,id);
            preparedStatement.setString(2, u.getUserName());
            preparedStatement.setString(3, u.getRealName());
            preparedStatement.setLong(4, u.getInstituteId());
            preparedStatement.setString(5, u.getPassword());
            preparedStatement.executeUpdate();
            toreturn = u;
            toreturn.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toreturn;
    }

    @Override
    public String  getNameByIdQuery(long uId) {
        String toReturn = null;
        try {
            String query = "SELECT user_name FROM USER_TABLE "
                    + "WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, uId);
            ResultSet rset = preparedStatement.executeQuery();
            rset.next();
            toReturn = rset.getString("user_name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public long getIdByNameQuery(String uName) {
        long toReturn = 0;
        try {
            String query = "SELECT user_id FROM USER_TABLE "
                    + "WHERE user_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, uName);
            ResultSet rset = preparedStatement.executeQuery();
            rset.next();
            toReturn = rset.getLong("user_id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public long getInstituteIdByNameQuery(String uName) {
        long toReturn = 0;
        try {
            String query = "SELECT institute_id FROM USER_TABLE "
                    + "WHERE user_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, uName);
            ResultSet rset = preparedStatement.executeQuery();
            rset.next();
            toReturn = rset.getLong("institute_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public ArrayList<Comment> getCommentsBySubjectQuery(Subject s) {
        ArrayList<Comment> result = new ArrayList<>();
        try {
            String query = "SELECT comment_id, user_id, subject_id, comment_text FROM SUBJECT_COMMENT "
                    + "WHERE subject_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, s.getId());
            ResultSet rset = preparedStatement.executeQuery();
            while (rset.next()) {
                Comment j = new Comment();
                j.setId(rset.getLong("comment_id"));
                j.setUserId(rset.getLong("user_id"));
                j.setSubjectId(rset.getLong("subject_id"));
                j.setCommentText(rset.getString("comment_text"));
                result.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void addCommentQuery(Comment c) {
        try {
            String idQuery = "SELECT MAX(COMMENT_ID) AS MAXID FROM SUBJECT_COMMENT";
            PreparedStatement idstatement = connection.prepareStatement(idQuery);
            ResultSet resultSet = idstatement.executeQuery();
            resultSet.next();
            long id = resultSet.getLong("MAXID") +1;
            String query = "INSERT INTO SUBJECT_COMMENT (comment_id, user_id, subject_id, comment_text) VALUES" +
                    "(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,id);
            preparedStatement.setLong(2, c.getUserId());
            preparedStatement.setLong(3, c.getSubjectId());
            preparedStatement.setString(4, c.getCommentText());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Subject> getSubjectsByInstitute(Institute i) {
        ArrayList<Subject> result = new ArrayList<>();
        try {
            String query = "SELECT  subject_id, name, semester, institute_id FROM SUBJECT "
                    + "WHERE institute_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, i.getId());
            ResultSet rset = preparedStatement.executeQuery();
            while (rset.next()) {
                Subject j = new Subject();
                j.setId(rset.getLong("subject_id"));
                j.setName(rset.getString("name"));
                j.setSemester(rset.getInt("semester"));
                j.setInstituteId(rset.getLong("institute_id"));
                result.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Subject getSubjectByNameQuery(String subjectName) {
        Subject toReturn = new Subject();
        try {
            String query = "SELECT * FROM SUBJECT " +
                    "WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, subjectName);
            ResultSet rset = preparedStatement.executeQuery();
            rset.next();
            toReturn.setName(rset.getString("name"));
            toReturn.setInstituteId(rset.getLong("institute_id"));
            toReturn.setSemester(rset.getInt("semester"));
            toReturn.setId(rset.getLong("subject_id"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public void addSubjectQuery(Subject subject) {
        try {
            String idQuery = "SELECT MAX(SUBJECT_ID) AS MAXID FROM SUBJECT";
            PreparedStatement idstatement = connection.prepareStatement(idQuery);
            ResultSet resultSet = idstatement.executeQuery();
            resultSet.next();
            long id = resultSet.getLong("MAXID") +1;
            String query = "INSERT INTO SUBJECT (subject_id, name, semester, institute_id)" +
                    "VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,id);
            preparedStatement.setString(2, subject.getName());
            preparedStatement.setInt(3, subject.getSemester());
            preparedStatement.setLong(4, subject.getInstituteId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
