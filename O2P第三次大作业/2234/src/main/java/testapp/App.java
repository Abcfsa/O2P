package testapp;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


 /*预期设想的功能：基础文本编辑，插入图片等*/
public class App extends JFrame implements ActionListener {
 
    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private JTextArea myTextArea;
	private JScrollPane my;
	private JMenuBar myMenuBar;
	private JToolBar mytoolBar;
	private JMenu file, edit, source, help,table;
	private JLabel tool_label_time;
	private JMenuItem Cut,Copy,Delete;
	private JMenu upload;
	private UndoManager und;
    private boolean flag = false;
    private int cl = 1;
    private JTable jTable;
    private DefaultTableModel model;
	
    private JPopupMenu popupMenu;
    private int linenum = 1;
    private int columnnum = 1;
    private int length = 0;
	
    private filemanager myfileM;
    private uploadmanager myuploM;
    private tablemanager mytabM;
	//
    @SuppressWarnings("deprecation")
	App() {
    	
    	
    	
        myTextArea = new JTextArea();
        my = new JScrollPane(myTextArea);
        myMenuBar = new JMenuBar();
        
        file= new JMenu("文件(F)");
        edit = new JMenu("编辑(E)");
        source = new JMenu("格式(O)");
        help = new JMenu("帮助(H)");
        upload = new JMenu("上传(U)");
        table = new JMenu("表格");
        myuploM=new uploadmanager();
 
        //设置ALT快捷键
        file.setMnemonic('F');
        edit.setMnemonic('E');
        source.setMnemonic('O');
        help.setMnemonic('H');
        upload.setMnemonic('U');
        
        JMenuItem New = new JMenuItem("新建(N)");
        New.setActionCommand("create");
        New.addActionListener(this);
        New.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_N,InputEvent.CTRL_MASK));
        
        JMenuItem Pic = new JMenuItem("插入图片(Y)");
        Pic.setActionCommand("pic");
        Pic.addActionListener(this);
        Pic.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_Y,InputEvent.CTRL_MASK));

        JMenuItem Open = new JMenuItem("打开(O)");
        Open.setActionCommand("open");
        Open.addActionListener(this);
        Open.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_O,InputEvent.CTRL_MASK));
 
 
        JMenuItem Save = new JMenuItem("保存(S)");
        Save.setActionCommand("save");
        Save.addActionListener(this);
        Save.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_S,InputEvent.CTRL_MASK));
 
 
        JMenuItem NewWindow = new JMenuItem("新窗口(W)");
        NewWindow.setActionCommand("new_window");
        NewWindow.addActionListener(this);
        NewWindow.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_W,InputEvent.CTRL_MASK));
 
 
        JMenuItem SaveAS = new JMenuItem("另存为(A)");
        SaveAS.setActionCommand("SaveAs");
        SaveAS.addActionListener(this);
        SaveAS.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_A,InputEvent.CTRL_MASK));
 
 
        JMenuItem exit = new JMenuItem("退出(X)");
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
 
 
        JMenuItem Paste = new JMenuItem("粘贴(P)");
        Paste.setActionCommand("paste");
        Paste.addActionListener(this);
        Paste.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_P,InputEvent.CTRL_MASK));
 
 
        JMenuItem Time = new JMenuItem("时间/日期(D)");
        Time.setActionCommand("time");
        Time.addActionListener(this);
        Time.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_D,InputEvent.CTRL_MASK));
 
        JMenuItem PicS = new JMenuItem("含图保存(G)");
        PicS.setActionCommand("pics");
        PicS.addActionListener(this);
        PicS.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_G,InputEvent.CTRL_MASK));
        
        JMenuItem PicO = new JMenuItem("含图打开(R)");
        PicO.setActionCommand("pico");
        PicO.addActionListener(this);
        PicO.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_R,InputEvent.CTRL_MASK));
        
        Delete = new JMenuItem("删除(L)");
        Delete.setActionCommand("delete");
        Delete.addActionListener(this);
        Delete.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_L,InputEvent.CTRL_MASK));

        JMenuItem All = new JMenuItem("全选(Q)");
        All.setActionCommand("all_selected");
        All.addActionListener(this);
        All.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_Q,InputEvent.CTRL_MASK));
 
 
        JMenuItem revoke = new JMenuItem("撤销(Z)");
        revoke.setActionCommand("revoke");
        revoke.addActionListener(this);
        revoke.setAccelerator(KeyStroke.getKeyStroke((char)KeyEvent.VK_Z,InputEvent.CTRL_MASK));
 
        JMenuItem Redo = new JMenuItem("恢复(B)");
        Redo.setActionCommand("redo");
        Redo.addActionListener(this);
        Redo.setAccelerator(KeyStroke.getKeyStroke((char)KeyEvent.VK_B,InputEvent.CTRL_MASK));
        
        JMenuItem autoLine = new JCheckBoxMenuItem("自动换行(W)");
        autoLine.setActionCommand("autoLine");
        autoLine.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_W,InputEvent.CTRL_MASK));
        
        JMenuItem Author = new JMenuItem("作者");
        Author.setActionCommand("Author");
        Author.addActionListener(this);
        
        JMenuItem Upload = new JMenuItem("上传到云");
        Upload.setActionCommand("up");
        Upload.addActionListener(this);
        
        JMenuItem addc = new JMenuItem("增加一列");
        addc.setActionCommand("addc");
        addc.addActionListener(this);
        
        JMenuItem addr = new JMenuItem("增加一行");
        addr.setActionCommand("addr");
        addr.addActionListener(this);

        JMenuItem subc = new JMenuItem("删除一列");
        subc.setActionCommand("subc");
        subc.addActionListener(this);

        JMenuItem subr = new JMenuItem("删除一行");
        subr.setActionCommand("subr");
        subr.addActionListener(this);

        
        myMenuBar.add(file);
        myMenuBar.add(edit);
        myMenuBar.add(source);
        myMenuBar.add(help);
        myMenuBar.add(upload);
        myMenuBar.add(table);
        
        table.add(addc);
        table.addSeparator();
        table.add(addr);
        table.addSeparator();
        table.add(subc);
        table.addSeparator();
        table.add(subr);

        
        upload.add(Upload);
        
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
        file.addSeparator();
        file.add(PicS);
        file.addSeparator();
        file.add(PicO);
        
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
        edit.add(All);
        edit.addSeparator();
        edit.add(revoke);
        edit.addSeparator();
        edit.add(Redo);
        
        popupMenu = new JPopupMenu();
        JMenuItem popCut = new JMenuItem("剪切");
        popCut.addActionListener(this);
        popCut.setActionCommand("cut");
        JMenuItem popCopy = new JMenuItem("复制");
        popCopy.addActionListener(this);
        popCopy.setActionCommand("copy");
        JMenuItem popPaste = new JMenuItem("粘贴");
        popPaste.addActionListener(this);
        popPaste.setActionCommand("paste");
        JMenuItem popDel = new JMenuItem("删除");
        popDel.addActionListener(this);
        popDel.setActionCommand("delete");
        JMenuItem popSA = new JMenuItem("全选");
        popSA.addActionListener(this);
        popSA.setActionCommand("all_selected");
        JMenuItem popRevoke = new JMenuItem("撤销");
        popRevoke.addActionListener(this);
        popRevoke.setActionCommand("revoke");
        JMenuItem popRedo = new JMenuItem("恢复");
        popRedo.addActionListener(this);
        popRedo.setActionCommand("redo");
        popupMenu.add(popCut);
        popupMenu.add(popCopy);
        popupMenu.add(popPaste);
        popupMenu.add(popDel);
        popupMenu.add(popSA);
        popupMenu.add(popRevoke);
        popupMenu.add(popRedo);
        
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocation(100, 200);
        this.setResizable(true);

        this.add(mytoolBar, BorderLayout.NORTH);
        
        
        
        

		
		GregorianCalendar c = new GregorianCalendar();  //获取系统时间	
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		tool_label_time = new JLabel("开始时间:" + hour + ":" + min + ":" + second);  //此标签用来显示从时钟获取的时间
		
		JLabel tool_label1 = new JLabel("  第" + linenum + "行  ");
		JLabel tool_label2 = new JLabel("  第"+ columnnum + "列  ");
		JLabel tool_label3 = new JLabel("  共" + length + "字  ");
		
		mytoolBar.add(tool_label1);
		mytoolBar.addSeparator();  //添加分割线
		mytoolBar.add(tool_label2);
		mytoolBar.addSeparator();  //添加分割线
		mytoolBar.add(tool_label3);
		mytoolBar.addSeparator();  //添加分割线
		mytoolBar.add(tool_label_time);
		mytoolBar.addSeparator();
		myTextArea.addCaretListener(new CaretListener() {  //文本改变监听器
			public void caretUpdate(CaretEvent e) {
				JTextArea editArea = (JTextArea)e.getSource();
				try {
					int caretpos = editArea.getCaretPosition();
					linenum = editArea.getLineOfOffset(caretpos);
					columnnum = caretpos - myTextArea.getLineStartOffset(linenum);
					linenum += 1;
					tool_label1.setText("  第" + linenum + "行  ");
					tool_label2.setText("  第"+ columnnum + "列  ");
					length = myTextArea.getText().toString().length();
					tool_label3.setText("  一共" + length + " 字 ");
				} catch (Exception ex) {}
			}
		});


      //创建一维数组，存储标题
        String[] titles = {"类别A","类别B","类别C"};
     
        //创建二维数组，存储数据
        Object[][] data = {
        		{" "," "," "},
        		{" "," "," "},
        		};
     
       
            //通过DefaultTableModel创建JTable
            model = new DefaultTableModel(data,titles);
            jTable = new JTable(model);
            
            
            JPanel jPanel = new JPanel();

            jPanel.add(jTable,BorderLayout.CENTER);
            this.add(jPanel,BorderLayout.SOUTH);

           mytabM=new tablemanager(jTable,model);
           myfileM=new filemanager(myTextArea,model,this);
         //end
           
           class PopupListener extends MouseAdapter
           {
               JPopupMenu popupMenu;
               PopupListener(JPopupMenu popupMenu)
               {
                   this.popupMenu=popupMenu;
               }
               public void mousePressed(MouseEvent e)
               {
                   showPopupMenu(e);
               }
               public void mouseReleased(MouseEvent e)
               {
                   showPopupMenu(e);
               }
               private void showPopupMenu(MouseEvent e)
               {
                   if(e.isPopupTrigger())
                   {
                        //如果当前事件与鼠标事件相关，则弹出菜单
                       popupMenu.show(e.getComponent(),e.getX(),e.getY());
                   }
               }
           }
           MouseListener popupListener=new PopupListener(popupMenu);
           myTextArea.addMouseListener(popupListener);
           
        // 设置自动换行
        autoLine.addActionListener(e -> {
            cl++;
            flag = cl % 2 == 0;
            myTextArea.setLineWrap(flag);
        });
 
 
        und = new UndoManager();// 实现撤销功能
        myTextArea.getDocument().addUndoableEditListener(und);
        
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
            final JButton colorbtn = new JButton("■");
            colorbtn.addActionListener(new ActionListener() {
    
                @Override
                public void actionPerformed(ActionEvent e) {
                    Color color = colorbtn.getForeground();
                    Color co = JColorChooser.showDialog(App.this, "设置字体颜色", color);
                     colorbtn.setForeground(co);
                     myTextArea.setForeground(co);
                }
            });
            return colorbtn;
        }
    
        // 记事本，字体格式
        private void fontStyleAction(JToolBar toolBar) {
            final JCheckBox boldBox = new JCheckBox("粗体");
            final JCheckBox itBox = new JCheckBox("斜体");
            ActionListener actionListener = new ActionListener() {
    
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
            final JComboBox<String> fontSize = new JComboBox<>(fontSizes);
            fontSize.addActionListener(new ActionListener() {
    
                @Override
                public void actionPerformed(ActionEvent e) {
                    int size = Integer.valueOf((String) fontSize.getSelectedItem());
                    Font font = App.this.myTextArea.getFont();
                    App.this.myTextArea.setFont(new Font(font.getName(), font.getStyle(), size));
    
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
                    Font font = App.this.myTextArea.getFont();
                    App.this.myTextArea.setFont(new Font(fontName, font.getStyle(), font.getSize()));
    
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
 
 
    
   
 
    @Override // 所有监听事件
    public void actionPerformed(ActionEvent e) throws java.lang.NullPointerException {
 
        if (e.getActionCommand().equals("create")) { // 新建
            if (myTextArea.getText() == null || "".equals(myTextArea.getText())) {
                return;
            } else {
                int num = JOptionPane.showConfirmDialog(null, "你确定保存吗?", "记事本",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (num == 0) {
                    myfileM.saveFile();
                    this.dispose();
                    new App();
                } else if (num == 1) {
                    this.dispose();
                    new App();
                }
            }
 
        }
 
        if (e.getActionCommand().equals("save")) {//保存
            myfileM.specialsave();
 
        }
 
        if (e.getActionCommand().equals("open")) {//打开
            myfileM.opentxt();
        }
        if (e.getActionCommand().equals("pic")) {//打开
             //按下插图实现
        	myfileM.openpic();
        }
        if (e.getActionCommand().equals("exit"))  {//退出
        	 if (myTextArea.getText() == null || "".equals(myTextArea.getText())) {
                 return;
             } else {
                 int num = JOptionPane.showConfirmDialog(null, "你确定保存吗?", "记事本",
                         JOptionPane.YES_NO_CANCEL_OPTION);
                 if (num == 0) {
                     myfileM.saveFile();
                     this.dispose();
                 } else if (num == 1) {
                     this.dispose();
                 }
             }
        }
        if (e.getActionCommand().equals("pics")) {//新窗口
        	myfileM.savemix();
        }
        if (e.getActionCommand().equals("pico")) {
        	myfileM.openmix();
        }
        if (e.getActionCommand().equals("new_window")) {//新窗口
            new App();
        }
 
        if (e.getActionCommand().equals("SaveAs")) {//另存为
        	myfileM.saveFile();
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
        if (e.getActionCommand().equals("revoke")) {
        	if (und.canUndo())
                und.undo();
        }
        if (e.getActionCommand().equals("redo")) {
        	if (und.canRedo())
                und.redo();
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
        if(e.getActionCommand().equals("up")){
        	myuploM.upfile();

        }
        if(e.getActionCommand().equals("addc")){
        	mytabM.addcol();

        }
        if(e.getActionCommand().equals("addr")){
        	mytabM.addrow();

        }
        if(e.getActionCommand().equals("subc")){
        	mytabM.delcol();
        }
        if(e.getActionCommand().equals("subr")){
        	mytabM.delrow();

        }
    }
    
}


                      
