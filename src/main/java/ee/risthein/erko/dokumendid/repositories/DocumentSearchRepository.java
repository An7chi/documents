package ee.risthein.erko.dokumendid.repositories;

import ee.risthein.erko.dokumendid.controllers.DocumentSearchForm;
import ee.risthein.erko.dokumendid.entities.Document;
import ee.risthein.erko.dokumendid.entities.UserAccount;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author Erko Risthein
 */
@Repository
public class DocumentSearchRepository {

    @Inject
    private JdbcTemplate jdbcTemplate;

    public List<Document> search(DocumentSearchForm form) {
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder()
                .append("select d.document as id, d.name, d.description, d.created_by, d.updated_by ")
                .append("from document d ")
                .append("left join document_doc_catalog ddc on d.document = ddc.document_fk ")
                .append("left join doc_catalog dc on ddc.doc_catalog_fk = dc.doc_catalog ")
                .append("where 1=1 ");

        if (form.getDocumentId() != null) {
            query.append("and d.document = ? ");
            params.add(form.getDocumentId());
        }

        if (isNotBlank(form.getDocumentName())) {
            query.append("and lower(d.name) like ? ");
            params.add("%" + form.getDocumentName().trim().toLowerCase() + "%");
        }

        if (isNotBlank(form.getDocumentDescription())) {
            query.append("and (to_tsvector(d.description) @@ plainto_tsquery(?)) ");
            params.add(form.getDocumentDescription().trim());
        }

        if (isNotBlank(form.getPersonLastName())) {
            if (!form.getUsers().isEmpty()) {
                query.append("and (1=0 ");
                for (UserAccount user : form.getUsers()) {
                    query.append("or created_by = ? ");
                    params.add(user.getId());
                    query.append("or updated_by = ? ");
                    params.add(user.getId());
                }
                query.append(") ");
            } else {
                query.append("and 1=0 "); // :)
            }
        }

        if (isNotBlank(form.getCatalogName())) {
            query.append("and lower(dc.name) like ? ");
            params.add("%" + form.getCatalogName().trim().toLowerCase() + "%");
        }

        if (form.getDocumentStatusTypeId() != null) {
            query.append("and d.doc_status_type_fk = ? ");
            params.add(form.getDocumentStatusTypeId());
        }

        if (isNotBlank(form.getDocumentAttributeValue())) {
//            query.append("and lower(da.value_text) like ? ");
//            params.add("%" + form.getDocumentAttributeValue().trim().toLowerCase() + "%");
        }

        String sql = query.toString();

        List<Document> documents = jdbcTemplate.query(sql, params.toArray(), new BeanPropertyRowMapper<>(Document.class));

        return documents;
    }
}
