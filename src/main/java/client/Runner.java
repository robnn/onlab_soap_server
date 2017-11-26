package client;

import java.net.MalformedURLException;

public class Runner {
    public static void main(String[] args) throws MalformedURLException {
        SoapClient soapClient = new SoapClient();
        soapClient.getInstitutesInLoop();
        SoapClient.getInstitutesNewThread();
    }
}
