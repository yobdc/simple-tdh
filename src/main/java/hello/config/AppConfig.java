package hello.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Value("${zookeeper.quorum}")
    private String quorum;
    @Value("${zookeeper.port}")
    private String port;

    @Value("${es.addresses}")
    private String esAddresses;

    @Bean
    public org.apache.hadoop.conf.Configuration hbaseConf() {
        org.apache.hadoop.conf.Configuration conf = null;
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", quorum);
        conf.set("hbase.zookeeper.property.clientPort", port);
        return conf;
    }

    @Bean
    public TransportClient esClient() {
        Map<String, Integer> result = new HashMap<>();
        String[] addrArray = esAddresses.split(",");
        for (int i = 0; i < addrArray.length; i++) {
            String[] addrWithPort = addrArray[i].split(":");
            if (addrWithPort.length == 2) {
                result.put(addrWithPort[0], Integer.parseInt(addrWithPort[1]));
            }
        }


        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        for(Map.Entry<String, Integer> entry : result.entrySet()){
            try {
                client.addTransportAddress(new InetSocketTransportAddress(
                        InetAddress.getByName(entry.getKey()),
                        entry.getValue()
                ));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return client;
    }
}
