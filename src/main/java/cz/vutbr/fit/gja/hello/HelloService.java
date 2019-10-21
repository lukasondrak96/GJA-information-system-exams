package cz.vutbr.fit.gja.hello;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class HelloService {

    private List<HelloObject> objectList = new ArrayList(
            Arrays.asList(
                new HelloObject(1, "prvniZeService", 1.1f),
                new HelloObject(2, "druhyZeService", 1.2f),
                new HelloObject(3, "tretiZeService", 1.3f)
            ));

    public List<HelloObject> getAllHelloObjects() {
        return objectList;
    }

    public HelloObject getObjectById(int id) {
        return objectList.stream().filter(t -> t.getId() == id).findFirst().get();
    }

    public void addHelloObject(HelloObject obj) {
        objectList.add(obj);
    }

    public void updateHelloObjectWithId(int id, HelloObject object) {
        for (int i = 0; i < objectList.size(); i++) {
             if(id == objectList.get(i).getId()) {
                 objectList.set(i, object);
                 return;
             }
        }
    }

    public void deleteHelloObjectsWithId(int id) {
        for (int i = 0; i < objectList.size(); i++) {
            if(id == objectList.get(i).getId()) {
                objectList.remove(i);
            }
        }
    }

}
