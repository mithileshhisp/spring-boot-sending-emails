
package org.hispindia.emailservice.pojo;

import java.util.List;

/**
 *
 * @author Mithilesh Thakur
 */
public class Events {
        private String event;
	private String programStage;
        private String eventDate;
	private List<DataValues> dataValues;

	public Events() {

	}

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public String getProgramStage() {
            return programStage;
        }

        public void setProgramStage(String programStage) {
            this.programStage = programStage;
        }

        public String getEventDate() {
            return eventDate;
        }

        public void setEventDate(String eventDate) {
            this.eventDate = eventDate;
        }

        public List<DataValues> getDataValues() {
            return dataValues;
        }

        public void setDataValues(List<DataValues> dataValues) {
            this.dataValues = dataValues;
        }

        @Override
        public String toString() {
                return "Events [event=" + event + ", programStage=" + programStage + ", eventDate=" + eventDate + ", dataValues=" + dataValues + "]";
        }
}
