package service1;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import java.time.Instant;
import java.util.Map;

@Controller("/")
public class HomeController {

    @Get("hi")
    Map<String,String> hi(){
        return Map.of("Hi", System.getenv("HOSTNAME"));
    }

    @Post("goodbye")
    Map<String,String> goodbye( String msg){
        return Map.of("Goodbye "+msg, System.getenv("HOSTNAME"));
    }

}
