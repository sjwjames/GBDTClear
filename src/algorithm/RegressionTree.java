package algorithm;

import model.RegressionTreeModel;
import struct.InputData;
import struct.TreeNode;
import struct.TreeRule;
import utils.Helper;

import java.util.*;


public class RegressionTree {
	private Map<String, List<InputData>> splitDataByFeature(String feature, List<InputData> data, boolean removeFeature) {
		List<String> columnValues = Helper.getColumnValues(data, feature);
		Map<String, List<InputData>> splittedData = new HashMap<>();
		for (int i = 0; i < columnValues.size(); i++) {
			String value = columnValues.get(i);
			InputData dataItem = data.get(i);
			if (removeFeature) {
				dataItem.getData().remove(feature);
			}
			if (splittedData.containsKey(value)) {
				List<InputData> dataList = splittedData.get(value);
				dataList.add(dataItem);
			} else {
				splittedData.put(value, new LinkedList<>() {{
					add(dataItem);
				}});
			}
		}
		return splittedData;
	}

	private double computeLoss(String feature, List<InputData> data, String lossFunction) {
		Map<String, List<InputData>> splittedData = splitDataByFeature(feature, data, false);
		double loss = 0.0;
		for (String key : splittedData.keySet()) {
			List<Double> values = Helper.getContinuousTargetValues(splittedData.get(key));
			double mean = Helper.computeMean(values);
			double error = 0.0;
			for (double value : values) {
				error += Math.pow(value - mean, 2);
			}
			loss += (splittedData.size() / data.size() * (error / splittedData.size()));

		}
		return loss;
	}

	private String getBestSplit(List<String> features, List<InputData> data, String lossFunction) {
		String minimumFeature = null;
		double minimumLoss = Double.MAX_VALUE;
		for (String feature : features) {
			double loss = computeLoss(feature, data, lossFunction);
			if (minimumLoss > loss) {
				minimumLoss = loss;
				minimumFeature = feature;
			}
		}
		return minimumFeature;
	}

	private boolean allTheSameInTarget(List<Double> targetValues) {
		boolean same = true;
		Set<Double> set = new HashSet<>();
		for (int i = 0; i < targetValues.size(); i++) {
			double value = targetValues.get(i);
			if (!set.contains(value)) {
				if (i == 0) {
					set.add(value);
				} else {
					same = false;
				}
			}
		}
		return same;
	}

	private TreeNode train(List<InputData> data, int minimumElements, String lossFunction) {
		if (data.size() == 0) {
			return null;
		} else {
			List<Double> targetValues = Helper.getContinuousTargetValues(data);
			List<String> features = data.get(0).getFeatureKeys();
			boolean allSame = allTheSameInTarget(targetValues);
			if (allSame) {
				return new TreeNode(features.get(0), targetValues.get(0), true, null);
			} else {
				if (data.size() < minimumElements) {
					return new TreeNode(features.get(0), Helper.computeMean(targetValues), true, null);
				} else if (features.size() == 1) {
					return new TreeNode(features.get(0), Helper.computeMean(targetValues), true, null);
				} else {
					String bestSplitFeature = getBestSplit(features, data, lossFunction);
					Map<String, List<InputData>> splittedData = splitDataByFeature(bestSplitFeature, data, true);
					Map<String, TreeNode> children = new HashMap<>();
					for (String key : splittedData.keySet()) {
						List<InputData> dataList = splittedData.get(key);
						children.put(key, train(dataList, minimumElements, lossFunction));
					}
					return new TreeNode(bestSplitFeature, null, false, children);

				}
			}
		}
	}

	public RegressionTreeModel trainModel(List<InputData> data, int minimumElements, String lossFunction) {
		TreeNode root = train(data, minimumElements, lossFunction);
		RegressionTreeModel regressionTreeModel = new RegressionTreeModel(root);
		Map<String, List<Double>> rules = new HashMap<>();
		for (InputData dataItem : data) {
			String rule = regressionTreeModel.getRule(dataItem);
			double targetValue = Double.parseDouble(dataItem.getData().get(dataItem.getTargetColumnName()));
			if(rules.containsKey(rule)){
				rules.get(rule).add(targetValue);
			}else{
				rules.put(rule,new LinkedList<>(){{
					add(targetValue);
				}});
			}
		}
		regressionTreeModel.setRules(rules);
		return regressionTreeModel;
	}

	public double evaluate(RegressionTreeModel model, InputData inputData) {
		TreeNode node = model.getRoot();
		while (!node.isLeaf()) {
			String feature = node.getFeature();
			String value = inputData.getData().get(feature);
			node = node.getChildren().get(value);
		}
		return Double.parseDouble(node.getValue().toString());
	}
}
