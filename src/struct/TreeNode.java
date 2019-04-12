package struct;

import java.util.Map;

public class TreeNode {
	private String feature;
	private Object value;
	private boolean isLeaf;
	private Map<String, TreeNode> children;

	public TreeNode(String feature, Object value, boolean isLeaf, Map<String, TreeNode> children) {
		this.feature = feature;
		this.value = value;
		this.isLeaf = isLeaf;
		this.children = children;
	}

	public Map<String, TreeNode> getChildren() {
		return children;
	}

	public void setChildren(Map<String, TreeNode> children) {
		this.children = children;
	}

	public Object getValue() {
		return value;
	}

	public String getFeature() {
		return feature;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public void setLeaf(boolean leaf) {
		isLeaf = leaf;
	}
}
