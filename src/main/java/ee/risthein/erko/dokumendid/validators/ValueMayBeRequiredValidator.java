package ee.risthein.erko.dokumendid.validators;

import ee.risthein.erko.dokumendid.entities.DataType;
import ee.risthein.erko.dokumendid.entities.DocAttribute;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author Erko Risthein
 */
public class ValueMayBeRequiredValidator implements ConstraintValidator<ValueMayBeRequired, DocAttribute> {

    @Override
    public boolean isValid(DocAttribute docAttribute, ConstraintValidatorContext constraintValidatorContext) {
        if (docAttribute == null) {
            return false;
        }

        boolean isRequired = docAttribute.getRequired();

        if (isRequired) {
            DataType dataType = docAttribute.getDataType();

            switch (dataType) {
                case STRING:
                    return isNotBlank(docAttribute.getValueText());
                case NUMBER:
                    return docAttribute.getValueNumber() != null;
                case DATE:
                    return docAttribute.getValueDate() != null;
                case SELECT:
                    return docAttribute.getAtrTypeSelectionValue() != null;
            }
        }

        return true;
    }

    @Override
    public void initialize(ValueMayBeRequired valueMayBeRequired) {
    }
}
