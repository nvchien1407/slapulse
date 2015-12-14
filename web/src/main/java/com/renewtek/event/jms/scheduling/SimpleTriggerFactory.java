package com.renewtek.event.jms.scheduling;

import java.util.Date;

import org.quartz.SimpleTrigger;

/**
 * User: khanhpham Date: 10/31/11
 */
public class SimpleTriggerFactory {
   public static final String GROUP_NAME = "SLA_GROUP";

   public static SimpleTrigger createTrigger(final Date startTime, final Date endTime,
         final String group, final String name) {
      SimpleTrigger trigger = new SimpleTrigger();
      trigger.setStartTime(startTime);
      trigger.setEndTime(endTime);
      trigger.setName(name);
      trigger.setGroup(group);
      trigger.setRepeatInterval(5000);
      trigger.setRepeatCount(0);
      trigger.setVolatility(false);
      return trigger;
   }
}
