package me.bunny.app._c.jpa.springjpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import me.bunny.kernel._c.model.JBaseModel;

public class JSpringJpaRepositoryFactoryBean<R extends JpaRepository<T, I>, T extends JBaseModel, I extends Serializable>
		extends JpaRepositoryFactoryBean<R, T, I> {

	protected RepositoryFactorySupport createRepositoryFactory(
			EntityManager entityManager) {
		return new JSpringJpaRepositoryFactory(entityManager);
	}

	private static class JSpringJpaRepositoryFactory<T, I extends Serializable> extends
			JpaRepositoryFactory {

		private EntityManager entityManager;

		public JSpringJpaRepositoryFactory(EntityManager entityManager) {
			super(entityManager);

			this.entityManager = entityManager;
		}

		protected Object getTargetRepository(RepositoryInformation information) {
			return new JSimpleSpringJpaImpl(
					(Class<T>) information.getDomainType(), entityManager);
		}

		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

			// The RepositoryMetadata can be safely ignored, it is used by the
			// JpaRepositoryFactory
			// to check for QueryDslJpaRepository's which is out of scope.
			return JSpringJpaRepository.class;
		}
	}
}
