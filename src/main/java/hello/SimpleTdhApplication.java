package hello;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class SimpleTdhApplication extends SpringBootServletInitializer {
    @Autowired(required = false)
    private TransportClient esClient;
    @Autowired(required = false)
    private Connection hiveConn;

    @PreDestroy
    public void tearDown() {
        if (esClient != null) {
            esClient.close();
        }
        if (hiveConn != null) {
            try {
                hiveConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleTdhApplication.class, args);
    }


}
