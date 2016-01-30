package mo.edu.mbc.LaterReg;

import java.io.File;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MBC_ListForm extends Activity {
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.listform);
	        findViews();
	        Initalize();
	 }
	 public void findViews()
	 {
		 lv=(android.widget.ListView) findViewById(R.id.listView1);
	 }
	 public void Initalize()
	 {
	        File file=mo.edu.mbc.lib.Pub.getCurrentDateFileFromSDCard("disciplineStud");
	        MyArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
	        lv.setAdapter(MyArrayAdapter);
	        mo.edu.mbc.lib.Pub.readFileToArrayAdapter(MyArrayAdapter, file );
	 }
	 private ListView lv;
	 private ArrayAdapter<String> MyArrayAdapter;
}
/*
MyArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
lv.setAdapter(MyArrayAdapter);
Date today; 
SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", new Locale("en", "US"));
today = new Date();
String date_str = formatter.format(today);
String FILE_NAME = "LaterRecord"+date_str+".txt";
String FILE_PATH = "/mnt/sdcard";   File sdcard_file=new File(FILE_PATH);   if(!sdcard_file.exists())   FILE_PATH = "/sdcard";
File file = new File(FILE_PATH , FILE_NAME);
if(!file.exists()) return;
Scanner scanner=null;
try{
	scanner=new Scanner(file);
	while(scanner.hasNextLine())
	{
		String line=scanner.nextLine();
		MyArrayAdapter.add(line);
	}
}catch(FileNotFoundException fnfe){
}finally{
	scanner.close();
}
MyArrayAdapter.notifyDataSetChanged();
*/