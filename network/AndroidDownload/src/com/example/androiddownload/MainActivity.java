package com.example.androiddownload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	private EditText et_path;
	private EditText et_threadNumber;
	private LinearLayout ll_progress;
	private ArrayList<ProgressBar> pgList = new ArrayList<ProgressBar>();
	private String path;
	private int threadCount;
	private int runningThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et_path = (EditText) findViewById(R.id.et_path);
		et_threadNumber = (EditText) findViewById(R.id.et_threadNumber);

		et_path.setText("https://t1.daumcdn.net/potplayer/PotPlayer/Version/Latest/PotPlayerSetup64.exe");
		et_threadNumber.setText("3");
		Button btn_download = (Button) findViewById(R.id.btn_download);
		ll_progress = (LinearLayout) findViewById(R.id.ll_progress);
	}

	public void click_download(View v) {

		String threadNumber_s = et_threadNumber.getText().toString().trim();
		int threadNumber = Integer.parseInt(threadNumber_s);
		threadCount = threadNumber;
		runningThread = threadCount;
		path = et_path.getText().toString().trim();
		// 创建之前先清空
		ll_progress.removeAllViews();
		pgList.clear();

		for (int i = 0; i < threadCount; ++i) {
			ProgressBar pgView = (ProgressBar) View.inflate(
					getApplicationContext(), R.layout.progress, null);
			pgList.add(pgView);
			ll_progress.addView(pgView);
			System.out.println("add success");
		}

		new Thread() {

			public void run() {

				try {
					URL url = new URL(path);
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(5000);
					int code = connection.getResponseCode();
					if (code == 200) {
						// 1、获取所需文件的文件长度
						long contentLength = connection.getContentLength();
						System.out.println(contentLength);
						// 2.创建一个大小一模一样的文件，先申请空间
						RandomAccessFile raf = new RandomAccessFile(Environment
								.getExternalStorageDirectory().getPath()
								+ "/temp.exe", "rw");
						raf.setLength(contentLength);
						// 计算每个线程需要下载的字节数
						long blockSize = contentLength / threadCount;
						for (int i = 0; i < threadCount; ++i) {
							long start = i * blockSize;
							long end = (i + 1) * blockSize - 1;
							// 最后一个线程特殊
							if (i == (threadCount - 1)) {
								end = contentLength - 1;
							}
							// 先设置进度条的最大值
							// pgList.get(i).setMax((int) (end - start));
							System.err.println(i + "thread pg max"
									+ (end - start));
							// 开启多线程下载文件
							DownloadThread downloadThread = new DownloadThread(
									start, end, i);
							downloadThread.start();
						}

					}
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		}.start();

	}

	private class DownloadThread extends Thread {
		private long start;
		private long end;
		private int threadId;
		private int pgLastPos;
		private int pgMax;

		public DownloadThread(long start, long end, int threadId) {
			this.start = start;
			this.end = end;
			this.threadId = threadId;
			this.pgMax = (int) (end - start);
			pgList.get(threadId).setMax(pgMax);
		}

		public void run() {
			long current = 0;
			try {
				// 创建URL对象
				URL url = new URL(path);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(10000);

				// 先查找上次下载的位置
				File file = new File(threadId + ".txt");
				if (file.exists() && file.length() > 0) {
					FileInputStream in = new FileInputStream(file);
					BufferedReader bufread = new BufferedReader(
							new InputStreamReader(in));
					String lastpos_s = bufread.readLine();
					long lastpos = Long.parseLong(lastpos_s);
					pgLastPos = (int) (start - lastpos);
					in.close();
					if (lastpos > start)
						start = lastpos;

					System.out.println(threadId + "线程继续下载  位置：" + start);
				}
				if (start > end) {
					System.out.println(threadId + "thread 传输完毕！");
					return;
				}
				// 设置一个请求头，用来设置接受数据的位置
				connection.setRequestProperty("Range", "bytes=" + start + "-"
						+ end);
				int code = connection.getResponseCode();
				// 在这里是206,206代表部分资源获取成功
				if (code == 206) {
					InputStream inputStream = connection.getInputStream();
					RandomAccessFile rafThread = new RandomAccessFile(
							Environment.getExternalStorageDirectory().getPath()
									+ "/temp.exe", "rw");

					rafThread.seek(start);
					int len = -1;
					long total = 0;
					current = start;
					byte[] buffer = new byte[1024 * 1024 * 5];

					while ((len = inputStream.read(buffer)) != -1) {
						rafThread.write(buffer, 0, len);
						// 实现断点续传,保存每次下载的位置
						total += len;
						current = start + total;

						// 下载时更新进度条的状态
						pgList.get(threadId).setProgress(
								(int) (pgLastPos + total));
						System.out.println(threadId + "thread current pg"
								+ (pgLastPos + total));

					}

					rafThread.close();
					System.out.println(threadId + " thread download finished!");
					synchronized (this) {
						if (--runningThread == 0) {
							for (int i = 0; i < threadCount; ++i) {
								File delfile = new File(getFileFullName(i));
								delfile.delete();
							}
							System.out.println("传输完毕！！！");
						}

					}
					// 最后删除所有的缓存文件

				}
			} catch (Exception e) {
				System.out.println(threadId + " thread exception");
				e.printStackTrace();
			} finally {
				if (current != end) {
					// 说明没下完
					try {
						RandomAccessFile raff = new RandomAccessFile(
								getFileFullName(threadId), "rwd");
						raff.write(String.valueOf(current).getBytes());
						raff.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		String getFileFullName(int i) {

			return Environment.getExternalStorageDirectory().getPath() + "/"
					+ i + ".txt";
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
