package quickstart;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.table.api.BatchTableEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.Types;
import org.apache.flink.table.sinks.CsvTableSink;
import org.apache.flink.table.sources.CsvTableSource;

public class TableImpl {

    public static void main(String[] args) {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        CsvTableSource csvSource = CsvTableSource
                .builder()
                .path("/Users/cwang/Flink/pipelines/directory.csv")
                .field("Name", Types.STRING())
                .field("Location", Types.STRING())
                .field("Extension", Types.INT())
                .field("Email", Types.STRING())
                .field("Title", Types.STRING())
                .field("Department", Types.STRING())
                .field("Dept ID", Types.INT())
                .ignoreFirstLine()
                .quoteCharacter('"')    //string field
                .build();

        tableEnv.registerTableSource("csvTable", csvSource);
        Table router = tableEnv.scan("csvTable").filter("Department === 'Sales'");

        Table output0 = router
                .filter("Location === 'Field'")
                .orderBy("Name");

        Table output1 = router
                .filter("Location === 'HQ'")
                .orderBy("Name");


        output0.writeToSink(new CsvTableSink(
                "/Users/cwang/Flink/pipelines/target/tbl0.csv",
                "|",
                1,
                FileSystem.WriteMode.OVERWRITE
        ));

        output1.writeToSink(new CsvTableSink(
                "/Users/cwang/Flink/pipelines/target/tbl1.csv",
                "|",
                1,
                FileSystem.WriteMode.OVERWRITE
        ));

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
