package ee.risthein.erko.dokumendid.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

/**
 * dokumendi tüüp. Kahetasemeline hierarhia, ülemtüübid ja alamtüübid. Konkreetse dokumendi tüübiks
 * sobib teise taseme tüüp (level=2).
 * <p/>
 * Määratakse dokumendi lisamise ajal – kas enne dokumendi lisamise vormi kuvamist (see tähendab –
 * enne kui rakendus näitab dokumendi lisamise vormi küsib ta kasutaja käest millist tüüpi dokumenti ta
 * tahab sisestada)  või dokumendi lisamise vormil .
 *
 * @author Erko Risthein
 */
@Entity
@Table(name = "doc_type")
public class DocType {

    /**
     * Dokumendi seisundi tüübi nimetus
     */
    private Integer id;
    /**
     * Dokumendi tüübi tase (level=1 – ülemtüüp,
     * level=2 – mingi ülemtüübi alamtüüp)
     */
    private Integer level;
    /**
     * Dokumendi tüübi nimi
     */
    private String typeName;
    /**
     * Viit ülemtüübile samasse tabelisse [doc_type] .
     * Kui level=1 siis väärtuseks 0
     */
    private DocType superType;
    private List<Document> documents;
    private List<DocType> childTypes;
    private List<DocTypeAttribute> docTypeAttributes;

    @Id
    @SequenceGenerator(name = "doc_type_seq", sequenceName = "doc_type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_type_seq")
    @Column(name = "doc_type")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @OneToMany(mappedBy = "docType")
    @JsonIgnore
    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    @ManyToOne
    @JoinColumn(name = "super_type_fk", referencedColumnName = "doc_type")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public DocType getSuperType() {
        return superType;
    }

    public void setSuperType(DocType superType) {
        this.superType = superType;
    }

    @OneToMany(mappedBy = "superType")
    @JsonIgnore
    public List<DocType> getChildTypes() {
        return childTypes;
    }

    public void setChildTypes(List<DocType> childTypes) {
        this.childTypes = childTypes;
    }

    @OneToMany(mappedBy = "docType")
    @OrderBy("orderBy")
    public List<DocTypeAttribute> getDocTypeAttributes() {
        return docTypeAttributes;
    }

    public void setDocTypeAttributes(List<DocTypeAttribute> docTypeAttributes) {
        this.docTypeAttributes = docTypeAttributes;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, level, typeName, superType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DocType other = (DocType) obj;
        return Objects.equal(this.id, other.id)
                && Objects.equal(this.level, other.level)
                && Objects.equal(this.typeName, other.typeName)
                && Objects.equal(this.superType, other.superType);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("typeName", typeName)
                .add("level", level)
                .toString();
    }
}
