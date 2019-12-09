package ca.ulaval.glo4002.garage.interfaces.filters;

import ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.EntityManagerFactory;
import ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.LocalEntityManager;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.*;

public class EntityManagerContextFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		EntityManager entityManager = EntityManagerFactory.getInstance().createEntityManager();
		LocalEntityManager.setEntityManager(entityManager);

		chain.doFilter(request, response);

		if(entityManager.getTransaction().getRollbackOnly()){
			entityManager.getTransaction().rollback();
		}else{
			entityManager.getTransaction().commit();
		}
		entityManager.close();

	}

	@Override
	public void destroy() {
	}
}
