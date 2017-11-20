package impls;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.api.java.io.TextOutputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;

import java.io.IOException;
import java.util.List;

import static org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE;


/**
 * no ignore first line
 * no parseQuotedStrings('"')
 * Albert Maro,Field,4357,amaro@company.com,"Director, Eastern US",Sales,1100
 */
public class RowTextImpl {

    public static void main(String[] args) throws Exception {
        String path = "/Users/cwang/Flink/SL_Flink/directory.csv";

        String[] fieldNames = {"Name", "Location", "Extension", "Email", "Title", "Department", "Dept ID"};
        String header = "Name,Location,Extension,Email,Title,Department,Dept ID";

        TypeInformation<?>[] fieldTypes = {BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
                BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
                BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.INT_TYPE_INFO};


        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        Path filePath = new Path(path);
        TextInputFormat inputFormat = new TextInputFormat(filePath);
        DataSet<String> lines = env.readFile(inputFormat, path);
        DataSet<Row> dataSet = lines.flatMap(new RawCsv2Row(header, fieldNames));
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

        sorted.writeAsFormattedText("/Users/cwang/Flink/SL_Flink/rowText.csv", OVERWRITE,
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

    private static class RawCsv2Row extends RichFlatMapFunction<String, Row> {

        private static final long serialVersionUID = 1L;

        private transient Row reuse;
        private transient CSVFormat csvFormat;
        private String header;
        private String[] fieldNames;

        public RawCsv2Row(String header, String[] fieldNames) {
            this.header = header;
            this.fieldNames = fieldNames;
        }

        @Override
        public void open(org.apache.flink.configuration.Configuration parameters) throws Exception {
            csvFormat = CSVFormat
                    .newFormat(',')
                    .withQuote('"')
                    .withQuoteMode(QuoteMode.MINIMAL)
                    .withNullString("")
                    .withHeader(fieldNames).withIgnoreSurroundingSpaces(true);
            this.reuse = new Row(fieldNames.length);
        }

        @Override
        public void flatMap(String line, Collector<Row> out) throws Exception {
            // skip header
            if (line.equals(header)) {
                return;
            }
            // subjects.clear();
            List<CSVRecord> rowList;
            try {
                rowList = CSVParser.parse(line, csvFormat).getRecords();
            } catch (IOException ex) {
                throw new IOException("Invalid line: " + line, ex);
            }
            for (CSVRecord row : rowList) {
                if (row.size() != fieldNames.length) {
                    throw new IOException("Invalid line lenght (current: " + row.size() + ", expected: " + fieldNames.length
                            + ") for line " + line);
                }
                for (int i = 0; i < fieldNames.length; i++) {
                    reuse.setField(i, row.get(i));
                }
                out.collect(reuse);
            }
        }
    }
}
