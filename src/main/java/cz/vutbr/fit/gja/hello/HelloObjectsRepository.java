package cz.vutbr.fit.gja.hello;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelloObjectsRepository extends CrudRepository<HelloObject, Integer> {

}
