package service;

import model.Comment;
import model.Institute;
import model.Subject;
import model.User;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 4/25/17.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Service {
    @WebMethod
    User register(User u);
    @WebMethod
    String getNameById(long uId);
    @WebMethod
    long getIdByName(String uName);
    @WebMethod
    long getInstituteIdByName(String uName);

    @WebMethod
    Institute getInstitutebyId(long id);
    @WebMethod
    ArrayList<Institute> getAllInstitutes();
    @WebMethod
    void addInstitute(Institute institute);

    @WebMethod
    ArrayList<Subject> getSubjectsByInstitute(Institute institute);
    @WebMethod
    Subject getSubjectByName(String subjectName);
    @WebMethod
    void addSubject(Subject subject);

    @WebMethod
    ArrayList<Comment> getCommentsBySubject(Subject subject);
    @WebMethod
    void addComment(Comment comment);
}

