package com.yuntian.baselibs.glide;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.yuntian.baselibs.util.HttpsUtils;

import java.io.InputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * @author guangleilei
 * @version 1.0 2016-12-29
 */
public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    /**
     * The default factory for {@link OkHttpUrlLoader}s.
     */
    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private static volatile OkHttpClient internalClient;
        private OkHttpClient client;

        private static OkHttpClient getInternalClient() {
            if (internalClient == null) {
                synchronized (Factory.class) {
                    if (internalClient == null) {
                        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
                        internalClient = new OkHttpClient.Builder()
                                .retryOnConnectionFailure(Boolean.FALSE)            // 失败时重新连接
                                .hostnameVerifier(new HostnameVerifier() {
                                    @Override
                                    public boolean verify(String hostname, SSLSession session) {

                                        return true;
                                    }
                                })
                                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                                .build();
                    }
                }
            }
            return internalClient;
        }

        /**
         * Constructor for a new Factory that runs requests using a static singleton client.
         */
        public Factory() {
            this(getInternalClient());
        }

        /**
         * Constructor for a new Factory that runs requests using given client.
         */
        public Factory(OkHttpClient client) {
            this.client = client;
        }

        @Override
        public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new OkHttpUrlLoader(client);
        }

        @Override
        public void teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }

    private final OkHttpClient client;

    public OkHttpUrlLoader(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
        return new OkHttpStreamFetcher(client, model);
    }


}
