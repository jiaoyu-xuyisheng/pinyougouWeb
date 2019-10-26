import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class mytext {

	public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
		ClientGlobal.init("D:\\Webpingyougo\\jiaoyu-fastdfs-demo\\src\\main\\resources\\fdfs_client.conf");
		
		TrackerClient trackC=new TrackerClient();
		TrackerServer trackerServer = trackC.getConnection();		
		StorageServer stoser=null;
		StorageClient sclient=new StorageClient(trackerServer, stoser);		
		String[] strings = sclient.upload_file("D:\\aaaaa\\image\\a.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
	
	
	
	
	}

}
