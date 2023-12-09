/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author intel
 */
public class VentaProducto {
    private int idVenta;
    private Venta venta;
    
    private int idProducto;
    private Producto producto;
    
    private int cantidad;
    private double precioUnitario;
    private double total;

    public VentaProducto(){};
    
    public VentaProducto(int idVenta, int idProducto, int cantidad, double precioUnitario, double total) {
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public double getSubTotal(){
        return (double) Math.round(cantidad * precioUnitario * 100) / 100;
    }
    
    public double calcularTotal(){
        double subTotal = getSubTotal();
        this.total = (double) Math.round((subTotal + subTotal*0.18) * 100) / 100;
        
        return this.total;
    }

    @Override
    public String toString() {
        return producto.toString();
    }
}
