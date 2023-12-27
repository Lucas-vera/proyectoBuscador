package com.proyecto.proyectoBuscador.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *Servicio para programar el otro servicio a ejecutarse a un tiempo determinado
 * @author chuky
 */

@Configuration
@EnableScheduling
public class ScheduleService {
    
    @Autowired
    private SpiderService spiderService;
    @Autowired
    private SearchService service;
    
    @Scheduled(cron = "0 25 14 * * ?")
    public void scheduleIndexWebPages(){
        spiderService.indexWebPages();
        service.cleanWebPages();
    }
}
