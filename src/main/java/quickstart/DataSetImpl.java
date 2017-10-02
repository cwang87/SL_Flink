package quickstart;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple7;

import static org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE;

public class DataSetImpl {

    public static void main(String[] args) {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        /* Name,Location,Extension,Email,Title,Department,Dept ID */
        DataSet<Tuple7<String, String, Integer, String, String, String, Integer>> csvInput
                = env.readCsvFile("/Users/cwang/Flink/pipelines/directory.csv")
                .ignoreFirstLine()
                .parseQuotedStrings('"')
                .types(String.class, String.class, Integer.class, String.class, String.class,String.class, Integer.class);


        DataSet<Tuple7<String, String, Integer, String, String, String, Integer>> output0
                = csvInput.filter(new FilterFunction<Tuple7<String, String, Integer, String, String, String, Integer>>() {
                    @Override
                    public boolean filter(Tuple7<String, String, Integer, String, String, String, Integer> input) throws Exception {
                        return input.f5.equals("Sales") && input.f1.equals("Field");
                    }
                })
                .sortPartition(0, Order.DESCENDING)
                .partitionByRange(0);   //for sorting
        // This operation requires an extra pass over the DataSet to compute the range boundaries and shuffles
        // the whole DataSet over the network. This can take significant amount of time.


        DataSet<Tuple7<String, String, Integer, String, String, String, Integer>> output1
                = csvInput.filter(new FilterFunction<Tuple7<String, String, Integer, String, String, String, Integer>>() {
                    @Override
                    public boolean filter(Tuple7<String, String, Integer, String, String, String, Integer> input) throws Exception {
                        return input.f5.equals("Sales") && input.f1.equals("HQ");
                    }
                })
                .sortPartition(0, Order.DESCENDING)
                .partitionByRange(0);


        output0.writeAsCsv("/Users/cwang/Flink/pipelines/target/ds0.csv",   //Only a Tuple DataSet can written as a CSV file
                "\n", "|", OVERWRITE).setParallelism(1);    //for one file: set the parallelism for the sink
        output1.writeAsCsv("/Users/cwang/Flink/pipelines/target/ds1.csv",
                "\n", "|", OVERWRITE).setParallelism(1);


        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
