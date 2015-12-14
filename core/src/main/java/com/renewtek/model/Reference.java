// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "reference")
public class Reference implements Serializable, Auditable {

   private static final long serialVersionUID = -5474974548802494359L;

   public static final Reference NULL_VALUE = new Reference("THE NULL VALUE");

   @Id
   @GeneratedValue(generator = "reference_gen")
   @GenericGenerator(name="reference_gen", strategy="com.renewtek.dao.hibernate.AssignedHiLoIdGenerator",  
         parameters = { 
         @Parameter(name="table", value="sequence_list"), 
         @Parameter(name="pkColumnName", value="name"), 
         @Parameter(name="valueColumnName", value="next_value"), 
         @Parameter(name="allocationSize", value="50"), 
         @Parameter(name="initialValue", value="3000"), 
         @Parameter(name="pkColumnValue", value="Reference") } )
   @Column(name = "id")
   private Integer id;

   @Column(name = "groupname", length = 100, nullable = true)
   private String groupName;

   @Column(name = "subgroupname", length = 100)
   private String subGroupName;

   @Column(name = "itemname", length = 250)
   private String itemName;

   @Column(name = "note", length = 255)
   private String note;

   @Column(name = "timezone", length = 50)
   private String timezone;

   @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = DayModel.class)
   private Set<DayModel> defaultWeeks;

   @OneToMany(mappedBy = "name", fetch = FetchType.LAZY, targetEntity = BusinessProcess.class)
   private Set<BusinessProcess> businessProcessNames;

   @OneToMany(mappedBy = "type", fetch = FetchType.LAZY, targetEntity = BusinessProcess.class)
   private Set<BusinessProcess> businessProcessTypes;

   @OneToMany(mappedBy = "calendar", fetch = FetchType.LAZY, targetEntity = ServiceLevelAgreement.class)
   private Set<ServiceLevelAgreement> calendars;

   @Column(name = "hashcodevalue")
   private Integer hashcodeValue = null;

   /**
    * default constructor
    */
   public Reference() {
   }

   public Reference(String itemName) {
      this.itemName = itemName;
   }

   public Integer getId() {
      return this.id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getGroupName() {
      return this.groupName;
   }

   /**
    * @spring.validator-var name="maxlength" value="100"
    * @spring.validator-args arg1resource="${var:maxlength}"
    * @spring.validator type = "maxlength"
    */
   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   public String getSubGroupName() {
      return this.subGroupName;
   }

   /**
    * @spring.validator type = "required"
    * @spring.validator-var name="maxlength" value="100"
    * @spring.validator-args arg1resource="${var:maxlength}"
    * @spring.validator type = "maxlength"
    */
   public void setSubGroupName(String subGroupName) {
      this.subGroupName = subGroupName;
   }

   public String getItemName() {
      return this.itemName;
   }

   /**
    * @spring.validator type = "required"
    * @spring.validator-var name="maxlength" value="250"
    * @spring.validator-args arg1resource="${var:maxlength}"
    * @spring.validator type = "maxlength"
    */
   public void setItemName(String itemName) {
      this.itemName = itemName;
   }

   public String getNote() {
      return this.note;
   }

   /**
    * @spring.validator-var name="maxlength" value="255"
    * @spring.validator-args arg1resource="${var:maxlength}"
    * @spring.validator type = "maxlength"
    */
   public void setNote(String note) {
      this.note = note;
   }

   public String toString() {
      return new ToStringBuilder(this).append("id", getId()).toString();
   }

   public boolean equals(Object other) {
      if (!(other instanceof Reference))
         return false;
      Reference castOther = (Reference) other;
      return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
   }

   public int hashCode() {
      if (hashcodeValue == null) {
         if (id == null) {
            hashcodeValue = super.hashCode();
         }
         else {
            hashcodeValue = generateHashCode();
         }
      }
      return hashcodeValue;
   }

   public int generateHashCode() {
      return new HashCodeBuilder().append(getId()).toHashCode();
   }

   public Set<DayModel> getDefaultWeekDays() {
      return defaultWeeks;
   }

   public void setDefaultWeekDays(Set<DayModel> defaultWeeks) {
      this.defaultWeeks = defaultWeeks;
   }

   public Set<BusinessProcess> getBusinessProcessNames() {
      return businessProcessNames;
   }

   public void setBusinessProcessNames(Set<BusinessProcess> businessProcessNames) {
      this.businessProcessNames = businessProcessNames;
   }

   public Set<BusinessProcess> getBusinessProcessTypes() {
      return businessProcessTypes;
   }

   public void setBusinessProcessTypes(Set<BusinessProcess> businessProcessTypes) {
      this.businessProcessTypes = businessProcessTypes;
   }

   public Set<ServiceLevelAgreement> getCalendars() {
      return calendars;
   }

   public void setCalendars(Set<ServiceLevelAgreement> calendars) {
      this.calendars = calendars;
   }

   public void setTimezone(String timezone) {
      this.timezone = timezone;
   }

   public String getTimezone() {
      return timezone;
   }
}
