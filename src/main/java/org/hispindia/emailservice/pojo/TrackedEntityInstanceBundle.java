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
public class TrackedEntityInstanceBundle {
   private List<TrackedEntityInstance> trackedEntityInstances;

    public TrackedEntityInstanceBundle() {

    }

    /**
     * @return the trackedEntityInstances
     */
    public List<TrackedEntityInstance> getTrackedEntityInstances() {
        return trackedEntityInstances;
    }

    /**
     * @param trackedEntityInstances the trackedEntityInstances to set
     */
    public void setTrackedEntityInstances(List<TrackedEntityInstance> trackedEntityInstances) {
        this.trackedEntityInstances = trackedEntityInstances;
    }

    @Override
    public String toString() {
            return "TrackedEntityInstanceBundle [trackedEntityInstances=" + trackedEntityInstances + "]";
    }

    
}
