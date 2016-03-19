#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.unittests.server;

import com.godaddy.logging.Logger;
import com.google.common.collect.ImmutableList;
import ${package}.ServiceConfiguration;
import ${package}.pub.client.ServiceClient;
import io.paradoxical.common.test.guice.OverridableModule;
import io.paradoxical.common.test.web.runner.ServiceTestRunner;
import io.paradoxical.common.test.web.runner.ServiceTestRunnerConfig;
import lombok.Getter;

import java.net.URI;
import java.util.List;
import java.util.Random;

import static com.godaddy.logging.LoggerFactory.getLogger;

public class SelfHostServer {
    private static final Logger logger = getLogger(SelfHostServer.class);

    @Getter
    private final List<OverridableModule> overridableModules;

    private static Random random = new Random();

    private ServiceTestRunner<ServiceConfiguration, TestService> serviceConfigurationTestServiceServiceTestRunner;

    public SelfHostServer(List<OverridableModule> overridableModules) {
        this.overridableModules = overridableModules;
    }

    public void start(ServiceConfiguration configuration) {


        serviceConfigurationTestServiceServiceTestRunner =
                new ServiceTestRunner<>(TestService::new,
                                        configuration,
                                        getNextPort());

        ServiceTestRunnerConfig serviceTestRunnerConfig = ServiceTestRunnerConfig.builder().applicationRoot("/").build();

        serviceConfigurationTestServiceServiceTestRunner.run(serviceTestRunnerConfig, ImmutableList.copyOf(overridableModules));
    }

    public void start() {
        start(getDefaultConfig());
    }

    public void close(){
        try {
            stop();
        } catch (Exception e) {
            logger.warn(e, "Error stopping");
        }
    }
    public void stop() throws Exception {
        serviceConfigurationTestServiceServiceTestRunner.close();
    }

    protected static long getNextPort() {
        return random.nextInt(35000) + 15000;
    }

    public ServiceClient getClient(String path) {
        return ServiceClient.createClient(getBaseUri().toString());
    }

    public URI getBaseUri() {

        final String uri = String.format("http://localhost:%s/", serviceConfigurationTestServiceServiceTestRunner.getLocalPort());

        return URI.create(uri);
    }

    private ServiceConfiguration getDefaultConfig() {
        return new ServiceConfiguration();
    }
}