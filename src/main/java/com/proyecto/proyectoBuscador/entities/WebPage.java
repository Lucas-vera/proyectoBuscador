package com.proyecto.proyectoBuscador.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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

    public WebPage() {
    }
    
    public WebPage(String url) {
        this.url=url;
    }

}
