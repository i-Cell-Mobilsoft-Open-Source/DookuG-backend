package hu.icellmobilsoft.dookug.common.health;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

/**
 * Provide base functionality for health checks
 * 
 * @author czenczl
 * @since 1.10.0
 *
 */
public abstract class AbstractBaseHealthCheck {
    public static final String NODE_NAME = "nodeName";
    public static final String JBOSS_NODE_NAME = "jboss.node.name";
    public static final String URL = "URL";

    public static final long CONNECT_TIMEOUT_SEC = 1;

    /**
     * create HealthCheckResponseBuilder
     * 
     * @return response builder
     */
    public HealthCheckResponseBuilder createHealthCheckResponseBuilder() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.builder().name(getBuilderName());
        builder.withData(NODE_NAME, getNodeName());
        return builder;
    }

    /**
     * get unique server identifier
     * 
     * @return node name
     */
    public String getNodeName() {
        return System.getProperty(JBOSS_NODE_NAME);
    }

    /**
     * get the response builder name
     * 
     * @return builder name
     */
    public abstract String getBuilderName();

}
