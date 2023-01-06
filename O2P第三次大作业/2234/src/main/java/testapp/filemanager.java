package testapp;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class filemanager{
	
	JTextArea myTextArea;
	DefaultTableModel model;
	App myApp;
	int iconx;
	int icony;
	String iconpath;
	filemanager(JTextArea a,DefaultTableModel b,App c){
		this.myTextArea=a;
		this.model=b;
		this.myApp=c;
	}
	
	 public void saveFile() {
		 
	        JFileChooser jFileChooser = new JFileChooser();
	 
	        jFileChooser.setDialogTitle("打开");
	        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        jFileChooser.showOpenDialog(null);
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
	            bw.write("---DATA IN THE TABLE---");
	            bw.flush();
	            for (int i=0;i<model.getRowCount();i++)
	            	for(int j=0;j<model.getColumnCount();j++)
	            		{bw.write((String)model.getValueAt(i, j));
	            		if(j==(model.getColumnCount()-1))
	            			bw.write("\n");
	            		else
	            			bw.write("\t");
	            		bw.flush();}
	            		
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
	 
	 public void specialsave() {
		 
		 File file = new File("C://java_swing_txt/新建文档.txt");
         if(!file.exists()||file.exists()&&file.isDirectory())
				try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
             bw.write("---DATA IN THE TABLE---\n");
             bw.flush();
             for (int i=0;i<model.getRowCount();i++)
             	for(int j=0;j<model.getColumnCount();j++)
             		{bw.write((String)model.getValueAt(i, j));
             		if(j==(model.getColumnCount()-1))
             			bw.write("\n");
             		else
             			bw.write("\t");
             		bw.flush();}
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
	public void opentxt() {
		FileDialog openFile = new FileDialog(myApp, "打开文件",FileDialog.LOAD);
		openFile.setVisible(true);
		// 获取文件路径
		String filePath = openFile.getDirectory() + openFile.getFile();
	try {
		FileInputStream fis = new FileInputStream(filePath);
		File fname=new File(filePath);
		byte[] content = new byte[fis.available()];
		fis.read(content);		
		myTextArea.setText(new String(content));
		//图片判断结尾
		fis.close();
		myApp.setTitle(fname.getName()+" - 记事本");
    }catch(IOException e3)
    {
     System.out.println("输入输出异常");
    }
	}
	public void openpic() {
	   FileDialog openFile = new FileDialog(myApp, "打开文件",FileDialog.LOAD);
 	   openFile.setVisible(true);
       // 获取文件路径
       iconpath = openFile.getDirectory() + openFile.getFile();
       ImageIcon icon1=new ImageIcon(iconpath);
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
           iconlable.setBounds(300,200,600, 400);
       }else{
           iconlable.setBounds(300,200,iconweidth, iconhigh);
       }
       
       
       myTextArea.add(iconlable);
       try {
           @SuppressWarnings("resource")
		FileInputStream iconin=new FileInputStream(iconpath);
           byte[] conicon=new byte[iconin.available()];
           iconin.read(conicon);
       } catch (FileNotFoundException e1) {
           e1.printStackTrace();
       }

       catch (IOException e1) {
        
           e1.printStackTrace();
       }

	}
	public void savemix() {
		FileDialog saveFile = new FileDialog(myApp, "保存混合文件",FileDialog.SAVE);
		 saveFile.setVisible(true);
		 String sp = saveFile.getDirectory() + saveFile.getFile();
		 
	try    {
			FileOutputStream fos = new FileOutputStream(sp);	
			FileInputStream icsin = new FileInputStream(iconpath);
			byte[] txtby=myTextArea.getText().getBytes();
			byte[] iconby=new byte[icsin.available()];//保存图片数组
			icsin.read(iconby);
			byte[] data3 =copy(txtby,iconby);
			
			data3=add(data3);
			
			fos.write(data3);
			fos.close();
			icsin.close();
			}
	catch (IOException ex) {
			ex.printStackTrace();
			}
	}
	public byte[] add(byte[] a)
	 {  byte[] lengths=new byte[3];
	 int ad=myTextArea.getText().length();
	     byte ad1=(byte)ad;
	     lengths[0]=ad1;
	     
	     int ad2=iconx;
	    byte ad21=(byte)(ad2);
	     lengths[1]=ad21;
	     
	     int ad3=icony;
	     byte ad31=(byte)(ad3);
	      lengths[2]=ad31;  
		byte[] d=copy(lengths,a);
	
		return d;
	 }
	public byte[] copy(byte[] a,byte[] b)
	 {
		byte[] data1=a;
		byte[] data2=b;
		byte[] data3 = new byte[data1.length+data2.length];
		System.arraycopy(data1,0,data3,0,data1.length);
		System.arraycopy(data2,0,data3,data1.length,data2.length);
		return data3;
	 }
	public int[] cutarr(byte[] a)
	  { 
		byte[] ad=new byte[3];
		System.arraycopy(a,0,ad,0,3);
		int t=ad[0]>0?ad[0]:ad[0]+256;
		int t1=ad[1]>0?ad[1]:ad[1]+256;
		int t2=ad[2]>0?ad[2]:ad[2]+256;
		int[] f={t,t1,t2};
		return f;
	  }
	public byte[] noone(byte[] a)
	 {  
		byte[] b=new byte[a.length-3];
		System.arraycopy(a,3,b,0,a.length-3);
		return b;
	 }
	public void showicon(byte[] b,int x,int y) {
	      ImageIcon	icon1=new ImageIcon(b);
        int iconhigh=icon1.getIconHeight();
        int iconweidth=icon1.getIconWidth();
        JLabel iconlable=new JLabel("");
        iconlable.setBounds(x, y, iconweidth, iconhigh);
        iconlable.setIcon(icon1);
            if(iconweidth<600&&iconhigh<400){
          	  iconlable.setBounds(300,200,600, 400);
             }else{
          	   iconlable.setBounds(300,200,iconweidth, iconhigh);
                 }
            myTextArea.add(iconlable);
           }
	public void openmix() {
		//打开混合文件
		FileDialog openFile = new FileDialog(myApp, "打开文件",FileDialog.LOAD);
    	openFile.setVisible(true);
    		// 获取文件路径
    		String filePath = openFile.getDirectory() + openFile.getFile();
    	try {
    		FileInputStream fis = new FileInputStream(filePath);
    		File fname=new File(filePath);
    		byte[] content = new byte[fis.available()];
    		fis.read(content);
    		
    		int[] f=new int[3];
    		f=cutarr(content);
    		int txtlengths =f[0];
    		int ix=f[1];
    		int iy=f[2];
    		content=noone(content);
    		
    		//分开文字图片
    		byte[] txtby=new byte[txtlengths];
    		byte[] iconby=new byte[content.length-txtlengths];
    		System.arraycopy(content,0,txtby,0,txtby.length);
    		System.arraycopy(content,txtby.length,iconby,0,iconby.length);
    		showicon(iconby,ix,iy);
    		
    		myTextArea.setText(new String(txtby));
    		//图片判断结尾
    		fis.close();
    		myApp.setTitle(fname.getName()+" - 记事本");
    	 }
    	catch(IOException e3)
    	 {
    		 System.out.println("输入输出异常");
    		}
	}
}