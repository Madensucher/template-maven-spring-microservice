package ${groupId}.config;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Configuration
public class MetricsConfig {
    private static Logger log = LoggerFactory.getLogger(MetricsConfig.class);

    @Bean
    public MetricRegistry metricRegistry() {
        return new MetricRegistry();
    }

    @Bean
    @Autowired
    public GraphiteReporter graphiteReporter(MetricRegistry metricRegistry,
                                             @Value("${graphite.server}") String server,
                                             @Value("${graphite.port}") String port,
                                             @Value("${graphite.prefix}") String prefix) {
        final Graphite graphite = new Graphite(new InetSocketAddress(server, new Integer(port)));
        final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry)
                .prefixedWith(prefix)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);
        reporter.start(10, TimeUnit.SECONDS);
        log.info("starte Graphite-Reporter {}:{}/{}", server, port, prefix);
        return reporter;
    }
}
