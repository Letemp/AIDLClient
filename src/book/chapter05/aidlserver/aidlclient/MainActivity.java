package book.chapter05.aidlserver.aidlclient;

import book.chapter05.aidlserver.Song;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private Button getData;//用于获取其他进程数据的按钮
	private EditText name,author;//显示获取的数据的文本编辑框
	private Song songBinder;//用户交互的IBinder对象
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //根据ID找到相应控件
        getData=(Button)findViewById(R.id.getData);
        name=(EditText)findViewById(R.id.name);
        author=(EditText)findViewById(R.id.author);
        final Intent intent=new Intent();//创建一个Intent对象
        intent.setAction("book.chapter05.aidlserver.AIDLServer");//设置Intent特征
        bindService(intent, conn, Service.BIND_AUTO_CREATE);//绑定service
        getData.setOnClickListener(new OnClickListener() {		
        	//添加单击事件处理
			public void onClick(View v) {
				try{
					name.setText(songBinder.getName());//显示获取的数据
					author.setText(songBinder.getAuthor());
				}catch(Exception ex){
					ex.printStackTrace();
				}				
			}
		});
    }
    private ServiceConnection conn=new ServiceConnection() {	
    	//创建ServiceConnection对象
		public void onServiceDisconnected(ComponentName name) {
			songBinder=null;			
		}
		public void onServiceConnected(ComponentName name, IBinder service) {
			songBinder=Song.Stub.asInterface(service);//将代理类转换成IBinder对象
		}
	};
	//销毁时解绑Service
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);
	};
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
