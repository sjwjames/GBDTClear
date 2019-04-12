package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GBDTModel {
	private List<RegressionTreeModel> treeModels= null;
	private List<Map<String,Double>> lambdas = new LinkedList<>();
	private double initialGuess;
	public GBDTModel(){

	}
	public GBDTModel(List<RegressionTreeModel> treeModels,List<Map<String,Double>> lambdas,double initialGuess){
		this.treeModels = treeModels;
		this.lambdas = lambdas;
		this.initialGuess = initialGuess;
	}

	public double getInitialGuess() {
		return initialGuess;
	}

	public void setInitialGuess(double initialGuess) {
		this.initialGuess = initialGuess;
	}

	public List<Map<String,Double>> getLambdas() {
		return lambdas;
	}

	public void setLambdas(List<Map<String,Double>> lambdas) {
		this.lambdas = lambdas;
	}

	public List<RegressionTreeModel> getTreeModels() {
		return treeModels;
	}

	public void setTreeModels(List<RegressionTreeModel> treeModels) {
		this.treeModels = treeModels;
	}


}
