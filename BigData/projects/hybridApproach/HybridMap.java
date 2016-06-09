package hybridApproach;

import java.io.IOException;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class HybridMap extends Mapper<Object, Text, KeyPair, IntWritable> {
	private MapWritable mapper = new MapWritable();

	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] words = value.toString().split("\\s+");
		// System.out.println("I am here " + words);

		for (int i = 0; i < words.length; i++) {
			for (int j = i + 1; j < words.length; j++) {
				if (words[i].trim().equals(words[j])) {
					break;
				} else {
					KeyPair mappedPair = new KeyPair(new Text(words[i]),
							new Text(words[j]));

					if (mapper.containsKey(mappedPair)) {
						IntWritable pair = (IntWritable) mapper.get(mappedPair);
						pair.set(pair.get() + 1);

					} else {
						mapper.put(mappedPair, new IntWritable(1));
					}

					KeyPair aggPair = new KeyPair(new Text(words[i]), new Text(
							"*"));
					if (mapper.containsKey(aggPair)) {
						IntWritable countPair = (IntWritable) mapper
								.get(aggPair);
						countPair.set(countPair.get() + 1);

					} else {
						mapper.put(aggPair, new IntWritable(1));
					}
				}
			}
		}

	}

	@Override
	protected void cleanup(Context context) throws IOException,
			InterruptedException {

		Set<Writable> pairs = mapper.keySet();
		for (Writable pair : pairs) {
			System.out.println(pair + " " + mapper.get(pair));
			context.write((KeyPair) pair, (IntWritable) mapper.get(pair));
		}
	}

}
