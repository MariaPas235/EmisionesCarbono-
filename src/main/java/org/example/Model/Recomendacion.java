package org.example.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "recomendacion", schema = "carbono")
public class Recomendacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recomendacion", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria")
    private Categoria idCategoria;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "impacto_estimado", precision = 10, scale = 2)
    private BigDecimal impactoEstimado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getImpactoEstimado() {
        return impactoEstimado;
    }

    public void setImpactoEstimado(BigDecimal impactoEstimado) {
        this.impactoEstimado = impactoEstimado;
    }

    @Override
    public String toString() {
        return "Recomendacion{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", impactoEstimado=" + impactoEstimado +
                '}';
    }
}