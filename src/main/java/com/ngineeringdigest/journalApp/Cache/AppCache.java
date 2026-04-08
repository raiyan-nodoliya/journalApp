package com.ngineeringdigest.journalApp.Cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngineeringdigest.journalApp.Entity.ConfigJournalAppEntity;
import com.ngineeringdigest.journalApp.Repository.ConfigJournalAppRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {
	
	public enum keys{
		weather_api;
	}

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> APP_CACHE;

    @PostConstruct
    public void init() {
    	APP_CACHE = new HashMap<>();

        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();

        for (ConfigJournalAppEntity configJournalAppEntity : all) {
            APP_CACHE.put(
                configJournalAppEntity.getKey(),
                configJournalAppEntity.getValue()
            );
        }
    }
}