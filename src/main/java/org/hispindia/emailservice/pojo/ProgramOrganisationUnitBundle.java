
package org.hispindia.emailservice.pojo;

import java.util.List;

/**
 *
 * @author Mithilesh Thakur
 */
public class ProgramOrganisationUnitBundle {

    private List<ProgramOrganisationUnit> organisationUnits;

    public ProgramOrganisationUnitBundle() {

    }

    /**
     * @return the organisationUnits
     */

    public List<ProgramOrganisationUnit> getOrganisationUnits() {
        return organisationUnits;
    }
    /**
     * @param organisationUnits the organisationUnits to set
     */

    
    public void setOrganisationUnits(List<ProgramOrganisationUnit> organisationUnits) {
        this.organisationUnits = organisationUnits;
    }


    @Override
    public String toString() {
            return "ProgramOrganisationUnitBundle [organisationUnits=" + organisationUnits + "]";
    }
}
