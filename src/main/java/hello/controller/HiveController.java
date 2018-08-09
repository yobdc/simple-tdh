package hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class HiveController {

    @Autowired
    private Connection hiveConn;

    @RequestMapping("/hive")
    public String index(String sql) {
        try {
            Statement stmt = hiveConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            StringBuilder sb = new StringBuilder();
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                sb.append(metadata.getColumnName(i) + ", ");
            }
            while (rs.next()) {
                String row = "";
                for (int i = 1; i <= columnCount; i++) {
                    row += rs.getString(i) + ", ";
                }
                sb.append(row);
                sb.append("\n");
            }
            rs.close();
            return sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
