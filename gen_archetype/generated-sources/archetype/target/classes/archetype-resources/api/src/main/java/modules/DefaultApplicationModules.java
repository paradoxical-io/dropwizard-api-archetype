#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.modules;

import com.google.inject.Module;

import java.util.Arrays;
import java.util.List;

public class DefaultApplicationModules {
    public static List<Module> getModules() {
        return Arrays.asList(
                new DataAccessModule(),
                new JsonMapperModule());
    }
}
