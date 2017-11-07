package quickstart;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.io.TextOutputFormat;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.types.Row;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE;

public class RowImpl {

    public static void main(String[] args) throws Exception {

        //Get List<Map<String, Object>>
        File csvFile = new File("/Users/cwang/Flink/SL_Flink/test/directory.json");
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> entries = mapper.readValue(csvFile, List.class);

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Map<String, Object>> mapDataSet = env.fromCollection(entries);

        DataSet<Row> rowDataSet = mapDataSet.map(new Map2Row());

        RowTypeInfo rowTypeInfo = new RowTypeInfo();


        DataSet<Row> output = rowDataSet.filter(new FilterFunction<Row>() {
            @Override
            public boolean filter(Row value) throws Exception {
                return value.getField(5).equals("Sales") && value.getField(1).equals("Field");
            }
        });

        DataSet<Row> sortedOutput = output.sortPartition(new KeySelector<Row, String>() {
            @Override
            public String getKey(Row value) throws Exception {
                return (String)value.getField(0);
            }
        }, Order.ASCENDING).setParallelism(1);


        sortedOutput.writeAsFormattedText("/Users/cwang/Flink/SL_Flink/row.csv", OVERWRITE,
                new TextOutputFormat.TextFormatter<Row>() {
                    @Override
                    public String format(Row record) {
                        return record.getField(0) + "|"
                                + record.getField(1) + "|"
                                + record.getField(2) + "|"
                                + record.getField(3) + "|"
                                + record.getField(4) + "|"
                                + record.getField(5) + "|"
                                + record.getField(6) + "|";
                    }
                }
        ).setParallelism(1);

        env.execute();


    }

    private static class Map2Row implements MapFunction<Map<String, Object>, Row> {

        @Override
        public Row map(Map<String, Object> record) throws Exception {
            Row row = new Row(record.size());

            int index = 0;
            for (String field : record.keySet()) {
                row.setField(index, record.get(field));
                index++;
            }
            return row;
        }
    }
}
