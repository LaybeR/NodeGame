import javax.swing.*;

import JSONControlling.OrderedJSONObjectFactory;
import Windows.WindowWikiView;
import com.formdev.flatlaf.FlatDarkLaf;

public class Main {

    public static void main(String[] args) {
        FlatDarkLaf.install();
        UIManager.put( "Button.arc", 0 );
        UIManager.put( "Component.arc", 0 );
        UIManager.put( "CheckBox.arc", 0 );
        UIManager.put( "ProgressBar.arc", 0 );
        UIManager.put( "TextComponent.arc", 0 );

        OrderedJSONObjectFactory.setupFieldAccessor();

        WindowWikiView.boot();
    }
}
