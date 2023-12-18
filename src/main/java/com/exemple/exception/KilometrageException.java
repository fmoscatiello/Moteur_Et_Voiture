package com.exemple.exception;
import java.security.PrivilegedActionException;

/**
 * Kilometrage exception qui herite de IllegalArgumentException
 * pour empecher l'attribution de valeur invalide au kilometrage des moteurs;
 */

public class KilometrageException extends IllegalArgumentException {
    //Versionnage de la gestion d'erreur. Change à chaque fois que nous modifions la gestion d'erreur;
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an {@code IllegalArgumentException} with no
     * detail message.
     */
    public KilometrageException() {
    }

    /**
     * Constructs an {@code IllegalArgumentException} with the
     * specified detail message.
     *
     * @param s the detail message.
     */
    public KilometrageException(String s) {
        super(s);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link Throwable#getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link Throwable#getCause()} method).  (A {@code null} value
     *                is permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.5
     */
    public KilometrageException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which
     * typically contains the class and detail message of {@code cause}).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link Throwable#getCause()} method).  (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.5
     */
    public KilometrageException(Throwable cause) {
        super(cause);
    }
}
