package ee.risthein.erko.dokumendid.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.List;

/**
 * dokumendi seisundi tüüp
 *
 * @author Erko Risthein
 */
@Entity
@Table(name = "doc_status_type")
public class DocStatusType {

    /**
     * Võtmeväli, sisu autonummerduv
     */
    private Integer id;
    /**
     * Dokumendi seisundi tüübi nimetus
     */
    private String typeName;

    private List<Document> documents;
    private List<DocStatus> docStatuses;

    @Id
    @SequenceGenerator(name = "doc_status_type_seq", sequenceName = "doc_status_type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_status_type_seq")
    @Column(name = "doc_status_type")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @OneToMany(mappedBy = "docStatusType")
    @JsonIgnore
    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    @OneToMany(mappedBy = "docStatusType")
    @JsonIgnore
    public List<DocStatus> getDocStatuses() {
        return docStatuses;
    }

    public void setDocStatuses(List<DocStatus> docStatuses) {
        this.docStatuses = docStatuses;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, typeName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DocStatusType other = (DocStatusType) obj;
        return Objects.equal(this.id, other.id)
                && Objects.equal(this.typeName, other.typeName);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("typeName", typeName)
                .toString();
    }
}
