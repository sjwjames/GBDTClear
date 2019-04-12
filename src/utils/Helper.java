package utils;

import struct.DiscreteFeatureData;
import struct.InputData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Helper {
	public static final String MSE = "MSE";
	public static final String MAE = "MAE";
	public static final int MIN_ELEMENT_IN_LEAF = 3;

	//read csv file and load the data
	public static List<InputData> readInputFromCSVFile(String fileName, String targetColumnName) {
		List<InputData> inputData = new LinkedList<>();
		List<String> keys = new ArrayList<>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
			String line;
			int count = 0;
			while ((line = bufferedReader.readLine()) != null) {
				String[] values = line.split(",");
				if (count == 0) {
					keys.addAll(Arrays.asList(values));
				} else {
					Map<String, String> features = new HashMap<>();
					for (int i = 0; i < values.length; i++) {
						String key = keys.get(i);
						String value = values[i];
						features.put(key, value);
					}
					inputData.add(new DiscreteFeatureData(features, targetColumnName));
				}
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputData;
	}

	public static List<Double> getContinuousTargetValues(List<InputData> inputDataList) {
		List<Double> targetValues = new LinkedList<>();
		for (InputData item : inputDataList) {
			targetValues.add(Double.parseDouble(item.getData().get(item.getTargetColumnName())));
		}
		return targetValues;
	}

	public static List<String> getColumnValues(List<InputData> inputDataList, String columnName) {
		List<String> targetValues = new LinkedList<>();
		for (InputData item : inputDataList) {
			targetValues.add(item.getData().get(columnName));
		}
		return targetValues;
	}

	public static double computeMean(List<Double> list) {
		double sum = 0;
		for (double item : list) {
			sum += item;
		}
		return sum / list.size();
	}

	public static double computeMedian(List<Double> list) {
		List<Double> tempList = new LinkedList<>(list);
		Collections.sort(tempList);
		int length = tempList.size();
		if (length % 2 == 0) {
			return (list.get(length / 2 - 1) + list.get(length / 2)) / 2;
		} else {
			return list.get((length - 1) / 2);
		}
	}
}
