#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godaddy.logging.Logger;
import ${package}.bundles.GuiceBundleProvider;
import ${package}.handlers.ParameterHandlerProvider;
import ${package}.serialization.JacksonJsonMapper;
import de.thomaskrille.dropwizard_template_config.TemplateConfigBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.jersey.jackson.JacksonMessageBodyProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.views.ViewRenderer;
import io.dropwizard.views.mustache.MustacheViewRenderer;
import io.paradoxical.common.web.web.filter.CorrelationIdFilter;
import io.paradoxical.dropwizard.swagger.SwaggerAssetsBundle;
import io.swagger.jaxrs.config.BeanConfig;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static com.godaddy.logging.LoggerFactory.getLogger;


public class ServiceApplication extends Application<ServiceConfiguration> {

    private static final Logger logger = getLogger(ServiceApplication.class);
    private final GuiceBundleProvider guiceBundleProvider;

    public ServiceApplication(final GuiceBundleProvider guiceBundleProvider) {

        this.guiceBundleProvider = guiceBundleProvider;
    }

    public static void main(String[] args) throws Exception {
        DateTimeZone.setDefault(DateTimeZone.UTC);

        ServiceApplication serviceApplication = new ServiceApplication(new GuiceBundleProvider());

        try {
            serviceApplication.run(args);
        }
        catch (Throwable ex) {
            ex.printStackTrace();

            System.exit(1);
        }
    }

    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
        bootstrap.addBundle(new TemplateConfigBundle());

        initializeViews(bootstrap);

        bootstrap.addBundle(new SwaggerAssetsBundle(this::getPublicSwagger));

        initializeDependencyInjection(bootstrap);
    }

    private void initializeViews(final Bootstrap<ServiceConfiguration> bootstrap) {
        List<ViewRenderer> viewRenders = new ArrayList<>();

        viewRenders.add(new MustacheViewRenderer());

        bootstrap.addBundle(new ViewBundle<>(viewRenders));

        bootstrap.addBundle(new AssetsBundle("/assets", "/assets"));
    }


    private void initializeDependencyInjection(final Bootstrap<ServiceConfiguration> bootstrap) {
        bootstrap.addBundle(guiceBundleProvider.getBundle());
    }

    @Override
    public void run(ServiceConfiguration config, final Environment env) throws Exception {

        ArrayList<BiConsumer<ServiceConfiguration, Environment>> run = new ArrayList<>();

        run.add(this::configureJson);

        run.add(this::configureLogging);

        run.add(this::configureFilters);

        run.stream().forEach(configFunction -> configFunction.accept(config, env));
    }

    private void configureFilters(final ServiceConfiguration serviceConfiguration, final Environment env) {
        env.jersey().register(new CorrelationIdFilter());
        env.jersey().register(new ParameterHandlerProvider());
    }

    private void configureLogging(final ServiceConfiguration serviceConfiguration, final Environment environment) {
        Logs.setup();
    }

    private BeanConfig getPublicSwagger(final Environment environment) {

        final BeanConfig swagConfig = new BeanConfig();

        swagConfig.setTitle("${parentArtifactId} API");
        swagConfig.setDescription("${parentArtifactId} API");
        swagConfig.setLicense("Apache 2.0");
        swagConfig.setResourcePackage(ServiceApplication.class.getPackage().getName());
        swagConfig.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html");
        swagConfig.setContact("admin@${parentArtifactId}.com");

        swagConfig.setBasePath(environment.getApplicationContext().getContextPath());

        swagConfig.setVersion("1.0.1");

        swagConfig.setScan(true);

        return swagConfig;
    }

    protected void configureJson(ServiceConfiguration config, final Environment environment) {
        ObjectMapper mapper = new JacksonJsonMapper().getMapper();

        JacksonMessageBodyProvider jacksonBodyProvider = new JacksonMessageBodyProvider(mapper, environment.getValidator());

        environment.jersey().register(jacksonBodyProvider);
    }
}
