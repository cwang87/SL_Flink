package quickstart;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.table.api.BatchTableEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.Types;
import org.apache.flink.table.sinks.CsvTableSink;
import org.apache.flink.table.sources.CsvTableSource;
import org.apache.flink.table.sources.TableSource;

// csv file from http://www.sample-videos.com/download-sample-csv.php

public class Simple {

    public static void main(String[] args) throws Exception {

        String path = "/Users/cwang/Flink/pipelines/directoryClean.csv";

        //1. obtain an execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        //2. register a TableSource
        TableSource csvSource = CsvTableSource.builder()
                .path("/Users/cwang/Flink/pipelines/500kRows.csv")
                .field("Name", Types.STRING())
                .field("Company", Types.STRING())
                .field("A", Types.INT())
                .field("B", Types.DOUBLE())
                .field("C", Types.DOUBLE())
                .field("D", Types.DOUBLE())
                .field("Location", Types.STRING())
                .field("Storage", Types.STRING())
                .field("E", Types.DOUBLE())
                .ignoreFirstLine()
                .ignoreParseErrors()
                .build();
        tableEnv.registerTableSource("CsvTable", csvSource);

        //3. Query
        Table table = tableEnv.scan("CsvTable");
        Table companyTable = table.filter("Company === 'Aaron Bergman'");

        tableEnv.registerTable("oneCompany", companyTable);

        Table result = tableEnv.sql(
                "SELECT Storage, SUM(A) AS Total " +
                        "FROM oneCompany " +
                        "GROUP BY Storage " +
                        "ORDER BY 2 DESC"
        );

        //4. TableSink
        result.writeToSink( new CsvTableSink(
                        "/Users/cwang/Flink/pipelines/target/result.csv",
                        "|",
                        1,
                        FileSystem.WriteMode.OVERWRITE
                ));

       env.execute();
    }
}
