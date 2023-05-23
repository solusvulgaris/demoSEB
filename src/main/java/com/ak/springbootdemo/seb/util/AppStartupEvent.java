package com.ak.springbootdemo.seb.util;

import com.ak.springbootdemo.seb.data.Subsidiary;
import com.ak.springbootdemo.seb.data.SubsidiaryRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Application Startup Event
 */
@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final SubsidiaryRepository subsidiaryRepository;

    public AppStartupEvent(SubsidiaryRepository subsidiaryRepository) {
        this.subsidiaryRepository = subsidiaryRepository;
    }

    /**
     * On Startup Message
     *
     * @param event application startup event
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Iterable<Subsidiary> subsidiaries = this.subsidiaryRepository.findAll();
        System.out.println("<-------    DEMO Subsidiaries Application HAS STARTED    ------->");
        System.out.println("<-------    HERE is the List of all Subsidiaries existing in the DB:");
        subsidiaries.forEach(System.out::println);
    }

}
