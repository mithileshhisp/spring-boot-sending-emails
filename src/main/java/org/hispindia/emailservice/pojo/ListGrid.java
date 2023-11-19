/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.hispindia.emailservice.pojo;

import java.util.List;

/**
 *
 * @author Mithilesh Thakur
 */
public class ListGrid {
        //private String listGrid;
        //private SqlViewResponse sqlViewResponse;

        private String title;
    	private List<SqlViewResponseHeader> headers;
	private Integer width;
	private Integer height;
	private List<List<String>> rows;


    	public ListGrid() {
		
	}

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<SqlViewResponseHeader> getHeaders() {
            return headers;
        }

        public void setHeaders(List<SqlViewResponseHeader> headers) {
            this.headers = headers;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public List<List<String>> getRows() {
            return rows;
        }

        public void setRows(List<List<String>> rows) {
            this.rows = rows;
        }

/*
        public SqlViewResponse getSqlViewResponse() {
            return sqlViewResponse;
        }

        public void setSqlViewResponse(SqlViewResponse sqlViewResponse) {
            this.sqlViewResponse = sqlViewResponse;
        }
*/

	@Override
	public String toString() {
		return "{ \"title\":\"" + title + "\" , \"headers\":\"" + headers + "\" , \"width\":\"" + width + "\" , \"height\":\"" + height + "\""
                            + ", \"rows\":\"" + rows + "\"}";
	}
}
