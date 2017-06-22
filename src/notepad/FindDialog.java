package notepad;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FindDialog extends JDialog {
	public static void main(String[] args) {
		FindDialog dialog = new FindDialog(null);
		dialog.setVisible(true);
	}

	JTextField txt = new JTextField(10);
	JButton next = new JButton("下一个");
	JButton previous = new JButton("上一个");

	public FindDialog(Frame father) {
		super(father);
		setTitle("查找");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(2, 1));
		add(txt);
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(next);
		
		panel.add(previous);
		add(panel);
		setLocationRelativeTo(father);
		pack();
		
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((Notepad) getOwner()).getSelectedTextTab().findNext(txt.getText());
			}
		});
		previous.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((Notepad) getOwner()).getSelectedTextTab().findPrevious(txt.getText());
			}
		});
		txt.getDocument().addDocumentListener(new DocumentListener() {
			

			@Override
			public void removeUpdate(DocumentEvent e) {
				find();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				find();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				find();
			}
		});
	}
	void find() {
		((Notepad) getOwner()).getSelectedTextTab().onFindTextChange(txt.getText());
	}
}
