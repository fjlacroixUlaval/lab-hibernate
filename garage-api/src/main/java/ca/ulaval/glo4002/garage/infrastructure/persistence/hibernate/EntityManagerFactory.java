package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate;

import javax.inject.Singleton;
import javax.persistence.Persistence;

public class EntityManagerFactory {

  private static javax.persistence.EntityManagerFactory entityManagerFactory = null;


  public static javax.persistence.EntityManagerFactory getInstance(){
    if(entityManagerFactory == null){
      entityManagerFactory = Persistence.createEntityManagerFactory("garage");
    }
    return entityManagerFactory;
  }




}
