/**
 * Created by robin on 4/25/17.
 */
import dal.exception.CouldNotConnectException;
import service.ServiceImpl;

import javax.xml.ws.Endpoint;
public class Publisher {

        public static void main(String[] args) {
            try {
                Endpoint.publish("http://localhost:8888/ws/service", new ServiceImpl());
            } catch (CouldNotConnectException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

}
