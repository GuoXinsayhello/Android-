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
        //�����ֽ�������  
        FileInputStream filea = new FileInputStream("d:\\JavaXor\\a");
    FileInputStream fileb = new FileInputStream("d:\\JavaXor\\b");
    File outfile=new File("d:\\JavaXor\\outfile");
    int filesizea=filea.available();//�����ļ��Ĵ�С
    FileOutputStream fos=new FileOutputStream(outfile);
         
        byte[] bufa = new byte[1024];  //���filea�ļ����ֽ�����
        byte[] bufb = new byte[1024];  //���fileb�ļ����ֽ�����
        byte[] bufc = new byte[1024];  //��������ļ�������ֽ�����
        byte[] buf_yu=new byte[filesizea%1024]; //����ļ��������һ���֣���Ϊ�ļ��Ĵ�С���ܲ���1024�������������������bufc�Ļ�������ļ���С���Ӧ��ֵ��
                                                //�������һ���ֽ�����û�з���1024���ֽ�
        
        int hasReada = 0;  
        int hasReadb = 0; 
        
      //FileInputStream���read���������Ѷ�ȡ��������bufa�У����ҷ����ֽڵĸ�������hasReada
        //����ĺ������ǽ��ļ������һ�������������ֱַ�Դ�
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
        
   
        filea.close(); //�ر����������
        fileb.close(); 
        fos.close();
    }  
}  