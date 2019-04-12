package struct;

import java.util.List;
import java.util.Map;

public class DiscreteFeatureData implements InputData{
	private Map<String,String> data;
	private String targetColumnName;
	public DiscreteFeatureData(Map<String,String> data,String targetColumnName){
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
		return null;
	}
}
