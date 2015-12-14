//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service;

import java.util.List;
import java.util.ArrayList;

import com.renewtek.dao.ChangeLogDAO;
import com.renewtek.model.ChangeLog;
import com.renewtek.service.impl.ChangeLogManagerImpl;

import org.jmock.Mock;
import org.springframework.orm.ObjectRetrievalFailureException;

public class ChangeLogManagerTest extends BaseManagerTestCase {
    private final String changeLogId = "1";
    private ChangeLogManager changeLogManager = new ChangeLogManagerImpl();
    private Mock changeLogDAO = null;
    private ChangeLog changeLog = null;

    protected void setUp() throws Exception {
        super.setUp();
        changeLogDAO = new Mock(ChangeLogDAO.class);
        changeLogManager.setChangeLogDAO((ChangeLogDAO) changeLogDAO.proxy());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        changeLogManager = null;
    }

    public void testGetChangeLogs() throws Exception {
        List<ChangeLog> results = new ArrayList<ChangeLog>();
        changeLog = new ChangeLog();
        results.add(changeLog);

        // set expected behavior on dao
        changeLogDAO.expects(once()).method("getChangeLogs")
                .will(returnValue(results));

        List<ChangeLog> changeLogs = changeLogManager.getChangeLogs(null);
        assertTrue(changeLogs.size() == 1);
        changeLogDAO.verify();
    }

    public void testGetChangeLog() throws Exception {
        // set expected behavior on dao
        changeLogDAO.expects(once()).method("getChangeLog")
                .will(returnValue(new ChangeLog()));
        changeLog = changeLogManager.getChangeLog(changeLogId);
        assertTrue(changeLog != null);
        changeLogDAO.verify();
    }

    public void testSaveChangeLog() throws Exception {
        // set expected behavior on dao
        changeLogDAO.expects(once()).method("saveChangeLog")
                .with(same(changeLog)).isVoid();

        changeLogManager.saveChangeLog(changeLog);
        changeLogDAO.verify();
    }

    public void testAddAndRemoveChangeLog() throws Exception {
        changeLog = new ChangeLog();

        // set required fields
        changeLog.setOperateType("DyRlFaFt");
        changeLog.setOperateTime(new java.util.Date());
        changeLog.setChanges("NnXeKnGvDjMcVaFxSzIrNpYdLaUiPqNcQyChCsGpBmRxElIpVvWtSnJfFwOlOeBvZaOhJxBqShLfYyHxWpNvCgKoEpMeMdUxIeBgOyIhWcKgXmRzYvAcKiWdQlOoIyTtHpMcPuAqJaZrNfNyQpLjZhNsFvGwMpFuJzUuWdErZuHiRnZdRmAlDhAcTiTmKuYyWnPmVmDrTtQeTzBpNnWhSiErRqMzVyHyNbNnJfUtDcJjPmKpJrPcUsNbNbAeXsB");
        changeLog.setTableName("JoNjQwHnUxLoRcAqUnIlWoJaDsFaXyPbQhPvSiSsNyMfCxJuCpAfBvAfIoQoScUm");
        changeLog.setUserId("FmVhOrUeXiDvYgAyKlSeQiZhBqIsUmOuGfPzYiEmUxPbReRgDaXkBmUsOdUhJmJv");
        changeLog.setEntityId("LeRkBgHmTu");

        // set expected behavior on dao
        changeLogDAO.expects(once()).method("saveChangeLog")
                .with(same(changeLog)).isVoid();
        changeLogManager.saveChangeLog(changeLog);
        changeLogDAO.verify();

        // reset expectations
        changeLogDAO.reset();

        changeLogDAO.expects(once()).method("removeChangeLog").with(eq(new Integer(changeLogId)));
        changeLogManager.removeChangeLog(changeLogId);
        changeLogDAO.verify();

        // reset expectations
        changeLogDAO.reset();
        // remove
        Exception ex = new ObjectRetrievalFailureException(ChangeLog.class, changeLog.getId());
        changeLogDAO.expects(once()).method("removeChangeLog").isVoid();
        changeLogDAO.expects(once()).method("getChangeLog").will(throwException(ex));
        changeLogManager.removeChangeLog(changeLogId);
        try {
            changeLogManager.getChangeLog(changeLogId);
            fail("ChangeLog with identifier '" + changeLogId + "' found in database");
        } catch (ObjectRetrievalFailureException e) {
            assertNotNull(e.getMessage());
        }
        changeLogDAO.verify();
    }
}
