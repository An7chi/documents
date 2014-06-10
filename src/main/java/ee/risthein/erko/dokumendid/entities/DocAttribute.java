package ee.risthein.erko.dokumendid.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import ee.risthein.erko.dokumendid.validators.ValueMayBeRequired;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;

/**
 * Dokumendi tunnus, dokumendi omadus ehk teiste sõnadega – dokumendi atribuut.
 * <p/>
 * Dokumendi atribuut sisaldab väärtust mille sisestab ekraanivormilt kasutaja ja atribuudi tüüpi (mida
 * väärtus tähendab), iga dokumendil võib süsteemis  olla 0...N atribuuti. Dokumendi atribuut on seotud
 * atribuudi tüübiga tabelis [doc_attribute_type].
 * <p/>
 * [doc_attribute_type] määrab
 * - mis on atribuudi tüübi nimi ("vastamise tähtaeg", "turvatase")
 * - mis on atribuudi andmetüüp (string, number,kuupäev või valik nimekirjast)
 * - mis on atribuudi vaikimisi väärtus tekstitüüpi atribuudi korral.
 * - mis on atribuudi väärtus valiku-tüüpi atribuudi korral (default_selection_id_fk)
 *
 * @author Erko Risthein
 */
@Entity
@Table(name = "doc_attribute")
@ValueMayBeRequired(message = "This attribute is required!")
public class DocAttribute {

    /**
     * võtmeväli, autonummerduv
     */
    private Integer id;
    /**
     * Dokumendi atribuudi tüübi nimi. Tegelikult on see
     * kirjas tabelis [doc_attribute_type] aga soovi
     * korral võib (atribuudi lisamisel) selle tüübi nime
     * siia ka dubleerida.
     */
    private String typeName;
    /**
     * Atribuudi väärtus. Kasutaja sisestab siia andmeid
     * ekraanivormilt. Täidetud siis kui tegemist on
     * ekst-tüüpi atribuudiga (data_type=1)
     */
    private String valueText;
    /**
     * Atribuudi väärtus. Kasutaja sisestab siia andmeid
     * ekraanivormilt. Täidetud siis kui tegemist on
     * number-tüüpi atribuudiga (data_type=2)
     */
    private Integer valueNumber;
    /**
     * Atribuudi väärtus. Kasutaja sisestab siia andmeid
     * ekraanivormilt. Täidetud siis kui tegemist on
     * kuupäev- või timestamp-tüüpi atribuudiga
     * (data_type=3)
     */
    private Date valueDate;
    /**
     * Atribuudi andmetüüp
     * data_type=1 – teksti-tüüpi atribuut
     * data_type=2 – number-tüüpi atribuut
     * data_type=3 – kuupäeva-tüüpi atribuut
     * data_type=4 – valiku-tüüpi atribuut
     * Selle välja sisust sõltub millisest andmeväljast
     * näidatakse andmeid ekraanivormil ja millisesse
     * andmevälja kirjutatakse kasutaja muudetud
     * andmed tagasi (samuti päringud oskavad
     * atribuudi väärtust selle välja järgi õigest
     * andmeväljast vaadata)
     */
    private DataType dataType;
    /**
     * järjekord , näitab millises järjekorras näidatakse
     * atribuute ekraanivormil
     * Võetakse atribuudi lisamisel tabelist
     * [doc_attribute_type]
     */
    private Integer orderBy;
    /**
     * Näitab kas kasutaja tohib andmeid sisestades ja
     * muutes ekraanivormil selle atribuudi väärtust
     * tühjaks jätta (required='N')või mitte
     * (required='Y').Võetakse atribuudi lisamisel
     * tabelist [doc_attribute_type]
     */
    private boolean required;
    /**
     * Viit atribuudi väärtusele kui valikule , tabelisse
     * [atr_type_selection_value]. Täidetud juhul k
     * data_type=4 (valiku-tüüpi atribuut).
     */
    private AtrTypeSelectionValue atrTypeSelectionValue;

    private DocAttributeType docAttributeType;
    private Document document;

    @Id
    @SequenceGenerator(name = "doc_attribute_seq", sequenceName = "doc_attribute_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_attribute_seq")
    @Column(name = "doc_attribute")
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

    @Column(name = "value_text")
    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    @Column(name = "value_number")
    public Integer getValueNumber() {
        return valueNumber;
    }

    public void setValueNumber(Integer valueNumber) {
        this.valueNumber = valueNumber;
    }

    @Column(name = "value_date")
    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    @Column(name = "data_type")
    @Enumerated
    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
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
    @JoinColumn(name = "atr_type_selection_value_fk", referencedColumnName = "atr_type_selection_value")
    public AtrTypeSelectionValue getAtrTypeSelectionValue() {
        return atrTypeSelectionValue;
    }

    public void setAtrTypeSelectionValue(AtrTypeSelectionValue atrTypeSelectionValue) {
        this.atrTypeSelectionValue = atrTypeSelectionValue;
    }

    @ManyToOne
    @JoinColumn(name = "doc_attribute_type_fk", referencedColumnName = "doc_attribute_type")
    public DocAttributeType getDocAttributeType() {
        return docAttributeType;
    }

    public void setDocAttributeType(DocAttributeType docAttributeType) {
        this.docAttributeType = docAttributeType;
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
        return Objects.hashCode(typeName, valueText, valueNumber, valueDate, dataType, orderBy, required, atrTypeSelectionValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DocAttribute other = (DocAttribute) obj;
        return Objects.equal(this.typeName, other.typeName)
                && Objects.equal(this.valueText, other.valueText)
                && Objects.equal(this.valueNumber, other.valueNumber)
                && Objects.equal(this.valueDate, other.valueDate)
                && Objects.equal(this.dataType, other.dataType)
                && Objects.equal(this.orderBy, other.orderBy)
                && Objects.equal(this.required, other.required)
                && Objects.equal(this.atrTypeSelectionValue, other.atrTypeSelectionValue);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("typeName", typeName)
                .add("valueText", valueText)
                .add("valueNumber", valueNumber)
                .add("valueDate", valueDate)
                .add("dataType", dataType)
                .add("orderBy", orderBy)
                .add("required", required)
                .add("atrTypeSelectionValue", atrTypeSelectionValue)
                .toString();
    }
}
