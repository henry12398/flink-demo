import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 从socket中实时接收数据的word count
 *
 * @author wxg
 */
public class SocketStreamWordCount {
    public static void main(String[] args) throws Exception {
        //获取Flink批处理执行环境
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        final String host = "localhost";
        final int port = 8000;
        //从socket中获取数据源
        DataStreamSource<String> dataSource = environment.socketTextStream(host, port);
        //单词计数
        dataSource
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

