package struct;

import java.util.List;
import java.util.Map;

public interface InputData {
	public void setData(Map<String, String> data);

	public Map<String, String> getData();

	public String getTargetColumnName();

	public void setTargetColumnName(String targetColumnName);

	public List<String> getFeatureKeys();

}
