import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class Button extends JButton {

    public int i;
    public int j;
    private Color borderColor;
    private final int thickOfBorder;
    private int alpha;

    public Button() {

        alpha = 50;
        borderColor = new Color(20, 20, 255, alpha);

        thickOfBorder = 3;
        setBorder(new LineBorder(borderColor, thickOfBorder));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                alpha = 255;
                borderColor = new Color(20, 20, 255, alpha);
                setBorder(new LineBorder(borderColor, thickOfBorder));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                alpha = 50;
                borderColor = new Color(20, 20, 255, alpha);
                setBorder(new LineBorder(borderColor, thickOfBorder));
            }
        });

    }
}
