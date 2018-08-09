package hello.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${zookeeper.quorum}")
    private String quorum;
    @Value("${zookeeper.port}")
    private String port;

    @Bean
    public org.apache.hadoop.conf.Configuration hbaseConf() {
        org.apache.hadoop.conf.Configuration conf = null;
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", quorum);
        conf.set("hbase.zookeeper.property.clientPort", port);
        return conf;
    }
}
