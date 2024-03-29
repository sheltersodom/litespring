package org.litespring.tx;

import org.litespring.util.MessageTracker;

/**
 * @autor sheltersodom
 * @create 2021-02-14-17:24
 */
public class TransactionManager {
    public void start() {
        System.out.println("start tx");
        MessageTracker.addMsg("start tx");
    }

    public void commit() {
        System.out.println("commit tx");
        MessageTracker.addMsg("commit tx");
    }

    public void rollback() {
        System.out.println("rollback tx");
        MessageTracker.addMsg("rollback tx");
    }
}
