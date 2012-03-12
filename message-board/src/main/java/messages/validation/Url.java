package messages.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;


/**
 * URL annotation.
 */
@Documented
@Constraint(validatedBy = UrlValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Url {

	/**
	 * @return message
	 */
	public abstract String message() default "must start with 'http'";

	/**
	 * @return groups
	 */
	public abstract Class[] groups() default {};

	/**
	 * @return payload
	 */
	public abstract Class[] payload() default {};
}