#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bundles;

import com.google.inject.Injector;
import com.google.inject.Module;
import ${package}.ServiceApplication;
import ${package}.ServiceConfiguration;
import ${package}.modules.DefaultApplicationModules;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.netflix.governator.InjectorBuilder;

import java.util.List;

public class GuiceBundleProvider {

    private GuiceBundle<ServiceConfiguration> bundle;

    public synchronized GuiceBundle<ServiceConfiguration> getBundle() {
        if (bundle == null) {
            bundle = buildGuiceBundle();
        }

        return bundle;
    }

    public GuiceBundleProvider() {
    }

    public static Injector getInjectorFactory(List<Module> modules) {
        return InjectorBuilder.fromModules(modules).createInjector();
    }

    protected List<Module> getModules() {
        return DefaultApplicationModules.getModules();
    }

    private GuiceBundle<ServiceConfiguration> buildGuiceBundle() {
        final GuiceBundle.Builder<ServiceConfiguration> builder = GuiceBundle.<ServiceConfiguration>newBuilder();

        builder.enableAutoConfig(ServiceApplication.class.getPackage().getName())
               .setConfigClass(ServiceConfiguration.class)
               .setInjectorFactory((stage, modules) -> getInjectorFactory(modules));

        getModules().stream().forEach(builder::addModule);

        final GuiceBundle<ServiceConfiguration> guiceBundle = builder.build();

        return guiceBundle;
    }
}
