package ee.risthein.erko.dokumendid.services;

import com.google.common.collect.Iterables;
import ee.risthein.erko.dokumendid.controllers.DocumentSearchForm;
import ee.risthein.erko.dokumendid.entities.*;
import ee.risthein.erko.dokumendid.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ee.risthein.erko.dokumendid.entities.DataType.SELECT;
import static ee.risthein.erko.dokumendid.entities.DataType.STRING;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class DocumentService {

    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);

    @Inject
    private DocumentRepository documentRepository;
    @Inject
    private CatalogRepository catalogRepository;
    @Inject
    private DocStatusTypeRepository docStatusTypeRepository;
    @Inject
    private DocTypeRepository docTypeRepository;
    @Inject
    private UserAccountRepository userAccountRepository;
    @Inject
    private DocumentSearchRepository documentSearchRepository;


    @Transactional
    public void save(Document document) {
        Integer accountId = getAccountId();
        Date currentTime = new Date();

        setUpdated(document, currentTime);
        setUpdatedBy(document, accountId);
        setStatus(document, currentTime);

        documentRepository.save(document);
    }

    public Document delete(Integer id) {
        Document document = documentRepository.findOne(id);
        log.debug("Deleting document: " + document);
        documentRepository.delete(document);
        return document;
    }

    public Document getDocument(Integer id) {
        log.debug("FIND ONE");
        return documentRepository.findOne(id);
    }

    public Document getNewDocument(Integer catalogId, Integer typeId) {
        Document document = new Document();
        document.setDocCatalog(catalogRepository.findOne(catalogId));
        DocType docType = docTypeRepository.findOne(typeId);
        addDocAttributes(document, docType);
        document.setDocType(docType);
        document.setCreatedBy(getAccountId());
        log.info("New document: " + document);
        return document;
    }

    public List<DocStatusType> getDocStatusTypes() {
        return docStatusTypeRepository.findAll();
    }

    public List<DocType> getDocTypes() {
        return docTypeRepository.findAll();
    }

    public List<DocCatalog> getCatalogsByLevel(Integer level) {
        return catalogRepository.findAllByLevel(level);
    }

    public List<DocCatalog> getCatalogsByUpperCatalog(Integer catalogId) {
        return catalogRepository.findAllByUpperCatalog(catalogId);
    }

    public List<Document> getDocumentsByDocCatalog(Integer catalogId) {
        return documentRepository.findAllByDocCatalog(catalogId);
    }

    @Transactional
    public void moveDocument(Integer documentId, Integer catalogId) {
        Document document = documentRepository.findOne(documentId);
        DocCatalog newCatalog = catalogRepository.findOne(catalogId);
        document.setDocCatalog(newCatalog);
        documentRepository.save(document);
    }

    public List<Document> getDocuments(Iterable<Integer> documentIds) {
        return documentRepository.findAll(documentIds);
    }

    public List<DocCatalog> getBreadcrumb(Integer catalogId) {
        DocCatalog currentCatalog = catalogRepository.getOne(catalogId);
        List<DocCatalog> breadcrumb = new ArrayList<>();
        DocCatalog upperCatalog = currentCatalog.getUpperCatalog();
        if (upperCatalog != null) {
            breadcrumb.addAll(getBreadcrumb(upperCatalog.getId()));
        }
        breadcrumb.add(currentCatalog);
        return breadcrumb;
    }

    public DocCatalog getCatalog(Integer catalogId) {
        return catalogRepository.getOne(catalogId);
    }

    public List<Document> search(DocumentSearchForm form) {
        if (isNotBlank(form.getPersonLastName())) {
            List<UserAccount> users = userAccountRepository.findByLastName(form.getPersonLastName());
            if (users != null && !users.isEmpty()) {
                form.setUsers(users);
            }
        }
        return documentSearchRepository.search(form);
    }

    private void setUpdated(Document document, Date time) {
        document.getDocCatalog().setContentUpdated(time);
        document.setUpdated(time);
    }

    private void setUpdatedBy(Document document, Integer accountId) {
        document.getDocCatalog().setContentUpdatedBy(accountId);
        document.setUpdatedBy(accountId);
    }

    private void setStatus(Document document, Date time) {
        if (document.isNew()) {
            log.debug("Is a new document. Setting created time to " + time);
            document.setCreated(time);
            createNewDocStatus(document, time);
        } else {
            log.debug("Is NOT a new document.");
            updateDocStatus(document, time);
        }
    }

    private void updateDocStatus(Document document, Date currentTime) {
        log.debug("Updating doc status");
        DocStatus oldDocStatus = Iterables.getLast(document.getDocStatuses());
        DocStatusType oldDocStatusType = oldDocStatus.getDocStatusType();
        DocStatusType newDocStatusType = document.getDocStatusType();
        if (!newDocStatusType.equals(oldDocStatusType)) {
            oldDocStatus.setStatusEnd(currentTime);
            createNewDocStatus(document, currentTime);
        }
        log.debug("old status with: " + oldDocStatus);
    }

    private void createNewDocStatus(Document document, Date currentTime) {
        log.debug("Creating a new doc status");
        DocStatus newDocStatus = new DocStatus();
        newDocStatus.setDocStatusType(document.getDocStatusType());
        newDocStatus.setStatusBegin(currentTime);
        newDocStatus.setCreatedBy(getAccountId());
        document.addDocStatus(newDocStatus);
        log.debug("new status: " + newDocStatus);
    }

    private void addDocAttributes(Document document, DocType docType) {
        List<DocTypeAttribute> docTypeAttributes = docType.getDocTypeAttributes();
        for (DocTypeAttribute docTypeAttribute : docTypeAttributes) {
            DocAttribute docAttribute = new DocAttribute();
            DocAttributeType docAttributeType = docTypeAttribute.getDocAttributeType();
            docAttribute.setDocAttributeType(docAttributeType);
            docAttribute.setTypeName(docAttributeType.getTypeName());
            docAttribute.setDataType(docAttributeType.getDataType());
            docAttribute.setRequired(docTypeAttribute.getRequired());
            docAttribute.setOrderBy(docTypeAttribute.getOrderBy());
            setDefaultValues(docAttribute);
            document.addDocAttribute(docAttribute);
        }
    }

    private void setDefaultValues(DocAttribute docAttribute) {
        if (docAttribute.getDataType() == STRING) {
            docAttribute.setValueText(docAttribute.getDocAttributeType().getDefaultValueText());
        } else if (docAttribute.getDataType() == SELECT) {
            docAttribute.setAtrTypeSelectionValue(docAttribute.getDocAttributeType().getDefaultSelection());
        }
    }

    private Integer getAccountId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = userAccountRepository.findByUsername(auth.getName());
        return userAccount.getId();
    }
}