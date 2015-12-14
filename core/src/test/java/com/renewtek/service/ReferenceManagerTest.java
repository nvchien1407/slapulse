package com.renewtek.service;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;

import com.renewtek.dao.ReferenceDAO;
import com.renewtek.model.Reference;
import com.renewtek.service.impl.ReferenceManagerImpl;

public class ReferenceManagerTest extends BaseManagerTestCase {

   private Mock mockReferenceDao;

   private ReferenceManager objectUnderTest;

   protected void setUp() throws Exception {
      objectUnderTest = new ReferenceManagerImpl();
      mockReferenceDao = new Mock(ReferenceDAO.class);
      objectUnderTest.setReferenceDAO((ReferenceDAO) mockReferenceDao.proxy());
   }

   public void testGetCountryStates() {
      List<Reference> expected = new ArrayList<Reference>();
      mockReferenceDao.expects(once()).method("getReferencesByTemplate").with(isA(Reference.class))
            .will(returnValue(expected));
      List<Reference> actual = objectUnderTest.getCountryStates();
      mockReferenceDao.verify();

      assertSame(expected, actual);
   }

   public void testGetReferences() {
      List<Reference> expected = new ArrayList<Reference>();
      mockReferenceDao.expects(once()).method("getReferences").with(isA(Reference.class)).will(returnValue(expected));
      List<Reference> actual = objectUnderTest.getReferences(new Reference());
      mockReferenceDao.verify();

      assertSame(expected, actual);
   }

   public void testGetReferencesByTemplate() {
      List<Reference> expected = new ArrayList<Reference>();
      mockReferenceDao.expects(once()).method("getReferencesByTemplate").with(isA(Reference.class))
            .will(returnValue(expected));
      List<Reference> actual = objectUnderTest.getReferencesByTemplate(new Reference());
      mockReferenceDao.verify();

      assertSame(expected, actual);
   }

   public void testGetReferenceByGroupName() {
      List<Reference> expected = new ArrayList<Reference>();
      mockReferenceDao.expects(once()).method("getReferencesByTemplate").with(isA(Reference.class))
            .will(returnValue(expected));
      List<Reference> actual = objectUnderTest.getRefernceByGroup("ABC");
      mockReferenceDao.verify();

      assertSame(expected, actual);
   }

   public void testGetReferenceByParam() {
      List<Reference> expected = new ArrayList<Reference>();
      mockReferenceDao.expects(once()).method("getReferences").with(eq("ABC")).will(returnValue(expected));
      List<Reference> actual = objectUnderTest.getReferencesByParam("ABC");
      mockReferenceDao.verify();

      assertSame(expected, actual);
   }

   public void testGetWithdrawStatus() {
      Reference expected = new Reference();
      mockReferenceDao.expects(once()).method("getReference").with(isA(String.class), isA(String.class))
            .will(returnValue(expected));
      Reference actual = objectUnderTest.getWithdrawStatus();

      mockReferenceDao.verify();
      assertSame(expected, actual);
   }

   public void testGetReference() {
      Reference expected = new Reference();

      mockReferenceDao.expects(once()).method("getReference").with(eq(1234)).will(returnValue(expected));

      Reference actual = objectUnderTest.getReference("1234");

      mockReferenceDao.verify();
      assertSame(expected, actual);
   }

   public void testSaveReference() {
      Reference reference = new Reference();

      mockReferenceDao.expects(once()).method("saveReference").with(same(reference)).isVoid();
      objectUnderTest.saveReference(reference);
      mockReferenceDao.verify();
   }

   public void testRemoveReference() {
      Reference reference = new Reference();

      mockReferenceDao.expects(once()).method("removeReference").with(eq(1234)).isVoid();
      objectUnderTest.removeReference("1234");
      mockReferenceDao.verify();
   }

   public void testGetSubgroups() {
      List<String> expected = new ArrayList<String>();
      mockReferenceDao.expects(once()).method("getSubgroups").will(returnValue(expected));

      List<String> actual = objectUnderTest.getSubgroups();

      mockReferenceDao.verify();
      assertSame(expected, actual);
   }

   public void testGetReferenceByItemName() {
      Reference expected = new Reference();

      mockReferenceDao.expects(once()).method("getReferenceByItemName").with(eq("ABC")).will(returnValue(expected));

      Reference actual = objectUnderTest.getReferenceByItemName("ABC");

      mockReferenceDao.verify();
      assertSame(expected, actual);
   }

   public void testEditSubGroupName() {
      List<Reference> references = new ArrayList<Reference>();
      references.add(new Reference());
      references.add(new Reference());
      references.add(new Reference());

      mockReferenceDao.expects(once()).method("getReferences").with(eq("oldName"))
            .will(returnValue(references));

      mockReferenceDao.expects(exactly(3)).method("saveReference");

      objectUnderTest.editSubgroupName("oldName", "newName");

   }

}
