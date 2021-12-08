package by.academy.jee.util;

import by.academy.jee.dao.EntityManagerHelper;
import javax.persistence.EntityManager;

public class ThreadLocalForEntityManager extends ThreadLocal<EntityManager> {

    private final EntityManagerHelper helper = EntityManagerHelper.getInstance();

    private static volatile ThreadLocalForEntityManager instance;

    private ThreadLocalForEntityManager() {
        //singleton
    }

    public static ThreadLocalForEntityManager getInstance() {

        if (instance == null) {
            synchronized (ThreadLocalForEntityManager.class) {
                if (instance == null) {
                    instance = new ThreadLocalForEntityManager();
                }
            }
        }
        return instance;
    }

    public void set() {
        set(helper.getEntityManager());
    }
}
