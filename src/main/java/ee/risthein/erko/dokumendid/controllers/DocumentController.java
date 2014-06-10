package ee.risthein.erko.dokumendid.controllers;

import ee.risthein.erko.dokumendid.entities.DocStatusType;
import ee.risthein.erko.dokumendid.entities.Document;
import ee.risthein.erko.dokumendid.services.DocumentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Erko Risthein
 */
@Controller
@SessionAttributes(types = Document.class)
public class DocumentController {

    @Inject
    private DocumentService documentService;

    @RequestMapping("/document/add")
    public String addDocument(Model model) {
        model.addAttribute("document", new Document());
        return "documentForm";
    }

    @RequestMapping("/document/add/{catalogId}")
    public String addDocumentToCatalog(@PathVariable Integer catalogId, Model model) {
        model.addAttribute("docTypes", documentService.getDocTypes());
        return "documentType";
    }

    @RequestMapping("/document/add/{catalogId}/{typeId}")
    public String addDocumentToCatalogAsSpecificType(@PathVariable Integer catalogId, @PathVariable Integer typeId, Model model) {
        model.addAttribute("document", documentService.getNewDocument(catalogId, typeId));
        return "documentForm";
    }

    @RequestMapping("/document/edit/{documentId}")
    public String editDocument(@PathVariable Integer documentId, Model model) {
        model.addAttribute("document", documentService.getDocument(documentId));
        return "documentForm";
    }

    @RequestMapping(value = "/document/save", method = POST)
    public String saveDocument(@Valid Document document, BindingResult result) {
        if (result.hasErrors()) {
            return "documentForm";
        }
        documentService.save(document);
        return "redirect:/catalog/" + document.getDocCatalog().getId() + "/save/success";
    }

    @ModelAttribute("docStatusTypes")
    public List<DocStatusType> getDocStatusTypes() {
        return documentService.getDocStatusTypes();
    }

}
