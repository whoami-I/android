package com.jit.lib;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 
 * Description锛氭敮鎸佺綉缁滃姞杞界殑ImageView
 * 鎵╁睍鍔熻兘锛氳嚜瀹氫箟鍔犺浇涓�佸姞杞藉け璐ョ殑鍥剧墖锛岀紦瀛樺浘鐗囨暟鎹紝鍔犲揩鍥剧墖璇诲彇閫熷害锛岄伩鍏嶅唴瀛樻孩鍑恒��
 * 
 */
public class SmartImageView extends ImageView {

	// 浣跨敤绾跨▼姹犵鐞嗙綉缁滆姹�
	private static final int LOADING_THREADS = 4;
	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(LOADING_THREADS);

	// 鍥剧墖涓嬭浇鐨勫紓姝ヤ换鍔＄被
	private SmartImageTask currentTask;

	// 鍔犺浇涓浘鐗囷紝甯冨眬閰嶇疆
	private Drawable mLoadingDrawable;
	// 鍔犺浇澶辫触鍥剧墖锛屽竷灞�閰嶇疆
	private Drawable mFailDrawable;

	public SmartImageView(Context context) {
		this(context, null);
	}

	public SmartImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SmartImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	/**
	 * @param url
	 *            璁剧疆鍔犺浇鐨勫浘鐗囧湴鍧�
	 */
	public void setImageUrl(String url) {
		setImage(new WebImage(url), null);
	}

	/**
	 * 
	 * @param url
	 *            璁剧疆鍔犺浇鐨勫浘鐗囧湴鍧�
	 * @param completeListener
	 *            鍥剧墖鍔犺浇瀹屾垚鐨勫洖璋�
	 */
	public void setImageUrl(String url,
			SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url), completeListener);
	}

	public void setImage(final SmartImage image,
			final SmartImageTask.OnCompleteListener completeListener) {
		// 璁剧疆鍔犺浇涓浘鐗�
		if (mLoadingDrawable != null) {
			setImageDrawable(mLoadingDrawable);
		}

		// 濡傛灉姝iew鐨勫姞杞戒换鍔″凡缁忓紑濮嬶紝鍙栨秷
		if (currentTask != null) {
			currentTask.cancel();
			currentTask = null;
		}

		// 鏂板缓鏂扮殑浠诲姟
		currentTask = new SmartImageTask(getContext(), image);
		currentTask
				.setOnCompleteHandler(new SmartImageTask.OnCompleteHandler() {
					@Override
					public void onComplete(Bitmap bitmap) {
						if (bitmap != null) {
							// 鍔犺浇鎴愬姛锛岃缃浘鐗�
							setImageBitmap(bitmap);
							// 璁剧疆鎴愬姛鐨勫洖璋�
							if (completeListener != null) {
								completeListener.onSuccess(bitmap);
							}
						} else {
							// 璁剧疆澶辫触鍥剧墖
							if (mFailDrawable != null) {
								setImageDrawable(mFailDrawable);
							}
							// 璁剧疆澶辫触鐨勫洖璋�
							if (completeListener != null) {
								completeListener.onFail();
							}
						}
					}
				});

		// 鎶婁换鍔″姞鍏ョ嚎绋嬫睜
		threadPool.execute(currentTask);
	}

	public static void cancelAllTasks() {
		threadPool.shutdownNow();
		threadPool = Executors.newFixedThreadPool(LOADING_THREADS);
	}

}
