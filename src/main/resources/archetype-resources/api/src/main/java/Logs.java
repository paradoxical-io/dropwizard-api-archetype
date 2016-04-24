#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.godaddy.logging.LoggingConfigs;
import io.paradoxical.common.valuetypes.ValueTypeWrapper;
import org.joda.time.DateTime;

import java.net.URI;

public class Logs {
    public static void setup() {
        LoggingConfigs.setCurrent(LoggingConfigs.getCurrent()
                                                .addOverride(URI.class, URI::toString)
                                                .addOverride(ValueTypeWrapper.class, ValueTypeWrapper::toString)
                                                .addOverride(DateTime.class, DateTime::toString)
                                                .addOverride(Class.class, Class::toString)
                                                .useJson());
    }
}
