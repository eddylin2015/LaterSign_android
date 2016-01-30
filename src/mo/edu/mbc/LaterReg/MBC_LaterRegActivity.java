package mo.edu.mbc.LaterReg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
public class MBC_LaterRegActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findView();
        InitailControls();
    }
    private void findView()
    {
        spinner_wrongtype = (android.widget.Spinner) findViewById(R.id.spinner1);
        spinner_class =(android.widget.Spinner) findViewById(R.id.spinner2);
        maxnum_edt=(android.widget.EditText) findViewById(R.id.editText1);
        maxnum_edt.setText("60");
    }
    private android.widget.Spinner spinner_wrongtype =null;
    private android.widget.Spinner spinner_class =null;
    private android.widget.EditText maxnum_edt=null;
    public void InitailControls()
    {
    	LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linnerlayout);
    	android.widget.Button ck0=new android.widget.Button(this);
    	ck0.setText("Submit");
    	ck0.setOnClickListener(ShowNameTableForm_Click);
        linearLayout.addView(ck0);
        
        ArrayAdapter<CharSequence> adapter_wrongtype = ArrayAdapter.createFromResource(
                this, R.array.wrong_type_arr, android.R.layout.simple_spinner_item);
        adapter_wrongtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_wrongtype.setAdapter(adapter_wrongtype);
        spinner_wrongtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            	wrong_type = adapterView.getItemAtPosition(i).toString();
            	return;
            } 
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            } 
        });
        ArrayAdapter<CharSequence> adapter_class = ArrayAdapter.createFromResource(
                this, R.array.class_arr, android.R.layout.simple_spinner_item);
        adapter_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_class.setAdapter(adapter_class);
        spinner_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            	class_no = adapterView.getItemAtPosition(i).toString();
            return;
            } 
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            } 
        });                       
    }
    private String class_no="";
    private String wrong_type="";
    private OnClickListener ShowNameTableForm_Click = new OnClickListener()
    {
                 public void onClick(View v)
                 {
                	 Intent intent = new Intent();
                	 intent.setClass(MBC_LaterRegActivity.this,MBC_LaterNameTableForm.class);
                	 Bundle bundle = new Bundle();
                	 bundle.putString("KEY_CLASSNO", class_no);
                	 bundle.putString("KEY_WRONGTYPE", wrong_type);
                	 bundle.putString("KEY_MAXNUM", maxnum_edt.getText().toString());
                	 intent.putExtras(bundle);
                	 startActivity(intent);
                 }        
    };
    protected static final int MENU_ABOUT = Menu.FIRST;
    protected static final int MENU_LIST = Menu.FIRST+1;
    protected static final int MENU_Quit = Menu.FIRST+2;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
          super.onCreateOptionsMenu(menu);
          menu.add(0, MENU_ABOUT, 0, "關於...");
          menu.add(0, MENU_LIST, 0, "列表");
          menu.add(0, MENU_Quit, 0, "結束");
          return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
     {
         super.onOptionsItemSelected(item);
         switch(item.getItemId()){
              case MENU_ABOUT:
                  openOptionsDialog();
                  break;
              case MENU_LIST:
            	  openListDialog();
            	  break;
             case MENU_Quit:
                  finish();
                 break;
           }
           return true;
    } 
    private void openListDialog() {
    	Intent intent = new Intent();
    	intent.setClass(this, MBC_ListForm.class);
    	startActivity(intent);
    }
    private void openOptionsDialog() {
        //Toast.makeText(MBC_LaterRegActivity.this, "BMI 計算器", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Title").setMessage("Message");
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			@Override
	        public void onClick(DialogInterface dialog, int which) {
	          }
		});
        dialog.setNegativeButton("goole",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Uri uri=Uri.parse(getString(R.string.homepage_uri));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
			}
		});
        dialog.show();
    } 
}