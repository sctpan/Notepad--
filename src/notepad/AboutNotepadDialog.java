package notepad;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutNotepadDialog extends JDialog {
    
    public AboutNotepadDialog(Frame father){
    	super(father,"关于");
        add(createTitle(), BorderLayout.NORTH);
        add(createMainBody());
        setLocationRelativeTo(father);

        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }
  
    private JPanel createMainBody(){
        String text = "<html><body>" 
                + "作者:&nbsp;&nbsp;sctpan " + "<br>"
                + "<hx><strong>" + "软件功能：" + "<br></strong></hx><ul>"
                + "<li>" + "实现对文本文档的读写操作" + "</li><br>"
        		+ "<li>" + "在编辑文本文档时有必要的辅助操作" + "</li><br>"
        		+ "<li>" + "能够对多种编程语言进行关键字加亮" + "</li></ul><br><br><br>"
        		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
        		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
        		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
        		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
        		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
        		+ "<font color = '#0000FF'>" + "©copyright " + "</font>" + "sctpan" ;
        JLabel label = new JLabel(text);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JPanel panel = new JPanel();
        panel.add(label);
        panel.setBorder(BorderFactory.createEtchedBorder());
        return panel;
    }
   
    private JPanel createTitle(){
        JLabel label = new JLabel("Notepad--  版本1.0");
        label.setFont(new Font("微软雅黑", Font.BOLD, 24));
        JPanel panel = new JPanel();
        panel.add(label);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        return panel;
    }
}
