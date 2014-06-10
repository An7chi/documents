package ee.risthein.erko.dokumendid.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.List;

/**
 * Dokumendi atribuudi tüüp.
 * <p/>
 * Dokumentidel võib süsteemis olla erinevaid atribuute. Atribuutidel on
 * tüübid, tüüp määrab mis on selle atribuudi tähendus, mis informatsiooni seda tüüpi atribuut hoiab.
 * Millised atribuudid on seotud milliste dokumendi tüüpidega – see on kirjas tabelis
 * [doc_type_attribute].
 * <p/>
 * Üks atribuudi tüüp võib olla seotud mitme dokumenditüübiga.
 * <p/>
 * Dokumendi atribuudi tüüp määrab ka ära mis on seda tüüpi atribuudi andmetüüp (tekst, number,
 * kuupäev,valik) ja mis on vaikimisi atribuudi väärtus (kui tegemist on teksti-tüüpi või valiku-tüüpi
 * atribuudiga).
 *
 * @author Erko Risthein
 */
@Entity
@Table(name = "doc_attribute_type")
public class DocAttributeType {

    /**
     * Võtmeväli, autonummerduv
     */
    private Integer id;
    /**
     * Atribuudi tüübi nimi
     */
    private String typeName;
    /**
     * Seda tüüpi dokumendi atribuudi vaikimisi väärtus,
     * võib olla täidetud siis kui data_type=1
     */
    private String defaultValueText;

    private List<DocTypeAttribute> docTypeAttributes;
    private List<DocAttribute> docAttributes;
    /**
     * Andmetüüp, viit tabelisse [data_type]
     */
    private DataType dataType;
    /**
     * Vaikimisi valiku id, viit tabelisse
     * [atr_type_selection_value] ("atribuudi tüübi
     * valikväärtused")
     */
    private AtrTypeSelectionValue defaultSelection;
    private List<AtrTypeSelectionValue> atrTypeSelectionValues;

    @Id
    @SequenceGenerator(name = "doc_attribute_type_seq", sequenceName = "doc_attribute_type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_attribute_type_seq")
    @Column(name = "doc_attribute_type")
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

    @Column(name = "default_value_text")
    public String getDefaultValueText() {
        return defaultValueText;
    }

    public void setDefaultValueText(String defaultValueText) {
        this.defaultValueText = defaultValueText;
    }

    @OneToMany(mappedBy = "docAttributeType")
    @OrderBy("orderBy")
    @JsonIgnore
    public List<DocTypeAttribute> getDocTypeAttributes() {
        return docTypeAttributes;
    }

    public void setDocTypeAttributes(List<DocTypeAttribute> docTypeAttributes) {
        this.docTypeAttributes = docTypeAttributes;
    }

    @OneToMany(mappedBy = "docAttributeType")
    @OrderBy("orderBy")
    @JsonIgnore
    public List<DocAttribute> getDocAttributes() {
        return docAttributes;
    }

    public void setDocAttributes(List<DocAttribute> docAttributes) {
        this.docAttributes = docAttributes;
    }

    @Column(name = "data_type_fk")
    @Enumerated
    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    @ManyToOne
    @JoinColumn(name = "default_selection_id_fk", referencedColumnName = "atr_type_selection_value")
    public AtrTypeSelectionValue getDefaultSelection() {
        return defaultSelection;
    }

    public void setDefaultSelection(AtrTypeSelectionValue defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    @OneToMany(mappedBy = "docAttributeType", fetch = FetchType.EAGER)
    @OrderBy("orderBy")
    public List<AtrTypeSelectionValue> getAtrTypeSelectionValues() {
        return atrTypeSelectionValues;
    }

    public void setAtrTypeSelectionValues(List<AtrTypeSelectionValue> atrTypeSelectionValues) {
        this.atrTypeSelectionValues = atrTypeSelectionValues;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, typeName, defaultValueText, dataType, defaultSelection);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DocAttributeType other = (DocAttributeType) obj;
        return Objects.equal(this.id, other.id)
                && Objects.equal(this.typeName, other.typeName)
                && Objects.equal(this.defaultValueText, other.defaultValueText)
                && Objects.equal(this.dataType, other.dataType)
                && Objects.equal(this.defaultSelection, other.defaultSelection);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("typeName", typeName)
                .add("defaultValueText", defaultValueText)
                .add("dataType", dataType)
                .add("defaultSelection", defaultSelection)
                .add("atrTypeSelectionValues", atrTypeSelectionValues)
                .toString();
    }
}
