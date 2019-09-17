package club.vasilis.coolq.jx3.config;

import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vasilis
 * @date 2019/7/25 -10:46
 */

public class ServerList {
    public static Map<String, String> serverlist = new HashMap<>();

    public static void loadConfig() {
        CsvReader reader = CsvUtil.getReader();
        //从文件中读取CSV数据
        // CsvData data = reader.read(FileUtil.file("config/serverinfo.xls"));
        //String filePath = Main.class.getClassLoader().getResource("serverlist.csv").getPath();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("serverlist.csv");
      //  System.out.println(filePath);
        CsvData data = reader.read(new BufferedReader(new InputStreamReader(is)));

        List<CsvRow> rows = data.getRows();
        //遍历行
        for (CsvRow csvRow : rows) {
            //getRawList返回一个List列表，列表的每一项为CSV中的一个单元格（既逗号分隔部分）
            List<String> rawList = csvRow.getRawList();
            serverlist.put(rawList.get(2), rawList.get(3));
        }

    }

    public static void main(String[] args) {
        loadConfig();

    }
}
