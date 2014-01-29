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

public class registroPQRDVO implements KvmSerializable {
    
    public String apellidos;
    public String ciudad;
    public String coordenadasGPS;
    public String direccion;
    public String email;
    public String fechaSolicitud;
    public boolean fechaSolicitudSpecified;
    public String idCiudadano;
    public String lugarDireccion;
    public String motivoSolicitud;
    public String nombres;
    public String telefonos;
    public long tipoIdentificacion;
    public boolean tipoIdentificacionSpecified;
    public String tipoPQRD;
    
    public registroPQRDVO(){}
    
    public registroPQRDVO(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("apellidos"))
        {
            Object obj = soapObject.getProperty("apellidos");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                apellidos = j.toString();
            }else if (obj!= null && obj instanceof String){
                apellidos = (String) obj;
            }
        }
        if (soapObject.hasProperty("ciudad"))
        {
            Object obj = soapObject.getProperty("ciudad");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                ciudad = j.toString();
            }else if (obj!= null && obj instanceof String){
                ciudad = (String) obj;
            }
        }
        if (soapObject.hasProperty("coordenadasGPS"))
        {
            Object obj = soapObject.getProperty("coordenadasGPS");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                coordenadasGPS = j.toString();
            }else if (obj!= null && obj instanceof String){
                coordenadasGPS = (String) obj;
            }
        }
        if (soapObject.hasProperty("direccion"))
        {
            Object obj = soapObject.getProperty("direccion");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                direccion = j.toString();
            }else if (obj!= null && obj instanceof String){
                direccion = (String) obj;
            }
        }
        if (soapObject.hasProperty("email"))
        {
            Object obj = soapObject.getProperty("email");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                email = j.toString();
            }else if (obj!= null && obj instanceof String){
                email = (String) obj;
            }
        }
        if (soapObject.hasProperty("fechaSolicitud"))
        {
            Object obj = soapObject.getProperty("fechaSolicitud");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                fechaSolicitud = j.toString();
            }else if (obj!= null && obj instanceof String){
                fechaSolicitud = (String) obj;
            }
        }
        if (soapObject.hasProperty("fechaSolicitudSpecified"))
        {
            Object obj = soapObject.getProperty("fechaSolicitudSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                fechaSolicitudSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                fechaSolicitudSpecified = (Boolean) obj;
            }
        }
        if (soapObject.hasProperty("idCiudadano"))
        {
            Object obj = soapObject.getProperty("idCiudadano");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                idCiudadano = j.toString();
            }else if (obj!= null && obj instanceof String){
                idCiudadano = (String) obj;
            }
        }
        if (soapObject.hasProperty("lugarDireccion"))
        {
            Object obj = soapObject.getProperty("lugarDireccion");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                lugarDireccion = j.toString();
            }else if (obj!= null && obj instanceof String){
                lugarDireccion = (String) obj;
            }
        }
        if (soapObject.hasProperty("motivoSolicitud"))
        {
            Object obj = soapObject.getProperty("motivoSolicitud");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                motivoSolicitud = j.toString();
            }else if (obj!= null && obj instanceof String){
                motivoSolicitud = (String) obj;
            }
        }
        if (soapObject.hasProperty("nombres"))
        {
            Object obj = soapObject.getProperty("nombres");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                nombres = j.toString();
            }else if (obj!= null && obj instanceof String){
                nombres = (String) obj;
            }
        }
        if (soapObject.hasProperty("telefonos"))
        {
            Object obj = soapObject.getProperty("telefonos");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                telefonos = j.toString();
            }else if (obj!= null && obj instanceof String){
                telefonos = (String) obj;
            }
        }
        if (soapObject.hasProperty("tipoIdentificacion"))
        {
            Object obj = soapObject.getProperty("tipoIdentificacion");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                tipoIdentificacion = Long.parseLong(j.toString());
            }else if (obj!= null && obj instanceof Number){
                tipoIdentificacion = (Integer) obj;
            }
        }
        if (soapObject.hasProperty("tipoIdentificacionSpecified"))
        {
            Object obj = soapObject.getProperty("tipoIdentificacionSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                tipoIdentificacionSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                tipoIdentificacionSpecified = (Boolean) obj;
            }
        }
        if (soapObject.hasProperty("tipoPQRD"))
        {
            Object obj = soapObject.getProperty("tipoPQRD");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                tipoPQRD = j.toString();
            }else if (obj!= null && obj instanceof String){
                tipoPQRD = (String) obj;
            }
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return apellidos;
            case 1:
                return ciudad;
            case 2:
                return coordenadasGPS;
            case 3:
                return direccion;
            case 4:
                return email;
            case 5:
                return fechaSolicitud;
            case 6:
                return fechaSolicitudSpecified;
            case 7:
                return idCiudadano;
            case 8:
                return lugarDireccion;
            case 9:
                return motivoSolicitud;
            case 10:
                return nombres;
            case 11:
                return telefonos;
            case 12:
                return tipoIdentificacion;
            case 13:
                return tipoIdentificacionSpecified;
            case 14:
                return tipoPQRD;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 15;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "apellidos";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ciudad";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "coordenadasGPS";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "direccion";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "email";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "fechaSolicitud";
                break;
            case 6:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "fechaSolicitudSpecified";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "idCiudadano";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "lugarDireccion";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "motivoSolicitud";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "nombres";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "telefonos";
                break;
            case 12:
                info.type = Long.class;
                info.name = "tipoIdentificacion";
                break;
            case 13:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "tipoIdentificacionSpecified";
                break;
            case 14:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "tipoPQRD";
                break;
        }
    }
    
    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
