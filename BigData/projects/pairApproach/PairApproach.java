package pairApproach;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PairApproach {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		Job job = new Job(conf, "pairApproach");
		job.setJarByClass(PairApproach.class);
		
		FileSystem fs = FileSystem.get(conf);
		fs.delete(new Path("/home/cloudera/workspace/BigData_RelativeFrequency/output/pairoutput"));

		job.setMapperClass(Map.class);
		job.setReducerClass(PairReducer.class);

		job.setMapOutputKeyClass(KeyPair.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(KeyPair.class);
		job.setOutputValueClass(DoubleWritable.class);
		
//		job.setInputFormatClass(TextInputFormat.class);
//		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(
				"/home/cloudera/workspace/BigData_RelativeFrequency/input/numbercount.txt"));
		FileOutputFormat.setOutputPath(job, new Path(
				"/home/cloudera/workspace/BigData_RelativeFrequency/output/pairoutput"));
//		 FileInputFormat.addInputPath(job, new Path(args[0]));
//		 FileOutputFormat.setOutputPath(job, new Path(args[1]));

		
		job.waitForCompletion(true);
	}

}
