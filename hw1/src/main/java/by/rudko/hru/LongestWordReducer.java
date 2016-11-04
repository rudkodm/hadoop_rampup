package by.rudko.hru;

import static by.rudko.hru.utils.CloneWritableUtils.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

class LongestWordReducer extends Reducer<IntWritable, Text, IntWritable, Text> {
    private static final Logger logger = LoggerFactory.getLogger(LongestWordReducer.class);
    private IntWritable maxSize = new IntWritable(0);
    private Iterable<Text> wordsWithMaxSize = Collections.emptyList();

    @Override
    protected void reduce(IntWritable size, Iterable<Text> words, Context context) throws IOException, InterruptedException {
        logger.info("Reducer IN: size: {} || maxSize: {}", size, maxSize);
        if(maxSize.compareTo(size) <= 0) {
            this.maxSize = cloneInt(size);
            this.wordsWithMaxSize = cloneIter(words);
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        logger.info("CleanUp Reducer");
        this.writeOutput(context);
    }

    private void writeOutput(Context context) throws IOException, InterruptedException {
        logger.info("Write Output");
        for (Text word : wordsWithMaxSize) {
            logger.info("Write Word To Output: {}", word);
            context.write(maxSize, word);
        }
    }


}
