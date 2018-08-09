package hello;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class SimpleTdhApplication extends SpringBootServletInitializer {
    @Autowired
    private TransportClient esClient;

    @PreDestroy
    public void tearDown() {
        if (esClient != null) {
            esClient.close();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleTdhApplication.class, args);
    }


}
