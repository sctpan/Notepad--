package notepad;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MyFontChooser extends JDialog {
	public static void main(String[] args) {
		MyFontChooser chooser = new MyFontChooser(null);
		chooser.setVisible(true);
		System.out.println(chooser.getSelectedFont());
	}

	String[] fontStyleNames = { "Plain", "Bold", "Italic", "BoldItalic" };
	int[] fontStyleNum = { Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD | Font.ITALIC };
	final int DefaultFontSize = 20;
	int selectedFontStyle;
	Font selectedFont = null;

	JList<String> fontNameList;
	JLabel fontSizeLabel = new JLabel("" + DefaultFontSize);
	JSlider fontSizeProgressBar = new JSlider(JSlider.VERTICAL, 6, 50, DefaultFontSize);
	JTextField sampleField = new JTextField();
	JButton cancel = new JButton("cancel"), ok = new JButton("ok");

	private ActionListener listenFontStyle = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedFontStyle = stringToFontStyle(e.getActionCommand());
			refreshSample();
		}
	};

	public MyFontChooser(JFrame father) {
		super(father, "字体选择", true);

		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		container.add(getFontNamePanel(), BorderLayout.CENTER);
		container.add(getFontStylePanel(), BorderLayout.WEST);
		container.add(getFontSizeBar(), BorderLayout.EAST);
		container.add(getSamplePanel(), BorderLayout.SOUTH);
		setLocationRelativeTo(father);
		pack();
		refreshSample();
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedFont = sampleField.getFont();
				setVisible(false);
			}
		});
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedFont = null;
				setVisible(false);
			}
		});
	}

	Font getSelectedFont() {
		return selectedFont;
	}

	void refreshSample() {
		sampleField
				.setFont(new Font(fontNameList.getSelectedValue(), selectedFontStyle, fontSizeProgressBar.getValue()));
	}

	int stringToFontStyle(String s) {
		for (int i = 0; i < fontStyleNames.length; i++)
			if (fontStyleNames[i].equals(s))
				return fontStyleNum[i];
		return Font.PLAIN;
	}

	private JPanel getSamplePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Font Sample"));
		panel.add(new JScrollPane(sampleField), BorderLayout.CENTER);
		sampleField.setText("这是样例！");
		sampleField.setPreferredSize(new Dimension(100, 50));
		panel.add(cancel, BorderLayout.WEST);
		panel.add(ok, BorderLayout.EAST);
		return panel;
	}

	JPanel getFontNamePanel() {
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontNameList = new JList<String>(fontNames);
		fontNameList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				refreshSample();
			}
		});
		JScrollPane scrollPane = new JScrollPane(fontNameList);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Font Name"));
		panel.add(scrollPane, BorderLayout.CENTER);
		return panel;
	}

	JPanel getFontStylePanel() {
		JPanel panel = new JPanel(new GridLayout(4, 1));
		panel.setBorder(BorderFactory.createTitledBorder("Font Style"));
		ButtonGroup group = new ButtonGroup();
		for (String style : fontStyleNames) {
			JRadioButton button = new JRadioButton(style);
			group.add(button);
			panel.add(button);
			button.addActionListener(listenFontStyle);
		}
		return panel;
	}

	JPanel getFontSizeBar() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(90, 0));
		panel.setBorder(BorderFactory.createTitledBorder("Font Size"));
		panel.add(fontSizeLabel, BorderLayout.NORTH);
		panel.add(fontSizeProgressBar, BorderLayout.CENTER);
		fontSizeProgressBar.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				fontSizeLabel.setText(fontSizeProgressBar.getValue() + "");
				refreshSample();
			}
		});
		return panel;
	}
}
