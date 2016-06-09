package stripeApproach;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

import stripeApproach.KeyPair;

public class StripeMap extends Mapper<Object, Text, Text, MapWritable> {
	private MapWritable mapper = new MapWritable();


	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] words = value.toString().split("\\s+");

		for (int i = 0; i < words.length; i++) {
			
			MapWritable mapperNeighbour = (MapWritable) (mapper.get(new Text(words[i].trim()
					.toLowerCase())) == null ? new MapWritable() : mapper.get(new Text(words[i].trim().toLowerCase())));
			
			for (int j = i + 1; j < words.length && !(words[i].trim().toLowerCase().equals(words[j].trim()
							.toLowerCase())); j++) {
		     			
					IntWritable val = (IntWritable) mapperNeighbour.get(new Text(words[j].trim().toLowerCase()));
					if(val == null){
						val = new IntWritable(1);
					}else{
						val.set((val.get()+1));	
					}
					mapperNeighbour.put(new Text(words[j].trim().toLowerCase()), val);
				}
			if(mapperNeighbour.size()>0)
			mapper.put(new Text(words[i]), mapperNeighbour)	;
		}
	}

	@Override
	protected void cleanup(Context context) throws IOException,
			InterruptedException {

		for (Entry<Writable,Writable> pair : mapper.entrySet()) {
			Text key = (Text) pair.getKey();
			MapWritable value = (MapWritable) pair.getValue();
//			System.out.println(key + " " + value);
			context.write( key, value);
		}
	}
}
