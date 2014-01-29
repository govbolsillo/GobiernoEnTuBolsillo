package com.Wsdl2Code.WebServices.pqrdService;

//------------------------------------------------------------------------------
// <wsdl2code-generated>
//    This code was generated by http://www.wsdl2code.com version  2.5
//
// Date Of Creation: 10/3/2013 4:21:41 AM
//    Please dont change this code, regeneration will override your changes
//</wsdl2code-generated>
//
//------------------------------------------------------------------------------
//
//This source code was auto-generated by Wsdl2Code  Version
//
import com.Wsdl2Code.WebServices.pqrdService.WS_Enums.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.HeaderProperty;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.serialization.MarshalFloat;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;


public class pqrdService {
    
    public String NAMESPACE ="http://servicios/";
    public String url="http://199.89.53.54:8080/pqrdService/pqrdService";
    public int timeOut = 60000;
    public IWsdl2CodeEvents eventHandler;
    public SoapProtocolVersion soapVersion;
    
    public pqrdService(){}
    
    public pqrdService(IWsdl2CodeEvents eventHandler)
    {
        this.eventHandler = eventHandler;
    }
    public pqrdService(IWsdl2CodeEvents eventHandler,String url)
    {
        this.eventHandler = eventHandler;
        this.url = url;
    }
    public pqrdService(IWsdl2CodeEvents eventHandler,String url,int timeOutInSeconds)
    {
        this.eventHandler = eventHandler;
        this.url = url;
        this.setTimeOut(timeOutInSeconds);
    }
    public void setTimeOut(int seconds){
        this.timeOut = seconds * 1000;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public void registrarPqrAsync(registroPQRDVO registroPQRD,archivoAdjuntoVO video,VectorByte dataVideo,archivoAdjuntoVO foto,VectorByte dataFoto) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        registrarPqrAsync(registroPQRD, video, dataVideo, foto, dataFoto, null);
    }
    
