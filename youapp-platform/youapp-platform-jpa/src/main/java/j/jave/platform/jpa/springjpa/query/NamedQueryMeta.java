package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JStringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.query.QueryUtils;

class NamedQueryMeta extends QueryMeta {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(NamedQueryMeta.class);
	
	private String namedSql;
	
	private String countNamedSql;
	
	private String sql;
	
	private String countSql;
	
	public NamedQueryMeta(EntityManager em) {
		super(em);
	}
	
	@Override
	public String getQueryString() {
		if(sql==null){
			PersistenceProvider persistenceProvider= PersistenceProvider.fromEntityManager(em);
			if(persistenceProvider.canExtractQuery()){
				sql = persistenceProvider.extractQueryString(em.createNamedQuery(namedSql));
			}
		}
		return sql;
	}
	
	@Override
	public String getCountQueryString() {
		if(countSql==null){
			boolean namedCountQueryIsPresent=false;
			if(JStringUtils.isNotNullOrEmpty(countNamedSql)){
				namedCountQueryIsPresent=hasNamedQuery(em, countNamedSql);
				JAssert.state(namedCountQueryIsPresent, " count named SQL canot fround as persistence context : " +countNamedSql);
			}
			PersistenceProvider persistenceProvider= PersistenceProvider.fromEntityManager(em);
			if(namedCountQueryIsPresent){
				if(persistenceProvider.canExtractQuery()){
					countSql=persistenceProvider.extractQueryString(em.createNamedQuery(countNamedSql));
				}
			}
			else{
				if(persistenceProvider.canExtractQuery()){
					String queryString = persistenceProvider.extractQueryString(em.createNamedQuery(namedSql));
					countSql = QueryUtils.createCountQueryFor(queryString);
				}
			}
		}
		return countSql;
	}

	
	private static boolean hasNamedQuery(EntityManager em, String queryName) {
		/*
		 * @see DATAJPA-617
		 * we have to use a dedicated em for the lookups to avoid a potential rollback of the running tx.
		 */
		EntityManager lookupEm = em.getEntityManagerFactory().createEntityManager();

		try {
			lookupEm.createNamedQuery(queryName);
			return true;
		} catch (IllegalArgumentException e) {
			LOGGER.debug("Did not find named query {}"+queryName);
			return false;
		} finally {
			lookupEm.close();
		}
	}

	@Override
	public Query getCountQuery() {
		boolean namedCountQueryIsPresent=false;
		if(JStringUtils.isNotNullOrEmpty(countNamedSql)){
			namedCountQueryIsPresent=hasNamedQuery(em, countNamedSql);
			JAssert.state(namedCountQueryIsPresent, " count named SQL canot fround as persistence context : " +countNamedSql);
		}
		PersistenceProvider persistenceProvider= PersistenceProvider.fromEntityManager(em);
		if(namedCountQueryIsPresent){
			if(persistenceProvider.canExtractQuery()){
				return em.createNamedQuery(countNamedSql);
			}
		}
		else{
			if(persistenceProvider.canExtractQuery()){
				String queryString = persistenceProvider.extractQueryString(em.createNamedQuery(namedSql));
				countSql = QueryUtils.createCountQueryFor(queryString);
				return em.createNamedQuery(countSql,Long.class);
			}
		}
		return null;
	}
	
	@Override
	public Query getQuery() {
		if(result!=null){
			return em.createNamedQuery(namedSql,result);
		}
		return em.createNamedQuery(namedSql);
	}

	public void setNamedSql(String namedSql) {
		this.namedSql = namedSql;
	}

	public void setCountNamedSql(String countNamedSql) {
		this.countNamedSql = countNamedSql;
	}

	public String getNamedSql() {
		return namedSql;
	}

	public String getCountNamedSql() {
		return countNamedSql;
	}
	
}
