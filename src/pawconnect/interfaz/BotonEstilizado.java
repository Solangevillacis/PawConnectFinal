package pawconnect.interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class BotonEstilizado extends JButton {

    public BotonEstilizado( String texto, Color colorFondo ) {
        super( texto );
        setFont( new Font( "SansSerif", Font.BOLD, 13 ) );
        setForeground( Color.WHITE );
        setBackground( colorFondo );
        setFocusPainted( false );
        setBorderPainted( false );
        setOpaque( true );
        setCursor( new Cursor( Cursor.HAND_CURSOR ) );
        setHorizontalAlignment( SwingConstants.CENTER );
        setPreferredSize( new java.awt.Dimension( 180, 38 ) );
    }
}
