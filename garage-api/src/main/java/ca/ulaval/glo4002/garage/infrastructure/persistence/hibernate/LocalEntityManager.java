package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate;

import javax.persistence.EntityManager;

public abstract class LocalEntityManager {

  private static final ThreadLocal<EntityManager> threadLocal = new ThreadLocal<EntityManager>();

  public static EntityManager getEntityManager(){
    return threadLocal.get();
  }

  public static void setEntityManager(EntityManager entityManager){
    threadLocal.set(entityManager);
  }


}
