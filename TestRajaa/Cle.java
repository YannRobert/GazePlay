package TestRajaa;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.KeyCode;

/**
 * Created by initial on 24/03/2017.
 */
public class Cle {
    private final KeyCode keyCode;
    private final BooleanProperty pressedProperty;
    public Cle(final KeyCode keyCode) {
        this.keyCode = keyCode;
        this.pressedProperty = new SimpleBooleanProperty(this, "pressed");
    }
    public KeyCode getKeyCode() {
        return keyCode;
    }

    public boolean isPressed() {
        return pressedProperty.get();
    }

    public void setPressed(final boolean value) {
        pressedProperty.set(value);
    }
}
