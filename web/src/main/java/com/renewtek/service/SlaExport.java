package com.renewtek.service;

import java.util.Set;

import com.renewtek.model.BusinessProcess;
import com.renewtek.model.Reference;
import com.renewtek.model.ServiceLevelAgreement;

public class SlaExport {
   public Set<BusinessProcess> bps;
   public Set<ServiceLevelAgreement> slas;
   public Set<Reference> refs;

   public SlaExport() {
   }

   public SlaExport(Set<BusinessProcess> bps, Set<ServiceLevelAgreement> slas, Set<Reference> refs) {
      this.bps = bps;
      this.slas = slas;
      this.refs = refs;
   }
}
