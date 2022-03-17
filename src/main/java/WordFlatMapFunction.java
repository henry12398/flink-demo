import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class WordFlatMapFunction implements FlatMapFunction<String, Tuple2<String, Integer>>{

    @Override
    public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
        //对读取到的每一行数据按照空格分割
        String[] split = s.split(" ");
        //将每个单词放入collector中作为输出,格式类似于{word:1}
        for (String word : split) {
            collector.collect(new Tuple2<String, Integer>(word, 1));
        }
    }
}
