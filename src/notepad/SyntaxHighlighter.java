package notepad;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.util.ArrayList;

class SyntaxHighlighter implements DocumentListener {
	private ArrayList <HashSet<String>> keywords; 
	private Style keywordstyle;
	private Style keywordstyle_java;	
	private Style keywordstyle_cpp;
	private Style keywordstyle_python;
	private Style normalStyle;
	private int i =0;
	public SyntaxHighlighter(JTextPane editor,int mode) {
		keywords = new ArrayList<HashSet<String>>();
		for(int j=0;j<=3;j++)
		{
			HashSet<String> key = new HashSet<String>();
			keywords.add(key);	
		}
		i = mode;
		normalStyle = ((StyledDocument) editor.getDocument()).addStyle("Keyword_Style", null);
		StyleConstants.setForeground(normalStyle, Color.black);
		
		keywordstyle_java = ((StyledDocument) editor.getDocument()).addStyle("Keyword_Style", null);	
		StyleConstants.setForeground(keywordstyle_java, Color.magenta);
		
		keywordstyle_cpp = ((StyledDocument) editor.getDocument()).addStyle("Keyword_Style", null);
		StyleConstants.setForeground(keywordstyle_cpp, Color.red);
		
		keywordstyle_python = ((StyledDocument) editor.getDocument()).addStyle("Keyword_Style", null);
		StyleConstants.setForeground(keywordstyle_python, Color.orange);
		if(mode==0)
		keywordstyle = keywordstyle_java;
		
		if(mode==1)
		keywordstyle = keywordstyle_cpp;		
		
		if(mode==2)
		keywordstyle = keywordstyle_python;
		
		if(mode==3)
		keywordstyle = normalStyle;
		
		keywords.get(0).add("public");
		keywords.get(0).add("protected");
		keywords.get(0).add("private");
		keywords.get(0).add("_int9");
		keywords.get(0).add("float");
		keywords.get(0).add("double");
		keywords.get(0).add("int");
		keywords.get(0).add("void");
		keywords.get(0).add("class");
		keywords.get(0).add("import");
		keywords.get(0).add("include");
		keywords.get(0).add("abstract");
		keywords.get(0).add("assert");
		keywords.get(0).add("boolean");
		keywords.get(0).add("break");
		keywords.get(0).add("byte");
		keywords.get(0).add("case");
		keywords.get(0).add("catch");
		keywords.get(0).add("char");
		keywords.get(0).add("class");
		keywords.get(0).add("const");
		keywords.get(0).add("continue");
		keywords.get(0).add("default");
		keywords.get(0).add("do");
		keywords.get(0).add("double");
		keywords.get(0).add("else");
		keywords.get(0).add("enum");
		keywords.get(0).add("extends");
		keywords.get(0).add("final");
		keywords.get(0).add("finally");
		keywords.get(0).add("float");
		keywords.get(0).add("for");
		keywords.get(0).add("goto");
		keywords.get(0).add("if");
		keywords.get(0).add("implements");
		keywords.get(0).add("import");
		keywords.get(0).add("instanceof");
		keywords.get(0).add("int");
		keywords.get(0).add("interface");
		keywords.get(0).add("long");
		keywords.get(0).add("native");
		keywords.get(0).add("new");
		keywords.get(0).add("package");
		keywords.get(0).add("private");
		keywords.get(0).add("protected");
		keywords.get(0).add("public");
		keywords.get(0).add("return");
		keywords.get(0).add("strictfp");
		keywords.get(0).add("short");
		keywords.get(0).add("static");
		keywords.get(0).add("super");
		keywords.get(0).add("switch");
		keywords.get(0).add("synchronized");
		keywords.get(0).add("this");
		keywords.get(0).add("throw");
		keywords.get(0).add("throws");
		keywords.get(0).add("transient");
		keywords.get(0).add("try");
		keywords.get(0).add("void");
		keywords.get(0).add("volatile");
		keywords.get(0).add("while");
		
		
		
		keywords.get(1).add("public");
		keywords.get(1).add("protected");
		keywords.get(1).add("private");
		keywords.get(1).add("_int9");
		keywords.get(1).add("float");
		keywords.get(1).add("asm");
		keywords.get(1).add("continue");
		keywords.get(1).add("extern");
		keywords.get(1).add("mutable");
		keywords.get(1).add("short");
		keywords.get(1).add("true");
		keywords.get(1).add("volatile");
		keywords.get(1).add("auto");
		keywords.get(1).add("default");
		keywords.get(1).add("false");
		keywords.get(1).add("namespace");
		keywords.get(1).add("signed");
		keywords.get(1).add("try");
		keywords.get(1).add("wchar_t");
		keywords.get(1).add("bool");
		keywords.get(1).add("delete");
		keywords.get(1).add("float");
		keywords.get(1).add("new");
		keywords.get(1).add("sizeof");
		keywords.get(1).add("typedef");
		keywords.get(1).add("while");
		keywords.get(1).add("break");
		keywords.get(1).add("do");
		keywords.get(1).add("for");
		keywords.get(1).add("operator");
		keywords.get(1).add("static");
		keywords.get(1).add("typeid");
		keywords.get(1).add("case");
		keywords.get(1).add("double");
		keywords.get(1).add("friend");
		keywords.get(1).add("private");
		keywords.get(1).add("static_cast");
		keywords.get(1).add("typename");
		keywords.get(1).add("catch");
		keywords.get(1).add("dynamic_cast");
		keywords.get(1).add("goto");
		keywords.get(1).add("protected");
		keywords.get(1).add("struct");
		keywords.get(1).add("union");
		keywords.get(1).add("char");
		keywords.get(1).add("else");
		keywords.get(1).add("if");
		keywords.get(1).add("public");
		keywords.get(1).add("switch");
		keywords.get(1).add("unsigned");
		keywords.get(1).add("class");
		keywords.get(1).add("enum");
		keywords.get(1).add("inline");
		keywords.get(1).add("register");
		keywords.get(1).add("template");
		keywords.get(1).add("using");
		keywords.get(1).add("const");
		keywords.get(1).add("explicit");
		keywords.get(1).add("int");
		keywords.get(1).add("reinterpret_cast");
		keywords.get(1).add("this");
		keywords.get(1).add("vitual");
		keywords.get(1).add("const_cast");
		keywords.get(1).add("export");
		keywords.get(1).add("long");
		keywords.get(1).add("return");
		keywords.get(1).add("throw");
		keywords.get(1).add("void");

		keywords.get(2).add("False");
		keywords.get(2).add("class");
		keywords.get(2).add("finally");
		keywords.get(2).add("is");
		keywords.get(2).add("return");
		keywords.get(2).add("None");
		keywords.get(2).add("continue");
		keywords.get(2).add("for");
		keywords.get(2).add("else");
		keywords.get(2).add("lambda");
		keywords.get(2).add("True");
		keywords.get(2).add("def");
		keywords.get(2).add("nonlocal");
		keywords.get(2).add("while");
		keywords.get(2).add("and");
		keywords.get(2).add("del");
		keywords.get(2).add("global");
		keywords.get(2).add("not");
		keywords.get(2).add("with");
		keywords.get(2).add("as");
		keywords.get(2).add("elif");
		keywords.get(2).add("if");
		keywords.get(2).add("or");
		keywords.get(2).add("yield");
		keywords.get(2).add("assert");
		keywords.get(2).add("import");
		keywords.get(2).add("pass");
		keywords.get(2).add("break");
		keywords.get(2).add("except");
		keywords.get(2).add("in");
		keywords.get(2).add("raise");
		
		keywords.get(3).add(null);
	}
	
