import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * 流处理的word count
 *
 * @author wxg
 */
public class StreamWordCount {
    public static void main(String[] args) throws Exception {
        //获取Flink批处理执行环境
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        //从文件中获取数据源
        final String fileName = "C:\\Users\\chenghaochen\\source\\workspace\\flink_demo\\src\\main\\resources\\word-count.txt";
        DataStreamSource<String> source = environment.readTextFile(fileName);
        //单词计数
        source
                //将一行句子按照空格拆分,输入一个字符串,输出一个2元组,key为一个单词,value为1
                .flatMap(new WordFlatMapFunction())
                //聚合算子,按照第一个字段(即word字段)进行分组
                .keyBy(v -> v.f0)
                //聚合算子,对每一个分租内的数据按照第二个字段进行求和
                .sum(1)
                .print();

        environment.execute();
    }
}
