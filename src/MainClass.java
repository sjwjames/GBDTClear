import struct.InputData;
import utils.Helper;

import java.util.List;
import java.util.Map;

public class MainClass {
	public static void main(String[] args) {
		List<InputData> data = Helper.readInputFromCSVFile("test.csv", "Decision");
		for (InputData item : data) {
			Map<String,String> features= item.getData();
			for (String key:features.keySet()) {
				System.out.println(key+" "+features.get(key));
			}
		}
	}
}
