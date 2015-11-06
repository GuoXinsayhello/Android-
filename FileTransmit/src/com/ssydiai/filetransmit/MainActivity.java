package com.ssydiai.filetransmit;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.string;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  {
	private TextView tvMsg;
	private EditText txtIP, txtPort, txtEt;
	private Button btnSend;
	private Handler handler;
	private ServerSocket server;
	private SocketManager socketManager;
	private Button sendreq;
	private EditText edt;
	private String ip;
	private Button starrece;
	private Button testdie;
	Boolean flag=true;
	
	
	
	//以下为加入的代码
	private static int BROADCAST_PORT=9898;
	private static String BROADCAST_IP="224.0.0.1";   
	InetAddress inetAddress=null; 
	Thread t=null;   
	/*发送广播端的socket*/  
    MulticastSocket multicastSocket=null;
    
    
    
    
    
    
    //以上为加入的代码
	
	private String strMessage;
	 private static final String ServerIP = "192.168.191.1";
		private static final int ServerPort = 9999;
		
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvMsg = (TextView)findViewById(R.id.tvMsg);
		txtIP = (EditText)findViewById(R.id.txtIP);
		txtPort = (EditText)findViewById(R.id.txtPort);
		txtEt = (EditText)findViewById(R.id.et);
		btnSend = (Button)findViewById(R.id.btnSend);
		sendreq=(Button)findViewById(R.id.button1);
		edt=(EditText)findViewById(R.id.editText1);
		starrece=(Button)findViewById(R.id.button2);
		testdie=(Button)findViewById(R.id.button3);
		
		btnSend.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), FilesViewActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
				case 0:
					SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
					txtEt.append("\n[" + format.format(new Date()) + "]" + msg.obj.toString());
					break;
				case 1:
					tvMsg.setText(msg.obj.toString());
				case 2:
					Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		};
		Thread listener = new Thread(new Runnable(){
			@Override
			public void run() {
				//绑定端口
				int port = 9999;
				while(port > 9000){
					try {
						server = new ServerSocket(port);
						break;
					} catch (Exception e) {
						port--;
					}
				}
				if (server != null){
					socketManager = new SocketManager(server);
					Message.obtain(handler, 1, "本机IP：" + GetIpAddress() + " 监听端口:" + port).sendToTarget();
					while(true){//接收文件
						String response = socketManager.ReceiveFile();
						Message.obtain(handler, 0, response).sendToTarget();
					}
				}else{
					Message.obtain(handler, 1, "未能绑定端口").sendToTarget();
				}
			}
		});
		listener.start();
		
		
		
		//以下为加入的代码
		
		try 
        {
        	inetAddress=InetAddress.getByName(BROADCAST_IP);
        	multicastSocket=new MulticastSocket(BROADCAST_PORT);
        	multicastSocket.setTimeToLive(1);
        	multicastSocket.joinGroup(inetAddress);
        	new Thread(new rece()).start();
        
        }catch(Exception e)
        {
        	e.printStackTrace();
        	
        }
     /*    t=new Thread(this); 
         t.start(); 
		*/
		
		
		
	}
	
	
 

	
	
	
	
	//以下为自己添加的
	
	
	/*public void star_rece(View view)
	{
		flag=true;
		
		new Thread(new rece()).start();
	}*/
	public void testdie(View view)
	{
		
		Toast.makeText(getApplicationContext(), "notdie", Toast.LENGTH_SHORT).show();
	}
	
	
	
	public void sendreq(View view)
	{
		String infostr=null;
	 infostr = edt.getText().toString();
		
		strMessage=infostr;
		strMessage=searchfile(infostr);
		strMessage=strMessage+"@"+GetIpAddress();
		flag=true;
		new Thread(new Multi()).start();
		new Thread(new Client()).start(); 
        new Thread(new rece()).start();
		
	}
	
	
	
