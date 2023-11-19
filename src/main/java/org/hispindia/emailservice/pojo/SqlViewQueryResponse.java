
package org.hispindia.emailservice.pojo;

/**
 *
 * @author Mithilesh Thakur
 */
public class SqlViewQueryResponse {

        private ListGrid listGrid;

        public SqlViewQueryResponse() {

        }

        public ListGrid getListGrid() {
            return listGrid;
        }

        public void setListGrid(ListGrid listGrid) {
            this.listGrid = listGrid;
        }

        @Override
        public String toString() {
                return "SqlViewQueryResponse [listGrid=" + listGrid + "]";
        }

}
