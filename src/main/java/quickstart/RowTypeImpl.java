package quickstart;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.io.RowCsvInputFormat;
import org.apache.flink.api.java.io.TextOutputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.table.plan.nodes.dataset.DataSetIntersect;
import org.apache.flink.table.plan.schema.TableSourceTable;
import org.apache.flink.table.sources.CsvTableSource;
import org.apache.flink.table.sources.TableSource;
import org.apache.flink.types.Row;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE;

public class RowTypeImpl {

    public static void main(String[] args) throws Exception {

        //        DataSet<Row> dsRow = tableEnv.toDataSet(table, Row.class);

        /**
         *  Get List<Map<String, Object>>
         */
        File csvFile = new File("/Users/cwang/Flink/SL_Flink/test/directory.json");
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> entries = mapper.readValue(csvFile, List.class);

        int size;
        String[] fieldnames;
        Map<String, Object> firstEntry = entries.get(0);
        size = firstEntry.size();
        fieldnames = new String[size];
        int index = 0;
        for (String name : firstEntry.keySet()) {
            fieldnames[index] = name;
            index++;
        }


        /**
         * Flink
         */
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        /* Parse Type information */
        String fieldTypesStr = "string, string, int, string, string, string, int";
        TypeInformation<?>[] fieldTypes = getFieldTypes(size, fieldTypesStr);
        TypeInformation<Row> typeInfo = new RowTypeInfo(fieldTypes, fieldnames);

        DataSet<Map<String, Object>> mapDataSet = env.fromCollection(entries);
        DataSet<Row> rowDataSet = mapDataSet.map(new Map2Row());
        List<Row> rows = rowDataSet.collect();
        DataSet<Row> typedRows = env.fromCollection(rows, typeInfo);

        DataSet<Row> output = typedRows.filter(new FilterFunction<Row>() {
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


        sortedOutput.writeAsFormattedText("/Users/cwang/Flink/SL_Flink/rowType.csv", OVERWRITE,
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
                Object obj = record.get(field);
                row.setField(index, record.get(field));
                index++;
            }
            return row;
        }
    }

    private static TypeInformation<?>[] getFieldTypes(int size, String fieldTypesStr) {
        TypeInformation<?>[] typeArr = new TypeInformation<?>[size];
        String[] split = fieldTypesStr.split(", ");
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("string")) {
                typeArr[i] = BasicTypeInfo.STRING_TYPE_INFO;
            } else if (split[i].equals("int")) {
                typeArr[i] = BasicTypeInfo.INT_TYPE_INFO;
            } else if (split[i].equals("double")) {
                typeArr[i] = BasicTypeInfo.DOUBLE_TYPE_INFO;
            } else if (split[i].equals("boolean")) {
                typeArr[i] = BasicTypeInfo.BOOLEAN_TYPE_INFO;
            } else {
                throw new IllegalArgumentException("Cannot get Flink type info for " + split[i]);
            }
        }
        return typeArr;
    }


}
