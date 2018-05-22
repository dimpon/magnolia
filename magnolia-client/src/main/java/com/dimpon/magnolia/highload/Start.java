package com.dimpon.magnolia.highload;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Start {

    //private static final String CALL_URL = "https://dec-service.apps.cf-p.wob.vw.cloud.vwgroup.com/api/v1/vehicledata/identificationData/";

    //private static final String CALL_URL = "http://localhost:8080/api/v1/vehicledata/identificationData/";

    //private static final String CALL_URL = "http://mandragora-client.cfapps.io/getMyPets/";

    private static final String CALL_URL = "http://localhost:8805/getMyPets/";




    private static final int NUMBER_OF_THREADS_IN_POOL = 10;

    private static final int TOTAL_NUMBER_OF_TASKS = 4000;

    private static final int NUMBER_OF_REPEAT_CALLS = 30;

    private static final List<String> VINs = Arrays.asList(
            "NQS00000000000025",
            "NQS00000000000026",
            "NQS00000000000027",
            "NQS00000000000028",
            "NQS00000000000029",
            "NQS00000000000030"
            );

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS_IN_POOL);
        Collection<RealCall> calls = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < TOTAL_NUMBER_OF_TASKS; i++) {
            String randomVIN = VINs.get(rand.nextInt(VINs.size()));
            calls.add(new RealCall(randomVIN, i));
        }
        executor.invokeAll(calls);
        executor.shutdown();
    }


    static class RealCall implements Callable<Integer> {
        private String vin;
        private int number;

        private RealCall(String vin, int number) {
            this.vin = vin;
            this.number = number;
        }

        @Override
        public Integer call() throws Exception {
            for (int i = 0; i < NUMBER_OF_REPEAT_CALLS; i++) {
                doCall();
            }
            return 0;
        }

        private int doCall() throws Exception {

            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            URL obj = new URL(CALL_URL + vin);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("\nTask number : " + number);
            System.out.println("Sending 'GET' request to URL : " + CALL_URL + vin);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return responseCode;
        }
    }
}


