import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

public class Gui  {

	private void button1ActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void slider1StateChanged(ChangeEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Dave Lemasonry
		frame1 = new JFrame();
		button1 = new JButton();
		label2 = new JLabel();
		button2 = new JButton();
		button3 = new JButton();
		slider1 = new JSlider();

		//======== frame1 ========
		{
			Container frame1ContentPane = frame1.getContentPane();
			frame1ContentPane.setLayout(new BorderLayout());

			//---- button1 ----
			button1.setText("Go");
			frame1ContentPane.add(button1, BorderLayout.SOUTH);

			//---- label2 ----
			label2.setText("text");
			label2.setMaximumSize(new Dimension(20, 30));
			label2.setMinimumSize(new Dimension(20, 50));
			label2.setAutoscrolls(true);
			frame1ContentPane.add(label2, BorderLayout.NORTH);

			//---- button2 ----
			button2.setText("text");
			frame1ContentPane.add(button2, BorderLayout.WEST);

			//---- button3 ----
			button3.setText("text");
			frame1ContentPane.add(button3, BorderLayout.EAST);
			frame1ContentPane.add(slider1, BorderLayout.CENTER);
			frame1.pack();
			frame1.setLocationRelativeTo(frame1.getOwner());
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Dave Lemasonry
	private JFrame frame1;
	private JButton button1;
	private JLabel label2;
	private JButton button2;
	private JButton button3;
	private JSlider slider1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