	public void set(int mode) throws BadLocationException{
		i = mode;
		if(mode==0)
		keywordstyle = keywordstyle_java;
		
		if(mode==1)
		keywordstyle = keywordstyle_cpp;
		
		if(mode==2)
		keywordstyle = keywordstyle_python;
		
		if(mode==3)
		keywordstyle = normalStyle;
	}
	
	
	public void Init(JTextPane editor) throws BadLocationException{
		StyledDocument doc = (StyledDocument)editor.getDocument();
			int startpos = 0;
			int endpos = 0;	 
			while(endpos<doc.getLength()&&startpos<doc.getLength()){
				if(isWordCharacter(doc,startpos)){
					endpos = indexOfWordEnd(doc,startpos,0);			
					String word = doc.getText(startpos, endpos-startpos);
					 
					if (keywords.get(i).contains(word)) {
						doc.setCharacterAttributes(startpos, endpos-startpos, keywordstyle, true);
						
					} 
					else {
						doc.setCharacterAttributes(startpos, endpos-startpos, normalStyle, true);
				
					}
					startpos = endpos;
				} else {
					startpos++;
				}
					
			}
	}
	
	public void colouring(StyledDocument doc, int pos, int len) throws BadLocationException {
		int start = indexOfWordStart(doc, pos);
		int end = indexOfWordEnd(doc, pos + len);

		char ch;
		while (start < end) {
			ch = getCharAt(doc, start);
			if (Character.isLetter(ch) || ch == '_' || ch=='#') {
				start = colouringWord(doc, start);
			} else {
				SwingUtilities.invokeLater(new ColouringTask(doc, start, 1, normalStyle));
				++start;
			}
		}
	}
	
