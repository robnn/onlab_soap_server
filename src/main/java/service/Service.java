package service;

import model.*;

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
    ListWrapper<Institute> getAllInstitutes();
    @WebMethod
    void addInstitute(Institute institute);

    @WebMethod
    ListWrapper<Subject> getSubjectsByInstitute(Institute institute);
    @WebMethod
    Subject getSubjectByName(String subjectName);
    @WebMethod
    void addSubject(Subject subject);

    @WebMethod
    ListWrapper<Comment> getCommentsBySubject(Subject subject);
    @WebMethod
    void addComment(Comment comment);
}

