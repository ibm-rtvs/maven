package com.mycompany.it_plugin_samples;

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(JUnitIntegrationTest.class)
public class SampleJUnitIntegrationTest {

   @Before
   public void installAllTrustingSSLContext() {
      try {
         SSLContext sc = SSLContext.getInstance("TLS");//$NON-NLS-1$
         sc.init(null, new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
               return null;
            }

            public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                  throws CertificateException {
            }

            public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                  throws CertificateException {
            }
         } }, new SecureRandom());
         HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testStubResponse() throws IOException {
      URL url = new URL("https://localhost:5444/RTCP/examples/phonebook/api/");
      HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

      assertEquals(HttpsURLConnection.HTTP_OK, conn.getResponseCode());
      assertEquals("true", conn.getHeaderField("X-Stub"));

      // get response body
      InputStream inputStream = conn.getInputStream();
      Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
      String urlResponse = scanner.hasNext() ? scanner.next() : "";
      assertEquals("{}", urlResponse);
   }
}
