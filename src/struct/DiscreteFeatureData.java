package struct;

import java.util.*;

public class DiscreteFeatureData implements InputData, Cloneable {
	private Map<String, String> data;
	private String targetColumnName;

	public DiscreteFeatureData(Map<String, String> data, String targetColumnName) {
		this.data = data;
		this.targetColumnName = targetColumnName;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public Map<String, String> getData() {
		return data;
	}

	public String getTargetColumnName() {
		return targetColumnName;
	}

	public void setTargetColumnName(String targetColumnName) {
		this.targetColumnName = targetColumnName;
	}

	@Override
	public List<String> getFeatureKeys() {
		List<String> featureKeys = new LinkedList<>();
		for (String key : data.keySet()) {
			if (!key.equals(this.targetColumnName)) {
				featureKeys.add(key);
			}
		}
		return featureKeys;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		InputData clone = null;
		try {
			clone = (InputData) super.clone();

			//Copy new date object to cloned method
			Map<String, String> data = this.getData();
			Map<String, String> dataCloned = new HashMap<>();
			for (String key : data.keySet()) {
				dataCloned.put(new String(key), new String(data.get(key)));
			}
			clone.setData(dataCloned);
			clone.setTargetColumnName(new String(this.getTargetColumnName()));
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		return clone;
	}
}
