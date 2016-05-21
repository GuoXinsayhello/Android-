import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Append 
{
	static int count=0;
	static int countb=0;

	public static void main(String args[]) throws IOException
	{
		 FileInputStream filea = new FileInputStream("d:\\JavaXor\\a");
		 FileInputStream fileb = new FileInputStream("d:\\JavaXor\\b");
		 File outfile=new File("d:\\JavaXor\\outfile");
		 int filesizea=filea.available();//计算文件的大小
		 int filesizeb=fileb.available();
		 FileOutputStream fos=new FileOutputStream(outfile);
		 
		 int hasReada = 0;
		 int hasReadb=0;
		 
		 byte[] bufa=new byte[1024];
		 byte[] bufc=new byte[1024];
		 byte[] buf_yua=new byte[filesizea%1024];
		 byte[] buf_yub=new byte[filesizeb%1024];
		 
		 while(  (hasReada=filea.read(bufa) )>0 )
	       {
	        if(count<filesizea-filesizea%1024)
	        {	
	        	for(int i=0;i<bufa.length && count<filesizea-filesizea%1024;i++)
	        	 {
	        		
	        		bufc[i]=(byte)(bufa[i] & 0xFF);
	        		count++;
	        		
	        	 }
	        	fos.write(bufc);
	        }
	        else if(count>=filesizea-filesizea%1024 && count<filesizea)
	        {
	        	
	        	for(int j=0; count>=filesizea-filesizea%1024 && count<filesizea ;j++)
	        	{
	        		buf_yua[j]=(byte)(bufa[j] & 0xFF);
	        		count++;
	        		
	        	}
	        	fos.write(buf_yua);
	        }
	        
	        
	        
	       }
		 
		 while(  (hasReadb=fileb.read(bufa) )>0 )
	       {
	        if(countb<filesizeb-filesizeb%1024)
	        {	
	        	for(int i=0;i<bufa.length && countb<filesizeb-filesizeb%1024;i++)
	        	 {
	        		
	        		bufc[i]=(byte)(bufa[i] & 0xFF);
	        		countb++;
	        		
	        	 }
	        	fos.write(bufc);
	        }
	        else if(countb>=filesizeb-filesizeb%1024 && countb<filesizeb)
	        {
	        	
	        	for(int j=0; countb>=filesizeb-filesizeb%1024 && countb<filesizeb ;j++)
	        	{
	        		buf_yub[j]=(byte)(bufa[j] & 0xFF);
	        		countb++;
	        		
	        	}
	        	fos.write(buf_yub);
	        }
	        
	        
	        
	       }
		 
		 
		 
		 
		 
		 
		 
		 
		 
		
	}
	

}


