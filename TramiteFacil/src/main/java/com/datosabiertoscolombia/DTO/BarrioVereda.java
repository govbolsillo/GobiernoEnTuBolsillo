package com.datosabiertoscolombia.DTO;

/**
 * Created by jgonzalez on 15/12/13.
 */
public class BarrioVereda {
    private String PartitionKey;
    private String RowKey;
    private String codigo;
    private String tipo;
    private String nombre;

    public String getPartitionKey() {return PartitionKey;}
    public void setPartitionKey(String partitionKey) {PartitionKey = partitionKey;}
    public String getRowKey() {return RowKey;}
    public void setRowKey(String rowKey) {RowKey = rowKey;}
    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}
    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
}
