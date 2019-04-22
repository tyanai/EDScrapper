package org.yanai.eds;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.yanai.eds.EDS.findTickerEarningDate;

@RestController
public class EDSController {

       

    @RequestMapping("/eds")
    public EDS greeting(@RequestParam(value="ticker", defaultValue="") String ticker) {
        return new EDS(ticker,findTickerEarningDate(ticker));
    }
}
