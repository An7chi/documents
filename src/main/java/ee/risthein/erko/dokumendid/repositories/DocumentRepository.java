package ee.risthein.erko.dokumendid.repositories;

import ee.risthein.erko.dokumendid.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Erko Risthein
 */
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    @Query("select d from Document d where d.docCatalog.id = :catalogId")
    List<Document> findAllByDocCatalog(@Param("catalogId") Integer catalogId);

}