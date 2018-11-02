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
				// 1����ȡ�����ļ����ļ�����
				long contentLength = connection.getContentLengthLong();
				System.out.println(contentLength);
				// 2.����һ����Сһģһ�����ļ���������ռ�
				RandomAccessFile raf = new RandomAccessFile("temp.exe", "rw");
				raf.setLength(contentLength);
				// ����ÿ���߳���Ҫ���ص��ֽ���
				long blockSize = contentLength / threadCount;
				for (int i = 0; i < threadCount; ++i) {
					long start = i * blockSize;
					long end = (i + 1) * blockSize - 1;
					// ���һ���߳�����
					if (i == (threadCount - 1)) {
						end = contentLength - 1;
					}

					// �������߳������ļ�
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
				// ����URL����
				URL url = new URL(path);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(10000);

				// �Ȳ����ϴ����ص�λ��
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
					System.out.println(threadId + "�̼߳�������  λ�ã�" + start);
				}
				if (start > end) {
					System.out.println(threadId + "thread ������ϣ�");
					return;
				}
				// ����һ������ͷ���������ý������ݵ�λ��
				connection.setRequestProperty("Range", "bytes=" + start + "-"
						+ end);
				int code = connection.getResponseCode();
				// ��������206,206��������Դ��ȡ�ɹ�
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
						// ʵ�ֶϵ�����,����ÿ�����ص�λ��
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
							System.out.println("������ϣ�����");
						}

					}
					// ���ɾ�����еĻ����ļ�

				}
			} catch (Exception e) {
				System.out.println(threadId + " thread exception");
				e.printStackTrace();
			}
		}
	}

}