	public int colouringWord(StyledDocument doc, int pos) throws BadLocationException {
		int wordEnd = indexOfWordEnd(doc, pos);
		String word = doc.getText(pos, wordEnd - pos);
		String pat = "^//.*?$";
		Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(word);
		if (keywords.get(i).contains(word)) {
			SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, keywordstyle));
		} 
		else {
			SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, normalStyle));
		}

		return wordEnd;
	}

	public char getCharAt(Document doc, int pos) throws BadLocationException {
		return doc.getText(pos, 1).charAt(0);
	}

	public int indexOfWordStart(Document doc, int pos) throws BadLocationException {
		for (; pos > 0 && isWordCharacter(doc, pos - 1); --pos);

		return pos;
	}

	public int indexOfWordEnd(Document doc, int pos) throws BadLocationException {
		for (; isWordCharacter(doc, pos); ++pos);

		return pos;
	}
	
	public int indexOfWordEnd(Document doc, int pos,int a) throws BadLocationException {
		for (; isWordCharacter(doc, pos) && (pos<=doc.getLength()); ++pos);

		return pos;
	}

	public boolean isWordCharacter(Document doc, int pos) throws BadLocationException {
		char ch = getCharAt(doc, pos);
		if (Character.isLetter(ch) || Character.isDigit(ch) || ch == '_' || ch == '#'|| ch=='<' || ch == '>') { return true; }
		return false;
	}

	public void changedUpdate(DocumentEvent e) {

	}

	public void insertUpdate(DocumentEvent e) {
		try {
			colouring((StyledDocument) e.getDocument(), e.getOffset(), e.getLength());
		} catch (BadLocationException e1) {
		}
	}

	public void removeUpdate(DocumentEvent e) {
		try {		
			colouring((StyledDocument) e.getDocument(), e.getOffset(), 0);
		} catch (BadLocationException e1) {
		}
	}

	private class ColouringTask implements Runnable {
		private StyledDocument doc;
		private Style style;
		private int pos;
		private int len;

		public ColouringTask(StyledDocument doc, int pos, int len, Style style) {
			this.doc = doc;
			this.pos = pos;
			this.len = len;
			this.style = style;
		}

		public void run() {
			try {
				doc.setCharacterAttributes(pos, len, style, true);
			} catch (Exception e) {}
		}
	}
}


