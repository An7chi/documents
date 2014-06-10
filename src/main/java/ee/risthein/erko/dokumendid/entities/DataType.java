package ee.risthein.erko.dokumendid.entities;

/**
 * Dokumendi atribuudi andmetüüp. Tabelites [doc_attribute_type] ja [doc_attribute] viidatakse sellele
 * andmetüübil.
 * <p/>
 * Võimalikud väärtused:
 * 1 – atribuut on teksti tüüpi
 * 2 – atribuut on number tüüpi
 * 3 – atribuut on kuupäev/timestamp tüüpi
 * 4 – atribuut on valiku tüüpi
 *
 * @author Erko Risthein
 */
public enum DataType {
    UNKNOWN, STRING, NUMBER, DATE, SELECT
}
