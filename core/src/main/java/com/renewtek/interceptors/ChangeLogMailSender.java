//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.interceptors;

import java.util.List;

import com.renewtek.model.ChangeLog;

public interface ChangeLogMailSender {

    void sendEmail(List<ChangeLog> changeLogs);
}
