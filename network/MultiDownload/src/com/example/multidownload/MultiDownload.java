package com.example.multidownload;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MultiDownload {
	static String path = "https://t1.daumcdn.net/potplayer/PotPlayer/Version/Latest/PotPlayerSetup64.exe";
	static final int threadCount = 3;

	public static void main(String[] args) {

		try {
			URL url = new URL(path);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			int code = connection.getResponseCode();
			if (code == 200) {
				// 1、获取所需文件的文件长度
				long contentLength = connection.getContentLengthLong();
				System.out.println(contentLength);
				// 2.创建一个大小一模一样的文件，先申请空间
				RandomAccessFile raf = new RandomAccessFile("temp.exe", "rw");
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

					// 开启多线程下载文件
					DownloadThread downloadThread = new DownloadThread(start,
							end, i);
					downloadThread.start();
				}
				// InputStream inputStream =
				// connection.getInputStream();

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private static class DownloadThread extends Thread {
		private long start;
		private long end;
		private int threadId;

		public DownloadThread(long start, long end, int threadId) {
			this.start = start;
			this.end = end;
			this.threadId = threadId;
		}

		public void run() {

			try {
				// 创建URL对象
				URL url = new URL(path);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(10000);
				// 设置一个请求头，用来设置接受数据的位置
				connection.setRequestProperty("Range", "bytes=" + start + "-"
						+ end);
				int code = connection.getResponseCode();
				if (code == 206) {
					InputStream inputStream = connection.getInputStream();
					RandomAccessFile rafThread = new RandomAccessFile(
							"temp.exe", "rw");
					rafThread.seek(start);
					int len = -1;
					byte[] buffer = new byte[1024];
					while ((len = inputStream.read(buffer)) != -1) {
						rafThread.write(buffer, 0, len);
					}
					rafThread.close();
					System.out.println(threadId + " thread download finished!");

				}
			} catch (Exception e) {
				System.out.println("exception");
				e.printStackTrace();
			}
		}
	}

}
