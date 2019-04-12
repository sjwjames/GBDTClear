package model;

import struct.InputData;
import struct.TreeNode;
import struct.TreeRule;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RegressionTreeModel {
	private TreeNode root;
	private Map<String,List<Double>> rules;

	public RegressionTreeModel(TreeNode root) {
		this.root = root;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public Map<String, List<Double>> getRules() {
		return rules;
	}

	public void setRules(Map<String, List<Double>> rules) {
		this.rules = rules;
	}

	public String getRule(InputData data) {
		StringBuilder ruleString = new StringBuilder();
		TreeNode node = this.root;
		while (node != null && !node.isLeaf()) {
			String feature = node.getFeature();
			String featureValue = data.getData().get(feature);
			ruleString.append(feature).append("=").append(featureValue).append(",");
			node = node.getChildren().get(featureValue);
		}
		return ruleString.toString();
	}
}
