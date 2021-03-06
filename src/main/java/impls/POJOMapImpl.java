package impls;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.io.TextOutputFormat;
import org.apache.flink.api.java.operators.DataSource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE;

public class POJOMapImpl {

    public static class POJOMap {

        public Map<String, Object> object = new HashMap<>();

        public POJOMap() {
        }

        public POJOMap(Map<String, Object> object) {
            this.object = object;
        }

        public Map<String, Object> getObject() {
            return object;
        }

        public void setObject(Map<String, Object> object) {
            this.object = object;
        }
    }

    public static void main(String[] args) throws IOException {

        File csvFile = new File("/Users/cwang/Flink/SL_Flink/test/directory.json");
        ObjectMapper mapper = new ObjectMapper();

        //Get List<Map<String, Object>>
        List<Map<String, Object>> entries = mapper.readValue(csvFile, List.class);

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSource<Map<String, Object>> csvDataSource = env.fromCollection(entries);

        DataSet<POJOMap> pojoMapDataSet = csvDataSource.map(new MapFunction<Map<String, Object>, POJOMap>() {
            @Override
            public POJOMap map(Map<String, Object> stringObjectMap) throws Exception {
                return new POJOMap(stringObjectMap);
            }
        });

        DataSet<POJOMap> output0 = pojoMapDataSet.filter(new FilterFunction<POJOMap>() {
            @Override
            public boolean filter(POJOMap pojoMap) throws Exception {
                return pojoMap.object.get("Department").equals("Sales") && pojoMap.object.get("Location").equals("Field");
            }
        });


        output0.sortPartition(new KeySelector<POJOMap, String>() {
            @Override
            public String getKey(POJOMap pojoMap) throws Exception {
                return (String) pojoMap.object.get("Name");
            }
        }, Order.DESCENDING);


        output0.writeAsFormattedText("/Users/cwang/Flink/SL_Flink/pojomap0.csv", OVERWRITE,
                new TextOutputFormat.TextFormatter<POJOMap>() {
                    @Override
                    public String format(POJOMap pojoMap) {
                        Map<String, Object> record = pojoMap.object;
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
//            System.out.print(env.getExecutionPlan());
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
