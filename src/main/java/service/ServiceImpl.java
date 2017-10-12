package service;

import dal.DataAccesLayer;
import dal.OnlabDal;
import dal.exception.CouldNotConnectException;
import model.*;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by robin on 4/25/17.
 */
@WebService(endpointInterface = "service.Service")
public class ServiceImpl implements Service {
    @Resource
    WebServiceContext webServiceContext;

    private static final String dbUsername = "system";
    private static final String dbPassword = "oracle";

    private DataAccesLayer dl = OnlabDal.getInstance();
    private static final Logger logger = Logger.getLogger("Service");
    public ServiceImpl() throws CouldNotConnectException, ClassNotFoundException {
        dl.connect(dbUsername, dbPassword);
    }


    @Override
    public boolean authenticate() {
        MessageContext messageContext = webServiceContext.getMessageContext();
        Map httpHeaders = (Map) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);
        if(httpHeaders.get("Username") == null)
            return false;
        String username =  ((List)httpHeaders.get("Username")).get(0).toString();
        String password =  ((List)httpHeaders.get("Password")).get(0).toString();
        if(username == null|| password == null || username.isEmpty() || password.isEmpty())
            return false;
        User userForValidation = new User();
        userForValidation.setUserName(username);
        userForValidation.setPassword(password);
        return dl.validateUser(userForValidation);
    }

    @Override
    public User register(User u) {
        MessageContext messageContext = webServiceContext.getMessageContext();
        logger.info("Recieved register request with user: " + u.toString());
        return dl.registerQuery(u);
    }

    @Override
    public String getNameById(long uId) {
        if(!authenticate())
            return null;
        return dl.getNameByIdQuery(uId);
    }

    @Override
    public long getIdByName(String uName) {
        return 0;
//        return dl.getIdByNameQuery(uName);
    }

    @Override
    public long getInstituteIdByName(String uName) {
        if(!authenticate())
            return -1L;
        return dl.getInstituteIdByNameQuery(uName);
    }

    @Override
    public Institute getInstitutebyId(long id) {
        if(!authenticate())
            return null;
        return dl.byIdQuery(id);
    }

    @Override
    public ListWrapper<Institute> getAllInstitutes() {
        return new ListWrapper<>(dl.allInstituteQuery());
    }

    @Override
    public void addInstitute(Institute institute) {
        dl.addInstituteQuery(institute);
    }

    @Override
    public ListWrapper<Subject> getSubjectsByInstitute(Institute institute) {
        if(!authenticate())
            return null;
        return new ListWrapper<>(dl.getSubjectsByInstitute(institute));
    }

    @Override
    public Subject getSubjectByName(String subjectName) {
        if(!authenticate())
            return null;
        return dl.getSubjectByNameQuery(subjectName);
    }

    @Override
    public void addSubject(Subject subject) {
        if(!authenticate())
            return;
        dl.addSubjectQuery(subject);
    }

    @Override
    public ListWrapper<Comment> getCommentsBySubject(Subject subject) {
        if(!authenticate())
            return null;
        return new ListWrapper<Comment>(dl.getCommentsBySubjectQuery(subject));
    }

    @Override
    public void addComment(Comment comment) {
        if(!authenticate())
            return;
        dl.addCommentQuery(comment);
    }
}
