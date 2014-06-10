package ee.risthein.erko.dokumendid.controllers;

import ee.risthein.erko.dokumendid.entities.DocStatusType;
import ee.risthein.erko.dokumendid.entities.Document;
import ee.risthein.erko.dokumendid.services.DocumentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Erko Risthein
 */
@Controller
@SessionAttributes(types = DocumentSearchForm.class)
public class DocumentSearchController {

    @Inject
    private DocumentService documentService;

    @RequestMapping("/search")
    public String searchDocument(Model model) {
        model.addAttribute("documentSearchForm", new DocumentSearchForm());
        return "documentSearch";
    }

    @RequestMapping(value = "/search", method = POST)
    public String saveDocument(DocumentSearchForm form, Model model) {
        List<Document> result = documentService.search(form);
        System.out.println("#### search result: " + result);
        model.addAttribute("documents", result);
        return "documentSearch";
    }

    @ModelAttribute("docStatusTypes")
    public List<DocStatusType> getDocStatusTypes() {
        return documentService.getDocStatusTypes();
    }

}
