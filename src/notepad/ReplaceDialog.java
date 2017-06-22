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

public class ReplaceDialog extends JDialog {
	public static void main(String[] args) {
		ReplaceDialog dialog = new ReplaceDialog(null);
		dialog.setVisible(true);
	}

	JTextField old = new JTextField(10), now = new JTextField(10);
	JButton next = new JButton("下一个"), previous = new JButton("上一个"), replace = new JButton("替换"),
			replaceAll = new JButton("替换全部");

	public ReplaceDialog(Frame father) {
		super(father);
		setTitle("替换");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(3, 1));
		add(old);
		add(now);
		JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
		buttonPanel.add(next);
		buttonPanel.add(previous);
		buttonPanel.add(replace);
		buttonPanel.add(replaceAll);
		add(buttonPanel);
		setLocationRelativeTo(father);
		pack();
		replace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((Notepad) getOwner()).getSelectedTextTab().replace(now.getText());
			}
		});
		replaceAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((Notepad) getOwner()).getSelectedTextTab().replaceAll(old.getText(), now.getText());
			}
		});
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((Notepad) getOwner()).getSelectedTextTab().findNext(old.getText());
			}
		});
		previous.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((Notepad) getOwner()).getSelectedTextTab().findPrevious(old.getText());
			}
		});
		old.getDocument().addDocumentListener(new DocumentListener() {

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
		((Notepad) getOwner()).getSelectedTextTab().onFindTextChange(old.getText());
	}
}