package alten.core.exceptions;

/**
 * Gère les erreurs techniques (utilisé pour les logs).
 *
 * @since 1.0
 */
public class TechnicalRelatedError extends RuntimeException {
    public TechnicalRelatedError(String message) {
        super(message);
    }

    public TechnicalRelatedError(Throwable cause) {
        super(cause);
    }

    public TechnicalRelatedError(String message, Throwable cause) {
        super(message, cause);
    }
}
