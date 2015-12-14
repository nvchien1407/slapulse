//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import java.io.Serializable;


/**
 * Base class for Model objects.  Child objects should implement toString(),
 * equals() and hashCode();
 * <p/>
 * <p>
 * <a href="BaseObject.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public abstract class BaseObject implements Serializable, Auditable {
    /**
    * 
    */
   private static final long serialVersionUID = -7962908359429001211L;

   public abstract String toString();

    public abstract boolean equals(Object o);

    public abstract int hashCode();
}
