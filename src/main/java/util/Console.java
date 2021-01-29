package util;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.OutputStream;

//Klasa która pozwala na wykorzystanie textFieldu jako console output, dzięki czemu można wyświetlać w nim logi
//Przy pomocy sout
public class Console extends OutputStream {
    private final TextArea console;

    private Console(TextArea console) {
        this.console = console;
    }

    public void appendText(String valueOf) {
        Platform.runLater(() -> console.appendText(valueOf));
    }

    public void write(int b) {
        appendText(String.valueOf((char) b));
    }
}
