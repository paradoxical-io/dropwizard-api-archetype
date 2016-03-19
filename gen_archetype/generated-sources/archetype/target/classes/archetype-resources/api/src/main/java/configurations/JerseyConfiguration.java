#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

public class JerseyConfiguration {

    @JsonProperty
    @Getter
    @Setter
    private boolean logTraffic = false;

    @NotEmpty
    @Getter
    @Setter
    private String basePath = "http://localhost:8080/api";
}