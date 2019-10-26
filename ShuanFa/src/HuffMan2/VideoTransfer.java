package HuffMan2;


	
	import java.io.*;


	public class VideoTransfer {

	     //ffmepg文件 安装目录
	     private static String ffmpeg = "D:\\BaiduNetdiskDownload\\Rar\\ffmpeg-4.1.3-win64-static\\bin\\ffmpeg";
	     
	     public static void main(String args[]) {
	        String infile = "D:\\BaiduNetdiskDownload\\Rar\\shiping\\myweb\\09.flv";
	        String outfile = "D:\\BaiduNetdiskDownload\\Rar\\shiping\\myweb\\09.mp4";

	        if(transfer(infile, outfile)) {
	            System.out.println("the transfer is ok!");
	        } else {
	            System.out.println("the transfer is error!");
	        }
	    }
	     
	    public static boolean transfer(String infile,String outfile) {
	        String h264tomp4 = ffmpeg + " -r 5 -i "+infile+" -vcodec copy -f mp4 -y "+outfile;
	        try {
	            Runtime rt = Runtime.getRuntime();
	            Process proc = rt.exec(h264tomp4);
	            InputStream stderr = proc.getErrorStream();
	            InputStreamReader isr = new InputStreamReader(stderr);
	            BufferedReader br = new BufferedReader(isr);
	            String line = null;

	            while ( (line = br.readLine()) != null) {
	                System.out.println(line);
	            }
	            int exitVal = proc.waitFor();
	            System.out.println("Process exitValue: " + exitVal);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	        return true;
	    }

	}


