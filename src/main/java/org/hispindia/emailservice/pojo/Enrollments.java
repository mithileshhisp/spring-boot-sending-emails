
package org.hispindia.emailservice.pojo;

import java.util.List;

/**
 *
 * @author Mithilesh Thakur
 */
public class Enrollments {
    	private String orgUnitName;
	private List<Events> events;

	public Enrollments() {

	}

        public String getOrgUnitName() {
            return orgUnitName;
        }

        public void setOrgUnitName(String orgUnitName) {
            this.orgUnitName = orgUnitName;
        }

        public List<Events> getEvents() {
            return events;
        }

        public void setEvents(List<Events> events) {
            this.events = events;
        }

        @Override
        public String toString() {
                return "Enrollments [orgUnitName=" + orgUnitName + ", events=" + events + "]";
        }
}
