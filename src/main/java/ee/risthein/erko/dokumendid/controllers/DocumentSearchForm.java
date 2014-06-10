package ee.risthein.erko.dokumendid.controllers;

import com.google.common.base.Objects;
import ee.risthein.erko.dokumendid.entities.UserAccount;

import java.util.Collections;
import java.util.List;

/**
 * @author Erko Risthein
 */
public class DocumentSearchForm {

    private Integer documentId;
    private String documentName;
    private String documentDescription;
    private String personLastName;
    private String catalogName;
    private Integer documentStatusTypeId;
    private String documentAttributeValue;
    private List<UserAccount> users = Collections.emptyList();

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Integer getDocumentStatusTypeId() {
        return documentStatusTypeId;
    }

    public void setDocumentStatusTypeId(Integer documentStatusTypeId) {
        this.documentStatusTypeId = documentStatusTypeId;
    }

    public String getDocumentAttributeValue() {
        return documentAttributeValue;
    }

    public void setDocumentAttributeValue(String documentAttributeValue) {
        this.documentAttributeValue = documentAttributeValue;
    }

    public void setUsers(List<UserAccount> users) {
        this.users = users;
    }

    public List<UserAccount> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("documentId", documentId)
                .add("documentName", documentName)
                .add("documentDescription", documentDescription)
                .add("personLastName", personLastName)
                .add("catalogName", catalogName)
                .add("documentStatusTypeId", documentStatusTypeId)
                .add("documentAttributeValue", documentAttributeValue)
                .toString();
    }

}
