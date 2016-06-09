package stripeApproach;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class stripeApproach {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		Job job = new Job(conf, "stripeApproach");
		job.setJarByClass(stripeApproach.class);
		
		FileSystem fs = FileSystem.get(conf);
		fs.delete(new Path("/home/cloudera/workspace/BigData_RelativeFrequency/output/stripeoutput"));

		job.setMapperClass(StripeMap.class);
		job.setReducerClass(StripeReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MapWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(StripeWriteable.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(
				"/home/cloudera/workspace/BigData_RelativeFrequency/input/numbercount.txt"));
		FileOutputFormat.setOutputPath(job, new Path(
				"/home/cloudera/workspace/BigData_RelativeFrequency/output/stripeoutput"));
/*		 FileInputFormat.addInputPath(job, new Path(args[0]));
		 FileOutputFormat.setOutputPath(job, new Path(args[1]));
*/
		
		job.waitForCompletion(true);
	}

}
