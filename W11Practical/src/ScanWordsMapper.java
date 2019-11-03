

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.util.List;
import java.util.Scanner;

public class ScanWordsMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	// The output of the mapper is a map from words (including duplicates) to the value 1.

	public void map(LongWritable key, Text value, Context output) throws IOException, InterruptedException {

		// The key is the character offset within the file of the start of the line, ignored.
		// The value is a line from the file.
		ReadJson readJson = new ReadJson();

		String line = value.toString();
//		Scanner scanner = new Scanner(line);

		List<String> urlList = readJson.read(line);

		if (!urlList.isEmpty()) {
			for(int i = 0; i < urlList.size(); i++) {
				String word = urlList.get(i);
				output.write(new Text(word), new LongWritable(1));
			}
		}
//		scanner.close();
	}
}
