package hello.controller;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

@RestController
public class HbaseController {
    @Autowired
    private Configuration conf;

    @RequestMapping("/hbase")
    public String index(String table, String rowKey) {
        try {
            Connection conn = ConnectionFactory.createConnection(conf);
            HTable hTable = (HTable) conn.getTable(TableName.valueOf(table));
            Get get = new Get(Bytes.toBytes(rowKey));
            Result result = hTable.get(get);
            String str = "";
            for (Cell cell : result.rawCells()) {
                str += "行键:" + new String(CellUtil.cloneRow(cell)) + "\t" +
                        "列族:" + new String(CellUtil.cloneFamily(cell)) + "\t" +
                        "列名:" + new String(CellUtil.cloneQualifier(cell)) + "\t" +
                        "值:" + new String(CellUtil.cloneValue(cell)) + "\t" +
                        "时间戳:" + cell.getTimestamp();
            }
            return str;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "fail";
    }
}
