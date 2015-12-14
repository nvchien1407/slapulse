//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "businessprocess")
@NamedQueries({
      @NamedQuery(name = "BusinessProcess.findByNameIdAndTypeId", query = "SELECT bp FROM BusinessProcess bp WHERE bp.name.id =:nameId AND bp.type.id=:typeId") })
public class BusinessProcess implements Auditable {

   public static final String QUERY_FIND_BY_NAMEID_AND_TYPEID = "BusinessProcess.findByNameIdAndTypeId";

   @Id
   @GeneratedValue(generator = "businessprocess_gen")
   @GenericGenerator(name="businessprocess_gen", strategy="com.renewtek.dao.hibernate.AssignedHiLoIdGenerator",  
         parameters = { 
         @Parameter(name="table", value="sequence_list"), 
         @Parameter(name="pkColumnName", value="name"), 
         @Parameter(name="valueColumnName", value="next_value"), 
         @Parameter(name="allocationSize", value="50"), 
         @Parameter(name="initialValue", value="3000"), 
         @Parameter(name="pkColumnValue", value="BusinessProcess") } )
   @Column(name = "id")
   private Integer id;

   @ManyToOne()
   @JoinColumn(name = "nameid", nullable = false)
   private Reference name;

   @ManyToOne()
   @JoinColumn(name = "typeid", nullable = false)
   private Reference type;

   @ManyToOne()
   @JoinColumn(name = "txnid", nullable = true)
   private Reference txn;

   @ManyToOne()
   @JoinColumn(name = "stepid", nullable = true)
   private Reference step;

   @Column(name = "description", length = 255, nullable = false)
   private String description;

   @ManyToOne()
   @JoinColumn(name = "servicelevelagreementid", nullable = true)
   private ServiceLevelAgreement serviceLevelAgreement;

   @Transient
   private Integer hashcodeValue = null;

   // fix to use nullable = true to allow the deleting of a business process from SLA

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getDescription() {
      return description;
   }

   /**
    * @spring.validator type="required" msgkey="errors.required"
    */
   public void setDescription(String description) {
      this.description = description;
   }

   public Reference getName() {
      return name;
   }

   /**
    * @spring.validator type="required" msgkey="errors.required"
    */
   public void setName(Reference name) {
      this.name = name;
   }

   public Reference getType() {
      return type;
   }

   /**
    * @spring.validator type="required" msgkey="errors.required"
    */
   public void setType(Reference type) {
      this.type = type;
   }

   public Reference getTxn() {
      return txn;
   }

   public void setTxn(Reference txn) {
      this.txn = txn;
   }
   
   public Reference getStep() {
      return step;
   }

   public void setStep(Reference step) {
      this.step = step;
   }

   public ServiceLevelAgreement getServiceLevelAgreement() {
      return serviceLevelAgreement;
   }

   public void setServiceLevelAgreement(ServiceLevelAgreement serviceLevelAgreement) {
      this.serviceLevelAgreement = serviceLevelAgreement;
   }

   public boolean equals(Object other) {
      if (!(other instanceof BusinessProcess)) {
         return false;
      }
      BusinessProcess castOther = (BusinessProcess) other;
      return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
   }

   public int hashCode() {
      if (hashcodeValue == null) {
         if (id == null) {
            hashcodeValue = super.hashCode();
         }
         else {
            hashcodeValue = this.generateHashCode();
         }
      }
      return hashcodeValue;
   }
   @Column(name = "emailnotification", nullable = false, columnDefinition="boolean default false")
   private Boolean emailNotification;
   
   public Boolean getEmailNotification() {
      return emailNotification;
   }

   public void setEmailNotification(Boolean emailNotification) {
      this.emailNotification = emailNotification;
   }
   private String emailTemplate;
   public String getEmailTemplate() {
      return emailTemplate;
   }

   public void setEmailTemplate(String emailTemplate) {
      this.emailTemplate = emailTemplate;
   }

   public String getFromEmail() {
      return fromEmail;
   }

   public void setFromEmail(String fromEmail) {
      this.fromEmail = fromEmail;
   }

   public String getToEmail() {
      return toEmail;
   }

   public void setToEmail(String toEmail) {
      this.toEmail = toEmail;
   }
   private String fromEmail;
   private String toEmail;
   
   public int generateHashCode() {
      return new HashCodeBuilder().append(getId()).toHashCode();
   }
}
