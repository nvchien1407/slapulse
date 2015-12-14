//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.util;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.springframework.validation.Errors;
import org.springmodules.validation.commons.FieldChecks;

/**
 * ValidationUtil Helper class for performing custom validations that
 * aren't already included in the core Commons Validator.
 *
 * <p>
 * <a href="ValidationUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class ValidationUtil {

   private static String[] schemes = { "http", "https", "ftp" };
   private static final UrlValidator urlValidator = new UrlValidator(schemes);

   //~ Methods ================================================================

   /**
    * Validates that two fields match.
    * @param bean
    * @param va
    * @param field
    * @param errors
    */
   public static boolean validateTwoFields(Object bean, ValidatorAction va, Field field, Errors errors) {
      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
      String sProperty2 = field.getVarValue("secondProperty");
      String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);

      if (!GenericValidator.isBlankOrNull(value)) {
         try {
            if (!value.equals(value2)) {
               FieldChecks.rejectValue(errors, field, va);
               return false;
            }
         }
         catch (Exception e) {
            FieldChecks.rejectValue(errors, field, va);
            return false;
         }
      }

      return true;
   }

   /**
    * Validates Url
    * @param bean
    * @param va
    * @param field
    * @param errors
    */
   public static boolean validateUrl(Object bean, ValidatorAction va, Field field, Errors errors) {
      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

      if (!GenericValidator.isBlankOrNull(value)) {
         try {
            if (!urlValidator.isValid(value)) {
               FieldChecks.rejectValue(errors, field, va);
               return false;
            }
         }
         catch (Exception e) {
            FieldChecks.rejectValue(errors, field, va);
            return false;
         }
      }

      return true;
   }

   /**
    * Validates integer
    * @param bean
    * @param va
    * @param field
    * @param errors
    */
   public static boolean validateInt(Object bean, ValidatorAction va, Field field, Errors errors) {
      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

      if (!GenericValidator.isBlankOrNull(value)) {
         try {
            Integer.parseInt(value);
         }
         catch (Exception e) {
            FieldChecks.rejectValue(errors, field, va);
            return false;
         }
      }

      return true;
   }

}
