package cz.vutbr.fit.gja.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @Autowired
    private DatabaseService databaseService;

    @RequestMapping("/hello") //method GET defaultne
    public String getHello() {
        return "Hello";
    }

    @GetMapping("/helloobjects")
    public List<HelloObject> getHelloObjects() {
        return Arrays.asList(
                new HelloObject(1, "prvni", 1.1f),
                new HelloObject(2, "druhy", 1.2f),
                new HelloObject(3, "treti", 1.3f)
        );
    }

    @GetMapping("/helloobjects2")
    public List<HelloObject> getHelloObjectsOfService() {
        return helloService.getAllHelloObjects();
    }

    @GetMapping("/helloobject/{id}")
    public HelloObject getHelloObjectById(@PathVariable int id) {
        return helloService.getObjectById(id);
    }

    @PostMapping("/helloobjects") //nebo @RequestMethod(method=RequestMethod.POST), get je u RM default
    public void addHelloObject(@RequestBody HelloObject object) { // potrebuju telo requestu
        helloService.addHelloObject(object);
    }

    @PutMapping("/helloobjects/{id}") //nebo @RequestMethod(method=RequestMethod.PUT), get je u RM default
    public void updateHelloObject(@PathVariable int id, @RequestBody HelloObject object) { // potrebuju telo requestu
        helloService.updateHelloObjectWithId(id, object);
    }

    @DeleteMapping("/helloobjects/{id}") //nebo @RequestMethod(method=RequestMethod.DELETE), get je u RM default
    public void deleteHelloObjectsWithId(@PathVariable int id) {
        helloService.deleteHelloObjectsWithId(id);
    }


    /*
    DATABASE
     */
    @GetMapping("/helloobjectsDB")
    public List<HelloObject> getHelloObjectsOfServiceDB() {
        return databaseService.getAllHelloObjects();
    }

    @PostMapping("/helloobjectsDB") //nebo @RequestMethod(method=RequestMethod.POST), get je u RM default
    public void addHelloObjectDB(@RequestBody HelloObject object) { // potrebuju telo requestu
        databaseService.addHelloObject(object);
    }

    @PutMapping("/helloobjectsDB/{id}") //nebo @RequestMethod(method=RequestMethod.PUT), get je u RM default
    public void updateHelloObjectDB(@PathVariable int id, @RequestBody HelloObject object) { // potrebuju telo requestu
        databaseService.updateHelloObjectWithId(id, object);
    }

    @DeleteMapping("/helloobjectsDB/{id}") //nebo @RequestMethod(method=RequestMethod.DELETE), get je u RM default
    public void deleteHelloObjectsDBWithId(@PathVariable int id) {
        databaseService.deleteHelloObjectsWithId(id);
    }

}
