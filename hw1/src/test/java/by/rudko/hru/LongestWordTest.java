package by.rudko.hru;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LongestWordTest {
    private MapDriver<LongWritable, Text, IntWritable, Text> mapDriver;
    private ReduceDriver<IntWritable, Text, IntWritable, Text> reduceDriver;
    private MapReduceDriver mapReduceDriver;

    @Before
    public void setUp() throws Exception {
        LongestWordMapper mapper = new LongestWordMapper();
        LongestWordReducer reducer = new LongestWordReducer();

        mapDriver = MapDriver.newMapDriver(mapper);
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
    }

    @Test
    public void mapper_ifSingleWord_shouldBeOneOutput() throws Exception {
        mapDriver.withInput(something(), new Text("word"));

        mapDriver.withOutput(new IntWritable(4), new Text("word"));

        mapDriver.runTest();
    }

    @Test
    public void mapper_ifTwoWord_shouldBeTwoOutputs() throws Exception {
        mapDriver.withInput(something(), new Text("wordA wordB"));

        mapDriver.withOutput(new IntWritable(5), new Text("wordA"));
        mapDriver.withOutput(new IntWritable(5), new Text("wordB"));

        mapDriver.runTest();
    }

    @Test
    public void mapper_ifTwoSameWords_shouldBeTwoOutputs() throws Exception {
        mapDriver.withInput(something(), new Text("word word"));

        mapDriver
                .withOutput(new IntWritable(4), new Text("word"))
                .withOutput(new IntWritable(4), new Text("word"));

        mapDriver.runTest();
    }

    @Test
    public void mapper_outKey_shouldMatchWordSize() throws Exception {
        mapDriver.withInput(something(), new Text("abcdefg"));

        mapDriver.withOutput(new IntWritable(7), new Text("abcdefg"));

        mapDriver.runTest();
    }




    @Test
    public void reducer_forOneWord_shouldReturnIt() throws Exception {
        reduceDriver.withInput(reducerInputPair(4, "word"));

        reduceDriver.withOutput(new IntWritable(4), new Text("word"));

        reduceDriver.runTest();
    }

    @Test
    public void reducer_forTwoWordsInInput_shouldReturnThemSeparately() throws Exception {
        reduceDriver.withInput(reducerInputPair(5, "wordA", "wordB"));

        reduceDriver
                .withOutput(new IntWritable(5), new Text("wordA"))
                .withOutput(new IntWritable(5), new Text("wordB"));

        reduceDriver.runTest();
    }

    @Test
    public void reducer_forTwoInputs_shouldReturnBiggerOne() throws Exception {
        reduceDriver
                .withInput(reducerInputPair(4, "word"))
                .withInput(reducerInputPair(5, "wordA"));

        reduceDriver.withOutput(new IntWritable(5), new Text("wordA"));

        reduceDriver.runTest();
    }


    private LongWritable something() {
      return new LongWritable(0);
    };

    private Pair<IntWritable, List<Text>> reducerInputPair(int i, String...str) {
        List<Text> words = new ArrayList<>();
        for (String s : str) {
            words.add(new Text(s));
        }
        return new Pair<>(new IntWritable(i), words);
    }
}