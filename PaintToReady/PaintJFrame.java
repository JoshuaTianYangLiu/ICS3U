import java.awt.*;
import java.util.*;
import javax.swing.JFrame;
import hsa.Console;

public class PaintJFrame extends JFrame{
    PaintJFrame(){
        super("Paint Screen");
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        System.out.println(x+","+y);//these co-ords are relative to the component
    }
}
