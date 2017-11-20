package impls;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.io.RowCsvInputFormat;
import org.apache.flink.api.java.io.TextOutputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.types.Row;

import static org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE;


/**
 * no ignore first line
 * no parseQuotedStrings('"')
 * Albert Maro,Field,4357,amaro@company.com,"Director, Eastern US",Sales,1100
 */
public class RowCsvImpl {

    public static void main(String[] args) throws Exception {
        String path = "/Users/cwang/Flink/SL_Flink/test/directory.csv";

        TypeInformation<?>[] fieldTypes = {BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
                BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
                BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.INT_TYPE_INFO};


        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        RowCsvInputFormat csvInputFormat = new RowCsvInputFormat(new Path(path), fieldTypes, "\n", ",");

        DataSet<Row> dataSet = env.createInput(csvInputFormat).setParallelism(1);
        DataSet<Row> filtered = dataSet.filter(new FilterFunction<Row>() {
            @Override
            public boolean filter(Row value) throws Exception {
                return value.getField(5).equals("Sales") && value.getField(1).equals("Field");
            }
        });

        DataSet<Row> sorted = filtered.sortPartition(new KeySelector<Row, String>() {
            @Override
            public String getKey(Row value) throws Exception {
                return (String)value.getField(0);
            }
        }, Order.ASCENDING).setParallelism(1);

        sorted.writeAsFormattedText("/Users/cwang/Flink/SL_Flink/rowCSV.csv", OVERWRITE,
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
}
