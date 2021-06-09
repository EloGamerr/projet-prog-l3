package fr.prog.tablut.view.components.generic;

/**
 * All Components that's uses ActionAdaptator must implement this interface.
 * <p>It's to check their properties when action is triggered</p>
 */
public interface GenericComponent {
    /**
     * Check if the component is disabled, generaly for remove all action performed on 
     */
    boolean isDisabled();
}