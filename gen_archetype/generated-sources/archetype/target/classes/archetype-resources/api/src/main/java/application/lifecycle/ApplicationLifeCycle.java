#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.lifecycle;

import com.godaddy.logging.Logger;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.lifecycle.Managed;

import static com.godaddy.logging.LoggerFactory.getLogger;


@Singleton
@SuppressWarnings("unused")
public class ApplicationLifeCycle implements Managed {
    private static final Logger logger = getLogger(ApplicationLifeCycle.class);

    @Inject
    public ApplicationLifeCycle() {
    }

    public void start() {
    }

    public void stop() throws Exception {
        logger.dashboard("STOPPED core app logic");
    }
}