package dev.kaykyfreitas.video.catalog.admin.infrastructure.configuration.properties.google;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class GoogleStorageProperties implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(GoogleStorageProperties.class);

    private String bucket;
    private int connectTimeout;
    private int readTimeout;
    private int retryDelay;
    private int retryMaxAttempts;
    private int retryMaxDelay;
    private double retryMultiplier;

    @Override
    public void afterPropertiesSet() {
        log.debug(toString());
    }

    @Override
    public String toString() {
        return "GoogleStorageProperties{" +
                "bucket='" + bucket + '\'' +
                ", connectTimeout=" + connectTimeout +
                ", readTimeout=" + readTimeout +
                ", retryDelay=" + retryDelay +
                ", retryMaxAttempts=" + retryMaxAttempts +
                ", retryMaxDelay=" + retryMaxDelay +
                ", retryMultiplier=" + retryMultiplier +
                '}';
    }

    public String getBucket() {
        return bucket;
    }

    public GoogleStorageProperties setBucket(final String bucket) {
        this.bucket = bucket;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public GoogleStorageProperties setConnectTimeout(final int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public GoogleStorageProperties setReadTimeout(final int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public int getRetryDelay() {
        return retryDelay;
    }

    public GoogleStorageProperties setRetryDelay(final int retryDelay) {
        this.retryDelay = retryDelay;
        return this;
    }

    public int getRetryMaxAttempts() {
        return retryMaxAttempts;
    }

    public GoogleStorageProperties setRetryMaxAttempts(final int retryMaxAttempts) {
        this.retryMaxAttempts = retryMaxAttempts;
        return this;
    }

    public int getRetryMaxDelay() {
        return retryMaxDelay;
    }

    public GoogleStorageProperties setRetryMaxDelay(final int retryMaxDelay) {
        this.retryMaxDelay = retryMaxDelay;
        return this;
    }

    public double getRetryMultiplier() {
        return retryMultiplier;
    }

    public GoogleStorageProperties setRetryMultiplier(final double retryMultiplier) {
        this.retryMultiplier = retryMultiplier;
        return this;
    }

}
