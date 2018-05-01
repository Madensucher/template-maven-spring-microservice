package ${groupId}.demo;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {
    private static Logger log = LoggerFactory.getLogger(DemoController.class);
    private Timer timer;

    @Autowired
    public DemoController(MetricRegistry metricRegistry) {
        timer = metricRegistry.timer("democontroller");
    }


    @RequestMapping(method = RequestMethod.GET, path = "/demo")
    public ResponseEntity<String> getDemo() {
        log.info("/demo aufgerufen");
        Timer.Context context = timer.time();
        try {
            return new ResponseEntity<>("Hello World!", HttpStatus.OK);
        } finally {
            context.close();
        }
    }
}
