package org.duid.dao;

import org.duid.model.DUIDProcess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DUIDDao {

    /*@Autowired
    private SessionFactory sessionFactory;

    public DUIDProcess save(DUIDProcess duidProcess) {
        Session s = sessionFactory.openSession();
        Transaction tr = s.beginTransaction();
        s.persist(duidProcess);
        s.flush();
        tr.commit();
        return duidProcess;
    }*/
}
