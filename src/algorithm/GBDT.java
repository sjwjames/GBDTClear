package algorithm;

import model.GBDTModel;
import model.RegressionTreeModel;
import struct.InputData;
import struct.TreeNode;
import struct.TreeRule;
import utils.Helper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GBDT {
	//right now only support discrete features.
	public double evaluate(InputData data,GBDTModel model) {
		List<RegressionTreeModel> treeModels = model.getTreeModels();
		List<Map<String, Double>> lambdaList = model.getLambdas();
		double lastRoundResult = model.getInitialGuess();
		for (RegressionTreeModel treeModel:treeModels) {
			int index= treeModels.indexOf(treeModel);
			Map<String, Double> ruleLambda = lambdaList.get(index);
			String rule = treeModel.getRule(data);
			double lambda = ruleLambda.get(rule);
			lastRoundResult += lambda;
		}
		return lastRoundResult;
	}

	public GBDTModel trainModel(List<InputData> data, int numberOfTrees, String lossFunction) {
		List<Double> targetValues = Helper.getContinuousTargetValues(data);
		GBDTModel model = new GBDTModel();
		RegressionTree regressionTree = new RegressionTree();
		List<RegressionTreeModel> treeModels = new LinkedList<>();
		List<Map<String, Double>> lambdaList = new LinkedList<>();
		if (lossFunction.equals(Helper.MAE)) {
			double initialMedian = Helper.computeMedian(targetValues);
			model.setInitialGuess(initialMedian);
			List<Double> lastRound = new LinkedList<>(targetValues);
			for (int i = 0; i < lastRound.size(); i++) {
				lastRound.set(i, initialMedian);
			}
			for (int i = 0; i < numberOfTrees; i++) {
				List<Double> residuals = new LinkedList<>();
				List<Double> yTilde = new LinkedList<>();
				for (int j = 0; j < lastRound.size(); j++) {
					double residual = targetValues.get(j) - lastRound.get(j);
					residuals.add(residual);
					yTilde.add(residual >= 0 ? 1.0 : -1.0);
				}
				for (InputData dataItem : data) {
					int index = data.indexOf(dataItem);
					String targetName = dataItem.getTargetColumnName();
					dataItem.getData().put(targetName, yTilde.get(index).toString());
				}
				RegressionTreeModel regressionTreeModel = regressionTree.trainModel(data, Helper.MIN_ELEMENT_IN_LEAF, Helper.MSE);
				Map<String, List<Double>> residualsInLeaf = new HashMap<>();
				for (InputData dataItem : data) {
					String rule = regressionTreeModel.getRule(dataItem);
					int index = data.indexOf(dataItem);
					if (residualsInLeaf.containsKey(rule)) {
						residualsInLeaf.get(rule).add(residuals.get(index));
					} else {
						residualsInLeaf.put(rule, new LinkedList<>() {{
							add(residuals.get(index));
						}});
					}
				}
				Map<String, Double> lambdas = new HashMap<>();
				for (String ruleKey : residualsInLeaf.keySet()) {
					lambdas.put(ruleKey, Helper.computeMedian(residualsInLeaf.get(ruleKey)));
				}
				for (InputData dataItem : data) {
					String rule = regressionTreeModel.getRule(dataItem);
					int index = data.indexOf(dataItem);
					double lastRoundValue = lastRound.get(index);
					lastRound.set(index, lastRoundValue + lambdas.get(rule));
				}

				treeModels.add(regressionTreeModel);
				lambdaList.add(lambdas);
			}
			model.setTreeModels(treeModels);
			model.setLambdas(lambdaList);
		}
		return model;
	}
}
