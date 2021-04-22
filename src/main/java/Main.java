import javax.swing.*;

import JSONControlling.JSONHandler;
import JSONControlling.OrderedJSONObjectFactory;
import Windows.WindowWikiView;
import com.formdev.flatlaf.FlatDarkLaf;

import java.util.HashMap;

public class Main {

    public static JSONHandler jsonHandler = new JSONHandler();

    public static void main(String[] args) {
        FlatDarkLaf.install();
        UIManager.put( "Button.arc", 0 );
        UIManager.put( "Component.arc", 0 );
        UIManager.put( "CheckBox.arc", 0 );
        UIManager.put( "ProgressBar.arc", 0 );
        UIManager.put( "TextComponent.arc", 0 );

        OrderedJSONObjectFactory.setupFieldAccessor();

        WindowWikiView.boot(jsonHandler);
    }
}
