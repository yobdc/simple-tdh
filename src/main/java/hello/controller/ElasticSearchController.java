package hello.controller;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ElasticSearchController {

    @Autowired
    private TransportClient esClient;

    @RequestMapping("/es")
    public String index(String index, String type, String key) {
        return null;
    }
}
