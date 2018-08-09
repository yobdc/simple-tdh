package hello.controller;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElasticSearchController {

    @Autowired
    private TransportClient esClient;

    @RequestMapping("/es")
    public GetResponse index(String index, String type, String id) {
        GetResponse response = esClient.prepareGet(index, type, id)
                .setOperationThreaded(false)
                .get();
        return response;
    }
}
