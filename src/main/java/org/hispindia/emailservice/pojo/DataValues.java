
package org.hispindia.emailservice.pojo;

/**
 *
 * @author Mithilesh Thakur
 */
public class DataValues {
	private String value;
	private String dataElement;
	
	public DataValues() {
		
	}

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
	
	public String getDataElement() {
		return dataElement;
	}
	public void setDataElement(String dataElement) {
		this.dataElement = dataElement;
	}

	@Override
	public String toString() {
            return "DataValues [value=" + value + ", dataElement=" + dataElement + "]";
	}
}
