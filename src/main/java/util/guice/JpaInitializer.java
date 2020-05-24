package util.guice;

import com.google.inject.persist.PersistService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Initialize the JPA.
 */
@Singleton
public class JpaInitializer {

    /**
     * Start the service.
     * @param persistService
     */
    @Inject
    public JpaInitializer (PersistService persistService) {
        persistService.start();
    }

}
