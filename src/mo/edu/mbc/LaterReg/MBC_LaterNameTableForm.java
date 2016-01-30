package mo.edu.mbc.LaterReg;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MBC_LaterNameTableForm extends Activity  {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        try{
        	max_num=Integer.parseInt(bundle.getString("KEY_MAXNUM").toString());
        }catch(Exception e){}
        class_no=bundle.getString("KEY_CLASSNO").toString();
        String[] temp_s=class_no.split("_");
        class_no1=temp_s[1];
        wrong_type=bundle.getString("KEY_WRONGTYPE").toString();
        setContentView(R.layout.nametableform);
        findViews();
        InitailControls();
    }
    private Hashtable<String, String> hashTable = new Hashtable<String, String>();
    public void findViews()
    {
    	nametableform_linearLayout = (LinearLayout)findViewById(R.id.nametableform_linearLayout);
    }
    public void setListener()
    {
    }
    private String class_no="";
    private String class_no1="";
    private String wrong_type="";
    private int max_num=60;
    private int rowCnt=0;
    private int lineCnt=0;
    private LinearLayout[] lls={null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
    private LinearLayout nametableform_linearLayout=null;
    public void InitailControls()
    {
    	Integer[] displayWidthHeight={0,0};
    	mo.edu.mbc.lib.Pub.getDislayWidthHeight(this, displayWidthHeight);
    	lineCnt =displayWidthHeight[0] / 80; if(lineCnt>10) lineCnt=10;
    	rowCnt=max_num/lineCnt+1;    	     if(rowCnt>16) rowCnt=16;
    	int noCnt=0;
        for(int i=0;i<rowCnt;i++)
        {
        	lls[i]=new LinearLayout(this);
        	for(int j=0;j<lineCnt;j++){
        		noCnt++;
        		android.widget.CheckBox ck0=new android.widget.CheckBox(this);
    			if(noCnt>max_num)continue;
    			String txt=Integer.toString(noCnt);	if(noCnt<10) txt="0"+txt;
    			ck0.setText(txt+" ");
    			ck0.setTextSize(ck0.getTextSize()*1.8f);
    			ck0.setOnClickListener(CheckedBox_Click);
    			lls[i].addView(ck0);
        	}	
        	nametableform_linearLayout.addView(lls[i]);
        }
    	android.widget.Button btn=new android.widget.Button(this); 
    	btn.setText(class_no+"_"+wrong_type+"_"+"Submit");
    	btn.setOnClickListener(ShowDialog_Click);
    	nametableform_linearLayout.addView(btn);
      
    	File extDir1 = Environment.getExternalStorageDirectory();
        String dbname="STUDMAINSQLITE.db";
        File dbfile=new File(extDir1,dbname);
        String[] keyfields={"GRADE","CLASS","C_NO"};
        String[] valuefields={"CLASSNO","C_NO","NAME_C","STUD_ID"};
        mo.edu.mbc.lib.Pub.getSqlTohashTable(hashTable, dbfile, "select GRADE||CLASS CLASSNO,STUD_ID,NAME_C,GRADE,CLASS,C_NO from student;",keyfields ,valuefields );
   }
    private OnClickListener CheckedBox_Click=new OnClickListener()
    {
    	public void onClick(View v)
    	{
    		android.widget.CheckBox ck0	=(android.widget.CheckBox)v;
    		String k=class_no1+ck0.getText().toString().split(" ")[0];
    		if(hashTable.containsKey(k)){ 
    			Toast.makeText(MBC_LaterNameTableForm.this, hashTable.get(k).toString(), Toast.LENGTH_SHORT).show();
    		}else{
    			Toast.makeText(MBC_LaterNameTableForm.this, k, Toast.LENGTH_SHORT).show();
    		}
    	}
    };
    private OnClickListener ShowDialog_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
           	 String no_s="";
           	 int noCnt=0;
           	 for(int i=0;i<rowCnt;i++)
           	 {
           	    for(int j=0;j<lineCnt;j++)
           	    {  
           	    	noCnt++;
             		if(noCnt>max_num)continue;
             		android.widget.CheckBox ck0=(android.widget.CheckBox)lls[i].getChildAt(j);
             		if(ck0.isChecked())
             			 no_s=no_s+ck0.getText().toString();
                 }
           	 }
           	 String[] str_a=no_s.split(" ");
           	 String msg="";
           	 File file=mo.edu.mbc.lib.Pub.getCurrentDateFileFromSDCard("disciplineStud");
             SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd_H:mm", new Locale("en", "US"));
             Date today = new Date();
             String time_str = formatter_time.format(today);
             String[] wrg_arr=wrong_type.split("_");
             String wrgid=wrg_arr[0];
             String wrgtype=wrg_arr[1];
             String wrg=wrg_arr[2];
             try 
             {
               	 if(!file.exists() && !file.createNewFile())
                 {
               		 Toast.makeText(v.getContext(),"未能建立檔案!", Toast.LENGTH_LONG).show();     return;}
                	 
               	     FileOutputStream out = new FileOutputStream(file,true);
                	 for(int i=0;i<str_a.length;i++)
                 	 {   
                		 if(!str_a[i].equals(""))
                 	     {
                 		     String k=class_no1+str_a[i];
                 		     String valu="";
                 	 	     String temp_s="";
                 		     if(hashTable.containsKey(k))
                 		     { 
                 			    valu=hashTable.get(k);
                 			    String[] stud_arr=valu.split("_");
                 			    String classno=stud_arr[0];
                 			    String seat=stud_arr[1];
                 			    String name=stud_arr[2];
                 			  	String stud_ref=stud_arr[3];
                 			  	mo.edu.mbc.lib.Wrg.writeStudWrg2File(out, wrgid, wrgtype, wrg, classno, seat, name, stud_ref, "", time_str);
                 			    msg+=class_no+"_"+wrong_type+"_"+str_a[i]+valu+"\n";
                 		     }else
                 		     {
                 			    msg+=class_no+"_"+wrong_type+"_"+str_a[i]+"\n";
                 			    temp_s="#"+class_no+"_"+str_a[i]+"_"+time_str+"_"+wrong_type+"\n";
                 			   out.write(temp_s.getBytes());
                 		      }
                		      
                 	     }
                 	 }
                	 out.flush();
                	 out.close();
               	  	Toast.makeText(MBC_LaterNameTableForm.this, msg, Toast.LENGTH_SHORT).show();
             } catch (IOException e) 
             {  
                  e.printStackTrace();  
                  Toast.makeText(v.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
   	         }         
           	 finish(); 
        }        
    };
}