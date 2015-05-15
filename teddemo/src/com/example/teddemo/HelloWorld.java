package com.example.teddemo;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

import com.example.teddemo.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class HelloWorld extends Activity implements PictureCallback  {
	
	private static final String CAMERA_CONTROLL = "CAMERA_CONTROLL";  
	
	private Camera camera;  
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);
        
        initCamera();
	}
	
	@Override
    protected void onStop() {  
        super.onStop(); 
        releaseCamera();
    } 
		

	private void initCamera(){
		int cameraNums = Camera.getNumberOfCameras();  
        Log.d(CAMERA_CONTROLL, cameraNums + "");  
        try {  
            camera = Camera.open(cameraNums - 1);  
        } catch (Exception e) {  
            Log.e(CAMERA_CONTROLL, e.getMessage());  
        }  
        try {
            Camera.Parameters parameters = camera.getParameters();  
            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {  
                parameters.set("orientation", "portrait");  
                camera.setDisplayOrientation(90);  
                parameters.setRotation(90);  
            }  
            List<String> colorEffects = parameters.getSupportedColorEffects();  
            if(colorEffects!=null){
	            Iterator<String> cei = colorEffects.iterator();  
	            while (cei.hasNext()) {  
	                String currentEffect = cei.next();  
	                if (currentEffect.equals(Camera.Parameters.EFFECT_SOLARIZE)) {  
	                    parameters  
	                            .setColorEffect(Camera.Parameters.EFFECT_SOLARIZE);  
	                    break;  
	                }  
	            }  
            }
    		  
            camera.setParameters(parameters);  
    		  
        } catch (Exception e) {  
            camera.release();  
        }  
	}
		
	@Override  
    public void onPictureTaken(byte[] data, Camera camera) {  

		Uri imageFileUri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, new ContentValues());  

		try {  
            OutputStream imageFileOS = getContentResolver().openOutputStream(imageFileUri);  
            imageFileOS.write(data);  
            imageFileOS.flush();  
            imageFileOS.close();  
        } catch (Exception e) {  
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();  
        } 
        
        

		sendPost("http://10.128.43.77:8082/AndroidUpload/Default.aspx",data);
    }  

	private void releaseCamera(){
        camera.release();  
	}
	
	public void onClick(View v) {
		
		camera.takePicture(null, null, null, HelloWorld.this);
	}
	
	public static String sendPost(String url, byte[] data) {
		BufferedOutputStream out = null;
		BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out=new BufferedOutputStream(conn.getOutputStream());

            // 发送请求参数
            out.write(data);

            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    } 
	


}
