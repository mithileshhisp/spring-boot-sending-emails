
package org.hispindia.emailservice.pojo;

/**
 *
 * @author Mithilesh Thakur
 */
public class Attributes {
	private String attribute;
	private String value;

	public Attributes() {
		
	}

	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

	@Override
	public String toString() {
            return "Attributes [value=" + value + ", attribute=" + attribute + "]";
	}
}
