
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
 /*预期设想的功能：基础文本编辑，插入图片和视频，插入LaTeX */
public class Selfmade extends JFrame implements ActionListener {
 
    JTextArea myTextArea;
    JScrollPane my;
    JMenuBar myMenuBar;
    JToolBar mytoolBar;
    JMenu file, edit, source, help;
    JFrame frame;
    JMenuItem New,Pic,Open, Save, NewWindow, exit, SaveAS;
    JMenuItem Cut, Copy, Paste, Delete, Time,  AllSelectd, revoke;
    JMenuItem autoLine;
    JMenuItem Edition,Author;
    String filePath;
    FileInputStream fis;
    byte[] content;
    UndoManager und;
    boolean flag = false;
    int cl = 1;
    FileDialog openFile;  
    ImageIcon icon1;
	FileInputStream iconin;
	byte[] conicon;
	String iconstr;
    String iconpath;
	int iconx;
	int icony;
	byte[] changdu=new byte[3];
    Selfmade() {
 
        myTextArea = new JTextArea();
        my = new JScrollPane(myTextArea);
        myMenuBar = new JMenuBar();
        
        file = new JMenu("文件(F)");
        edit = new JMenu("编辑(E)");
        source = new JMenu("格式(O)");
        help = new JMenu("帮助(H)");
        openFile = new FileDialog(this, "打开文件",FileDialog.LOAD);
 
        //设置ALT快捷键
        file.setMnemonic('F');
        edit.setMnemonic('E');
        source.setMnemonic('O');
        help.setMnemonic('H');
 
        New = new JMenuItem("新建(N)");
        New.setActionCommand("create");
        New.addActionListener(this);
        New.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_N,InputEvent.CTRL_MASK));
        
        Pic = new JMenuItem("插入图片(Y)");
        Pic.setActionCommand("pic");
        Pic.addActionListener(this);
        Pic.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_Y,InputEvent.CTRL_MASK));

        Open = new JMenuItem("打开(O)");
        Open.setActionCommand("open");
        Open.addActionListener(this);
        Open.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_O,InputEvent.CTRL_MASK));
 
 
        Save = new JMenuItem("保存(S)");
        Save.setActionCommand("save");
        Save.addActionListener(this);
        Save.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_S,InputEvent.CTRL_MASK));
 
 
        NewWindow = new JMenuItem("新窗口(W)");
        NewWindow.setActionCommand("new_window");
        NewWindow.addActionListener(this);
        NewWindow.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_W,InputEvent.CTRL_MASK));
 
 
        SaveAS = new JMenuItem("另存为(A)");
        SaveAS.setActionCommand("SaveAs");
        SaveAS.addActionListener(this);
        SaveAS.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_A,InputEvent.CTRL_MASK));
 
 
        exit = new JMenuItem("退出(X)");
        exit.setActionCommand("exit");
        exit.addActionListener(this);
        exit.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_X,InputEvent.CTRL_MASK));
 
 
        Cut = new JMenuItem("剪切(T)");
        Cut.setActionCommand("cut");
        Cut.addActionListener(this);
        Cut.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_T,InputEvent.CTRL_MASK));
 
 
        Copy = new JMenuItem("复制(C)");
        Copy.setActionCommand("copy");
        Copy.addActionListener(this);
        Copy.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_C,InputEvent.CTRL_MASK));
 
 
        Paste = new JMenuItem("粘贴(P)");
        Paste.setActionCommand("paste");
        Paste.addActionListener(this);
        Paste.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_P,InputEvent.CTRL_MASK));
 
 
        Time = new JMenuItem("时间/日期(D)");
        Time.setActionCommand("time");
        Time.addActionListener(this);
        Time.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_D,InputEvent.CTRL_MASK));
 
 
        Delete = new JMenuItem("删除(L)");
        Delete.setActionCommand("delete");
        Delete.addActionListener(this);
        Delete.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_L,InputEvent.CTRL_MASK));

        AllSelectd = new JMenuItem("全选(Q)");
        AllSelectd.setActionCommand("all_selected");
        AllSelectd.addActionListener(this);
        AllSelectd.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_Q,InputEvent.CTRL_MASK));
 
 
        revoke = new JMenuItem("撤销(Z)");
        revoke.setActionCommand("revoke");
        revoke.setAccelerator(KeyStroke.getKeyStroke((char)KeyEvent.VK_Z,InputEvent.CTRL_MASK));
 
        autoLine = new JCheckBoxMenuItem("自动换行(W)");
        autoLine.setActionCommand("autoLine");
        autoLine.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_W,InputEvent.CTRL_MASK));
        
        Author = new JMenuItem("作者");
        Author.setActionCommand("Author");
        Author.addActionListener(this);


        myMenuBar.add(file);
        myMenuBar.add(edit);
        myMenuBar.add(source);
        myMenuBar.add(help);
 
        file.add(New);
        file.addSeparator();
        file.add(Pic);
        file.addSeparator();
        file.add(Open);
        file.addSeparator();
        file.add(Save);
        file.addSeparator();
        file.add(NewWindow);
        file.addSeparator();
        file.add(SaveAS);
        file.addSeparator();
        file.add(exit);
 
        edit.add(Cut);
        edit.addSeparator();
        edit.add(Copy);
        edit.addSeparator();
        edit.add(Paste);
        edit.addSeparator();
        edit.add(Delete);
        edit.addSeparator();
        edit.add(Time);
        edit.addSeparator();
        edit.add(AllSelectd);
        edit.addSeparator();
        edit.add(revoke);
 
        source.add(autoLine);

        help.add(Author);

        mytoolBar = new JToolBar();
		JComboBox<String> fontCom = fontAction();
		mytoolBar.add(fontCom);
		JComboBox<String> fontSize = fontSizeAction();
		mytoolBar.add(fontSize);
		fontStyleAction(mytoolBar);
		JButton colorbtn = fontColorAction();
        mytoolBar.add(colorbtn);

 
        this.add(my);
        this.setTitle("记事本");
        this.setJMenuBar(myMenuBar);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setLocation(100, 200);
        this.setResizable(true);

        this.add(mytoolBar, BorderLayout.NORTH);

        

        // 设置自动换行
        autoLine.addActionListener(e -> {
            cl++;
            flag = cl % 2 == 0;
            myTextArea.setLineWrap(flag);
        });
 
 
        und = new UndoManager();// 实现撤销功能
        myTextArea.getDocument().addUndoableEditListener(und);
        revoke.addActionListener(e -> {
            if (und.canUndo())
                und.undo();
        });
 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    //置窗口是否可以关闭
 
        edit.addMenuListener(new MenuListener() {//当选择编辑时判断可用性
            @Override
            public void menuSelected(MenuEvent e) {
                checkMenuItemEnabled();
            }
 
            @Override
            public void menuDeselected(MenuEvent e) {
                checkMenuItemEnabled();
            }
 
            @Override
            public void menuCanceled(MenuEvent e) {
                checkMenuItemEnabled();
            }
        });
 
    }
        private JButton fontColorAction() {
            JButton colorbtn = new JButton("■");
            colorbtn.addActionListener(new ActionListener() {
    
                @Override
                public void actionPerformed(ActionEvent e) {
                    Color color = colorbtn.getForeground();
                    Color co = JColorChooser.showDialog(Selfmade.this, "设置字体颜色", color);
                     colorbtn.setForeground(co);
                     myTextArea.setForeground(co);
                }
            });
            return colorbtn;
        }
    
        // 记事本，字体格式
        private void fontStyleAction(JToolBar toolBar) {
            JCheckBox boldBox = new JCheckBox("粗体");
            JCheckBox itBox = new JCheckBox("斜体");
            ActionListener actionListener = new ActionListener() {
    
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean bold = boldBox.isSelected();
                    boolean it = itBox.isSelected();
                    int style = (bold ? Font.BOLD : Font.PLAIN) | (it ? Font.ITALIC : Font.PLAIN);
                    Font font = myTextArea.getFont();
                    myTextArea.setFont(new Font(font.getName(), style, font.getSize()));
                    //myTextArea.setFont(new Font(font.getName(), style, font.getSize()));
                }
            };
            boldBox.addActionListener(actionListener);
            itBox.addActionListener(actionListener);
            toolBar.add(boldBox);
            toolBar.add(itBox);
        }
    
        // 记事本，设置字体大小
        private JComboBox<String> fontSizeAction() {
            String[] fontSizes = new String[] { "10", "20", "30", "50" };
            JComboBox<String> fontSize = new JComboBox<>(fontSizes);
            fontSize.addActionListener(new ActionListener() {
    
                @Override
                public void actionPerformed(ActionEvent e) {
                    int size = Integer.valueOf((String) fontSize.getSelectedItem());
                    Font font = Selfmade.this.myTextArea.getFont();
                    Selfmade.this.myTextArea.setFont(new Font(font.getName(), font.getStyle(), size));
    
                }
            });
            return fontSize;
        }
    
        // 记事本，设置字体
        private JComboBox<String> fontAction() {
            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontNames = environment.getAvailableFontFamilyNames();
    
            JComboBox<String> fontCom = new JComboBox<>(fontNames);
    
            fontCom.addActionListener(new ActionListener() {
    
                @Override
                public void actionPerformed(ActionEvent e) {
                    String fontName = (String) fontCom.getSelectedItem();
                    Font font = Selfmade.this.myTextArea.getFont();
                    Selfmade.this.myTextArea.setFont(new Font(fontName, font.getStyle(), font.getSize()));
    
                }
            });
            return fontCom;
        }
    //设置菜单项的可用性：剪切，复制，删除功能
    public void checkMenuItemEnabled() {
            String selectText=myTextArea.getSelectedText();
        if(selectText==null) {
            Cut.setEnabled(false);
            Copy.setEnabled(false);
            Delete.setEnabled(false);
        }
        else {
            Cut.setEnabled(true);
            Copy.setEnabled(true);
            Delete.setEnabled(true);
        }
    }
 
 
 
    public void saveFile() {//保存文件方法
 
        JFileChooser jFileChooser = new JFileChooser();
 
        jFileChooser.setDialogTitle("打开");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//设置模式为只可以选择
        jFileChooser.showOpenDialog(null);//默认值
        jFileChooser.setVisible(true);
 
        String abs = jFileChooser.getSelectedFile().getAbsolutePath();
        FileWriter fw = null;
        BufferedWriter bw = null;
 
        File file = new File(abs);
 
        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
 
            String[] s = myTextArea.getText().split("\n");
            for (String value : s) {
                bw.write(value + "\n");
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert bw != null;
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 

 
    @Override // 所有监听事件
    public void actionPerformed(ActionEvent e) {
 
        if (e.getActionCommand().equals("create")) { // 新建
            if (myTextArea.getText() == null || "".equals(myTextArea.getText())) {
                return;
            } else {
                int num = JOptionPane.showConfirmDialog(null, "你确定保存吗?", "记事本",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (num == 0) {
                    saveFile();
                    this.dispose();
                    new Selfmade();
                } else if (num == 1) {
                    this.dispose();
                    new Selfmade();
                }
            }
 
        }
 
        if (e.getActionCommand().equals("save")) {//保存
            File file = new File("C://java_swing_txt/新建文档.txt");
            FileWriter fw = null;
            BufferedWriter bw = null;
            try {
                fw = new FileWriter(file);
                bw = new BufferedWriter(fw);
 
                String[] s = myTextArea.getText().split("\n");
                for (String value : s) {
                    bw.write(value + "\n");
                    bw.flush();
                }
                System.out.println("保存成功");
            } catch (Exception ee) {
                ee.printStackTrace();
            } finally {
                try {
                    assert bw != null;
                    bw.close();
                    fw.close();
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
 
        }
 
        if (e.getActionCommand().equals("open")) {//打开
            openFile.setVisible(true);
			// 获取文件路径
			filePath = openFile.getDirectory() + openFile.getFile();
		try {
			fis = new FileInputStream(filePath);
			File fname=new File(filePath);
			content = new byte[fis.available()];
			fis.read(content);		
			myTextArea.setText(new String(content));
			//图片判断结尾
			fis.close();
			setTitle(fname.getName()+" - 记事本");
        }catch(IOException e3)
        {
         System.out.println("输入输出异常");
        }
        }
        if (e.getActionCommand().equals("pic")) {//打开
             //按下插图实现
	    	   openFile.setVisible(true);
               // 获取文件路径
               iconpath = openFile.getDirectory() + openFile.getFile();
               icon1=new ImageIcon(iconpath);
               int iconhigh=icon1.getIconHeight();
               int iconweidth=icon1.getIconWidth();
               JLabel iconlable=new JLabel("");
               int x=myTextArea.getCaretPosition();//guangbiao
               int y=myTextArea.getLineCount();
               iconx=x*8;
               icony=y*16;
               iconlable.setBounds(x*8, y*16, iconweidth, iconhigh);
               iconlable.setIcon(icon1);
               if(iconweidth<600&&iconhigh<400){
                   setBounds(300,200,600, 400);
               }else{
                   setBounds(300,200,iconweidth, iconhigh);
               }
               
               
               myTextArea.add(iconlable);
               System.out.println("光标：（"+x+"  ，  "+y+"）");
               try {
                   iconin=new FileInputStream(iconpath);
                   conicon=new byte[iconin.available()];
                   iconin.read(conicon);
                   iconstr=new String(conicon);
               } catch (FileNotFoundException e1) {
                   e1.printStackTrace();
               }

catch (IOException e1) {
                
                   e1.printStackTrace();
               }
        }
        if (e.getActionCommand().equals("exit")) {//退出
            this.dispose();
        }
 
        if (e.getActionCommand().equals("new_window")) {//新窗口
            new Selfmade();
        }
 
        if (e.getActionCommand().equals("SaveAs")) {//另存为
            saveFile();
        }
 
        if (e.getActionCommand().equals("copy")) {//复制
            myTextArea.copy();
        }
 
        if (e.getActionCommand().equals("paste")) {//粘贴
            myTextArea.paste();
        }
 
        if (e.getActionCommand().equals("cut")) {//剪切
            myTextArea.copy();
            myTextArea.cut();
        }
 
        if (e.getActionCommand().equals("all_selected")) {//全选
            myTextArea.selectAll();
        }
 
        
        if (e.getActionCommand().equals("delete")) {
            myTextArea.replaceRange("", myTextArea.getSelectionStart(), myTextArea.getSelectionEnd());
                    }
 
        if (e.getActionCommand().equals("time")) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm yyyy/MM/dd");
            myTextArea.append(dateFormat.format(calendar.getTime()));
        }
        if(e.getActionCommand().equals("Author")){
            JOptionPane.showMessageDialog(null, "版本1.0\n","作者",JOptionPane.PLAIN_MESSAGE);
        }
        
    }
    public static void main(String[] args) {
        new Selfmade();
    }
 
    
}
