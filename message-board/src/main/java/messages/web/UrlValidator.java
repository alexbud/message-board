package messages.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Custom URL validator.
 */
public class UrlValidator implements ConstraintValidator<Url, String> {

	@Override
	public void initialize(final Url target) {

	}

	/**
	 * Validates a given url. If an url was inputted then it should start with
	 * 'http://'.
	 */
	@Override
	public boolean isValid(final String url,
			final ConstraintValidatorContext context) {
		if (url != null && url.length() > 0) {
			return url.startsWith("http://");
		}
		return true;
	}
}
