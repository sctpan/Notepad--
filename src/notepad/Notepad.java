package notepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;


import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;

class TextTab extends JTextPane {
	static Font titleFont = new Font("serif", Font.BOLD,20);
	static Font myFont = new Font("serif", Font.BOLD,24);
	File file;
	UndoManager manager = new UndoManager();

	public TextTab() {
		setFont(myFont);
		setSelectedTextColor(Color.white);
		setSelectionColor(Color.black);
		getDocument().addUndoableEditListener(new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				manager.addEdit(e.getEdit());
			}
		});
	}

	public TextTab(File file) {
		this();
		this.file = file;
		setText(readFile(file));
	}

	String readFile(File file) {
		try {
			FileInputStream cin = new FileInputStream(file);
			byte[] contents = new byte[(int) file.length()];
			cin.read(contents);
			cin.close();
			return new String(contents);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	boolean isDirty() {
		return file == null ? true : !readFile(file).equals(getText());
	}

	void onFindTextChange(String s) {
		enterfind(s);
	}

	private ArrayList<Integer> indexOffind = new ArrayList<Integer>();
	private ArrayList<Integer> indexOfenter = new ArrayList<Integer>();
	private int indexOfnow=0;  

	public void enterfind(String s) {
		/**
		 *查找算法思路：
		 *首先通过正则表达式匹配需要查找的关键词，并将关键词在编辑器文本中的位置保存在数组中。以备以后查找使用。
		 *但是在匹配过程中，由于换行符的存在，关键词的位置会出现偏移，因此需要将换行符的位置保存在一个数组中，
		 *然后计算出相应的偏移量。
		 * 
		 * */
			String mainstring = getText();
			Pattern pattern = Pattern.compile(s);
			Matcher matcher= pattern.matcher(mainstring);//利用正则表达式匹配要查找的关键词
			
			String pat = "\\n";
			Pattern pattern1 = Pattern.compile(pat);
			Matcher matcher1= pattern1.matcher(mainstring);//对换行符进行匹配
			
			if(!indexOfenter.isEmpty()){
				indexOfenter.clear();
			}
			while(matcher1.find()){  //将换行符位置保存在数组中
				indexOfenter.add(matcher1.start());
			}
			
			indexOfenter.add(Integer.MAX_VALUE);
			if(!indexOffind.isEmpty()){
				indexOffind.clear();
			}
			while(matcher.find()){ //将关键词位置保存在数组中
				indexOffind.add(matcher.start());
			}
			if(!indexOffind.isEmpty()){
				int offset = 0;
				for(int i=0;i<indexOfenter.size();i++)  //计算出相应的偏移量
				{
					if(indexOffind.get(0) < indexOfenter.get(i))
					{
						offset = i;
						break;
					}
				}
				select(indexOffind.get(0) -offset, indexOffind.get(0) + s.length()-offset);
			}

	}
	
	public void enterfind(String s,int a) {  //重载方法，用于替换功能
		// TODO Auto-generated method stub
			String mainstring = getText();
			Pattern pattern = Pattern.compile(s);
			Matcher matcher= pattern.matcher(mainstring);
			
			String pat = "\\n";
			Pattern pattern1 = Pattern.compile(pat);
			Matcher matcher1= pattern1.matcher(mainstring);
			
			if(!indexOfenter.isEmpty()){
				indexOfenter.clear();
			}
			while(matcher1.find()){
				indexOfenter.add(matcher1.start());
			}
			
			indexOfenter.add(Integer.MAX_VALUE);
			if(!indexOffind.isEmpty()){
				indexOffind.clear();
			}
			while(matcher.find()){
				indexOffind.add(matcher.start());
			}
			
			indexOfnow = -1;

	}
	
		void findNext(String s) { //查找下一个关键词
		if(!indexOffind.isEmpty()){
			indexOfnow = ++indexOfnow % indexOffind.size(); //关键词数组指针增加1
			int startpos = indexOffind.get(indexOfnow);  //获得当前关键字的位置
			int endpos = startpos + s.length();
			int offset = 0;
			for(int i=0;i<indexOfenter.size();i++)  //计算出相应偏移量
			{
				if(startpos < indexOfenter.get(i))
				{
					offset = i;
					break;
				}
			}

			select(startpos-offset,endpos-offset);		//选中关键字
		}
	}

	void findPrevious(String s) {
		if(!indexOffind.isEmpty()){
			indexOfnow--; //关键词数组指针减少1
			if(indexOfnow<0){
				indexOfnow = indexOffind.size()-1;
			}
			int startpos = indexOffind.get(indexOfnow);//获得当前关键字的位置
			int endpos = startpos + s.length();
			int offset = 0;
			for(int i=0;i<indexOfenter.size();i++) //计算出相应偏移量
			{
				if(startpos < indexOfenter.get(i))
				{
					offset = i;
					break;
				}
			}
				
			select(startpos-offset, endpos-offset); //选中关键字
		}
	}

	void replace(String now,String old) {
		
		if (getSelectedText() == null)
			return;
	
		String s = getText();
		int pos = getSelectionStart();  //标出选中词的位置
		int offset = 0;
		for(int i=0;i<indexOfenter.size();i++)//计算出相应偏移量
		{
			if(pos < indexOfenter.get(i))
			{
				offset = i;
				break;
			}
		}
		setText(s.substring(0, getSelectionStart()+offset) + now + s.substring(getSelectionEnd()+offset));
		//将选中的词替换
		enterfind(old,0);//更新关键词数组
		select(pos, pos + now.length());
	}

	void replaceAll(String old, String now) {
		if (old.isEmpty())
			return;
		setText(getText().replaceAll(old, now));
	}

	void undo() {
		if (manager.canUndo())
			manager.undo();
	}

	void redo() {
		if (manager.canRedo())
			manager.redo();
	}

	boolean save() {
		if (file == null) {
			return saveAs();
		} else {
			writeOut();
			return true;
		}
	}

	void writeOut() {
		try {
			FileWriter cout = new FileWriter(file);
			cout.write(getText());
			cout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	boolean saveAs() {
		JFileChooser chooser = new JFileChooser();
		chooser.showSaveDialog(this);
		File file = chooser.getSelectedFile();
		if (file == null)
			return false;
		else {
			this.file = file;
			writeOut();
			return true;
		}
	}
	

	

}

public class Notepad extends JFrame {
	private SyntaxHighlighter tex;
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new Notepad();
	}

	JTabbedPane tabbedPane;
	String[] fileMenu = { "文件", "新建 |control N", "打开 | control O", "关闭 | control W",
			"关闭全部 | control shift W", "保存 | control S", "另存为 | control shift S", "=", "退出 | control Q" };
	String[] editMenu = { "编辑", "撤销 | control Z", "=", "剪切 | control X", "复制 |control C",
			"粘贴 | control V", "=", "查找 | control F", "替换 | control R","时间/日期 | control B" };
	String[] formatMenu = { "格式", "字体"};
	String[] highlightMenu = {"语法高亮","Java","C++","Python","取消高亮"};
	String[] aboutMenu = {"关于","关于"};
		
	TextTab getSelectedTextTab() {
		JScrollPane pane = (JScrollPane) tabbedPane.getSelectedComponent();
		if (pane == null)
			return null;
		return (TextTab) pane.getViewport().getComponent(0);
	}

	boolean isFileOpened(String path) {
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			JScrollPane pane = (JScrollPane) tabbedPane.getComponentAt(i);
			TextTab tab = (TextTab) pane.getViewport().getComponent(0);
			if (tab.file != null && tab.file.getPath().equals(path)) {
				tabbedPane.setSelectedComponent(pane);
				return true;
			}
		}
		return false;
	}

	void open() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(this);
		File file = chooser.getSelectedFile();
		if (file == null || isFileOpened(file.getPath()))
			return;
		addTextTab(file.getName(), new TextTab(file));
	}

	void addTextTab(String name, TextTab tab) {
		tab.getDocument().addDocumentListener(tex);
		Component pane = new JScrollPane(tab);
		tabbedPane.addTab(name == null ? "file" + (tabbedPane.getComponentCount() +1): name, pane);
		tabbedPane.setSelectedComponent(pane);
	}

	boolean close() {
		JScrollPane pane = (JScrollPane) tabbedPane.getSelectedComponent();
		TextTab it = getSelectedTextTab();
		if (it == null)
			return true;
		if (it.isDirty()) {
			int res = JOptionPane.showConfirmDialog(this, "是否要保存文件?", "保存?",
					JOptionPane.YES_NO_CANCEL_OPTION);
			switch (res) {
			case JOptionPane.OK_OPTION:
				if (it.save()) {
					tabbedPane.remove(pane);
					return true;
				}
				return false;
			case JOptionPane.CANCEL_OPTION:
				return false;
			case JOptionPane.NO_OPTION:
				tabbedPane.remove(pane);
				return true;
			default:
				return false;
			}
		} else {
			tabbedPane.remove(pane);
			return true;
		}
	}

	void font() {
		MyFontChooser fontChooser = new MyFontChooser(this);
		fontChooser.setVisible(true);
		if (fontChooser.getSelectedFont() == null)
			return;
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			JScrollPane pane = (JScrollPane) tabbedPane.getComponentAt(i);
			TextTab tab = (TextTab) pane.getViewport().getComponent(0);
			tab.setFont(fontChooser.getSelectedFont());
		}
		TextTab.myFont = fontChooser.getSelectedFont();
	}

	boolean closeAll() {
		while (getSelectedTextTab() != null)
			if (close() == false)
				return false;
		return true;
	}

	void exit() {
		if (closeAll())
			System.exit(0);
	}

	void find() {
		FindDialog dlg = new FindDialog(this);
		dlg.setVisible(true);
	}

	void replace() {
		ReplaceDialog dlg = new ReplaceDialog(this);
		dlg.setVisible(true);
	}
	
	void highlight(int i){
		TextTab it = getSelectedTextTab();	
		if(it != null){				
				try {
					tex.set(i);
					tex.Init(it);
				} catch (BadLocationException e1) {
				}	
				it.getDocument().addDocumentListener(tex);
		}		
	}
	
	
	
	void about(){
		AboutNotepadDialog dlg = new AboutNotepadDialog(this);
		dlg.setVisible(true);
	}
	void refreshEditMenu() {
		setEditMenuEnabled(getSelectedTextTab() != null);
	}

	void setEditMenuEnabled(boolean enabled) {
		JMenu editMenu = getJMenuBar().getMenu(1);
		for (int i = 0; i < editMenu.getItemCount(); i++) {
			if (editMenu.getItem(i) != null)
				editMenu.getItem(i).setEnabled(enabled);
		}
	}

	ActionListener act = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JScrollPane pane = (JScrollPane) tabbedPane.getSelectedComponent();
			TextTab tab = null;
			if (pane != null) {
				tab = (TextTab) pane.getViewport().getComponent(0);
			}
			switch (e.getActionCommand()) {
			case "退出":
				exit();
				break;
			case "新建":
				addTextTab(null, new TextTab());
				refreshEditMenu();
				break;
			case "关闭":
				close();
				refreshEditMenu();
				break;
			case "关闭全部":
				closeAll();
				break;
			case "打开":
				open();
				refreshEditMenu();
				break;
			case "保存":
				tab.save();
				break;
			case "另存为":
				tab.saveAs();
			case "撤销":
				tab.undo();
				break;
			case "剪切":
				tab.cut();
				break;
			case "复制":
				tab.copy();
				break;
			case "粘贴":
				tab.paste();
				break;
			case "查找":
				find();
				break;
			case "替换":
				replace();
				break;
			case "时间/日期":{
				Date nowTime=new Date();
				SimpleDateFormat times=new SimpleDateFormat("HH:mm yy-MM-dd");
				tab.replaceSelection(times.format(nowTime));
			}
				break;
			case "字体":
				font();
				break;
			case "Java":
				highlight(0);
				break;
			case "C++":
				highlight(1);
				break;
			case "Python":
				highlight(2);
				break;
			case "取消高亮":
				highlight(3);
				break;
			case "关于":
				about();
				break;
			default:
				System.out.println(e.getActionCommand() + " not handled");
			}
		}
	};

	JMenu getMenu(String[] s) {
		JMenu menu = new JMenu(s[0]);
		for (int i = 1; i < s.length; i++) {
			if (s[i].equals("=")) {
				menu.addSeparator();
			} else {
				JMenuItem item = null;
				int pos = s[i].indexOf('|');
				String name = null, key = null;
				if (pos == -1)
					name = s[i];
				else {
					name = s[i].substring(0, pos - 1);
					key = s[i].substring(pos + 1);
				}
				if (name.startsWith("["))
					item = new JCheckBoxMenuItem(name.substring(1));
				else
					item = new JMenuItem(name);
				item.setAccelerator(KeyStroke.getKeyStroke(key));
				item.addActionListener(act);
				menu.add(item);
			}
		}
		return menu;
	}

	Notepad() {
		super("Notepad 1.0");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		setLayout(new BorderLayout());
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(getMenu(fileMenu));
		menuBar.add(getMenu(editMenu));
		menuBar.add(getMenu(formatMenu));
		menuBar.add(getMenu(highlightMenu));
		menuBar.add(getMenu(aboutMenu));			
		setJMenuBar(menuBar);
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(TextTab.titleFont);
		add(tabbedPane, BorderLayout.CENTER);
		addTextTab(null, new TextTab());
		tex = new SyntaxHighlighter(getSelectedTextTab(),0);
		refreshEditMenu();
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);		
	}
}
