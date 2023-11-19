
package org.hispindia.emailservice.pojo;

import java.util.List;

/**
 *
 * @author Mithilesh Thakur
 */
public class TrackedEntityInstance {

        private String orgUnit;
        private String trackedEntityInstance;
        private List<Attributes> attributes;
        private List<Enrollments> enrollments;

	public TrackedEntityInstance() {

	}

        public String getOrgUnit() {
            return orgUnit;
        }

        public void setOrgUnit(String orgUnit) {
            this.orgUnit = orgUnit;
        }

        public String getTrackedEntityInstance() {
            return trackedEntityInstance;
        }

        public void setTrackedEntityInstance(String trackedEntityInstance) {
            this.trackedEntityInstance = trackedEntityInstance;
        }

        public List<Attributes> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<Attributes> attributes) {
            this.attributes = attributes;
        }

        public List<Enrollments> getEnrollments() {
            return enrollments;
        }

        public void setEnrollments(List<Enrollments> enrollments) {
            this.enrollments = enrollments;
        }


        @Override
        public String toString() {
                return "TrackedEntityInstance [orgUnit=" + orgUnit + ", trackedEntityInstance=" + trackedEntityInstance + 
                       ", attributes=" + attributes + ", enrollments=" + enrollments + "]";
        }
}
