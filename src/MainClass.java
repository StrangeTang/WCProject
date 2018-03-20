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
		boolean isChangeOutFile=false;//是否需要更改输出的文件
		boolean isFileList=false;//是否读取的是目录中所有的.c文件
		boolean isExcept=false;//是否有剔除的读取单词
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
		  // 统计一个文件的字符数，单词数，行数
		  int countChar = 0;
		  int countword = 0;
		  int countline = 0;
		  InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName));
		  //用来读取文件中的数据
		  BufferedReader br = new BufferedReader(isr);//使用缓冲区，可以使用缓冲区的read(),readLine()方法；
		  while(br.read()!=-1)//read()=-1代表数据读取完毕
		  {
		   String s = br.readLine();
		   if (s!=null)
		   {
			countChar += s.length();//字符个数就是字符长度
		   	countword += s.split(" |,").length;//split() 方法用于把一个字符串分割成字符串数组,字符串数组的长度，就是单词个数
		   	if (isExcept){//如果有剔除表的话
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
		   countline++;//因为是按行读取，所以每次增加一即可计算出行的数目
		  }
		  isr.close();//关闭文件
		  countChar++;
		  
		  String Cstring="",Wstring="",lString="";
		  if (c==1)
			  Cstring="字符数："+countChar+"\r\n";
		  if (w==1)
			  Wstring="单词数："+countword+"\r\n";
		  if (l==1)
			  lString="行数："+countline+"\r\n";
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
		  //用来读取文件中的数据
		  BufferedReader br = new BufferedReader(isr);//使用缓冲区，可以使用缓冲区的read(),readLine()方法；
		  String s =null;
		  s = br.readLine();
		  isr.close();//关闭文件
		  return s.split(" ");
	}
	
}
