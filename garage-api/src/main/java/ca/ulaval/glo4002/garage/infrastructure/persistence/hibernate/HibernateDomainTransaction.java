package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate;

import org.glassfish.jersey.internal.util.Producer;

import ca.ulaval.glo4002.garage.domain.DomainTransaction;

import javax.persistence.EntityManager;

public class HibernateDomainTransaction implements DomainTransaction {
	@Override
	public <T> T wrapInTransaction(Producer<T> call) {
		EntityManager entityManager = LocalEntityManager.getEntityManager();
		entityManager.getTransaction().begin();
		return call.call();
	}
}
