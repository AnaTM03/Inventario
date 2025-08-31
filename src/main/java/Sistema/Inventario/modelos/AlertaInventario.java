package Sistema.Inventario.modelos;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AlertaInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    private LocalDateTime fechaAlerta;
    private String estado; // Pendiente / Resuelta

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public LocalDateTime getFechaAlerta() { return fechaAlerta; }
    public void setFechaAlerta(LocalDateTime fechaAlerta) { this.fechaAlerta = fechaAlerta; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
