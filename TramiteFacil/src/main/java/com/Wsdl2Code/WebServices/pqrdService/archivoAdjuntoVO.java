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
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import java.util.Hashtable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

public class archivoAdjuntoVO implements KvmSerializable {
    
    public String mimeType;
    public String name;
    
    public archivoAdjuntoVO(){}
    
    public archivoAdjuntoVO(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("mimeType"))
        {
            Object obj = soapObject.getProperty("mimeType");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                mimeType = j.toString();
            }else if (obj!= null && obj instanceof String){
                mimeType = (String) obj;
            }
        }
        if (soapObject.hasProperty("name"))
        {
            Object obj = soapObject.getProperty("name");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                name = j.toString();
            }else if (obj!= null && obj instanceof String){
                name = (String) obj;
            }
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return mimeType;
            case 1:
                return name;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 2;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "mimeType";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "name";
                break;
        }
    }
    
    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
