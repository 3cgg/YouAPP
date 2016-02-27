package j.jave.platform.basicwebcomp.spirngjpa;

import j.jave.kernal.jave.model.JBaseModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.ObjectUtils;

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
		
		private final Map<RepositoryInformationCacheKey, RepositoryInformation> repositoryInformationCache = new HashMap<RepositoryInformationCacheKey, RepositoryInformation>();
		
		private static class RepositoryInformationCacheKey {

			private final String repositoryInterfaceName;
			private final String customImplementationClassName;

			/**
			 * Creates a new {@link RepositoryInformationCacheKey} for the given {@link RepositoryMetadata} and cuytom
			 * implementation type.
			 * 
			 * @param repositoryInterfaceName must not be {@literal null}.
			 * @param customImplementationClassName
			 */
			public RepositoryInformationCacheKey(RepositoryMetadata metadata, Class<?> customImplementationType) {
				this.repositoryInterfaceName = metadata.getRepositoryInterface().getName();
				this.customImplementationClassName = customImplementationType == null ? null : customImplementationType.getName();
			}

			/* 
			 * (non-Javadoc)
			 * @see java.lang.Object#equals(java.lang.Object)
			 */
			@Override
			public boolean equals(Object obj) {

				if (!(obj instanceof RepositoryInformationCacheKey)) {
					return false;
				}

				RepositoryInformationCacheKey that = (RepositoryInformationCacheKey) obj;
				return this.repositoryInterfaceName.equals(that.repositoryInterfaceName)
						&& ObjectUtils.nullSafeEquals(this.customImplementationClassName, that.customImplementationClassName);
			}

			/* 
			 * (non-Javadoc)
			 * @see java.lang.Object#hashCode()
			 */
			@Override
			public int hashCode() {

				int result = 31;

				result += 17 * repositoryInterfaceName.hashCode();
				result += 17 * ObjectUtils.nullSafeHashCode(customImplementationClassName);

				return result;
			}
		}
		
		@Override
		protected RepositoryInformation getRepositoryInformation(
				RepositoryMetadata metadata, Class<?> customImplementationClass) {
			RepositoryInformationCacheKey cacheKey = new RepositoryInformationCacheKey(metadata, customImplementationClass);
			RepositoryInformation repositoryInformation = repositoryInformationCache.get(cacheKey);

			if (repositoryInformation != null) {
				return repositoryInformation;
			}

			Class<?> repositoryBaseClass = getRepositoryBaseClass(metadata);

			repositoryInformation = new JSimpleRepositoryInformation(metadata, repositoryBaseClass, customImplementationClass);
			repositoryInformationCache.put(cacheKey, repositoryInformation);
			return repositoryInformation;
		}
		
	}
}
