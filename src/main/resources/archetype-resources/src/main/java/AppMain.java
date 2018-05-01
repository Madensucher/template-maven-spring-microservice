package ${groupId};

import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.Locale;

@SpringBootApplication
public class AppMain extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        prepareMdcAndLocale();
        return builder.sources(AppMain.class);
    }

    public static void main(String[] args) {
        prepareMdcAndLocale();
        SpringApplication.run(AppMain.class,args);
    }

    private static void prepareMdcAndLocale() {
        MDC.clear();
        MDC.put("traceId", "main");
        Locale.setDefault(Locale.GERMAN);
    }
}