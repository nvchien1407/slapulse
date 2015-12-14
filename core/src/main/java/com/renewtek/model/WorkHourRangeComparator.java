//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import java.util.Comparator;

public class WorkHourRangeComparator implements Comparator<Object> {
    public int compare(Object range1, Object range2) {
        WorkHourRange r1 = (WorkHourRange) range1;
        WorkHourRange r2 = (WorkHourRange) range2;

        return r1.getFromTime().compareTo(r2.getFromTime());
    }

    public boolean equals(Object obj) {
        return false;
    }
}