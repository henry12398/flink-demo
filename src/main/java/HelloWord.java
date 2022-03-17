import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.shaded.guava18.com.google.common.collect.Lists;

import java.util.Collections;

public class HelloWord {

    public static void main(String[] args) throws Exception {
        //获取Flink执行环境
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        //从集合中获取数据源
        DataSource<String> dataSource = environment.fromCollection(Lists.newArrayList("Hello Word", "Good morning"));
        //输出到控制台
        dataSource.print();
    }
}
