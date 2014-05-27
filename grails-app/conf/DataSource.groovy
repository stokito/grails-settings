import net.sf.ehcache.hibernate.EhCacheRegionFactory
import org.hibernate.dialect.H2Dialect

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = EhCacheRegionFactory.class.canonicalName
}
// environment specific settings
environments {
    test {
        dataSource {
            dbCreate = 'create'
            url = 'jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;MODE=MySQL;DATABASE_TO_UPPER=FALSE;IGNORECASE=TRUE'
            pooled = true
            driverClassName = org.h2.Driver.class.canonicalName
            dialect = H2Dialect
            username = 'sa'
            password = ''
            logSql = true
            formatSql = true
            pooled = true
        }
    }
}
