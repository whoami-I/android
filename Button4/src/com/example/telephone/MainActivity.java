package com.example.telephone;

import com.example.Button4.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

    private EditText number;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number = (EditText)findViewById(R.id.editText1);
        Button button = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        //ʹ�������Ȳ��
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }
	private void callPhone(){
		String num = number.getText().toString().trim();
		if("".equals(num)){
			Toast.makeText(MainActivity.this, "number can not be empty", Toast.LENGTH_LONG).show();
			return;
		}
		//������ͼ
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:"+num));
		//��绰��ִ����ͼ
		startActivity(intent);
		//����һ���ӿڣ�ʵ�ֳ��󷽷�
       	
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.button1:
			System.out.println("button1 clicked");
			callPhone();
			break;
		case R.id.button2:
			System.out.println("button2 clicked");
			break;
		case R.id.button3:
			System.out.println("button3 clicked");
			break;
		default:
			break;
		}
		
	}
    
}
