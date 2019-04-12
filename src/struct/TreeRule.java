package struct;

import java.util.List;
import java.util.Map;

public class TreeRule {
	private List<Map<String,String>> rule;

	public TreeRule(List<Map<String,String>> rule) {
		this.rule = rule;
	}

	public List<Map<String, String>> getRule() {
		return rule;
	}

	public void setRule(List<Map<String, String>> rule) {
		this.rule = rule;
	}
}
