import javax.swing.JFrame;
import java.awt.*;
import java.util.Random;


public class Graph extends JFrame{
    private Random ran;

    public Graph(){
        super();
        ran = new Random();
        setTitle("Customer Information");
        setSize(1200,800);
        setLocation((dim.width-WIDTH)/2,(dim.height-HEIGHT)/2);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public java.awt.Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    public void drawGraph(Graphics g){
        int Width = getWidth();
        int Height = getHeight();
        int leftMargin = 20;
        int topMargin = 50;
        Graphics2D g2 = (Graphics2D)g;
        int ruler = Height - topMargin - 5;
        int rulerStep = ruler / 10;
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0,Width,Height);
        g2.setColor(Color.DARK_GRAY);

        for(int i=0;i<=10;i++){
            g2.drawString((100-10*i)+"%",5,topMargin+rulerStep*i);
            g2.drawLine(5,topMargin+rulerStep*i,Width,topMargin+rulerStep*i);
        }
        g2.setColor(Color.GREEN);
        for(int i=0;i<4;i++){
            int value = ran.nextInt(Height-topMargin-10)+10;
            int step = (i+1)*40;

            g2.drawRoundRect(leftMargin + step*2, Height-value,40,value,40,10);
            g2.fillRoundRect(leftMargin+step*2,Height-value,40,value,40,10);

            g2.drawString("Custom" + (i+1), leftMargin+step*2,Height-value-5);
        }
    }
}
