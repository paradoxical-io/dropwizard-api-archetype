#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.unittests.server;

import com.google.inject.Module;
import ${package}.ServiceApplication;
import ${package}.ServiceConfiguration;
import ${package}.bundles.GuiceBundleProvider;
import io.dropwizard.setup.Environment;
import io.paradoxical.common.test.guice.ModuleUtils;
import io.paradoxical.common.test.guice.OverridableModule;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TestService extends ServiceApplication {
    private final CountDownLatch serviceReady = new CountDownLatch(1);

    public TestService(final List<OverridableModule> modules) {
        super(new TestGuiceBundleProvier(modules));
    }

    private static class TestGuiceBundleProvier extends GuiceBundleProvider {
        private List<OverridableModule> overridableModules;

        public TestGuiceBundleProvier(final List<OverridableModule> overridableModules) {
            this.overridableModules = overridableModules;
        }

        @Override
        protected List<Module> getModules() {
            return ModuleUtils.mergeModules(super.getModules(), overridableModules);
        }
    }

    public void waitForReady() throws InterruptedException {
        serviceReady.await();
    }
    
    @Override
    public void run(final ServiceConfiguration config, final Environment env) throws Exception {
        super.run(config, env);

        env.lifecycle().addServerLifecycleListener(server -> serviceReady.countDown());
    }
}
