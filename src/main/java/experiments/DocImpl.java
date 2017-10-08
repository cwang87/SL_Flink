package experiments;

import org.apache.flink.api.java.ExecutionEnvironment;


/**
 * This class represents a document that provides access to the data as well as the metadata.
 *
 * A Json serialized document object is shown below:
 * {
 *      {
 *          "data": {
 *              "field": "foo",
 *          },
 *          "meta_data": {
 *              "global":{
 *                  "doc_id" : 1
 *              },
 *              "parse_pipeline.parser": {
 *                  "parser_type":"JSON"
 *              }
 *          }
 *      }
 * }
 *
 */

public class DocImpl {

    public static void main(String[] args) {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //用JSON parser (Jackson?)生成json object/POJO
        //1. data
        //2. meta_data

        //continue with POJOImpl code

    }
}
