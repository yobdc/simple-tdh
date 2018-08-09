package hello.controller;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElasticSearchController {

    @Autowired
    private TransportClient esClient;

    @RequestMapping("/es")
    public String index(String index, String type, String id) {
        GetResponse response = esClient.prepareGet(index, type, id)
                .setOperationThreaded(false)
                .get();
        return response.toString();
    }

    @RequestMapping("/es/search")
    public String search(String index, String type, String field, String value) {
        SearchResponse response = esClient.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery(field, value))
                .get();
        return response.toString();
    }
}
