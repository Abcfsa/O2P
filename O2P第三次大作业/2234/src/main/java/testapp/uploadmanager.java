package testapp;

import java.io.*;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

class uploadmanager{
	uploadmanager()
		{}
	public void upfile() {
		String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5t5YgKQZ49qvSPgt1zte";
        String accessKeySecret = "GsgAzhr0cuo73u5xMuyACVsHqR8L0z";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String objectname = "file/" + System.currentTimeMillis()+"/demo/";
        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("C://java_swing_txt/新建文档.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //调用oss实现上传第一个参数bucket名称  第二个参数文件名称  第三个参数输入流
        String url = objectname+"java_swing_txt/新建文档.txt";
        ossClient.putObject("gong-osstest1", url, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        //返回组成的文件url
        String photoUrl = "https://" + "gong-osstest1." + "oss-cn-hangzhou.aliyuncs.com"+ "/" + url;
        System.out.println(photoUrl);

	}
	
}