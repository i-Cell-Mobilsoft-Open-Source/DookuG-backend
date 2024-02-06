package hu.icellmobilsoft.dookug.common.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

/**
 * Provides rediness and liveness check for microprofile-health api
 * 
 * @author czenczl
 * @since 1.10.0
 */
public interface IHealth {

    /**
     * check rediness
     * 
     * @return health check invocation response
     */
    HealthCheckResponse checkReadiness();

    /**
     * nincs liveness logika jelenleg, default működés
     * 
     * @return the default liveness check
     */
    default HealthCheck checkLiveness() {
        return () -> HealthCheckResponse.builder().up().build();
    }

    /**
     * get the checked resource name
     * 
     * @return the builder name
     */
    String getBuilderName();

}
