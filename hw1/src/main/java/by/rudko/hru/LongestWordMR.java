package by.rudko.hru;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LongestWordMR {

    private static final Logger log = LoggerFactory.getLogger(LongestWordMR.class);


    static class LongestWordMapper extends Mapper<Object, Text, IntWritable, Text>{
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            log.debug("Mapper IN: key: {}, value: {}", key, value);
            List<String> words = wordsInText(value.toString());
            for (String word : words) {
                context.write(new IntWritable(word.length()), new Text(word));
            }
        }
    }

    static class LongestWordReducer extends Reducer<IntWritable, Text, IntWritable, Text> {
        private IntWritable maxSize = new IntWritable(0);
        private Iterable<Text> wordsWithMaxSize;

        @Override
        protected void reduce(IntWritable size, Iterable<Text> words, Context context) throws IOException, InterruptedException {
            log.debug("Reducer IN: key: {}", size);
            if(maxSize.compareTo(size) > 0) return;
            maxSize = size;
            wordsWithMaxSize = words;

        }

        @Override
        public void run(Context context) throws IOException, InterruptedException {
            setup(context);
            iterate(context);
            writeOutput(context);
            cleanup(context);
        }

        private void iterate(Context context) throws IOException, InterruptedException {
            while (context.nextKey()){
                this.reduce(context.getCurrentKey(), context.getValues(), context);
            }
        }

        private void writeOutput(Context context) throws IOException, InterruptedException {
            for (Text word : wordsWithMaxSize) {
                context.write(maxSize, word);
            }
        }
    }

    // Util methods
    private static List<String> wordsInText(String s) {
        Pattern p = Pattern.compile("\\w+");
        Matcher m = p.matcher(s);
        List<String> result = new ArrayList<>();
        while(m.find()) {
            result.add(m.group());
        }
        return result;
    }

    // Main
    public static void main(String...args) {

    }
}


