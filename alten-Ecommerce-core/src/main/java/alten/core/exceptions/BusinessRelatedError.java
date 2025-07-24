package alten.core.exceptions;

/**
 * Gère les erreurs fonctionnelles (utilisé pour les logs).
 *
 * @author e.audrain
 * @since 1.0
 */
public class BusinessRelatedError extends RuntimeException {
    public BusinessRelatedError(String message) {
        super(message);
    }
}
