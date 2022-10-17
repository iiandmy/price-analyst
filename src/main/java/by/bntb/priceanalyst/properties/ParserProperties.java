package by.bntb.priceanalyst.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix="parser")
public class ParserProperties {
    private String parentLink;
    private String pageBlockClass;
    private String tablesClass;
    private String infoBlockId;
    private String leftTablesId;
    private String rightTablesId;
}
