package service;

import dal.DataAccesLayer;
import dal.OnlabDal;
import dal.exception.CouldNotConnectException;
import model.Comment;
import model.Institute;
import model.Subject;
import model.User;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 4/25/17.
 */
@WebService(endpointInterface = "service.Service")
public class ServiceImpl implements Service {
    private static final String username = "system";
    private static final String password = "oracle";

    private DataAccesLayer dl = OnlabDal.getInstance();

    public ServiceImpl() throws CouldNotConnectException, ClassNotFoundException {
        dl.connect(username, password);
    }

    @Override
    public User register(User u) {
        return dl.registerQuery(u);
    }

    @Override
    public String getNameById(long uId) {
        return dl.getNameByIdQuery(uId);
    }

    @Override
    public long getIdByName(String uName) {
        return dl.getIdByNameQuery(uName);
    }

    @Override
    public long getInstituteIdByName(String uName) {
        return dl.getInstituteIdByNameQuery(uName);
    }

    @Override
    public Institute getInstitutebyId(long id) {
        return dl.byIdQuery(id);
    }

    @Override
    public ArrayList<Institute> getAllInstitutes() {
        return dl.allInstituteQuery();
    }

    @Override
    public void addInstitute(Institute institute) {
        dl.addInstituteQuery(institute);
    }

    @Override
    public ArrayList<Subject> getSubjectsByInstitute(Institute institute) {
        return dl.getSubjectsByInstitute(institute);
    }

    @Override
    public Subject getSubjectByName(String subjectName) {
        return dl.getSubjectByNameQuery(subjectName);
    }

    @Override
    public void addSubject(Subject subject) {
        dl.addSubjectQuery(subject);
    }

    @Override
    public ArrayList<Comment> getCommentsBySubject(Subject subject) {
        return dl.getCommentsBySubjectQuery(subject);
    }

    @Override
    public void addComment(Comment comment) {
        dl.addCommentQuery(comment);
    }
}
