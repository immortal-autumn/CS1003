

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;

public class W11Practical {

	public static void main(String[] args) throws IOException {

		// This produces an output file in which each line contains a separate word followed by
		// the total number of occurrences of that word in all the input files.
		
		if(args.length != 2) {
			System.out.println("Usage: java -cp \"lib/*:bin\" W11Practical <input_path> <output_directory>");
			System.exit(1);
		}

		String input_path = args[0];
		String output_path = args[1];

		//test
//		ReadJson readJson = new ReadJson();
//		readJson.read(input_path);
//		System.exit(1);

		//set output file and delete if it exist:

		File output = new File(output_path);
		if (output.exists()) {
			String[] childFile = output.list();
			if (childFile == null) {
				output.delete();
			}
			else {
				for (String aChildFile : childFile) {
					File file = new File(output_path + "/" + aChildFile);
					file.delete();
				}
				output.delete();
			}
		}
		// Setup new Job and Configuration 
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Word Count");
		
		// Specify input and output paths
		FileInputFormat.setInputPaths(job, new Path(input_path));
		FileOutputFormat.setOutputPath(job, new Path(output_path));

		// Set our own ScanWordsMapper as the mapper
		job.setMapperClass(ScanWordsMapper.class);

		// Specify output types produced by mapper (words with count of 1)
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);

		// The output of the reducer is a map from unique words to their total counts.
		job.setReducerClass(CountWordsReducer.class);

		// Specify the output types produced by reducer (words with total counts)
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		try {
			job.waitForCompletion(true);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}

		//read the output file and print the maximum value
		GetMaximumNumber maximumNumber = new GetMaximumNumber();
//		maximumNumber.read(output_path + "/" + "part-r-00000");
		maximumNumber.re_order(output_path + "/" + "part-r-00000", output_path);
	}

//	public static void secondReducer(String input_path, String output_path) {
//		try {
//			// Setup new Job and Configuration
//			Configuration conf = new Configuration();
//			Job job = Job.getInstance(conf, "wordOrder");
//
//			// Specify input and output paths
//			FileInputFormat.setInputPaths(job, new Path(input_path));
//			FileOutputFormat.setOutputPath(job, new Path(output_path));
//
//			// Set our own ScanWordsMapper as the mapper
//			job.setMapperClass(ScanWordsMapper.class);
//
//			// Specify output types produced by mapper (words with count of 1)
//			job.setMapOutputKeyClass(Text.class);
//			job.setMapOutputValueClass(LongWritable.class);
//
//			// The output of the reducer is a map from unique words to their total counts.
//			job.setReducerClass(CountWordsReducer.class);
//
//			// Specify the output types produced by reducer (words with total counts)
//			job.setOutputKeyClass(Text.class);
//			job.setOutputValueClass(LongWritable.class);
//		}
//		catch (FileNotFoundException e) {
//
//		}
//		catch (IOException e) {
//
//		}
//		}
}
