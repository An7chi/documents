package ee.risthein.erko.dokumendid.controllers;

import ee.risthein.erko.dokumendid.entities.DocCatalog;
import ee.risthein.erko.dokumendid.entities.Document;
import ee.risthein.erko.dokumendid.services.DocumentClipboard;
import ee.risthein.erko.dokumendid.services.DocumentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Erko Risthein
 */
@Controller
@SessionAttributes(types = DocumentClipboard.class)
public class DocumentListController {

    @Inject
    private DocumentService documentService;

    @RequestMapping({"/", "/catalog"})
    public String documentList(Model model) {
        model.addAttribute("catalogs", documentService.getCatalogsByLevel(1));
        return "documentList";
    }

    @RequestMapping("/catalog/{catalogId}")
    public String documentList(@PathVariable Integer catalogId, Model model) {
        model.addAttribute("catalogs", documentService.getCatalogsByUpperCatalog(catalogId));
        model.addAttribute("documents", documentService.getDocumentsByDocCatalog(catalogId));
        model.addAttribute("breadcrumb", documentService.getBreadcrumb(catalogId));
        DocCatalog upperCatalog = documentService.getCatalog(catalogId).getUpperCatalog();
        if (upperCatalog != null) {
            model.addAttribute("upperCatalogId", upperCatalog.getId());
        }
        return "documentList";
    }

    @RequestMapping(value = "/document/delete", method = POST)
    public String deleteDocument(@RequestParam Integer id, DocumentClipboard clipboard) {
        Document deletedDocument = documentService.delete(id);
        clipboard.removeDocumentId(id);
        return "redirect:/catalog/" + deletedDocument.getDocCatalog().getId() + "/delete/success";
    }

    @RequestMapping("/catalog/{catalogId}/save/success")
    public String saveSuccess(@PathVariable Integer catalogId, Model model) {
        return documentList(catalogId, model.addAttribute("successMsg", "Save successful!"));
    }

    @RequestMapping("/catalog/{catalogId}/delete/success")
    public String deleteSuccess(@PathVariable Integer catalogId, Model model) {
        return documentList(catalogId, model.addAttribute("successMsg", "Delete successful!"));
    }

    @RequestMapping("/document/addToClipboard/{documentId}")
    public String addDocumentToClipboard(@PathVariable Integer documentId, DocumentClipboard clipboard) {
        Document document = documentService.getDocument(documentId);
        clipboard.addDocumentId(document.getId());
        return "redirect:/catalog/" + document.getDocCatalog().getId();
    }

    @RequestMapping(value = "/document/removeFromClipboard", method = POST)
    @ResponseBody
    public String removeDocumentFromClipboard(@RequestParam Integer id, DocumentClipboard clipboard) {
        clipboard.removeDocumentId(id);
        Document document = documentService.getDocument(id);
        return "redirect:/catalog/" + document.getDocCatalog().getId();
    }

    @RequestMapping("/document/paste/{catalogId}/{documentId}")
    public String pasteDocument(@PathVariable Integer catalogId, @PathVariable Integer documentId, DocumentClipboard clipboard) {
        clipboard.removeDocumentId(documentId);
        documentService.moveDocument(documentId, catalogId);
        return "redirect:/catalog/" + catalogId;
    }

    @ModelAttribute("clipboardDocuments")
    public List<Document> getDocStatusTypes(DocumentClipboard documentClipboard) {
        return documentService.getDocuments(documentClipboard.getDocumentIds());
    }

}
