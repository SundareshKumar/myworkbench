package com.fss.plugin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class iPayTransactionPipeOab
{
  public String performTranPortalTransaction(String request, String webAddress)
  {
    webAddress = webAddress + (webAddress.lastIndexOf("/") == webAddress.length() - 1 ? "tranPipe.htm?param=tranInit" : "/tranPipe.htm?param=tranInit");
    String response = "";
    String tranType = "tran";
    response = sendMessage(request, webAddress, tranType);
    return response;
  }

  public synchronized String performTranPortalVbyVTransaction(String request, String webAddress)
  {
    webAddress = webAddress + "/VPAS.htm?actionVPAS=tranInit";
    String response = "";
    String tranType = "tran";
    response = sendMessage(request, webAddress, tranType);
    return response;
  }

  public String sendMessage(String request, String webAddress, String tranType)
  {
    String rawResponse = "";
    if (webAddress.indexOf("https") != -1)
      System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
    try {
      URL url = new URL(webAddress);

      URLConnection urlconnection = url.openConnection();
      urlconnection.setConnectTimeout(60000);
      urlconnection.setReadTimeout(60000);
      urlconnection.setDoInput(true);
      urlconnection.setDoOutput(true);
      urlconnection.setUseCaches(false);
      if (tranType.equals("host"))
        urlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      if (tranType.equals("tran"))
        urlconnection.setRequestProperty("Content-Type", "application/xml");
      if (request.length() > 0) {
        DataOutputStream dataoutputstream = new DataOutputStream(urlconnection.getOutputStream());
        dataoutputstream.writeBytes(request);
        dataoutputstream.flush();
        dataoutputstream.close();
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
        if (tranType.equals("host")) {
          rawResponse = bufferedreader.readLine();
        } else if (tranType.equals("tran")) {
          StringBuffer buf = new StringBuffer();
          while (true) {
            String s = bufferedreader.readLine();
            if (s == null)
              break;
            buf.append(s);
          }
          rawResponse = buf.toString();
        }
      } else {
        return null;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    }
    return rawResponse;
  }
}