package ee.risthein.erko.dokumendid.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Erko Risthein
 */
@Entity
@Table(name = "doc_catalog")
public class DocCatalog {

    /**
     * Võtmeväli, sisu on autonummerduv
     */
    private Integer id;
    /**
     * Kataloogi nimi
     */
    private String name;
    /**
     * Kataloogi kirjeldus, ei ole vaja kasutada
     */
    private String description;
    /**
     * Kataloogi tase kataloogipuus, kõige esimesel
     * tasemel level=1
     */
    private Integer level;
    /**
     * Millal kataloogi sisu viimati muutus, timestamp
     */
    private Date contentUpdated;
    /**
     * Kataloogile vastav failikataloog serveri
     * failisüsteemis. Kui teete selles ülesandes
     * dokumendifailide üleslaadimise (mida ei ole
     * otseselt nõutud) siis saab selles väljas hoida
     * failikataloogi „path”-i (sellise andmbaasis hoitava
     * virtuaalse „doc_catalog”-iga võib seostada
     * reaalse failisüsteemis oleva kataloogi milles
     * hoitakse siis sellesse kataloogi uploaditud
     * dokumendifaile).
     */
    private Integer contentUpdatedBy;
    private String folder;
    private List<Document> documents;
    private DocCatalog upperCatalog;
    private List<DocCatalog> lowerCatalogs;

    @Id
    @SequenceGenerator(name = "doc_catalog_seq", sequenceName = "doc_catalog_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_catalog_seq")
    @Column(name = "doc_catalog")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(name = "content_updated")
    public Date getContentUpdated() {
        return contentUpdated;
    }

    public void setContentUpdated(Date contentUpdated) {
        this.contentUpdated = contentUpdated;
    }

    @Column(name = "content_updated_by")
    public Integer getContentUpdatedBy() {
        return contentUpdatedBy;
    }

    public void setContentUpdatedBy(Integer contentUpdatedBy) {
        this.contentUpdatedBy = contentUpdatedBy;
    }


    @Column(name = "folder")
    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    @OneToMany(mappedBy = "docCatalog")
    @JsonIgnore
    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    @ManyToOne
    @JoinColumn(name = "upper_catalog_fk", referencedColumnName = "doc_catalog")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public DocCatalog getUpperCatalog() {
        return upperCatalog;
    }

    public void setUpperCatalog(DocCatalog upperCatalog) {
        this.upperCatalog = upperCatalog;
    }

    @OneToMany(mappedBy = "upperCatalog")
    @JsonIgnore
    public List<DocCatalog> getLowerCatalogs() {
        return lowerCatalogs;
    }

    public void setLowerCatalogs(List<DocCatalog> lowerCatalogs) {
        this.lowerCatalogs = lowerCatalogs;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, level, contentUpdated, folder);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DocCatalog other = (DocCatalog) obj;
        return Objects.equal(this.id, other.id)
                && Objects.equal(this.name, other.name)
                && Objects.equal(this.description, other.description)
                && Objects.equal(this.level, other.level)
                && Objects.equal(this.contentUpdated, other.contentUpdated)
                && Objects.equal(this.folder, other.folder);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("level", level)
                .add("contentUpdated", contentUpdated)
                .add("folder", folder)
                .toString();
    }
}
