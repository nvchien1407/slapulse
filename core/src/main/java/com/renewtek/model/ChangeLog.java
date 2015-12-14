// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "changelog")
public class ChangeLog implements Serializable {

   private static final long serialVersionUID = -4294469923779710475L;

   @Id
   @GeneratedValue(generator = "changelog_gen")
   @GenericGenerator(name="changelog_gen", strategy="com.renewtek.dao.hibernate.AssignedHiLoIdGenerator",  
         parameters = { 
         @Parameter(name="table", value="sequence_list"), 
         @Parameter(name="pkColumnName", value="name"), 
         @Parameter(name="valueColumnName", value="next_value"), 
         @Parameter(name="allocationSize", value="50"), 
         @Parameter(name="initialValue", value="3000"), 
         @Parameter(name="pkColumnValue", value="ChangeLog") } )
   @Column(name = "id")
   private Integer id;

   @Column(name = "operatetype", length = 8, nullable = false)
   private String operateType;

   @Column(name = "operatetime", length = 23, nullable = false)
   private Date operateTime;

   @Column(name = "changes", length = 2000, nullable = false)
   private String changes;

   @Column(name = "tablename", length = 64, nullable = false)
   private String tableName;

   @Column(name = "userid", length = 64, nullable = false)
   private String userId;

   @Column(name = "entityid", length = 20, nullable = false)
   private String entityId;

   @Transient
   private Object entity;

   @Transient
   private Integer hashcodeValue = null;

   /**
    * full constructor
    */
   public ChangeLog(Integer id, String operateType, Date operateTime, String changes, String tableName, String userId,
                    String entityId) {
      this.id = id;
      this.operateType = operateType;
      this.operateTime = operateTime;
      this.changes = changes;
      this.tableName = tableName;
      this.userId = userId;
      this.entityId = entityId;
   }

   /**
    * default constructor
    */
   public ChangeLog() {
   }

   public Integer getId() {
      return this.id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getOperateType() {
      return this.operateType;
   }

   public void setOperateType(String operateType) {
      this.operateType = operateType;
   }

   public Date getOperateTime() {
      return this.operateTime;
   }

   public void setOperateTime(Date operateTime) {
      this.operateTime = operateTime;
   }

   public String getChanges() {
      return this.changes;
   }

   public void setChanges(String changes) {
      int MAX = 2000; // magic number!
      changes = checkMaxLength(changes, MAX);
      this.changes = changes;
   }

   private String checkMaxLength(String changes, int MAX) {
      if (changes == null) {
         changes = "";
      }
      if (changes.length() > MAX) {
         changes = changes.substring(0, MAX);
      }
      return changes;
   }

   public String getTableName() {
      return this.tableName;
   }

   public void setTableName(String tableName) {
      this.tableName = tableName;
   }

   public String getUserId() {
      return this.userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getEntityId() {
      return this.entityId;
   }

   public void setEntityId(String entityId) {
      int MAX = 20; // magic number!
      entityId = checkMaxLength(entityId, MAX);
      this.entityId = entityId;
   }

   public boolean equals(Object other) {
      if (!(other instanceof ChangeLog)) {
         return false;
      }
      ChangeLog castOther = (ChangeLog) other;
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

   public Object getEntity() {
      return entity;
   }

   public void setEntity(Object entity) {
      this.entity = entity;
   }

   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", this.id).append("operateType",
         this.operateType).append("userId", this.userId).append("operateTime", this.operateTime).append("tableName",
         this.tableName).append("entityId", this.entityId).append("changes", this.changes).toString();
   }

   public String toStringValue() {
      return new StringBuffer().append("operateType").append(this.operateType).append("userId").append(this.userId).append("operateTime").append(this.operateTime).append("tableName").append(this.tableName).append("entityId").append(this.entityId).append("changes").append(this.changes).toString();
   }
}
