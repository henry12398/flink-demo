import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * 批处理的word count
 *
 * @author wxg
 */
public class BatchWordCount {

    public static void main(String[] args) throws Exception {
        //获取Flink批处理执行环境
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();

        //从文件中获取数据源
        final String fileName = "C:\\Users\\chenghaochen\\source\\workspace\\flink_demo\\src\\main\\resources\\word-count.txt";
        DataSource<String> dataSource = environment.readTextFile(fileName);

        dataSource
                //将一行句子按照空格拆分,输入一个字符串,输出一个2元组,key为一个单词,value为1
                .flatMap(new WordFlatMapFunction())
                //聚合算子,按照第一个字段(即word字段)进行分组
                .groupBy(0)
                //聚合算子,对每一个分租内的数据按照第二个字段进行求和
                .sum(1)
                //打印结果到控制台
                .print();
    }

}
