import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
public class MainClass {
	public static void main(String[] args) throws Exception {
		
		int cs=0,ws=0,ls=0;
		String fileName="1.c",outFileName="G:/java new projects/WordCount/result.txt";
		boolean isChangeOutFile=false;//�Ƿ���Ҫ����������ļ�
		boolean isFileList=false;//�Ƿ��ȡ����Ŀ¼�����е�.c�ļ�
		boolean isExcept=false;//�Ƿ����޳��Ķ�ȡ����
		for (int i=0;i<args.length;++i)
		{
			if (args[i].contains("-c"))
				cs=1;
			if (args[i].contains("-w"))
				ws=1;
			if (args[i].contains("-l"))
				ls=1;
			if (args[i].contains("-o"))
				isChangeOutFile=true;
			if (args[i].contains("-s"))
				isFileList=true;
			if (args[i].contains("-e"))
				isExcept=true;
			if (args[i].contains(".c"))
				fileName=args[i];
			if (args[i].contains(".txt")&&isChangeOutFile)
				outFileName="G:/java new projects/WordCount/"+args[i];
		}
		if (!isFileList)
			Counts(fileName,outFileName,cs,ws,ls,isFileList,isExcept);		  
		else{
	        File file = new File("G:\\java new projects\\WordCount");
	        String[] files = file.list(new FilenameFilter(){
	            public boolean accept(File dir,String name){
	                return name.endsWith(".c");
	            }
	        });		  		  
	        for(int i=0;i<files.length;i++){
	        	Counts(files[i],outFileName,cs,ws,ls,isFileList,isExcept);
	        }
		}  
       
 //       String[] s =ExceptNameList();
  //      for (int i=0;i<s.length;++i){
  //		  System.out.println(s[i]);
   //     }
}
	
	public static void Counts(String fileName,String outFileName,int c,int w,int l,boolean isFileList,boolean isExcept) throws IOException{
		  // ͳ��һ���ļ����ַ�����������������
		  int countChar = 0;
		  int countword = 0;
		  int countline = 0;
		  InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName));
		  //������ȡ�ļ��е�����
		  BufferedReader br = new BufferedReader(isr);//ʹ�û�����������ʹ�û�������read(),readLine()������
		  while(br.read()!=-1)//read()=-1�������ݶ�ȡ���
		  {
		   String s = br.readLine();
		   if (s!=null)
		   {
			countChar += s.length();//�ַ����������ַ�����
		   	countword += s.split(" |,").length;//split() �������ڰ�һ���ַ����ָ���ַ�������,�ַ�������ĳ��ȣ����ǵ��ʸ���
		   	if (isExcept){//������޳���Ļ�
		   		String[] tmp=ExceptNameList();
		   		String[] tmpS=s.split(" |,");
		   		for (int i =0;i<tmpS.length;++i){
		   			for (int j=0;j<tmp.length;++j){
		   				if (tmpS[i].equals(tmp[j]))
		   					countword--;
		   			}
		   		}
		   	}
		   }
		   countline++;//��Ϊ�ǰ��ж�ȡ������ÿ������һ���ɼ�����е���Ŀ
		  }
		  isr.close();//�ر��ļ�
		  countChar++;
		  
		  String Cstring="",Wstring="",lString="";
		  if (c==1)
			  Cstring="�ַ�����"+countChar+"\r\n";
		  if (w==1)
			  Wstring="��������"+countword+"\r\n";
		  if (l==1)
			  lString="������"+countline+"\r\n";
		  String content = fileName+ "\r\n"+Cstring+Wstring+lString;	  
		  
		  File file = new File(outFileName);
		  if(!file.exists()){
			  file.createNewFile();
			  }
		  FileOutputStream fos = new FileOutputStream(file,isFileList);		  
		  fos.write(content.getBytes());
		  fos.flush();
		  fos.close();
		  
		  System.out.println(fileName);
		  System.out.println("char cont "+countChar);
		  System.out.println("word count "+countword );
		  System.out.println("line count "+countline); 
	}
	
	public static String[] ExceptNameList() throws IOException{
		  InputStreamReader isr = new InputStreamReader(new FileInputStream("except.txt"));
		  //������ȡ�ļ��е�����
		  BufferedReader br = new BufferedReader(isr);//ʹ�û�����������ʹ�û�������read(),readLine()������
		  String s =null;
		  s = br.readLine();
		  isr.close();//�ر��ļ�
		  return s.split(" ");
	}
	
}
