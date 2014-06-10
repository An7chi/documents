package ee.risthein.erko.dokumendid.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Erko Risthein
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@ReportAsSingleViolation
@Constraint(validatedBy = ValueMayBeRequiredValidator.class)
public @interface ValueMayBeRequired {

    String message() default "{ee.risthein.erko.documendid.constraints.ValueMayBeRequired.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}