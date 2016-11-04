package by.rudko.hru.utils;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.util.ArrayList;

public final class CloneWritableUtils {

    private CloneWritableUtils() {
    }

    public static IntWritable cloneInt(IntWritable i) {
        return new IntWritable(i.get());
    }

    public static Text cloneTxt(Text t) {
        return new Text(t.getBytes());
    }

    public static Iterable<Text> cloneIter(Iterable<Text> iterable) {
        ArrayList<Text> result = new ArrayList<>();
        for (Text i : iterable) {
            result.add(cloneTxt(i));
        }
        return result;
    }
}
