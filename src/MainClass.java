import algorithm.GBDT;
import algorithm.RegressionTree;
import model.GBDTModel;
import struct.DiscreteFeatureData;
import struct.InputData;
import utils.Helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainClass {
	public static void main(String[] args) {
		List<InputData> data = Helper.readInputFromCSVFile("test.csv", "Decision");
		GBDT gbdt = new GBDT();
		GBDTModel model = gbdt.trainModel(data,5,Helper.MAE,Helper.MIN_ELEMENT_IN_LEAF);
		Map<String,String> featureMap = new HashMap<>(){{
			put("Outlook","Rain");
			put("Wind","Strong");
			put("Humidity","Normal");
		}};
		double result = gbdt.evaluate(new DiscreteFeatureData(featureMap,""),model);
		System.out.println(result);
	}
}
