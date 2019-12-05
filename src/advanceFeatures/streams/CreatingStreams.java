package advanceFeatures.streams;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 产生一个流的各种方式
 */
public class CreatingStreams {
    //定义了一个只打印Stream中前10个元素的函数
    public static <T> void show(String title, Stream<T> stream){
        final int SIZE = 10;
        List<T> firstElements = stream.limit(SIZE+1)
                .collect(Collectors.toList());
        System.out.println(title + ":");
        for (int i = 0; i<firstElements.size();i++){
            if (i>0) System.out.print(",");
            if (i<SIZE) System.out.print(firstElements.get(i));
            else System.out.print("...");
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        //先将文件内容转成String
        Path path = Paths.get("github_my.txt");
        String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        //生成Stream
        Stream<String> words = Stream.of(content.split("\\PL+"));
        show("words",words );

        Stream<String> lovers = Stream.of("liuchen","yangxiao","daidinan","zhongyucui");
        show("lovers",lovers );
        //无限流
        Stream<String> echos = Stream.generate(()->"Echos");
        show("echos",echos );

        Stream<Double> randoms = Stream.generate(Math::random);
        show("randoms",randoms );

        Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE,n->n.add(BigInteger.ONE));
        show("integers",integers );
        //Pattern类产生流
        Stream<String> wordsAnotherWay = Pattern.compile("\\PL+").splitAsStream(content);
        show("wordsAnotherWay",wordsAnotherWay );
        //Files方法产生流
        try (Stream<String> lines = Files.lines(path,StandardCharsets.UTF_8)){
            show("lines",lines);
        }

    }
}
