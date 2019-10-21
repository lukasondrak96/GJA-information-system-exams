package cz.vutbr.fit.gja.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {

    @Autowired
    private HelloObjectsRepository helloObjectsRepository;

    public List<HelloObject> getAllHelloObjects() {
        return (ArrayList<HelloObject>) helloObjectsRepository.findAll();
    }

    public HelloObject getObjectById(int id) {
        return helloObjectsRepository.findById(id).orElse(null);
    }

    public void addHelloObject(HelloObject obj) {
        helloObjectsRepository.save(obj);
    }

    public void updateHelloObjectWithId(int id, HelloObject object) {
        helloObjectsRepository.save(object); // repository pozna, ze id uz v db je
    }

    public void deleteHelloObjectsWithId(int id) {
        helloObjectsRepository.deleteById(id);
    }

}
