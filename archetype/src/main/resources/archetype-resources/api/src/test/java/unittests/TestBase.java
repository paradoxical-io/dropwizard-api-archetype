#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.unittests;

import ch.qos.logback.classic.Level;

import com.godaddy.logging.LoggingConfigs;
import io.dropwizard.logging.BootstrapLogging;
import io.paradoxical.common.valuetypes.ValueTypeWrapper;
import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

class TestBase {
    TestBase() {

    }

    protected static PodamFactory fixture = new PodamFactoryImpl();

    protected final static Random random = new Random();

    static {
        LoggingConfigs.getCurrent()
                      .addOverride(URI.class, URI::toString)
                      .addOverride(ValueTypeWrapper.class, ValueTypeWrapper::toString)
                      .addOverride(DateTime.class, DateTime::toString)
                      .addOverride(Collection.class, c -> c.size() > 20 ? String.valueOf(c.size()) : c.toString())
                      .addOverride(Map.class, c -> c.size() > 20 ? String.valueOf(c.size()) : c.toString())
                      .addOverride(Class.class, Class::toString);


        final String environmentLogLevel = System.getenv("LOG_LEVEL");

        BootstrapLogging.bootstrap(environmentLogLevel != null ? Level.toLevel(environmentLogLevel) : Level.ERROR);

        String[] disableLogging = new String[]{ "uk.co.jemos.podam",
                                                "com.datastax",
                                                "org.cassandraunit",
                                                "io.netty",
                                                "com.netflix.governator",
                                                "com.hazelcast.nio",
                                                "org.glassfish",
                                                "org.apache"
        };

        Arrays.stream(disableLogging).forEach(i -> {
            ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(i)).setLevel(Level.OFF);
        });
    }
}
