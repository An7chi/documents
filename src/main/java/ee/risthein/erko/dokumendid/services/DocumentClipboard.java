package ee.risthein.erko.dokumendid.services;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Erko Risthein
 */
public class DocumentClipboard {

    private Set<Integer> documentIds = new HashSet<>();

    public Set<Integer> getDocumentIds() {
        return documentIds;
    }

    public void addDocumentId(Integer documentId) {
        documentIds.add(documentId);
    }

    public void removeDocumentId(Integer documentId) {
        documentIds.remove(documentId);
    }

    @Override
    public String toString() {
        return documentIds.toString();
    }
}
