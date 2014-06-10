package ee.risthein.erko.dokumendid.entities;

import com.google.common.base.Objects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Dokument, selle süsteemi põhiobjekt.
 * <p/>
 * Dokumendi kui infoobjekti omadused.
 * <p/>
 * - dokumendil on tüüp (seos tüübiga vahetabelis) ja tüübist sõltuvad atribuudid
 * - dokument asub dokumendi kataloogis (seos vahetabeli kaudu)
 * - dokument on seotud subjektidega (isikute ja ettevõtetega) SUBJEKTIDE allsüsteemist
 * - dokumendil on staatus , staatuste ajalugu hoitakse eraldi tabelis [doc_status]
 *
 * @author Erko Risthein
 */
@Entity
@Table(name = "document")
public class Document {

    /**
     * Võtmeväli, sisu autonummerduv
     */
    private Integer id;
    /**
     * Dokumendi nimi, suvaline tekst
     */
    private String name;
    /**
     * Dokumendi kirjeldus, suvaline tekst
     */
    private String description;
    /**
     * Dokumendi andmebaasi sisestamise aeg
     */
    private Date created;
    private Integer createdBy;
    private Integer updatedBy;
    /**
     * Dokumendi andmete salvestamise aeg
     */
    private Date updated;
    /**
     * Dokumendi failini. Kui teete rakendusse ka
     * dokumendifailide üleslaadimise (mida ei ole
     * nõutud) siis sellesse välja saab salvestada faili
     * nime. Muidu  tühi.
     */
    private String filename;
    /**
     * Dokumendi praegu kehtuv staatus. Kehtivale
     * staatuse vastab ka kirje tabelis [doc_status] mille
     * "status_end" on NULL
     * Staatuse muutmisel lõpetatakse tabelis
     * [doc_status] eelmine staatus ("status_end" =
     * NOW()) ja sisestatakse uus jooksev staatus
     * ("status_begin"=NOW()).
     * Viit tabelisse [doc_status_type]
     */
    private DocStatusType docStatusType;
    private DocCatalog docCatalog;
    private List<DocStatus> docStatuses = new ArrayList<>();
    private DocType docType;
    private List<DocAttribute> docAttributes = new ArrayList<>();

    @Id
    @SequenceGenerator(name = "document_seq", sequenceName = "document_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_seq")
    @Column(name = "document")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotBlank(message = "The name cannot be empty!")
    @Length(max = 50, message = "The name field can be up to 50 characters long!")
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank(message = "The description cannot be empty!")
    @Length(max = 255, message = "The description can be up to 255 characters long!")
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "updated")
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Column(name = "filename")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @ManyToOne
    @JoinColumn(name = "doc_status_type_fk", referencedColumnName = "doc_status_type")
    public DocStatusType getDocStatusType() {
        return docStatusType;
    }

    public void setDocStatusType(DocStatusType docStatusType) {
        this.docStatusType = docStatusType;
    }

    @ManyToOne
    @JoinTable(name = "document_doc_catalog", joinColumns = {
            @JoinColumn(name = "document_fk", referencedColumnName = "document", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "doc_catalog_fk", referencedColumnName = "doc_catalog", nullable = false)})
    public DocCatalog getDocCatalog() {
        return docCatalog;
    }

    public void setDocCatalog(DocCatalog docCatalog) {
        this.docCatalog = docCatalog;
    }

    @OneToMany(mappedBy = "document", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("statusBegin")
    public List<DocStatus> getDocStatuses() {
        return docStatuses;
    }

    public void setDocStatuses(List<DocStatus> docStatuses) {
        this.docStatuses = docStatuses;
    }

    public void addDocStatus(DocStatus docStatus) {
        docStatus.setDocument(this);
        docStatuses.add(docStatus);
    }

    @OneToOne(mappedBy = "document")
    @ManyToOne
    @JoinTable(name = "document_doc_type", joinColumns = {
            @JoinColumn(name = "document_fk", referencedColumnName = "document", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "doc_type_fk", referencedColumnName = "doc_type", nullable = false)})
    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderBy")
    @Valid
    public List<DocAttribute> getDocAttributes() {
        return docAttributes;
    }

    public void setDocAttributes(List<DocAttribute> docAttributes) {
        this.docAttributes = docAttributes;
    }

    public void addDocAttribute(DocAttribute docAttribute) {
        docAttribute.setDocument(this);
        docAttributes.add(docAttribute);
    }

    @Column(name = "updated_by")
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Column(name = "created_by")
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Transient
    public boolean isNew() {
        return id == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, created, updated, filename);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Document other = (Document) obj;
        return Objects.equal(this.id, other.id)
                && Objects.equal(this.name, other.name)
                && Objects.equal(this.description, other.description)
                && Objects.equal(this.created, other.created)
                && Objects.equal(this.updated, other.updated)
                && Objects.equal(this.filename, other.filename);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("created", created)
                .add("updated", updated)
                .add("filename", filename)
                .add("docStatusType", docStatusType)
                .add("docCatalog", docCatalog)
                .add("docType", docType)
                .toString();
    }
}
