package util.guice;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

/**
 * Create the persistence module.
 */
public class PersistenceModule extends AbstractModule {

    private String jpaUnit;

    /**
     * Create the JUnit.
     * @param jpaUnit
     */
    public PersistenceModule(String jpaUnit) {
        this.jpaUnit = jpaUnit;
    }

    @Override
    protected void configure() {
        install(new JpaPersistModule(jpaUnit));
        bind(JpaInitializer.class).asEagerSingleton();
    }

}
