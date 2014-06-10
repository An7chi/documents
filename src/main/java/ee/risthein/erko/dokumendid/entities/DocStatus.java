package ee.risthein.erko.dokumendid.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Date;

/**
 * Dokumendi staatuste ajaloo ja jooksva staatuse tabel.
 *
 * Dokumendi peaks saama ekraanivormil valida dokumendi staatust, mis need staatuse tüübid on –
 * see pole tähtis, suvalised.
 *
 * @author Erko Risthein
 */
@Entity
@Table(name = "doc_status")
public class DocStatus {

    private Integer id;
    private Date statusBegin;
    private Date statusEnd;
    private Integer createdBy;
    private DocStatusType docStatusType;
    private Document document;

    @Id
    @SequenceGenerator(name = "doc_status_seq", sequenceName = "doc_status_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_status_seq")
    @Column(name = "doc_status")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "status_begin")
    public Date getStatusBegin() {
        return statusBegin;
    }

    public void setStatusBegin(Date statusBegin) {
        this.statusBegin = statusBegin;
    }

    @Column(name = "status_end")
    public Date getStatusEnd() {
        return statusEnd;
    }

    public void setStatusEnd(Date statusEnd) {
        this.statusEnd = statusEnd;
    }

    @Column(name = "created_by")
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @ManyToOne
    @JoinColumn(name = "doc_status_type_fk", referencedColumnName = "doc_status_type")
    public DocStatusType getDocStatusType() {
        return docStatusType;
    }

    public void setDocStatusType(DocStatusType docStatusType) {
        this.docStatusType = docStatusType;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_fk", referencedColumnName = "document")
    @JsonIgnore
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, statusBegin, statusEnd);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DocStatus other = (DocStatus) obj;
        return Objects.equal(this.id, other.id)
                && Objects.equal(this.statusBegin, other.statusBegin)
                && Objects.equal(this.statusEnd, other.statusEnd);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("statusBegin", statusBegin)
                .add("statusEnd", statusEnd)
                .add("docStatusType", docStatusType)
                .toString();
    }

}
