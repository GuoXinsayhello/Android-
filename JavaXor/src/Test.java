import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test
{  
	int i=0;
	static int count=0;
    public static void main(String[] args) throws IOException  
    {  
        //创建字节输入流  
        FileInputStream filea = new FileInputStream("d:\\JavaXor\\a");
    FileInputStream fileb = new FileInputStream("d:\\JavaXor\\b");
    File outfile=new File("d:\\JavaXor\\outfile");
    int filesizea=filea.available();//计算文件的大小
    FileOutputStream fos=new FileOutputStream(outfile);
         
        byte[] bufa = new byte[1024];  //存放filea文件的字节数组
        byte[] bufb = new byte[1024];  //存放fileb文件的字节数组
        byte[] bufc = new byte[1024];  //存放两个文件异或后的字节数组
        byte[] buf_yu=new byte[filesizea%1024]; //存放文件异或的最后一部分，因为文件的大小可能不是1024的整数倍，如果继续用bufc的话输出的文件大小会比应有值大
                                                //就是最后一个字节数组没有放满1024个字节
        
        int hasReada = 0;  
        int hasReadb = 0; 
        
      //FileInputStream类的read（）方法把读取的流放在bufa中，并且返回字节的个数赋给hasReada
        //下面的函数就是将文件的最后一部分与其他部分分别对待
      while( ((hasReada=filea.read(bufa))>0) && ((hasReadb=fileb.read(bufb))>0) )
       {
        if(count<filesizea-filesizea%1024)
        {	
        	for(int i=0;i<bufa.length && count<filesizea-filesizea%1024;i++)
        	 {
        		
        		bufc[i]=(byte)((bufa[i]^bufb[i]) & 0xFF);
        		count++;
        		
        	 }
        	fos.write(bufc);
        }
        else if(count>=filesizea-filesizea%1024 && count<filesizea)
        {
        	
        	for(int j=0; count>=filesizea-filesizea%1024 && count<filesizea ;j++)
        	{
        		buf_yu[j]=(byte)((bufa[j]^bufb[j]) & 0xFF);
        		count++;
        		
        	}
        	fos.write(buf_yu);
        }
        	
       }
        System.out.println(count);
        
   
        filea.close(); //关闭输入输出流
        fileb.close(); 
        fos.close();
    }  
}  