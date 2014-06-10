package ee.risthein.erko.dokumendid.repositories;

import ee.risthein.erko.dokumendid.entities.DocStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Erko Risthein
 */
public interface DocStatusTypeRepository extends JpaRepository<DocStatusType, Integer> {
}