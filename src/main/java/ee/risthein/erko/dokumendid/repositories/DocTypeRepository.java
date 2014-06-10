package ee.risthein.erko.dokumendid.repositories;

import ee.risthein.erko.dokumendid.entities.DocType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Erko Risthein
 */
public interface DocTypeRepository extends JpaRepository<DocType, Integer> {
}