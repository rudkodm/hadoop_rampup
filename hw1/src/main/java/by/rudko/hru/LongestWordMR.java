package by.rudko.hru;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.*;

import java.util.Enumeration;

public class LongestWordMR {

    public static void main(String...args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Larges Word");

        job.setJarByClass(LongestWordMR.class);
        job.setMapperClass(LongestWordMapper.class);
        job.setCombinerClass(LongestWordReducer.class);
        job.setReducerClass(LongestWordReducer.class);
        job.setSortComparatorClass(DescComparator.class);

        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    static class DescComparator extends WritableComparator {
        private static final IntWritable.Comparator ascComparator = new IntWritable.Comparator();

        @Override
        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
            int comparison = ascComparator.compare(b1, s1, l1, b2, s2, l2);
            return (comparison > 0) ? -1 : (comparison == 0) ? 0 : 1;
        }
    }
}


