package com.bwie.xushuai;

import android.app.Application;
import android.os.Environment;

import com.bwie.xushuai.newsdrag.adapter.db.SQLHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.DbManager;
import org.xutils.x;

public class MyApplication extends Application {
    private static MyApplication mAppApplication;
    private SQLHelper sqlHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppApplication = this;

        x.Ext.init(this);

        initImageLoader();

        getDaoConfig();

    }

    /**
     * 获取Application
     */
    public static MyApplication getApp() {
        return mAppApplication;
    }

    private void initImageLoader() {

        String path = Environment.getExternalStorageDirectory() + "/imageload";

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)//缓存图片最大的长和宽
                .threadPoolSize(2)//线程池的数量
                .threadPriority(4)
                .memoryCacheSize(2 * 1024 * 1024)//设置内存缓存区大小
                .diskCacheSize(20 * 1024 * 1024)//设置sd卡缓存区大小
//                .diskCache(new UnlimitedDiskCache(new File(path)))//自定义缓存目录
                .writeDebugLogs()//打印日志内容
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 获取数据库Helper
     */
    public SQLHelper getSQLHelper() {
        if (sqlHelper == null)
            sqlHelper = new SQLHelper(mAppApplication);
        return sqlHelper;
    }

    /**
     * 摧毁应用进程时候调用
     */
    public void onTerminate() {
        if (sqlHelper != null)
            sqlHelper.close();
        super.onTerminate();
    }

    public void clearAppCache() {
    }


    public static DisplayImageOptions getOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) // resource or drawable
                .showImageForEmptyUri(R.mipmap.ic_launcher) // resource or drawable
                .showImageOnFail(R.mipmap.ic_launcher) // resource or drawable
                .delayBeforeLoading(1000)
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
//                .displayer(new RoundedBitmapDisplayer(360))//设置是否为圆角，弧度为多少
                .build();
        return options;
    }

    public static DbManager.DaoConfig daoConfig;

    public static DbManager.DaoConfig getDaoConfig() {
        if (daoConfig == null) {
            daoConfig = new DbManager.DaoConfig()
                    .setDbVersion(1)
                    .setDbName("tt")//设置数据库的名字
                    .setAllowTransaction(true)
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        }
                    });
        }
        return daoConfig;
    }
}