public String searchfile(String keyword)
{
	File[] files = new File("/storage/sdcard0/").listFiles();
	
	
	for (File file : files)
	{
		if(file.getName().indexOf(keyword) >= 0)
		{
			Toast.makeText(getApplicationContext(), "yes, wehave",Toast.LENGTH_LONG).show();
			keyword=keyword+"-"+file.getName();
		}
		
	}
	
	return keyword;
}
	public class Client implements Runnable {  
	       @Override  
	       public void run() {  
	           
try {  
	                Thread.sleep(500);  
	            } catch (InterruptedException e1) {  
	               // TODO Auto-generated catch block  
	               e1.printStackTrace();  
	           }  
	           try {  
	               InetAddress serverAddr = InetAddress.getByName(ServerIP);  
	     
	           
	               DatagramSocket socket = new DatagramSocket();  
	               byte[] buf;  
	              
	                 
	               buf=strMessage.getBytes();
	                
	              DatagramPacket packet = new DatagramPacket(buf, buf.length,  
	                       serverAddr, ServerPort);  
	              
	                socket.send(packet);  
	                
	               
	              
	            } catch (Exception e) {  
	               
	            }  
	        }  
	    }  
	
	
	public class Multi implements Runnable {  
		public void run()  
		{ 
			
			
			try 
	        {
				 /*MulticastSocket multicastSocket=null;*/
				 
				 
				 
	        	inetAddress=InetAddress.getByName(BROADCAST_IP);
	        	multicastSocket=new MulticastSocket(BROADCAST_PORT);
	        	multicastSocket.setTimeToLive(1);
	        	multicastSocket.joinGroup(inetAddress);
	        	
	        
	        }catch(Exception e)
	        {
	        	e.printStackTrace();
	        	
	        }
			
			
	    	//发送的数据包，局网内的所有地址都可以收到该数据包  
	        DatagramPacket dataPacket = null;          
	        //将本机的IP（这里可以写动态获取的IP）地址放到数据包里，其实server端接收到数据包后也能获取到发包方的IP的  
	        byte[] data =strMessage.getBytes();   
	        dataPacket = new DatagramPacket(data, data.length, inetAddress,BROADCAST_PORT);  
			while(true)  
			{
				if(flag==true) 
				{
					try  
			        {  
			           multicastSocket.send(dataPacket); 
			           Thread.sleep(3000);  
			           System.out.println("再次发送ip地址广播:.....");  
			           
			           
			         /*  Toast.makeText(getApplicationContext(), "发送是可以的",Toast.LENGTH_SHORT).show();*/
			        } catch (Exception e)              
			        {    
			            e.printStackTrace();     
			        } 
				}
			} 
		}
		
	}
	

	public class rece extends Thread implements Runnable {  

		public void run( )    
		{   
			/* multicastSocket=null;
			try 
	        {
				 MulticastSocket multicastSocket=null;
				 
				 
				 
	        	inetAddress=InetAddress.getByName(BROADCAST_IP);
	        	multicastSocket=new MulticastSocket(BROADCAST_PORT);
	        	multicastSocket.setTimeToLive(1);
	        	multicastSocket.joinGroup(inetAddress);
	        	
	        
	        }catch(Exception e)
	        {
	        	e.printStackTrace();
	        	
	        }*/
			
			
			
			// TODO Auto-generated method stub
			Message msg=new Message();
			msg.what=1;
			
			byte buf[]=null;
			 buf = new byte[1024];  
			
			
	        DatagramPacket dp =null;    
	        dp=new DatagramPacket(buf,buf.length,inetAddress,BROADCAST_PORT); 
			 
		        while (flag==true)     
		        {      
		            try                 
		            {        
		            
		                multicastSocket.receive(dp);
		                Thread.sleep(3000);
		                ip=new String(buf, 0, dp.getLength());
		                msg.obj=ip;     
		                myHandler.sendMessage(msg);
		                System.out.println("检测到服务端IP : "+ip);
		               /* Toast.makeText(getApplicationContext(), "接收时可疑的",Toast.LENGTH_LONG).show();*/
		               /* multicastSocket.close();  */
		               flag=false;
		                
		               /* Toast.makeText(getApplicationContext(), ip, Toast.LENGTH_LONG).show();*/
		              //  Toast.makeText(this, new String(buf, 0, dp.getLength()), Toast.LENGTH_SHORT);
		            } catch (Exception e)    
		            {      
		                e.printStackTrace();          
		            }    
		        }    
		} 

		 Handler  myHandler=new Handler() 
	    {
			
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				if(msg.what==1)
				{ 
					Toast.makeText(getApplicationContext(), ip, Toast.LENGTH_SHORT).show();
				
				}
			}
	    };
		
		
	}
	
	
	
	//以上为自己添加的
	
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//选择了文件发送
		if (resultCode == RESULT_OK){
			final String fileName = data.getStringExtra("FileName");
			final String path = data.getStringExtra("FilePath");
			final String ipAddress = txtIP.getText().toString();
			final int port = Integer.parseInt(txtPort.getText().toString());
			Message.obtain(handler, 0, fileName + " 正在发送至" + ipAddress + ":" +  port).sendToTarget();
			Thread sendThread = new Thread(new Runnable(){
				@Override
				public void run() {
					String response = socketManager.SendFile(fileName, path, ipAddress, port);
					Message.obtain(handler, 0, response).sendToTarget();
				}
			});
			sendThread.start();
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
		multicastSocket.close();     
		System.out.println("UDP Server程序退出,关掉socket,停止广播");
		finish(); 
	}
	public String GetIpAddress() {     
	    WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);     
	    WifiInfo wifiInfo = wifiManager.getConnectionInfo();     
	    int i = wifiInfo.getIpAddress();
	    return (i & 0xFF) + "." +     
	    	   ((i >> 8 ) & 0xFF) + "." +     
	    	   ((i >> 16 ) & 0xFF)+ "." +     
	           ((i >> 24 ) & 0xFF );     
	}    
}
