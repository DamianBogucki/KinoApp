package kinoap.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
//@RestController("/kino")
public class KinoController {



//    @GetMapping("/main")
    public String mainPage(){
        return "kino";
    }


}