    public void registrarPqrAsync(final registroPQRDVO registroPQRD,final archivoAdjuntoVO video,final VectorByte dataVideo,final archivoAdjuntoVO foto,final VectorByte dataFoto,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, respuestaRegistroVO>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected respuestaRegistroVO doInBackground(Void... params) {
                return registrarPqr(registroPQRD, video, dataVideo, null, foto, dataFoto, null, headers);
            }
            @Override
            protected void onPostExecute(respuestaRegistroVO result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("registrarPqr", result);
                }
        }
        }.execute();
    }
    
    public respuestaRegistroVO registrarPqr(registroPQRDVO registroPQRD,archivoAdjuntoVO video,VectorByte dataVideo,archivoAdjuntoVO foto,VectorByte dataFoto){
        return registrarPqr(registroPQRD, video, dataVideo, null, foto, dataFoto, null, null);
    }
    
    public respuestaRegistroVO registrarPqr(registroPQRDVO registroPQRD
            , archivoAdjuntoVO video, VectorByte dataVideo, File fVideo
            , archivoAdjuntoVO foto, VectorByte dataFoto, File fFoto
            , List<HeaderProperty> headers){

        //http://blog.tacticalnuclearstrike.com/2010/01/using-multipartentity-in-android-applications/#sthash.pXMhEW1m.dpuf
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        respuestaRegistroVO resultVariable = new respuestaRegistroVO();

        try {

            prepareHttpPost(httppost, registroPQRD, fVideo, fFoto);
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entityResponse = response.getEntity();

            String responseText = EntityUtils.toString(entityResponse);

            StringReader reader = new StringReader(responseText);
            InputSource source = new InputSource(reader);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(source);
            doc.getDocumentElement().normalize();

            if (response.getStatusLine().getStatusCode() == 200) {
                try {
                    /*
                    XPath xpath = XPathFactory.newInstance().newXPath();
                    String xpathPattern = "/S:Envelope/S:Body[1]/ns2:registrarPqrResponse[1]/return[1]/anno[1]/text()";

                    XPathExpression expr = xpath.compile(xpathPattern);
                    Object result = expr.evaluate(doc, XPathConstants.NODESET);

                    resultVariable.annoSpecified = result
                    NodeList nodes = (NodeList) result;

                    NodeList nodes = (NodeList) xpath.evaluate(expr, doc, XPathConstants.NODESET);
                    */

                    NodeList lstDoc = doc.getElementsByTagName("S:Envelope");
                    NodeList lstReturn = lstDoc.item(0) //envelope
                            .getChildNodes().item(0) //body
                            .getChildNodes().item(0) //response
                            .getChildNodes().item(0) //return
                            .getChildNodes();
                    String vigencia = lstReturn.item(0).getChildNodes().item(0).getNodeValue();
                    String msg = lstReturn.item(1).getChildNodes().item(0).getNodeValue();
                    String radicado = lstReturn.item(2).getChildNodes().item(0).getNodeValue();

                    resultVariable.anno = Integer.parseInt(vigencia);
                    resultVariable.numRadicado = Long.parseLong(radicado);
                    resultVariable.mensajeError = msg;
                } catch (Exception e) {
                    resultVariable.mensajeError = e.getMessage();
                }
            } else {
                NodeList lstDoc = doc.getElementsByTagName("S:Envelope");
                NodeList lstReturn = lstDoc.item(0) //envelope
                        .getChildNodes().item(0) //body
                        .getChildNodes().item(0) //fault
                        .getChildNodes();
                String code = lstReturn.item(0).getChildNodes().item(0).getNodeValue();
                String msg = lstReturn.item(1).getChildNodes().item(0).getNodeValue();
                resultVariable.mensajeError = msg;
            }

            return resultVariable;
        } catch (ClientProtocolException e) {
            resultVariable.mensajeError = e.getMessage();
            //return null;
        } catch (IOException e) {
            resultVariable.mensajeError = e.getMessage();
        } catch (Exception e) {
            resultVariable.mensajeError = e.getMessage();
        }
        return resultVariable;
        /*
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = false;
        soapEnvelope.dotNet = false;
        SoapObject soapReq = new SoapObject("http://servicios/","registrarPqr");
        soapEnvelope.addMapping("http://servicios/","registroPQRD",new registroPQRDVO().getClass());

        if(video != null)
            soapEnvelope.addMapping("http://servicios/","video",new archivoAdjuntoVO().getClass());

        if(foto != null)
            soapEnvelope.addMapping("http://servicios/","foto",new archivoAdjuntoVO().getClass());

        soapReq.addProperty("registroPQRD",registroPQRD);

        if(video != null)
            soapReq.addProperty("video",video);

        if(dataVideo != null)
            soapReq.addProperty("dataVideo",dataVideo.toString());

        if(foto != null)
            soapReq.addProperty("foto",foto);

        if(dataFoto != null)
            soapReq.addProperty("dataFoto",dataFoto.toString());

        soapEnvelope.setOutputSoapObject(soapReq);



        HttpTransportSE httpTransport = new HttpTransportSE(url,timeOut);
        httpTransport.debug = true;
        try{
            if (headers!=null){
                httpTransport.call("http://servicios/pqrdService/registrarPqrRequest", soapEnvelope,headers);
            }else{
                httpTransport.call("http://servicios/pqrdService/registrarPqrRequest", soapEnvelope);
            }
            Object retObj = soapEnvelope.bodyIn;
            if (retObj instanceof SoapFault){
                SoapFault fault = (SoapFault)retObj;
                Exception ex = new Exception(fault.faultstring);
                if (eventHandler != null)
                    eventHandler.Wsdl2CodeFinishedWithException(ex);
            }else{
                SoapObject result=(SoapObject)retObj;
                if (result.getPropertyCount() > 0){
                    Object obj = result.getProperty(0);
                    SoapObject j = (SoapObject)obj;
                    respuestaRegistroVO resultVariable =  new respuestaRegistroVO (j);
                    return resultVariable;
                    
                }
            }
        }catch (Exception e) {
            if (eventHandler != null)
                eventHandler.Wsdl2CodeFinishedWithException(e);
            e.printStackTrace();
        }
        return null;
        */
    }

    private void prepareHttpPost(HttpPost httppost, registroPQRDVO registroPQRD, File fVideo, File fFoto) throws Exception{
        httppost.addHeader("Accept-Encoding", "gzip,deflate");
        httppost.addHeader("Content-Type", "multipart/related; type=\"text/xml\"; start=\"<rootpart@soapui.org>\"; boundary=\"----=_Part_0_125988802.1383104763144\"");
        httppost.addHeader("SOAPAction", "\"\"");
        httppost.addHeader("MIME-Version", "1.0");
        httppost.addHeader("Host", "199.89.53.54:8080");
        httppost.addHeader("Connection", "Keep-Alive");
        httppost.addHeader("User-Agent", "Apache-HttpClient/4.1.1 (java 1.5)");
        MultipartEntity mpEntity = getMultipartEntity(registroPQRD, fVideo, fFoto);
        httppost.setEntity(mpEntity);
    }

    private MultipartEntity getMultipartEntity(registroPQRDVO pqr, File fVideo, File fFoto) throws Exception{
        String xml =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://servicios/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <ser:registrarPqr>\n" +
                        "         <registroPQRD>\n" +
                        "            <tipoPQRD>%1$s</tipoPQRD>\n" +
                        "            <fechaSolicitud>%2$s</fechaSolicitud>\n" +
                        "            <tipoIdentificacion>%3$s</tipoIdentificacion>\n" +
                        "            <idCiudadano>%4$s</idCiudadano>\n" +
                        "            <apellidos>%5$s</apellidos>\n" +
                        "            <nombres>%6$s</nombres>\n" +
                        "            <email>%7$s</email>\n" +
                        "            <telefonos>%8$s</telefonos>\n" +
                        "            <direccion>%9$s</direccion>\n" +
                        "            <lugarDireccion>%10$s</lugarDireccion>\n" +
                        "            <ciudad>%11$s</ciudad>\n" +
                        "            <coordenadasGPS>%12$s</coordenadasGPS>\n" +
                        "            <motivoSolicitud>%13$s</motivoSolicitud>\n" +
                        "         </registroPQRD>\n";
        xml =  String.format(xml
                , pqr.tipoPQRD, pqr.fechaSolicitud, pqr.tipoIdentificacion, pqr.idCiudadano
                , pqr.apellidos, pqr.nombres, pqr.email, pqr.telefonos, pqr.direccion, pqr.lugarDireccion
                , pqr.ciudad, pqr.coordenadasGPS, pqr.motivoSolicitud);
        if(fFoto != null)
            xml +=  String.format(
                    "         <foto>\n" +
                    "            <mimeType>%1$s</mimeType>\n" +
                    "            <name>%2$s</name>\n" +
                    "         </foto>\n" +
                    "         <dataFoto>cid:%2$s</dataFoto>\n"
                    , "image/jpeg", fFoto.getName()
            );

        if(fVideo != null)
            xml +=  String.format(
                    "         <video>\n" +
                    "            <mimeType>%1$s</mimeType>\n" +
                    "            <name>%2$s</name>\n" +
                    "         </video>\n" +
                    "         <dataVideo>cid:%2$s</dataVideo>\n"
                    , "video/mp4", fVideo.getName()
            );

        xml +=  "      </ser:registrarPqr>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        String boundary = "----=_Part_0_125988802.1383104763144";

        StringBody xmlHeader = new StringBody( xml, "text/xml", Charset.forName( "UTF-8" ) );
        FormBodyPart xmlBody = new FormBodyPart( "string_body_name", xmlHeader );
        xmlBody.getHeader().removeFields( "Content-Disposition" );
        xmlBody.getHeader().removeFields( "Content-Transfer-Encoding" );
        xmlBody.addField("Content-Id", "<rootpart@soapui.org>");

        MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.STRICT, boundary, Charset.forName( "UTF-8" ));
        mpEntity.addPart( xmlBody );

        if(fFoto != null)
            putFile(mpEntity, fFoto, fFoto.getName(), "image/jpeg");

        if(fVideo != null)
            putFile(mpEntity, fVideo, fVideo.getName(), "video/mp4");

        return mpEntity;
    }

    private void putFile(MultipartEntity mpEntity, File file, String fileName, String contentType) throws Exception{
        ContentBody cbFile = new FileBody(file);
        FormBodyPart binaryBody = new FormBodyPart("form_body_name", cbFile);
        binaryBody.getHeader().removeFields( "Content-Disposition" );
        binaryBody.getHeader().removeFields( "Content-Type" );
        binaryBody.addField("Content-Type", String.format("%1$s; name=%2$s", contentType, fileName));
        binaryBody.addField("Content-Disposition", String.format("attachment; name=\"%1$s\"; filename=\"%1$s\"", fileName));
        binaryBody.addField("content-id", String.format("<%1$s>", fileName));
        mpEntity.addPart(binaryBody);
    }

    public void getRespuestasPQRDAsync(long numSolicitud,boolean numSolicitudSpecified,int vigencia,boolean vigenciaSpecified) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getRespuestasPQRDAsync(numSolicitud, numSolicitudSpecified, vigencia, vigenciaSpecified, null);
    }
    
    public void getRespuestasPQRDAsync(final long numSolicitud,final boolean numSolicitudSpecified,final int vigencia,final boolean vigenciaSpecified,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, VectorrespuestaProcesoVO>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected VectorrespuestaProcesoVO doInBackground(Void... params) {
                return getRespuestasPQRD(numSolicitud, numSolicitudSpecified, vigencia, vigenciaSpecified, headers);
            }
            @Override
            protected void onPostExecute(VectorrespuestaProcesoVO result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getRespuestasPQRD", result);
                }
            }
        }.execute();
    }
    
    public VectorrespuestaProcesoVO getRespuestasPQRD(long numSolicitud,boolean numSolicitudSpecified,int vigencia,boolean vigenciaSpecified){
        return getRespuestasPQRD(numSolicitud, numSolicitudSpecified, vigencia, vigenciaSpecified, null);
    }
    
    public VectorrespuestaProcesoVO getRespuestasPQRD(long numSolicitud,boolean numSolicitudSpecified,int vigencia,boolean vigenciaSpecified,List<HeaderProperty> headers){
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = false;
        SoapObject soapReq = new SoapObject("http://servicios/","getRespuestasPQRD");
        soapReq.addProperty("numSolicitud",numSolicitud);
        soapReq.addProperty("numSolicitudSpecified",numSolicitudSpecified);
        soapReq.addProperty("vigencia",vigencia);
        soapReq.addProperty("vigenciaSpecified",vigenciaSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(url,timeOut);
        httpTransport.debug = true;
        try{
            if (headers!=null){
                httpTransport.call("http://servicios/pqrdService/getRespuestasPQRDRequest", soapEnvelope,headers);
            }else{
                httpTransport.call("http://servicios/pqrdService/getRespuestasPQRDRequest", soapEnvelope);
                // Logging the raw request and response (for debugging purposes)
                //Log.d(TAG, "HTTP REQUEST:\n" + httpTransport.requestDump);
                //Log.d(TAG, "HTTP RESPONSE:\n" + httpTransport.responseDump);
                //String requestDump = httpTransport.requestDump;
            }
            Object retObj = soapEnvelope.bodyIn;
            if (retObj instanceof SoapFault){
                SoapFault fault = (SoapFault)retObj;
                Exception ex = new Exception(fault.faultstring);
                if (eventHandler != null)
                    eventHandler.Wsdl2CodeFinishedWithException(ex);
            }else{
                SoapObject result=(SoapObject)retObj;
                if (result.getPropertyCount() > 0){
                    //Object obj = result.getProperty(0);
                    //SoapObject j = (SoapObject)obj;
                    VectorrespuestaProcesoVO resultVariable = new VectorrespuestaProcesoVO(result);
                    return resultVariable;
                }
            }
        }catch (Exception e) {
            if (eventHandler != null)
                eventHandler.Wsdl2CodeFinishedWithException(e);
            e.printStackTrace();
        }
        return null;
    }
    
}
