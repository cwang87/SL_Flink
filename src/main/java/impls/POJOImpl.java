package impls;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.TextOutputFormat;

import static org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE;

public class POJOImpl {

    public static void main(String[] args) {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Record> csvInput = env.readCsvFile("/Users/cwang/Flink/SL_Flink/directory.csv")
                .ignoreFirstLine()
                .parseQuotedStrings('"')
                .pojoType(Record.class,
                        "name", "location", "extension", "email", "title", "department", "deptID");

        ClientFilter filter = new ClientFilter();
        DataSet<Record> filtered = csvInput.filter(filter);
        DataSet<Record> sorted = filtered.partitionByRange("name").sortPartition("name", Order.ASCENDING);

        //Write elements line-wise as Strings.
        // The Strings are obtained by calling a user-defined format() method for each element.
        CsvTextFormatter formatter = new CsvTextFormatter();
        sorted.writeAsFormattedText("/Users/cwang/Flink/SL_Flink/pojo0.csv", OVERWRITE, formatter).setParallelism(1);

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * override methods
     */
    public static class ClientFilter implements FilterFunction<Record> {
        @Override
        public boolean filter(Record record) throws Exception {
            return record.getDepartment().equals("Sales") && record.getLocation().equals("Field");
        }
    }

    public static class CsvTextFormatter implements TextOutputFormat.TextFormatter<Record> {
        @Override
        public String format(Record record) {
            return record.getName() + "|"
                    + record.getLocation() + "|"
                    + record.getExtension() + "|"
                    + record.getEmail() + "|"
                    + record.getTitle() + "|"
                    + record.getDepartment() + "|"
                    + record.getDeptID() + "|";
        }
    }
}
