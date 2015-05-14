package com.example.teddemo;

import java.io.OutputStream;
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
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//take picture with preview
public class CopyOfHelloWorld extends Activity implements 
SurfaceHolder.Callback,
OnClickListener,
PictureCallback  {
	
	private static final String CAMERA_CONTROLL = "CAMERA_CONTROLL";  
	
	private SurfaceView imageSView;
	private SurfaceHolder surfaceHolder;
	private Camera camera;  
	private Button startButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);
        
        startButton = (Button) findViewById(R.id.startBtn); 
         
        imageSView = (SurfaceView) findViewById(R.id.imageView);
        
        surfaceHolder = imageSView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); 
        surfaceHolder.addCallback(this); 
        
        startButton.setOnClickListener(this);  
        
	}
	


	
	
	@Override  
    public void onPictureTaken(byte[] data, Camera camera) {  
    	Log.d("MYDEBUG", "3");  
        // TODO Auto-generated method stub  
        Uri imageFileUri = getContentResolver().insert(  
                Media.EXTERNAL_CONTENT_URI, new ContentValues());  
        try {  
            OutputStream imageFileOS = getContentResolver().openOutputStream(  
                    imageFileUri);  
            imageFileOS.write(data);  
            imageFileOS.flush();  
            imageFileOS.close();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();  
        }  
        camera.startPreview();  
    }  

	@Override
	public void onClick(View v) {
		Log.d("MYDEBUG", "onClick");  
		camera.takePicture(null, null, null, CopyOfHelloWorld.this);
		Log.d("MYDEBUG", "onClick exit");  
	}
	
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		Log.d("MYDEBUG", "surfaceChanged");  
		camera.startPreview();  
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d("MYDEBUG", "surfaceCreated");  
		int cameraNums = Camera.getNumberOfCameras();  
        Log.e(CAMERA_CONTROLL, cameraNums + "");  
        try {  
            camera = Camera.open(cameraNums - 1);  
        } catch (Exception e) {  
            Log.e(CAMERA_CONTROLL, e.getMessage());  
        }  
        try {  
            camera.setPreviewDisplay(holder);  
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
    		Log.d("MYDEBUG", "setParameters");  
            camera.setParameters(parameters);  
    		Log.d("MYDEBUG", "setParameters2");  
        } catch (Exception e) {  
    		Log.d("MYDEBUG", "surfaceCreated EX");  
            // e.printStackTrace();  
            camera.release();  
        }  
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		camera.stopPreview();  
        camera.release();  
	}

}
