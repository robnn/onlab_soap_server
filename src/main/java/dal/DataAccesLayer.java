package dal;

import dal.exception.CouldNotConnectException;
import model.Comment;
import model.Institute;
import model.Subject;
import model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 4/6/17.
 */
public interface DataAccesLayer {
    //institute
    void connect(String username, String password) throws CouldNotConnectException, ClassNotFoundException, CouldNotConnectException;
    ArrayList<Institute> allInstituteQuery();
    Institute byIdQuery(long id);
    void addInstituteQuery(Institute institute);

    //user
    User registerQuery(User u);
    String getNameByIdQuery(long uId);
    long getIdByNameQuery(String uName);
    long getInstituteIdByNameQuery(String uName);

    //comments
    ArrayList<Comment> getCommentsBySubjectQuery(Subject s);
    void addCommentQuery(Comment c);

    //subjects
    ArrayList<Subject> getSubjectsByInstitute(Institute i);
    Subject getSubjectByNameQuery(String subjectName);
    void addSubjectQuery(Subject subject);
}
