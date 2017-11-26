package client;



import com.google.common.base.Stopwatch;
import model.Institute;
import model.ListWrapper;
import service.Service;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceRef;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SoapClient implements Runnable{

    javax.xml.ws.Service service;

    Service customService;

    public SoapClient() throws MalformedURLException {
        service = javax.xml.ws.Service.create(new URL("http://127.0.0.1:8888/ws/service/?wsdl"),new QName("http://service/","ServiceImplService"));
        customService = service.getPort(Service.class);
    }
    public void getInstitutesInLoop(){
        Stopwatch stopwatch = Stopwatch.createStarted();
        System.out.println("Getting institutes in for loop");
        for(int i = 0; i< 100; i ++) {
            ListWrapper<Institute> instituteListWrapper = customService.getAllInstitutes();
        }
        System.out.println("Took: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
    }

    public static void getInstitutesNewThread() throws MalformedURLException {
        System.out.println("Getting institutes in new threads");
        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread[] threads = new Thread[100];
        for(int i = 0 ; i<100; i ++ ) {
            Thread t = new Thread(new SoapClient());
            threads[i] = t;
            t.start();
        }
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println("Finished all threads, took: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");

    }

    @Override
    public void run() {
        ListWrapper<Institute> instituteListWrapper = customService.getAllInstitutes();
    }
}
