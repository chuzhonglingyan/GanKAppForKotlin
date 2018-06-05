package com.yuntian.baselibs.glide;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author guangleilei
 * @version 1.0 2016-12-30
 */
public class GlideCacheUtil {


    private static final String TAG = GlideCacheUtil.class.getSimpleName();

    public static String ZERO_B = "0.0B";
    private static Context mContext;
    private List<AsyncTask<Void, Void, Boolean>> asyncTaskList = new ArrayList<>();


    private GlideCacheUtil() {

    }

    public static GlideCacheUtil getInstance(Context context) {
        mContext = context.getApplicationContext();
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final GlideCacheUtil INSTANCE = new GlideCacheUtil();
    }


    /**
     * 清除图片磁盘缓存
     */
    public void clearImageDiskCache() {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                if (isCancelled()) {
                    return false;
                }
                Glide.get(mContext).clearDiskCache();
                return true;
            }

            @Override
            protected void onPostExecute(Boolean b) {  //执行完毕
                super.onPostExecute(b);
                if (b) {
                    LogUtils.d(TAG, "清除完毕");
                }
            }
        };
        task.execute();
        asyncTaskList.add(task);
    }

    public void clear() {
        cancleAllAsyncTask();
    }


    private void cancleAllAsyncTask() {
        for (AsyncTask task : asyncTaskList) {
            task.cancel(true);
            LogUtils.d(TAG, "异步任务取消");
        }
        asyncTaskList.clear();
    }


    /**
     * 清除图片内存缓存
     */
    public void clearImageMemoryCache() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(mContext).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 清除图片所有缓存
     */
    public void clearImageAllCache() {
        clearImageDiskCache();
        clearImageMemoryCache();
    }


    /**
     * 清除图片所有缓存
     */
    public void clearImageAllCache(String catchDir) {
        clearImageDiskCache();
        clearImageMemoryCache();
//        //清除gilde原来的文件夹
//        if (mContext.getExternalCacheDir() != null) {
//            String ImageExternalCatchDir = mContext.getExternalCacheDir() + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
//            deleteFolderFile(ImageExternalCatchDir, true);
//        }
//        if (mContext.getCacheDir() != null) {
//            String ImageInternalCacheDisk = mContext.getCacheDir() + File.separator + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
//            deleteFolderFile(ImageInternalCacheDisk, true);
//        }
//        deleteFolderFile(catchDir, true);
    }

    /**
     * 清除图片所有缓存
     */
    public void clearCache() {
        clearImageDiskCache();
        clearImageMemoryCache();
        //清除gilde原来的文件夹
//        if (mContext.getExternalCacheDir() != null) {
//            String ImageExternalCatchDir = mContext.getExternalCacheDir() + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
//            deleteFolderFile(ImageExternalCatchDir, true);
//        }
//        if (mContext.getCacheDir() != null) {
//            String ImageInternalCacheDisk = mContext.getCacheDir() + File.separator + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
//            deleteFolderFile(ImageInternalCacheDisk, true);
//        }
    }


    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize() {
        try {
            return getFormatSize(getFolderSize(new File(mContext.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public long getCacheRealSize() {
        try {
            return getFolderSize(new File(mContext.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    private long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    private void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
