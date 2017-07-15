package com.example.asynctask_app;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MyAsyncTask extends Activity {
	ImageView imageview;
	ProgressBar progressbar;
	String url;
	ProgressBar pg;
	Asynctask asynctask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.async_task);
		imageview = (ImageView) findViewById(R.id.image);
		progressbar = (ProgressBar) findViewById(R.id.progressbar);
		pg = (ProgressBar) findViewById(R.id.progressBar1);
		url = "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fimg.hao224.com%2Fzhuanti%2F20160803%2F1470205507437243.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3631093620%2C1758986378%26fm%3D11%26gp%3D0.jpg";
		asynctask = new Asynctask();
		asynctask.execute(url);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i("Main", "onPause()");
		if (asynctask != null && asynctask.getStatus() == AsyncTask.Status.RUNNING) {
			asynctask.cancel(true);
		}

		super.onPause();
	}

	class Asynctask extends AsyncTask<String, Integer, Bitmap> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			Log.i("AsyncTask", "onPreExecute()");
			// progressbar.setVisibility(View.VISIBLE);
			pg.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			Log.i("AsyncTask", "onPostExecute()");
			// progressbar.setVisibility(View.GONE);
			pg.setVisibility(View.GONE);
			super.onPostExecute(result);
			imageview.setImageBitmap(result);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			Bitmap bitmap = null;

			for (int i = 0; i < 100; i++) {
				if (asynctask.isCancelled()) {
					break;
				}
				publishProgress(i);

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Log.i("AsyncTask", "doINBackground()");
			String url = params[0];

			URLConnection connection;
			InputStream inputStream;

			try {
				connection = new URL(url).openConnection();
				inputStream = connection.getInputStream();
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				Thread.sleep(3000);
				bitmap = BitmapFactory.decodeStream(bufferedInputStream);
				inputStream.close();
				bufferedInputStream.close();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bitmap;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			if (asynctask.isCancelled()) {
				return;
			}
			pg.setProgress(values[0]);
			super.onProgressUpdate(values);

		}

	}
}
