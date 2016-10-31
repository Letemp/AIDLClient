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

	private Button getData;//���ڻ�ȡ�����������ݵİ�ť
	private EditText name,author;//��ʾ��ȡ�����ݵ��ı��༭��
	private Song songBinder;//�û�������IBinder����
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //����ID�ҵ���Ӧ�ؼ�
        getData=(Button)findViewById(R.id.getData);
        name=(EditText)findViewById(R.id.name);
        author=(EditText)findViewById(R.id.author);
        final Intent intent=new Intent();//����һ��Intent����
        intent.setAction("book.chapter05.aidlserver.AIDLServer");//����Intent����
        bindService(intent, conn, Service.BIND_AUTO_CREATE);//��service
        getData.setOnClickListener(new OnClickListener() {		
        	//��ӵ����¼�����
			public void onClick(View v) {
				try{
					name.setText(songBinder.getName());//��ʾ��ȡ������
					author.setText(songBinder.getAuthor());
				}catch(Exception ex){
					ex.printStackTrace();
				}				
			}
		});
    }
    private ServiceConnection conn=new ServiceConnection() {	
    	//����ServiceConnection����
		public void onServiceDisconnected(ComponentName name) {
			songBinder=null;			
		}
		public void onServiceConnected(ComponentName name, IBinder service) {
			songBinder=Song.Stub.asInterface(service);//��������ת����IBinder����
		}
	};
	//����ʱ���Service
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);
	};
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
