package advanceFeatures.streams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CountLongWords {
    /**
     * 分别用循环和流的方式统计文件中特定字长的个数
     * @param args
     * @throws IOException
     * 结论：流遵循了“做什么而非怎么做”的原则
     * 1、流并不存储元素
     * 2、流的操作不会操作数据源
     * 3、流的操作是尽可能惰性执行的--“并未理解”
     */
    public static void main(String[] args) throws IOException {
        //将文件中的内容输出为String
        String contents = new String(Files.readAllBytes(Paths.get("github_my.txt")));
        //分割String并用list保存每一个字
        List<String> words = Arrays.asList(contents.split("\\PL+"));
        //循环遍历统计
        long count = 0;
        for (String w:words){
            if (w.length() > 9) count++;
        }
        System.out.println(count);
        //使用流的方式间接明了
        count = words.stream().filter(w->w.length()>9).count();
        System.out.println(count);
        //其中filter方法不会从新的流中移除元素，而是会生成一个新的流，其中不包含移除掉的元素
        count = words.parallelStream().filter(w->w.length()>9).count();
        System.out.println(count);
    }
}
