/**
 * Created by robin on 4/25/17.
 */
import dal.exception.CouldNotConnectException;
import service.ServiceImpl;

import javax.xml.ws.Endpoint;
import java.util.logging.Logger;

public class Publisher {

    private static final Logger logger = Logger.getLogger("SOAP Server");
        public static void main(String[] args) {
            try {
                logger.info("Starting server");
                Endpoint.publish("http://0.0.0.0:8888/ws/service", new ServiceImpl());
            } catch (CouldNotConnectException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

}
