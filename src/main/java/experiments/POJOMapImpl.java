package experiments;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.io.TextOutputFormat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE;

public class POJOMapImpl {

    public static class POJOMap {

        public Map<String, Object> record = new HashMap<>();

        public POJOMap() {
        }

        public POJOMap(Map<String, Object> record) {
            this.record = record;
        }
    }

    public static void main(String[] args) throws IOException {

        File csvFile = new File("/Users/cwang/Flink/SL_Flink/directory.json");
        ObjectMapper mapper = new ObjectMapper();

        //Get List<Map<String, Object>>
        List<Map<String, Object>> entries = mapper.readValue(csvFile, List.class);

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<POJOMap> pojoMapDataSet = env.fromCollection(entries).map(new MapFunction<Map<String, Object>, POJOMap>() {
            @Override
            public POJOMap map(Map<String, Object> stringObjectMap) throws Exception {
                return new POJOMap(stringObjectMap);
            }
        });

        DataSet<POJOMap> output0 = pojoMapDataSet.filter(new FilterFunction<POJOMap>() {
            @Override
            public boolean filter(POJOMap pojoMap) throws Exception {
                return pojoMap.record.get("Department").equals("Sales") && pojoMap.record.get("Location").equals("Field");
            }
        });


        output0.sortPartition(new KeySelector<POJOMap, String>() {
            @Override
            public String getKey(POJOMap pojoMap) throws Exception {
                return (String) pojoMap.record.get("Name");
            }
        }, Order.DESCENDING);


        output0.writeAsFormattedText("/Users/cwang/Flink/SL_Flink/pojomap0.csv", OVERWRITE,
                new TextOutputFormat.TextFormatter<POJOMap>() {
                    @Override
                    public String format(POJOMap pojoMap) {
                        Map<String, Object> record = pojoMap.record;
                        return record.get("Name") + "|"
                                + record.get("Location") + "|"
                                + record.get("Extension") + "|"
                                + record.get("Email") + "|"
                                + record.get("Title") + "|"
                                + record.get("Department") + "|"
                                + record.get("Dept ID") + "|";
                    }
                }
        ).setParallelism(1);

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
