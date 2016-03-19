#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.modules;

import ${package}.serialization.JacksonJsonMapper;
import ${package}.serialization.JsonMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class JsonMapperModule extends AbstractModule {

    @Override protected void configure() {
    }

    @Provides
    @Singleton
    public JsonMapper getJsonMapper() {
        return new JacksonJsonMapper();
    }
}
