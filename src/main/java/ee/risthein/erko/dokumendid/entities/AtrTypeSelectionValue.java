package ee.risthein.erko.dokumendid.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.List;

/**
 * Dokumendi atribuudi tüübi valikväärtused
 *
 * Mingi dokukendi atribuudi tüübi valikväärtused (sellel atribuudi tüübi peab data_type=4) mille hulgast
 * kasutaja saab ekraanivormil valida atribuudile väärtuseid, väärtused ei ole siis kasutaja poolt
 * sisestavad vaid ta saab ainult etteantud „listist” valida.
 *
 * @author Erko Risthein
 */
@Entity
@Table(name = "atr_type_selection_value")
public class AtrTypeSelectionValue {

    /**
     * Võtmeväli, sisu autonummerduv
     */
    private Integer id;

    /**
     * Valikväärtuse nimetus
     */
    private String valueText;

    /**
     * Järhekord – näitab millises järjekorras näidatakse
     * kasutajale valikväärtusi (näiteks „combo-box”-is)
     */
    private Integer orderBy;

    private List<DocAttribute> docAttributes;
    private List<DocAttributeType> defaultDocAttributeTypes;
    private DocAttributeType docAttributeType;

    @Id
    @SequenceGenerator(name = "atr_type_selection_value_seq", sequenceName = "atr_type_selection_value_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atr_type_selection_value_seq")
    @Column(name = "atr_type_selection_value")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "value_text")
    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    @Column(name = "orderby")
    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    @OneToMany(mappedBy = "atrTypeSelectionValue")
    @OrderBy("orderBy")
    @JsonIgnore
    public List<DocAttribute> getDocAttributes() {
        return docAttributes;
    }

    public void setDocAttributes(List<DocAttribute> docAttributes) {
        this.docAttributes = docAttributes;
    }

    @OneToMany(mappedBy = "defaultSelection")
    @JsonIgnore
    public List<DocAttributeType> getDefaultDocAttributeTypes() {
        return defaultDocAttributeTypes;
    }

    public void setDefaultDocAttributeTypes(List<DocAttributeType> defaultDocAttributeTypes) {
        this.defaultDocAttributeTypes = defaultDocAttributeTypes;
    }

    @ManyToOne
    @JoinColumn(name = "doc_attribute_type_fk", referencedColumnName = "doc_attribute_type")
    @JsonIgnore
    public DocAttributeType getDocAttributeType() {
        return docAttributeType;
    }

    public void setDocAttributeType(DocAttributeType docAttributeType) {
        this.docAttributeType = docAttributeType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, valueText, orderBy);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AtrTypeSelectionValue other = (AtrTypeSelectionValue) obj;
        return Objects.equal(this.id, other.id)
                && Objects.equal(this.valueText, other.valueText)
                && Objects.equal(this.orderBy, other.orderBy);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("valueText", valueText)
                .add("orderBy", orderBy)
                .toString();
    }
}
