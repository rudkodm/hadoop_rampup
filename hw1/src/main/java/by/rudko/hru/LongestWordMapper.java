package by.rudko.hru;

import by.rudko.hru.utils.TextUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

class LongestWordMapper extends Mapper<LongWritable, Text, IntWritable, Text> {
    private static final Logger logger = LoggerFactory.getLogger(LongestWordMapper.class);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        logger.info("Mapper IN: key: {}, value: {}", key, value);
        List<String> words = TextUtils.wordsInText(value.toString());
        for (String word : words) {
            context.write(new IntWritable(word.length()), new Text(word));
        }
    }
}
