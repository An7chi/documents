package ee.risthein.erko.dokumendid.repositories;

import ee.risthein.erko.dokumendid.entities.DocCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Erko Risthein
 */
public interface CatalogRepository extends JpaRepository<DocCatalog, Integer> {

    List<DocCatalog> findAllByLevel(Integer level);

    @Query("select d from DocCatalog d where d.upperCatalog.id = :id")
    List<DocCatalog> findAllByUpperCatalog(@Param("id") Integer id);

}