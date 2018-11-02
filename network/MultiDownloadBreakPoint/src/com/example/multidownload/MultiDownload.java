package com.example.multidownload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MultiDownload {
	static String path = "https://t1.daumcdn.net/potplayer/PotPlayer/Version/Latest/PotPlayerSetup64.exe";
	static final int threadCount = 3;
	private static int runningThread = threadCount;

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

				// 先查找上次下载的位置
				File file = new File(threadId + ".txt");
				if (file.exists() && file.length() > 0) {
					FileInputStream in = new FileInputStream(file);
					BufferedReader bufread = new BufferedReader(
							new InputStreamReader(in));
					String lastpos_s = bufread.readLine();
					long lastpos = Long.parseLong(lastpos_s);

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
							"temp.exe", "rw");

					rafThread.seek(start);
					int len = -1;
					long total = 0;
					long current = start;
					byte[] buffer = new byte[1024 * 1024];
					while ((len = inputStream.read(buffer)) != -1) {
						rafThread.write(buffer, 0, len);
						// 实现断点续传,保存每次下载的位置
						total += len;
						current = start + total;

						RandomAccessFile raff = new RandomAccessFile(threadId
								+ ".txt", "rwd");
						raff.write(String.valueOf(current).getBytes());
						raff.close();
					}

					rafThread.close();
					System.out.println(threadId + " thread download finished!");
					synchronized (this) {
						if (--runningThread == 0) {
							for (int i = 0; i < threadCount; ++i) {
								File delfile = new File(i + ".txt");
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
			}
		}
	}

}
