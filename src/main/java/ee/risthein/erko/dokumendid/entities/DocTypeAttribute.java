package ee.risthein.erko.dokumendid.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * "dokumendi tüübi atribuut (atribuudi tüüp)", seosetabel
 * Näitab millised atribuudi tüübid on seotud millise dokumendi tüübiga. Kui rakendus siestab tabelisse
 * [document uue dokumendi siis peab rakendus sellest tabelist lugema millised atribuudi tüübid sellisel
 * dokumendi tüübil on ja sisestama vastava arvu kirjeid tabelisse [doc_attribute].
 *
 * @author Erko Risthein
 */
@Entity
@Table(name = "doc_type_attribute")
public class DocTypeAttribute {

    /**
     * Võtmeväli, sisu on autonummerduv
     */
    private Integer id;
    /**
     * Dokumendi atribuudi järjestus ekraanivormil.
     * Selle välja sisu kopeeritakse tabelisse
     * doc_attribute].orderby kui seda tüüpi
     * dokumendi atribuut andmebaasi lisatakse
     */
    private Integer orderBy;
    /**
     * Kas seda tüüpi atribuudi väärtus peab kindlasti
     * olema täidetud (selle dokumendi tüübi puhul)
     * Selle välja sisu kopeeritakse tabelisse
     * [doc_attribute].orderby kui seda tüüpi
     * dokumendi atribuut andmebaasi lisatakse
     */
    private boolean required;

    private DocAttributeType docAttributeType;
    private DocType docType;

    @Id
    @SequenceGenerator(name = "doc_type_attribute_seq", sequenceName = "doc_type_attribute_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_type_attribute_seq")
    @Column(name = "doc_type_attribute")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "orderby")
    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    @Column(name = "required")
    @Type(type = "yes_no")
    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @ManyToOne
    @JoinColumn(name = "doc_attribute_type_fk", referencedColumnName = "doc_attribute_type")
    public DocAttributeType getDocAttributeType() {
        return docAttributeType;
    }

    public void setDocAttributeType(DocAttributeType docAttributeType) {
        this.docAttributeType = docAttributeType;
    }

    @ManyToOne
    @JoinColumn(name = "doc_type_fk", referencedColumnName = "doc_type")
    @JsonIgnore
    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, orderBy, required);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DocTypeAttribute other = (DocTypeAttribute) obj;
        return Objects.equal(this.id, other.id)
                && Objects.equal(this.orderBy, other.orderBy)
                && Objects.equal(this.required, other.required);
    }
}
