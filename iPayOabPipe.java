package com.fss.plugin;

import java.io.PrintStream;
import java.security.Key;
import java.security.Security;
import java.util.HashMap;
import java.util.Scanner;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public final class iPayOabPipe
{
  String id;
  String action;
  String transId;
  String amt;
  String responseURL;
  String trackId;
  String udf1;
  String udf2;
  String udf3;
  String udf4;
  String udf5;
  String paymentPage;
  String paymentId;
  String result;
  String auth;
  String ref;
  String avr;
  String date;
  String currency;
  String errorURL;
  String language;
  String error;
  String error_text;
  String rawResponse;
  String alias;
  StringBuffer debugMsg;
  String responseCode;
  String zip;
  String addr;
  String member;
  String cvv2;
  String cvv2Verification;
  String type;
  String card;
  String expDay;
  String expMonth;
  String expYear;
  String eci;
  String cavv;
  String xid;
  String resourcePath;
  String acsurl;
  String pareq;
  String pares;
  String error_service_tag;
  String keystorePath;
  static String seperator = "\\";
  static String sep = "/";
  String webAddress;
  String key = "";
  String initializationVector = "";
  String ivrFlag;
  String npc356chphoneidformat;
  String npc356chphoneid;
  String npc356shopchannel;
  String npc356availauthchannel;
  String npc356pareqchannel;
  String npc356itpcredential;
  String authDataName;
  String authDataLength;
  String authDataType;
  String authDataLabel;
  String authDataPrompt;
  String authDataEncryptKey;
  String authDataEncryptType;
  String authDataEncryptMandatory;
  String ivrPasswordStatus;
  String ivrPassword;
  String itpauthtran;
  String itpauthiden;
  String url;
  String paymentdebitId;
  String paymentUrl;
  String custid;
  int timeoutInSeconds;
  boolean enableLogger;
  int loggerLevel = 1;
  static String log4jFileLocation;
  String card_PostalCd;
  String card_Address;
  String card_FirstName;
  String card_LastName;
  String card_Phn_Num;
  String ship_To_Postalcd;
  String ship_To_Address;
  String ship_To_LastName;
  String ship_To_FirstName;
  String ship_To_Phn_Num;
  String ship_To_CountryCd;
  String cust_email;
  String mobilenum;
  String tokenFlag;
  String tokenNumber;
  String tokenCustomerId;
  String tranPostData;

  public iPayOabPipe()
  {
    this.action = "";
    this.transId = "";
    this.amt = "";
    this.responseURL = "";
    this.trackId = "";
    this.udf1 = "";
    this.udf2 = "";
    this.udf3 = "";
    this.udf4 = "";
    this.udf5 = "";
    this.custid = "";
    this.paymentPage = "";
    this.paymentId = "";
    this.result = "";
    this.auth = "";
    this.ref = "";
    this.avr = "";
    this.date = "";
    this.currency = "";
    this.errorURL = "";
    this.language = "";
    this.error = "";
    this.rawResponse = "";
    this.resourcePath = "";
    this.keystorePath = "";
    this.debugMsg = new StringBuffer();
    this.ivrFlag = "";
    this.npc356chphoneidformat = "";
    this.npc356chphoneid = "";
    this.npc356shopchannel = "";
    this.npc356availauthchannel = "";
    this.npc356pareqchannel = "";
    this.npc356itpcredential = "";
    this.authDataName = "";
    this.authDataLength = "";
    this.authDataType = "";
    this.authDataLabel = "";
    this.authDataPrompt = "";
    this.authDataEncryptKey = "";
    this.authDataEncryptType = "";
    this.authDataEncryptMandatory = "";
    this.ivrPasswordStatus = "";
    this.ivrPassword = "";
    this.itpauthtran = "";
    this.itpauthiden = "";
    this.id = "";

    this.card_PostalCd = "";
    this.card_Address = "";
    this.card_FirstName = "";
    this.card_LastName = "";
    this.card_Phn_Num = "";
    this.ship_To_Postalcd = "";
    this.ship_To_Address = "";
    this.ship_To_LastName = "";
    this.ship_To_FirstName = "";
    this.ship_To_Phn_Num = "";
    this.ship_To_CountryCd = "";
    this.cust_email = "";
    this.member = "";
    this.mobilenum = "";

    this.tokenFlag = "";
    this.tokenNumber = "";
    this.tokenCustomerId = "";
    this.tranPostData = "";
    Security.addProvider(new BouncyCastleProvider());
  }

  public int performPaymentInitializationHTTP()
  {
    String request = null;
    StringBuilder requestbuffer = null;
    String xmlData = null;
    HashMap hm = null;
    TripleDesAlgorithmOab tripleDesAlgorithm = null;
    try
    {
      tripleDesAlgorithm = new TripleDesAlgorithmOab();
      xmlData = parseResource(this.keystorePath, this.resourcePath, this.alias);
      hm = new HashMap();
      if (xmlData != null)
        hm = parseXMLRequest(xmlData);
      else {
        this.error = "Alias name does not exits";
      }

      this.key = ((String)hm.get("resourceKey"));
      requestbuffer = buildHostRequest();
      if (requestbuffer.toString() != null)
      {
        requestbuffer.append("id=" + (String)hm.get("id") + "&");
      }requestbuffer.append("password=" + (String)hm.get("password") + "&");
      this.webAddress = ((String)hm.get("webaddress"));
      this.id = ((String)hm.get("id"));

      this.tranPostData = tripleDesAlgorithm.encryptText(this.key, requestbuffer.toString());
      request = "&trandata=" + this.tranPostData;

      request = request + "&errorURL=" + this.errorURL + "&responseURL=" + this.responseURL + "&tranportalId=" + (String)hm.get("id");

      this.webAddress += "/PaymentHTTP.htm?param=paymentInit";
    }
    catch (Exception e) {
      this.error = "Problem while encrypting request data";

      return -1;
    } finally {
      request = null;
      requestbuffer = null;
      xmlData = null;
      hm = null;
      tripleDesAlgorithm = null;
    }
    return 0;
  }

  private String parseResource(String keystorePath, String resourcePath, String alias) {
    String xmlData = null;
    Key key = null;
    ParseResouceOab pr = null;
    try {
      pr = new ParseResouceOab();
      key = ParseResouceOab.loadKeyStore(keystorePath);
      xmlData = pr.getCGNData(resourcePath, alias, key);
      String str1 = xmlData;
      return str1;
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      pr = null;
      key = null;
    }throw localObject;
  }

  private StringBuilder buildHostRequest() {
    StringBuilder requestbuffer = null;
    try {
      requestbuffer = new StringBuilder();
      if (this.amt.length() > 0) {
        requestbuffer.append("amt=" + this.amt + "&");
      }
      if (this.action.length() > 0) {
        requestbuffer.append("action=" + this.action + "&");
      }
      if (this.responseURL.length() > 0) {
        requestbuffer.append("responseURL=" + this.responseURL + "&");
      }
      if (this.errorURL.length() > 0) {
        requestbuffer.append("errorURL=" + this.errorURL + "&");
      }
      if (this.trackId.length() > 0) {
        requestbuffer.append("trackId=" + this.trackId + "&");
      }
      if (this.udf1.length() > 0) {
        requestbuffer.append("udf1=" + this.udf1 + "&");
      }
      if (this.udf2.length() > 0) {
        requestbuffer.append("udf2=" + this.udf2 + "&");
      }
      if (this.udf3.length() > 0) {
        requestbuffer.append("udf3=" + this.udf3 + "&");
      }
      if (this.udf4.length() > 0) {
        requestbuffer.append("udf4=" + this.udf4 + "&");
      }
      if ((this.tokenFlag != null) && (this.tokenFlag.length() > 0)) {
        requestbuffer.append("tokenFlag=" + this.tokenFlag + "&");
      }
      if ((this.tokenNumber != null) && (this.tokenNumber.length() > 0)) {
        requestbuffer.append("tokenNumber=" + this.tokenNumber + "&");
      }
      if (this.udf5.length() > 0) {
        requestbuffer.append("udf5=" + this.udf5 + "&");
      }
      if (this.currency.length() > 0) {
        requestbuffer.append("currencycode=" + this.currency + "&");
      }
      if ((this.language != null) && (this.language.length() > 0)) {
        requestbuffer.append("langid=" + this.language + "&");
      }
      if (this.card_Phn_Num.length() > 0) {
        requestbuffer.append("card_Phn_Num=" + this.card_Phn_Num + "&");
      }
      if (this.card_Address.length() > 0) {
        requestbuffer.append("card_Address=" + this.card_Address + "&");
      }
      if (this.card_PostalCd.length() > 0) {
        requestbuffer.append("card_PostalCd=" + this.card_PostalCd + "&");
      }
      if (this.ship_To_Postalcd.length() > 0) {
        requestbuffer.append("ship_To_Postalcd=" + this.ship_To_Postalcd + "&");
      }
      if (this.ship_To_Address.length() > 0) {
        requestbuffer.append("ship_To_Address=" + this.ship_To_Address + "&");
      }
      if (this.ship_To_FirstName.length() > 0) {
        requestbuffer.append("ship_To_FirstName=" + this.ship_To_FirstName + "&");
      }
      if (this.ship_To_LastName.length() > 0) {
        requestbuffer.append("ship_To_LastName=" + this.ship_To_LastName + "&");
      }
      if (this.ship_To_Phn_Num.length() > 0) {
        requestbuffer.append("ship_To_Phn_Num=" + this.ship_To_Phn_Num + "&");
      }
      if (this.ship_To_CountryCd.length() > 0) {
        requestbuffer.append("ship_To_CountryCd=" + this.ship_To_CountryCd + "&");
      }
      if (this.cust_email.length() > 0) {
        requestbuffer.append("cust_email=" + this.cust_email + "&");
      }
      if (this.mobilenum.length() > 0) {
        requestbuffer.append("mobile_num=" + this.mobilenum + "&");
      }
      return requestbuffer;
    } catch (Exception e) {
    }
    return null;
  }

  public short performTransactionHTTP()
  {
    String request = null;
    StringBuilder requestbuffer = null;
    String xmlData = null;
    HashMap hm = null;
    TripleDesAlgorithmOab tripleDesAlgorithm = null;
    try
    {
      tripleDesAlgorithm = new TripleDesAlgorithmOab();
      requestbuffer = buildXMLRequest();
      xmlData = parseResource(this.keystorePath, this.resourcePath, this.alias);
      if (xmlData == null) {
        this.error = "Alias name does not exits";
        return -1;
      }
      hm = parseXMLRequest(xmlData);
      requestbuffer.append("<id>" + (String)hm.get("id") + "</id>");
      requestbuffer.append("<password>" + (String)hm.get("password") + "</password>");
      requestbuffer.append("</request>");
      if ((this.responseURL == null) || (this.responseURL.trim().equals(""))) {
        this.error = "Response URL is Invalid or NULL";
        return -1;
      }

      this.key = ((String)hm.get("resourceKey"));
      this.tranPostData = tripleDesAlgorithm.encryptText(this.key, requestbuffer.toString());
      request = "&trandata=" + tripleDesAlgorithm.encryptText(this.key, requestbuffer.toString()) + "&errorURL=" + this.errorURL + "&responseURL=" + this.responseURL + "&tranportalId=" + (String)hm.get("id");
      this.webAddress = ((String)hm.get("webaddress"));

      this.id = ((String)hm.get("id"));

      setId((String)hm.get("id"));

      this.webAddress += "/tranPipeHTTP.htm?param=tranInit";
      return 0;
    }
    catch (Exception e)
    {
      return -1;
    } finally {
      request = null;
      requestbuffer = null;
      xmlData = null;
      hm = null;
      tripleDesAlgorithm = null;
    }throw localObject;
  }

  public short performVbVTransaction()
  {
    String request = null;
    String xmlData = null;
    StringBuilder requestbuffer = null;
    HashMap hm = null;
    try
    {
      requestbuffer = buildXMLRequest();
      xmlData = parseResource(this.keystorePath, this.resourcePath, this.alias);
      hm = new HashMap();
      if (xmlData == null) {
        this.error = "Alias name does not exits";
        return -1;
      }
      hm = parseXMLRequest(xmlData);
      requestbuffer.append("<id>" + (String)hm.get("id") + "</id>");
      requestbuffer.append("<password>" + (String)hm.get("password") + "</password>");
      requestbuffer.append("</request>");
      if ((this.responseURL == null) || (this.responseURL.trim().equals(""))) {
        this.error = "Response URL is Invalid or NULL";
        return -1;
      }

      this.key = ((String)hm.get("resourceKey"));
      this.tranPostData = new TripleDesAlgorithmOab().encryptText(this.key, requestbuffer.toString());
      request = "&trandata=" + new TripleDesAlgorithmOab().encryptText(this.key, requestbuffer.toString()) + "&errorURL=" + this.errorURL + "&responseURL=" + this.responseURL + "&tranportalId=" + (String)hm.get("id");
      this.webAddress = ((String)hm.get("webaddress"));
      this.id = ((String)hm.get("id"));

      this.webAddress += "/VPAS.htm?actionVPAS=VbvVEReqProcessHTTP";
      return 0;
    }
    catch (Exception e) {
      this.error = ("Error! " + e.getMessage());
      return -1;
    } finally {
      request = null;
      xmlData = null;
      requestbuffer = null;
      hm = null;
    }throw localObject;
  }
  public synchronized short performVETransaction() {
    String request = "";
    String xmlData = "";
    String webaddr = "";
    String response = "";
    HashMap hm;
    try {
      iPayTransactionPipeOab pipe = new iPayTransactionPipeOab();
      StringBuilder requestbuffer = new StringBuilder();
      requestbuffer = buildXMLRequest();
      xmlData = parseResource(this.keystorePath, this.resourcePath, this.alias);
      HashMap hm = new HashMap();
      hm = parseXMLRequest(xmlData);
      requestbuffer.append("<id>" + (String)hm.get("id") + "</id>");
      requestbuffer.append("<password>" + (String)hm.get("password") + "</password>");
      requestbuffer.append("<errorURL>" + this.errorURL + "</errorURL>");
      requestbuffer.append("<responseURL>" + this.responseURL + "</responseURL>");
      requestbuffer.append("</request>");
      request = requestbuffer.toString();
      webaddr = (String)hm.get("webaddress");

      response = pipe.performTranPortalVbyVTransaction(request, webaddr);
      if (response != null) {
        int i = response.indexOf(":");
        if (i == -1) {
          this.error = ("Error while connecting " + response);
          return -1;
        }
        if (response != null) {
          this.paymentId = response.substring(0, i);
          System.out.println("pay id : " + this.paymentId);
          this.paymentPage = response.substring(i + 1);
          System.out.println("paymentPage : " + this.paymentPage);
          return 0;
        }
        this.error = ("Error while connecting " + response);
        return -1;
      }

      this.error = ("Error while connecting " + response);
      return -1;
    }
    catch (Exception e)
    {
      iPayTransactionPipeOab pipe;
      StringBuilder requestbuffer;
      HashMap hm;
      return -1;
    } finally {
      iPayTransactionPipeOab pipe = null;
      request = null;
      xmlData = null;
      webaddr = null;
      response = null;
      StringBuilder requestbuffer = null;
      hm = null;
    }
    throw localObject;
  }

  private StringBuilder buildXMLRequest()
  {
    StringBuilder requestbuffer = null;
    try {
      requestbuffer = new StringBuilder();
      requestbuffer.append("<request>");
      if ((this.card != null) && (this.card.length() > 0)) {
        requestbuffer.append("<card>" + this.card + "</card>");
      }
      if ((this.cvv2 != null) && (this.cvv2.length() > 0)) {
        requestbuffer.append("<cvv2>" + this.cvv2 + "</cvv2>");
      }
      if ((this.currency != null) && 
        (this.currency.length() > 0)) {
        requestbuffer.append("<currencycode>" + this.currency + "</currencycode>");
      }
      if ((this.expYear != null) && 
        (this.expYear.length() > 0)) {
        requestbuffer.append("<expyear>" + this.expYear + "</expyear>");
      }
      if ((this.expMonth != null) && 
        (this.expMonth.length() > 0)) {
        requestbuffer.append("<expmonth>" + this.expMonth + "</expmonth>");
      }
      if ((this.expDay != null) && 
        (this.expDay.length() > 0)) {
        requestbuffer.append("<expday>" + this.expDay + "</expday>");
      }
      if ((this.type != null) && 
        (this.type.length() > 0)) {
        requestbuffer.append("<type>" + this.type + "</type>");
      }
      if ((this.transId != null) && 
        (this.transId.length() > 0)) {
        requestbuffer.append("<transid>" + this.transId + "</transid>");
      }
      if ((this.zip != null) && 
        (this.zip.length() > 0)) {
        requestbuffer.append("<zip>" + this.zip + "</zip>");
      }
      if ((this.addr != null) && 
        (this.addr.length() > 0)) {
        requestbuffer.append("<addr>" + this.addr + "</addr>");
      }
      if ((this.member != null) && 
        (this.member.length() > 0)) {
        requestbuffer.append("<member>" + this.member + "</member>");
      }
      if ((this.amt != null) && 
        (this.amt.length() > 0)) {
        requestbuffer.append("<amt>" + this.amt + "</amt>");
      }
      if ((this.action != null) && 
        (this.action.length() > 0)) {
        requestbuffer.append("<action>" + this.action + "</action>");
      }
      if ((this.trackId != null) && 
        (this.trackId.length() > 0)) {
        requestbuffer.append("<trackid>" + this.trackId + "</trackid>");
      }
      if ((this.udf1 != null) && 
        (this.udf1.length() > 0)) {
        requestbuffer.append("<udf1>" + this.udf1 + "</udf1>");
      }
      if ((this.udf2 != null) && 
        (this.udf2.length() > 0)) {
        requestbuffer.append("<udf2>" + this.udf2 + "</udf2>");
      }
      if ((this.udf3 != null) && 
        (this.udf3.length() > 0)) {
        requestbuffer.append("<udf3>" + this.udf3 + "</udf3>");
      }
      if ((this.udf4 != null) && 
        (this.udf4.length() > 0)) {
        requestbuffer.append("<udf4>" + this.udf4 + "</udf4>");
      }
      if ((this.udf5 != null) && 
        (this.udf5.length() > 0)) {
        requestbuffer.append("<udf5>" + this.udf5 + "</udf5>");
      }
      if ((this.currency != null) && 
        (this.currency.length() > 0)) {
        requestbuffer.append("<currencycode>" + this.currency + "</currencycode>");
      }
      if ((this.eci != null) && 
        (this.eci.length() > 0)) {
        requestbuffer.append("<eci>" + this.eci + "</eci>");
      }

      if ((this.ivrFlag != null) && 
        (this.ivrFlag.length() > 0)) {
        requestbuffer.append("<ivrFlag>" + this.ivrFlag + "</ivrFlag>");
      }
      if ((this.npc356chphoneidformat != null) && 
        (this.npc356chphoneidformat.length() > 0)) {
        requestbuffer.append("<npc356chphoneidformat>" + this.npc356chphoneidformat + "</npc356chphoneidformat>");
      }
      if ((this.npc356chphoneid != null) && 
        (this.npc356chphoneid.length() > 0)) {
        requestbuffer.append("<npc356chphoneid>" + this.npc356chphoneid + "</npc356chphoneid>");
      }
      if ((this.npc356shopchannel != null) && 
        (this.npc356shopchannel.length() > 0)) {
        requestbuffer.append("<npc356shopchannel>" + this.npc356shopchannel + "</npc356shopchannel>");
      }
      if ((this.npc356availauthchannel != null) && 
        (this.npc356availauthchannel.length() > 0)) {
        requestbuffer.append("<npc356availauthchannel>" + this.npc356availauthchannel + "</npc356availauthchannel>");
      }
      if ((this.npc356pareqchannel != null) && 
        (this.npc356pareqchannel.length() > 0)) {
        requestbuffer.append("<npc356pareqchannel>" + this.npc356pareqchannel + "</npc356pareqchannel>");
      }
      if ((this.npc356itpcredential != null) && 
        (this.npc356itpcredential.length() > 0)) {
        requestbuffer.append("<npc356itpcredential>" + this.npc356itpcredential + "</npc356itpcredential>");
      }
      if ((this.ivrPasswordStatus != null) && (this.ivrPasswordStatus.length() > 0))
        requestbuffer.append("<ivrPasswordStatus>" + this.ivrPasswordStatus + "</ivrPasswordStatus>");
      if ((this.ivrPassword != null) && (this.ivrPassword.length() > 0)) {
        requestbuffer.append("<ivrPassword>" + this.ivrPassword + "</ivrPassword>");
      }

      if (this.card_PostalCd.length() > 0) {
        requestbuffer.append("<card_PostalCd>" + this.card_PostalCd + "</card_PostalCd>");
      }
      if (this.card_Address.length() > 0) {
        requestbuffer.append("<card_Address>" + this.card_Address + "</card_Address>");
      }
      if (this.card_FirstName.length() > 0) {
        requestbuffer.append("<card_FirstName>" + this.card_FirstName + "</card_FirstName>");
      }
      if (this.card_LastName.length() > 0) {
        requestbuffer.append("<card_LastName>" + this.card_LastName + "</card_LastName>");
      }
      if (this.card_Phn_Num.length() > 0) {
        requestbuffer.append("<card_Phn_Num>" + this.card_Phn_Num + "</card_Phn_Num>");
      }
      if (this.ship_To_Postalcd.length() > 0) {
        requestbuffer.append("<ship_To_Postalcd>" + this.ship_To_Postalcd + "</ship_To_Postalcd>");
      }
      if (this.ship_To_Address.length() > 0) {
        requestbuffer.append("<ship_To_Address>" + this.ship_To_Address + "</ship_To_Address>");
      }
      if (this.ship_To_FirstName.length() > 0) {
        requestbuffer.append("<ship_To_FirstName>" + this.ship_To_FirstName + "</ship_To_FirstName>");
      }
      if (this.ship_To_LastName.length() > 0) {
        requestbuffer.append("<ship_To_LastName>" + this.ship_To_LastName + "</ship_To_LastName>");
      }
      if (this.ship_To_Phn_Num.length() > 0) {
        requestbuffer.append("<ship_To_Phn_Num>" + this.ship_To_Phn_Num + "</ship_To_Phn_Num>");
      }
      if (this.ship_To_CountryCd.length() > 0) {
        requestbuffer.append("<ship_To_CountryCd>" + this.ship_To_CountryCd + "</ship_To_CountryCd>");
      }
      if (this.cust_email.length() > 0) {
        requestbuffer.append("<cust_email>" + this.cust_email + "</cust_email>");
      }

      if ((this.tokenFlag != null) && 
        (this.tokenFlag.length() > 0)) {
        requestbuffer.append("<tokenflag>" + this.tokenFlag + "</tokenflag>");
      }
      if ((this.tokenNumber != null) && 
        (this.tokenNumber.length() > 0)) {
        requestbuffer.append("<tokennumber>" + this.tokenNumber + "</tokennumber>");
      }

      StringBuilder localStringBuilder1 = requestbuffer;
      return localStringBuilder1;
    }
    catch (Exception e)
    {
      return null;
    } finally {
      requestbuffer = null;
    }throw localObject;
  }

  public int parseEncryptedRequest(String trandata)
  {
    int result = 0;
    String xmlData = null;
    HashMap hm = null;
    HashMap resultMap = null;
    TripleDesAlgorithmOab tripleDesAlgorithm = null;
    String responseTemp = null;
    try {
      tripleDesAlgorithm = new TripleDesAlgorithmOab();
      if (trandata == null) {
        return 0;
      }

      xmlData = parseResource(this.keystorePath, this.resourcePath, this.alias);
      if (xmlData != null) {
        hm = parseXMLRequest(xmlData);
      } else {
        this.error = "Alias name does not exits";
        return -1;
      }
      this.key = ((String)hm.get("resourceKey"));
      trandata = tripleDesAlgorithm.decryptText(this.key, trandata);
      result = parsetrandata(trandata);

      resultMap = parseResponse(trandata);
      if (resultMap != null) {
        responseTemp = (String)resultMap.get("url");
        if (responseTemp != null)
          this.acsurl = responseTemp.trim();
        responseTemp = (String)resultMap.get("PAReq");
        if (responseTemp != null)
          this.pareq = responseTemp.trim();
        responseTemp = (String)resultMap.get("paymentid");
        if (responseTemp != null)
          this.paymentId = responseTemp.trim();
        responseTemp = (String)resultMap.get("eci");
        if (responseTemp != null)
          this.eci = responseTemp.trim();
        responseTemp = (String)resultMap.get("result");
        if (responseTemp != null)
          this.result = responseTemp.trim();
        responseTemp = (String)resultMap.get("auth");
        if (responseTemp != null)
          this.auth = responseTemp.trim();
        responseTemp = (String)resultMap.get("ref");
        if (responseTemp != null)
          this.ref = responseTemp.trim();
        responseTemp = (String)resultMap.get("avr");
        if (responseTemp != null)
          this.avr = responseTemp.trim();
        responseTemp = (String)resultMap.get("postdate");
        if (responseTemp != null)
          this.date = responseTemp.trim();
        responseTemp = (String)resultMap.get("tranid");
        if (responseTemp != null)
          this.transId = responseTemp.trim();
        responseTemp = (String)resultMap.get("amt");
        if (responseTemp != null)
          this.amt = responseTemp.trim();
        responseTemp = (String)resultMap.get("trackid");
        if (responseTemp != null)
          this.trackId = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf1");
        if (responseTemp != null)
          this.udf1 = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf2");
        if (responseTemp != null)
          this.udf2 = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf3");
        if (responseTemp != null)
          this.udf3 = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf4");
        if (responseTemp != null)
          this.udf4 = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf5");
        if (responseTemp != null)
          this.udf5 = responseTemp.trim();
        responseTemp = (String)resultMap.get("error_code_tag");
        if (responseTemp != null)
          this.error = responseTemp.trim();
        responseTemp = (String)resultMap.get("error_service_tag");
        if (responseTemp != null)
          this.error_service_tag = responseTemp.trim();
        responseTemp = (String)resultMap.get("error_text");
        if (responseTemp != null)
          this.error_text = responseTemp.trim();
        responseTemp = (String)resultMap.get("responsecode");
        if (responseTemp != null)
          this.responseCode = responseTemp.trim();
        responseTemp = (String)resultMap.get("custid");
        if (responseTemp != null)
          this.custid = responseTemp.trim();
        responseTemp = (String)resultMap.get("tokencustid");
        if (responseTemp != null)
          this.tokenCustomerId = responseTemp.trim();
        responseTemp = (String)resultMap.get("cvv2response");
        if (responseTemp != null) {
          this.cvv2Verification = responseTemp.trim();
        }
        responseTemp = (String)resultMap.get("paymentId");
        if (responseTemp != null)
          this.paymentdebitId = responseTemp.trim();
        responseTemp = (String)resultMap.get("paymenturl");
        if (responseTemp != null) {
          this.paymentUrl = responseTemp.trim();
          return 2;
        }
      }

      int i = result;
      return i;
    }
    catch (Exception e)
    {
      return -1;
    } finally {
      result = 0;
      xmlData = null;
      hm = null;
      tripleDesAlgorithm = null;
    }throw localObject;
  }

  public int parseResult(String response)
  {
    HashMap resultMap = null;
    String responseTemp = "";
    try
    {
      resultMap = parseResponse(response);
      if (resultMap != null) {
        responseTemp = (String)resultMap.get("url");
        if (responseTemp != null)
          this.acsurl = responseTemp.trim();
        responseTemp = (String)resultMap.get("PAReq");
        if (responseTemp != null)
          this.pareq = responseTemp.trim();
        responseTemp = (String)resultMap.get("paymentid");
        if (responseTemp != null)
          this.paymentId = responseTemp.trim();
        responseTemp = (String)resultMap.get("eci");
        if (responseTemp != null)
          this.eci = responseTemp.trim();
        responseTemp = (String)resultMap.get("result");
        if (responseTemp != null)
          this.result = responseTemp.trim();
        responseTemp = (String)resultMap.get("auth");
        if (responseTemp != null)
          this.auth = responseTemp.trim();
        responseTemp = (String)resultMap.get("ref");
        if (responseTemp != null)
          this.ref = responseTemp.trim();
        responseTemp = (String)resultMap.get("avr");
        if (responseTemp != null)
          this.avr = responseTemp.trim();
        responseTemp = (String)resultMap.get("postdate");
        if (responseTemp != null)
          this.date = responseTemp.trim();
        responseTemp = (String)resultMap.get("tranid");
        if (responseTemp != null)
          this.transId = responseTemp.trim();
        responseTemp = (String)resultMap.get("amt");
        if (responseTemp != null)
          this.amt = responseTemp.trim();
        responseTemp = (String)resultMap.get("trackid");
        if (responseTemp != null)
          this.trackId = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf1");
        if (responseTemp != null)
          this.udf1 = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf2");
        if (responseTemp != null)
          this.udf2 = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf3");
        if (responseTemp != null)
          this.udf3 = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf4");
        if (responseTemp != null)
          this.udf4 = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf5");
        if (responseTemp != null)
          this.udf5 = responseTemp.trim();
        responseTemp = (String)resultMap.get("custid");
        if (responseTemp != null)
          this.custid = responseTemp.trim();
        responseTemp = (String)resultMap.get("tokencustid");
        if (responseTemp != null)
          this.tokenCustomerId = responseTemp.trim();
        responseTemp = (String)resultMap.get("error_code_tag");
        if (responseTemp != null)
          this.error = responseTemp.trim();
        responseTemp = (String)resultMap.get("error_service_tag");
        if (responseTemp != null)
          this.error_service_tag = responseTemp.trim();
        responseTemp = (String)resultMap.get("error_text");
        if (responseTemp != null)
          this.error_text = responseTemp.trim();
        responseTemp = (String)resultMap.get("responsecode");
        if (responseTemp != null)
          this.responseCode = responseTemp.trim();
        responseTemp = (String)resultMap.get("cvv2response");
        if (responseTemp != null) {
          this.cvv2Verification = responseTemp.trim();
        }
        responseTemp = (String)resultMap.get("paymentId");
        if (responseTemp != null)
          this.paymentdebitId = responseTemp.trim();
        responseTemp = (String)resultMap.get("paymenturl");
        if (responseTemp != null) {
          this.paymentUrl = responseTemp.trim();
          return 2;
        }
        return 0;
      }
      return -1;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.error = ("Internal Error: " + e);
      return -1;
    }
    finally {
      resultMap = null;
      responseTemp = null;
    }throw localObject;
  }

  public int parseEncryptedResult(String trandata)
  {
    int result = 0;
    String xmlData = null;
    HashMap hm = null;
    HashMap resultMap = null;
    TripleDesAlgorithmOab tripleDesAlgorithm = null;
    String responseTemp = null;
    try {
      tripleDesAlgorithm = new TripleDesAlgorithmOab();
      if (trandata == null) {
        return 0;
      }

      xmlData = parseResource(this.keystorePath, this.resourcePath, this.alias);
      if (xmlData != null) {
        hm = parseXMLRequest(xmlData);
      } else {
        this.error = "Alias name does not exits";
        return -1;
      }
      this.key = ((String)hm.get("resourceKey"));
      trandata = tripleDesAlgorithm.decryptText(this.key, trandata);
      result = parsetrandata(trandata);
      resultMap = parseResponse(trandata);
      if (resultMap != null) {
        responseTemp = (String)resultMap.get("url");
        if (responseTemp != null)
          this.acsurl = responseTemp.trim();
        responseTemp = (String)resultMap.get("PAReq");
        if (responseTemp != null)
          this.pareq = responseTemp.trim();
        responseTemp = (String)resultMap.get("paymentid");
        if (responseTemp != null)
          this.paymentId = responseTemp.trim();
        responseTemp = (String)resultMap.get("eci");
        if (responseTemp != null)
          this.eci = responseTemp.trim();
        responseTemp = (String)resultMap.get("result");
        if (responseTemp != null)
          this.result = responseTemp.trim();
        responseTemp = (String)resultMap.get("auth");
        if (responseTemp != null)
          this.auth = responseTemp.trim();
        responseTemp = (String)resultMap.get("ref");
        if (responseTemp != null)
          this.ref = responseTemp.trim();
        responseTemp = (String)resultMap.get("avr");
        if (responseTemp != null)
          this.avr = responseTemp.trim();
        responseTemp = (String)resultMap.get("postdate");
        if (responseTemp != null)
          this.date = responseTemp.trim();
        responseTemp = (String)resultMap.get("tranid");
        if (responseTemp != null)
          this.transId = responseTemp.trim();
        responseTemp = (String)resultMap.get("amt");
        if (responseTemp != null)
          this.amt = responseTemp.trim();
        responseTemp = (String)resultMap.get("trackid");
        if (responseTemp != null)
          this.trackId = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf1");
        if (responseTemp != null)
          this.udf1 = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf2");
        if (responseTemp != null)
          this.udf2 = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf3");
        if (responseTemp != null)
          this.udf3 = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf4");
        if (responseTemp != null)
          this.udf4 = responseTemp.trim();
        responseTemp = (String)resultMap.get("udf5");
        if (responseTemp != null)
          this.udf5 = responseTemp.trim();
        responseTemp = (String)resultMap.get("custid");
        if (responseTemp != null)
          this.custid = responseTemp.trim();
        responseTemp = (String)resultMap.get("tokencustid");
        if (responseTemp != null)
          this.tokenCustomerId = responseTemp.trim();
        responseTemp = (String)resultMap.get("error_code_tag");
        if (responseTemp != null)
          this.error = responseTemp.trim();
        responseTemp = (String)resultMap.get("error_service_tag");
        if (responseTemp != null)
          this.error_service_tag = responseTemp.trim();
        responseTemp = (String)resultMap.get("error_text");
        if (responseTemp != null)
          this.error_text = responseTemp.trim();
        responseTemp = (String)resultMap.get("responsecode");
        if (responseTemp != null)
          this.responseCode = responseTemp.trim();
        responseTemp = (String)resultMap.get("cvv2response");
        if (responseTemp != null) {
          this.cvv2Verification = responseTemp.trim();
        }
        responseTemp = (String)resultMap.get("paymentId");
        if (responseTemp != null)
          this.paymentdebitId = responseTemp.trim();
        responseTemp = (String)resultMap.get("paymenturl");
        if (responseTemp != null) {
          this.paymentUrl = responseTemp.trim();
          return 2;
        }
      }

      int i = result;
      return i;
    }
    catch (Exception e)
    {
      return -1;
    } finally {
      result = 0;
      xmlData = null;
      hm = null;
      tripleDesAlgorithm = null;
    }throw localObject;
  }

  public int parsetrandata(String trandata) {
    Scanner s = null;
    String[] sObj = null;
    try {
      s = new Scanner(trandata).useDelimiter("&");
      while (s.hasNext()) {
        sObj = s.next().split("=");
        if (sObj[0].equals("paymentid"))
          this.paymentId = sObj[1];
        else if (sObj[0].equals("result"))
          this.result = sObj[1];
        else if (sObj[0].equals("udf1"))
          this.udf1 = sObj[1];
        else if (sObj[0].equals("udf2"))
          this.udf2 = sObj[1];
        else if (sObj[0].equals("udf3"))
          this.udf3 = sObj[1];
        else if (sObj[0].equals("udf4"))
          this.udf4 = sObj[1];
        else if (sObj[0].equals("udf5"))
          this.udf5 = sObj[1];
        else if (sObj[0].equals("amt"))
          this.amt = sObj[1];
        else if (sObj[0].equals("auth"))
          this.auth = sObj[1];
        else if (sObj[0].equals("ref"))
          this.ref = sObj[1];
        else if (sObj[0].equals("tranid"))
          this.transId = sObj[1];
        else if (sObj[0].equals("postdate"))
          this.date = sObj[1];
        else if (sObj[0].equals("trackId"))
          this.trackId = sObj[1];
        else if (sObj[0].equals("trackid"))
          this.trackId = sObj[1];
        else if (sObj[0].equals("action"))
          this.action = sObj[1];
        else if (sObj[0].equals("Error"))
          this.error = sObj[1];
        else if (sObj[0].equals("ErrorText"))
          this.error_text = sObj[1];
        else if (sObj[0].equals("custid"))
          this.custid = sObj[1];
        else if (sObj[0].equals("tokencustid"))
          this.tokenCustomerId = sObj[1];
      }
      return 0;
    }
    catch (Exception e)
    {
      return -1;
    } finally {
      s = null;
      sObj = null;
    }throw localObject;
  }

  public int parseEncryptedResultHttp(String response) {
    String xmlData = null;
    HashMap hm = null;
    HashMap resultMap = null;
    String responseTemp = null;
    String ds = null;
    TripleDesAlgorithmOab tripleDesAlgorithm = null;
    try {
      tripleDesAlgorithm = new TripleDesAlgorithmOab();
      xmlData = parseResource(this.keystorePath, this.resourcePath, this.alias);
      hm = new HashMap();
      if (xmlData != null) {
        hm = parseXMLRequest(xmlData);
      } else {
        this.error = "Alias name does not exits";
        return -1;
      }
      this.key = ((String)hm.get("resourceKey"));
      ds = tripleDesAlgorithm.decryptText(this.key, response);
      if (ds == null) {
        ds = response;
        int i = parsetrandata(response);
        int i = i;
        return i;
      }
      parsetrandata(response);

      resultMap = parseResponse(ds);
      responseTemp = (String)resultMap.get("url");
      if (responseTemp != null)
        this.acsurl = responseTemp.trim();
      responseTemp = (String)resultMap.get("PAReq");
      if (responseTemp != null)
        this.pareq = responseTemp.trim();
      responseTemp = (String)resultMap.get("paymentid");
      if (responseTemp != null)
        this.paymentId = responseTemp.trim();
      responseTemp = (String)resultMap.get("eci");
      if (responseTemp != null)
        this.eci = responseTemp.trim();
      responseTemp = (String)resultMap.get("result");
      if (responseTemp != null)
        this.result = responseTemp.trim();
      responseTemp = (String)resultMap.get("auth");
      if (responseTemp != null)
        this.auth = responseTemp.trim();
      responseTemp = (String)resultMap.get("ref");
      if (responseTemp != null)
        this.ref = responseTemp.trim();
      responseTemp = (String)resultMap.get("avr");
      if (responseTemp != null)
        this.avr = responseTemp.trim();
      responseTemp = (String)resultMap.get("postdate");
      if (responseTemp != null)
        this.date = responseTemp.trim();
      responseTemp = (String)resultMap.get("tranid");
      if (responseTemp != null)
        this.transId = responseTemp.trim();
      responseTemp = (String)resultMap.get("amt");
      if (responseTemp != null)
        this.amt = responseTemp.trim();
      responseTemp = (String)resultMap.get("trackid");
      if (responseTemp != null)
        this.trackId = responseTemp.trim();
      responseTemp = (String)resultMap.get("udf1");
      if (responseTemp != null)
        this.udf1 = responseTemp.trim();
      responseTemp = (String)resultMap.get("udf2");
      if (responseTemp != null)
        this.udf2 = responseTemp.trim();
      responseTemp = (String)resultMap.get("udf3");
      if (responseTemp != null)
        this.udf3 = responseTemp.trim();
      responseTemp = (String)resultMap.get("udf4");
      if (responseTemp != null)
        this.udf4 = responseTemp.trim();
      responseTemp = (String)resultMap.get("udf5");
      if (responseTemp != null)
        this.udf5 = responseTemp.trim();
      responseTemp = (String)resultMap.get("error_code_tag");
      if (responseTemp != null)
        this.error = responseTemp.trim();
      responseTemp = (String)resultMap.get("error_service_tag");
      if (responseTemp != null)
        this.error_service_tag = responseTemp.trim();
      responseTemp = (String)resultMap.get("error_text");
      if (responseTemp != null)
        this.error_text = responseTemp.trim();
      responseTemp = (String)resultMap.get("responsecode");
      if (responseTemp != null)
        this.responseCode = responseTemp.trim();
      responseTemp = (String)resultMap.get("cvv2response");
      if (responseTemp != null) {
        this.cvv2Verification = responseTemp.trim();
      }
      responseTemp = (String)resultMap.get("paymentId");
      if (responseTemp != null)
        this.paymentdebitId = responseTemp.trim();
      responseTemp = (String)resultMap.get("paymenturl");
      if (responseTemp != null) {
        this.paymentUrl = responseTemp.trim();
        return 2;
      }
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
      this.error = ("Internal Error: " + e.getMessage());
      return -1;
    } finally {
      xmlData = null;
      hm = null;
      resultMap = null;
      responseTemp = null;
    }throw localObject;
  }

  private static HashMap<String, String> parseResponse(String response) {
    HashMap responseMap = null;
    int begin = 0;
    int end = 0;
    String beginString = null;
    String value = null;
    try {
      responseMap = new HashMap();
      response = response.trim();
      if ((response == null) || (!response.startsWith("<")) || (response.length() < 0))
        return null;
      try {
        do {
          beginString = response.substring(response.indexOf("<") + 1, response.indexOf(">"));
          begin = response.indexOf("<") + beginString.length() + 2;
          end = response.indexOf("</" + beginString);
          value = response.substring(begin, end);
          end = end + beginString.length() + 3;
          response = response.substring(end, response.length());
          responseMap.put(beginString, value);
          begin = 0;
          end = 0;
          beginString = "";
        }
        while (
          response.length() > 0);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      HashMap localHashMap1 = responseMap;
      return localHashMap1;
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      begin = 0;
      end = 0;
      beginString = null;
      value = null;
      responseMap = null;
    }throw localObject;
  }

  private static HashMap<String, String> parseXMLRequest(String request) {
    HashMap responseMap = null;
    try {
      request = request.trim();
      request = request.substring(request.indexOf("<id>"), request.length());
      request = request.replaceAll("</terminal>", "");
      if ((request == null) || (!request.startsWith("<")) || (request.length() < 0))
        return null;
      try {
        responseMap = parseResponse(request);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      HashMap localHashMap1 = responseMap;
      return localHashMap1;
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      responseMap = null;
    }throw localObject;
  }

  public String getTokenFlag()
  {
    return this.tokenFlag;
  }

  public void setTokenFlag(String tokenFlag) {
    this.tokenFlag = tokenFlag;
  }

  public String getTokenNumber() {
    return this.tokenNumber;
  }

  public void setTokenNumber(String tokenNumber) {
    this.tokenNumber = tokenNumber;
  }

  public String getAction()
  {
    return this.action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getAmt() {
    return this.amt;
  }

  public void setAmt(String amt) {
    this.amt = amt;
  }

  public String getAuth() {
    return this.auth;
  }

  public void setAuth(String auth) {
    this.auth = auth;
  }

  public String getAvr() {
    return this.avr;
  }

  public void setAvr(String avr) {
    this.avr = avr;
  }

  public String getCurrency()
  {
    return this.currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getDate() {
    return this.date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public StringBuffer getDebugMsg() {
    return this.debugMsg;
  }

  public void setDebugMsg(StringBuffer debugMsg) {
    this.debugMsg = debugMsg;
  }

  public String getError() {
    return this.error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getErrorURL() {
    return this.errorURL;
  }

  public void setErrorURL(String errorURL) {
    this.errorURL = errorURL;
  }

  public String getLanguage()
  {
    return this.language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getPaymentId()
  {
    return this.paymentId;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public String getPaymentPage() {
    return this.paymentPage;
  }

  public void setPaymentPage(String paymentPage) {
    this.paymentPage = paymentPage;
  }

  public String getRawResponse() {
    return this.rawResponse;
  }

  public void setRawResponse(String rawResponse) {
    this.rawResponse = rawResponse;
  }

  public String getRef() {
    return this.ref;
  }

  public void setRef(String ref) {
    this.ref = ref;
  }

  public boolean isEnableLogger() {
    return this.enableLogger;
  }

  public void setEnableLogger(boolean enableLogger) {
    this.enableLogger = enableLogger;
  }

  public String getResponseURL() {
    return this.responseURL;
  }

  public void setResponseURL(String responseURL) {
    this.responseURL = responseURL;
  }

  public String getResult() {
    return this.result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getTrackId() {
    return this.trackId;
  }

  public void setTrackId(String trackId) {
    this.trackId = trackId;
  }

  public String getTransId() {
    return this.transId;
  }

  public void setTransId(String transId) {
    this.transId = transId;
  }
  public String getCustid() {
    return this.custid;
  }
  public void setCustid(String custid) {
    this.custid = custid;
  }
  public String getUdf1() {
    return this.udf1;
  }

  public void setUdf1(String udf1) {
    this.udf1 = udf1;
  }

  public String getUdf2() {
    return this.udf2;
  }

  public void setUdf2(String udf2) {
    this.udf2 = udf2;
  }

  public String getUdf3() {
    return this.udf3;
  }

  public void setUdf3(String udf3) {
    this.udf3 = udf3;
  }

  public String getUdf4() {
    return this.udf4;
  }

  public void setUdf4(String udf4) {
    this.udf4 = udf4;
  }

  public String getUdf5() {
    return this.udf5;
  }

  public void setUdf5(String udf5) {
    this.udf5 = udf5;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getAddr()
  {
    return this.addr;
  }

  public void setAddr(String addr)
  {
    this.addr = addr;
  }

  public String getCard()
  {
    return this.card;
  }

  public void setCard(String card)
  {
    this.card = card;
  }

  public String getCavv()
  {
    return this.cavv;
  }

  public void setCavv(String cavv)
  {
    this.cavv = cavv;
  }

  public String getCvv2()
  {
    return this.cvv2;
  }

  public void setCvv2(String cvv2)
  {
    this.cvv2 = cvv2;
  }

  public String getCvv2Verification()
  {
    return this.cvv2Verification;
  }

  public void setCvv2Verification(String cvv2Verification)
  {
    this.cvv2Verification = cvv2Verification;
  }

  public String getEci()
  {
    return this.eci;
  }

  public void setEci(String eci)
  {
    this.eci = eci;
  }

  public String getExpDay()
  {
    return this.expDay;
  }

  public void setExpDay(String expDay)
  {
    this.expDay = expDay;
  }

  public String getExpMonth()
  {
    return this.expMonth;
  }

  public void setExpMonth(String expMonth)
  {
    this.expMonth = expMonth;
  }

  public String getExpYear()
  {
    return this.expYear;
  }

  public void setExpYear(String expYear)
  {
    this.expYear = expYear;
  }

  public String getMember()
  {
    return this.member;
  }

  public void setMember(String member)
  {
    this.member = member;
  }

  public String getResponseCode()
  {
    return this.responseCode;
  }

  public void setResponseCode(String responseCode)
  {
    this.responseCode = responseCode;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getZip()
  {
    return this.zip;
  }

  public void setZip(String zip)
  {
    this.zip = zip;
  }

  public String getAlias()
  {
    return this.alias;
  }

  public String getError_text()
  {
    return this.error_text;
  }

  public void setError_text(String error_text)
  {
    this.error_text = error_text;
  }

  public String getResourcePath()
  {
    return this.resourcePath;
  }

  public void setResourcePath(String resourcePath)
  {
    this.resourcePath = resourcePath;
  }

  public String getKeystorePath()
  {
    return this.keystorePath;
  }

  public void setKeystorePath(String keystorePath)
  {
    this.keystorePath = keystorePath;
  }

  public String getAcsurl()
  {
    return this.acsurl;
  }

  public void setAcsurl(String acsurl)
  {
    this.acsurl = acsurl;
  }

  public String getPareq()
  {
    return this.pareq;
  }

  public void setPareq(String pareq)
  {
    this.pareq = pareq;
  }

  public String getPares()
  {
    return this.pares;
  }

  public void setPares(String pares)
  {
    this.pares = pares;
  }

  public String getError_service_tag()
  {
    return this.error_service_tag;
  }

  public void setError_service_tag(String error_service_tag)
  {
    this.error_service_tag = error_service_tag;
  }

  public String getWebAddress() {
    return this.webAddress;
  }

  public void setWebAddress(String webAddress) {
    this.webAddress = webAddress;
  }

  public String getXid()
  {
    return this.xid;
  }

  public void setXid(String xid)
  {
    this.xid = xid;
  }

  public String getIvrFlag()
  {
    return this.ivrFlag;
  }

  public void setIvrFlag(String ivrFlag)
  {
    this.ivrFlag = ivrFlag;
  }

  public String getNpc356chphoneidformat()
  {
    return this.npc356chphoneidformat;
  }

  public void setNpc356chphoneidformat(String npc356chphoneidformat)
  {
    this.npc356chphoneidformat = npc356chphoneidformat;
  }

  public String getNpc356chphoneid()
  {
    return this.npc356chphoneid;
  }

  public void setNpc356chphoneid(String npc356chphoneid)
  {
    this.npc356chphoneid = npc356chphoneid;
  }

  public String getNpc356shopchannel()
  {
    return this.npc356shopchannel;
  }

  public void setNpc356shopchannel(String npc356shopchannel)
  {
    this.npc356shopchannel = npc356shopchannel;
  }

  public String getNpc356availauthchannel()
  {
    return this.npc356availauthchannel;
  }

  public void setNpc356availauthchannel(String npc356availauthchannel)
  {
    this.npc356availauthchannel = npc356availauthchannel;
  }

  public String getNpc356pareqchannel()
  {
    return this.npc356pareqchannel;
  }

  public void setNpc356pareqchannel(String npc356pareqchannel)
  {
    this.npc356pareqchannel = npc356pareqchannel;
  }

  public String getNpc356itpcredential()
  {
    return this.npc356itpcredential;
  }

  public void setNpc356itpcredential(String npc356itpcredential)
  {
    this.npc356itpcredential = npc356itpcredential;
  }

  public String getAuthDataName()
  {
    return this.authDataName;
  }

  public void setAuthDataName(String authDataName)
  {
    this.authDataName = authDataName;
  }

  public String getAuthDataLength()
  {
    return this.authDataLength;
  }

  public void setAuthDataLength(String authDataLength)
  {
    this.authDataLength = authDataLength;
  }

  public String getAuthDataType()
  {
    return this.authDataType;
  }

  public void setAuthDataType(String authDataType)
  {
    this.authDataType = authDataType;
  }

  public String getAuthDataLabel()
  {
    return this.authDataLabel;
  }

  public void setAuthDataLabel(String authDataLabel)
  {
    this.authDataLabel = authDataLabel;
  }

  public String getAuthDataPrompt()
  {
    return this.authDataPrompt;
  }

  public void setAuthDataPrompt(String authDataPrompt)
  {
    this.authDataPrompt = authDataPrompt;
  }

  public String getAuthDataEncryptKey()
  {
    return this.authDataEncryptKey;
  }

  public void setAuthDataEncryptKey(String authDataEncryptKey)
  {
    this.authDataEncryptKey = authDataEncryptKey;
  }

  public String getAuthDataEncryptType()
  {
    return this.authDataEncryptType;
  }

  public void setAuthDataEncryptType(String authDataEncryptType)
  {
    this.authDataEncryptType = authDataEncryptType;
  }

  public String getAuthDataEncryptMandatory()
  {
    return this.authDataEncryptMandatory;
  }

  public void setAuthDataEncryptMandatory(String authDataEncryptMandatory)
  {
    this.authDataEncryptMandatory = authDataEncryptMandatory;
  }

  public String getIvrPasswordStatus()
  {
    return this.ivrPasswordStatus;
  }

  public void setIvrPasswordStatus(String ivrPasswordStatus)
  {
    this.ivrPasswordStatus = ivrPasswordStatus;
  }

  public String getIvrPassword()
  {
    return this.ivrPassword;
  }

  public void setIvrPassword(String ivrPassword)
  {
    this.ivrPassword = ivrPassword;
  }

  public String getItpauthtran()
  {
    return this.itpauthtran;
  }

  public void setItpauthtran(String itpauthtran)
  {
    this.itpauthtran = itpauthtran;
  }

  public String getItpauthiden()
  {
    return this.itpauthiden;
  }

  public void setItpauthiden(String itpauthiden)
  {
    this.itpauthiden = itpauthiden;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPaymentdebitId() {
    return this.paymentdebitId;
  }

  public void setPaymentdebitId(String paymentdebitId) {
    this.paymentdebitId = paymentdebitId;
  }

  public String getPaymentUrl() {
    return this.paymentUrl;
  }

  public void setPaymentUrl(String paymentUrl) {
    this.paymentUrl = paymentUrl;
  }

  public String getCard_PostalCd()
  {
    return this.card_PostalCd;
  }

  public void setCard_PostalCd(String card_PostalCd) {
    this.card_PostalCd = card_PostalCd;
  }

  public String getCard_Address() {
    return this.card_Address;
  }

  public void setCard_Address(String card_Address) {
    this.card_Address = card_Address;
  }

  public String getCard_FirstName() {
    return this.card_FirstName;
  }

  public void setCard_FirstName(String card_FirstName) {
    this.card_FirstName = card_FirstName;
  }

  public String getCard_LastName() {
    return this.card_LastName;
  }

  public void setCard_LastName(String card_LastName) {
    this.card_LastName = card_LastName;
  }

  public String getCard_Phn_Num() {
    return this.card_Phn_Num;
  }

  public void setCard_Phn_Num(String card_Phn_Num) {
    this.card_Phn_Num = card_Phn_Num;
  }

  public String getShip_To_Postalcd() {
    return this.ship_To_Postalcd;
  }

  public void setShip_To_Postalcd(String ship_To_Postalcd) {
    this.ship_To_Postalcd = ship_To_Postalcd;
  }

  public String getShip_To_Address() {
    return this.ship_To_Address;
  }

  public void setShip_To_Address(String ship_To_Address) {
    this.ship_To_Address = ship_To_Address;
  }

  public String getShip_To_LastName() {
    return this.ship_To_LastName;
  }

  public void setShip_To_LastName(String ship_To_LastName) {
    this.ship_To_LastName = ship_To_LastName;
  }

  public String getShip_To_FirstName() {
    return this.ship_To_FirstName;
  }

  public void setShip_To_FirstName(String ship_To_FirstName) {
    this.ship_To_FirstName = ship_To_FirstName;
  }

  public String getShip_To_Phn_Num() {
    return this.ship_To_Phn_Num;
  }

  public void setShip_To_Phn_Num(String ship_To_Phn_Num) {
    this.ship_To_Phn_Num = ship_To_Phn_Num;
  }

  public String getShip_To_CountryCd() {
    return this.ship_To_CountryCd;
  }

  public void setShip_To_CountryCd(String ship_To_CountryCd) {
    this.ship_To_CountryCd = ship_To_CountryCd;
  }

  public String getCust_email() {
    return this.cust_email;
  }

  public void setCust_email(String cust_email) {
    this.cust_email = cust_email;
  }

  public String getMobilenum() {
    return this.mobilenum;
  }

  public void setMobilenum(String mobilenum) {
    this.mobilenum = mobilenum;
  }

  public void setTokenCustomerId(String tokenCustomerId) {
    this.tokenCustomerId = tokenCustomerId;
  }

  public String getTokenCustomerId() {
    return this.tokenCustomerId;
  }

  public String getTranPostData()
  {
    return this.tranPostData;
  }

  public void setTranPostData(String tranPostData) {
    this.tranPostData = tranPostData;
  }

  public static void main(String[] args)
  {
    System.out.println("test merchant");
  }

  public short performTransaction() {
    iPayTransactionPipeOab pipe = null;
    String xmlData = null;
    String request = null;
    StringBuilder requestbuffer = null;
    HashMap hm = null;
    String response = null;
    String webaddr = null;
    int parseresult = 0;
    try {
      xmlData = parseResource(this.keystorePath, this.resourcePath, this.alias);
      hm = new HashMap();
      if ((xmlData != null) && (!xmlData.contains("!!Exception!!"))) {
        hm = parseXMLRequest(xmlData);
      } else {
        this.error = (xmlData.contains("!!Exception!!") ? xmlData : "Alias name does not exits");
        return -1;
      }
      requestbuffer = new StringBuilder();
      requestbuffer = buildXMLRequest();
      requestbuffer.append("<id>" + (String)hm.get("id") + "</id>");
      setId((String)hm.get("id"));
      requestbuffer.append("<password>" + (String)hm.get("password") + "</password>");
      requestbuffer.append("</request>");
      request = requestbuffer.toString();
      webaddr = (String)hm.get("webaddress");

      pipe = new iPayTransactionPipeOab();
      response = pipe.performTranPortalTransaction(request, webaddr);
      if ((response == null) || (!response.contains("<paymenturl>")))
      {
        parseresult = parseResult(response);
      }
      if (parseresult == 0) {
        return 0;
      }
      this.error = ("Error while connecting " + response);
      return -1;
    }
    catch (Exception e) {
      this.error = "Error while processing request!";
      return -1;
    } finally {
      pipe = null;
      xmlData = null;
      request = null;
      requestbuffer = null;
      hm = null;
      response = null;
      webaddr = null;
      parseresult = 0;
    }throw localObject;
  }
}