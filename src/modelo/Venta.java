/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author intel
 */
public class Venta {
    private int id;
    
    private int idCliente;
    private Cliente cliente;
    
    private String idUsuario;
    private Usuario usuario;
    
    private double ganancia;
    
    private LocalDateTime fecha;
    
    public List<VentaProducto> ventasProducto = new ArrayList();
    
    public Venta(){};
    
    public Venta(int id, int idCliente, String idUsuario, double ganancia, LocalDateTime fecha) {
        this.id = id;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.ganancia = ganancia;
        this.fecha = fecha;
    }
    
    public Venta(int id, Cliente cliente, Usuario usuario, double ganancia, LocalDateTime fecha) {
        this.id = id;
        this.usuario = usuario;
        this.cliente = cliente;
        this.ganancia = ganancia;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public double getGanancia() {
        return ganancia;
    }

    public void setGanancia(double ganancia) {
        this.ganancia = ganancia;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    public void calcularGanancia(){
        double ganancia = 0;
        for (VentaProducto ventaProducto : ventasProducto) {
            ganancia += ventaProducto.getSubTotal();
        }
        
        this.ganancia = ganancia;
    }

    public List<VentaProducto> getVentasProducto() {
        return ventasProducto;
    }

    public void setVentasProducto(List<VentaProducto> ventasProducto) {
        this.ventasProducto = ventasProducto;
    }
    
}
