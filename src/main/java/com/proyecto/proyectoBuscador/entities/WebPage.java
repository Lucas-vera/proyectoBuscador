package com.proyecto.proyectoBuscador.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 *
 * @author chuky
 */
@Entity
@Table(name = "webpage")
@Getter @Setter
@ToString @EqualsAndHashCode
public class WebPage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "url")
    private String url;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;

    private Integer contadorRevision;

    public WebPage() {
    }
    
    public WebPage(String url) {
        this.url=url;
    }

}
