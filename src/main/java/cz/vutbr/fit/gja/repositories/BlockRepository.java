package cz.vutbr.fit.gja.repositories;

import cz.vutbr.fit.gja.models.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Integer> {

}
