package top.ywlog.o2o.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Author: Durian
 * Date: 2020/1/4 15:34
 * Description: 证书管理器(y用于https请求)
 */
public class MyX509TrustManager implements X509TrustManager
{
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException
    {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException
    {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers()
    {
        return null;
    }
}
