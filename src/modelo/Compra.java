package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author intel
 */
public class Compra {
    private int id;
    
    private int idProveedor;
    private Proveedor proveedor;
    
    private String idUsuario;
    private Usuario usuario;
    
    private double pago;
    
    private LocalDateTime fecha;
    
    public List<CompraProducto> comprasProducto = new ArrayList();
    
    public Compra(){};

    public Compra(int id, int idProveedor, String idUsuario, double pago, LocalDateTime fecha) {
        this.id = id;
        this.idProveedor = idProveedor;
        this.idUsuario = idUsuario;
        this.pago = pago;
        this.fecha = fecha;
    }

    public Compra(int id, Proveedor proveedor, Usuario usuario, double pago, LocalDateTime fecha) {
        this.id = id;
        this.proveedor = proveedor;
        this.usuario = usuario;
        this.pago = pago;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<CompraProducto> getComprasProducto() {
        return comprasProducto;
    }

    public void setComprasProducto(List<CompraProducto> comprasProducto) {
        this.comprasProducto = comprasProducto;
    }
    
    public void calcularPago(){
        double pago = 0;
        for (CompraProducto compraProducto : comprasProducto) {
            pago += compraProducto.getPrecioUnitario() * compraProducto.getCantidad();
        }
        
        this.pago = pago;
    }
}
