
package org.hispindia.emailservice.pojo;

/**
 *
 * @author Mithilesh Thakur
 */
public class SqlViewResponseHeader {

        private String name;
        private String column;
        private String type;
        private Boolean hidden;
        private Boolean meta;

        public SqlViewResponseHeader() {

        }

        public String getName() {
                return name;
        }
        public void setName(String name) {
                this.name = name;
        }
        public String getColumn() {
                return column;
        }
        public void setColumn(String column) {
                this.column = column;
        }
        public String getType() {
                return type;
        }
        public void setType(String type) {
                this.type = type;
        }
        public Boolean getHidden() {
                return hidden;
        }
        public void setHidden(Boolean hidden) {
                this.hidden = hidden;
        }
        public Boolean getMeta() {
                return meta;
        }
        public void setMeta(Boolean meta) {
                this.meta = meta;
        }

        @Override
        public String toString() {
                return "SqlViewResponseHeader [name=" + name + ", column=" + column + ", type=" + type + ", hidden=" + hidden
                                + ", meta=" + meta + "]";
        }
    
}
