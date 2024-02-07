package edu.java.bot;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class TempDB {
    private final Set<String> resources;

    public TempDB() {
        resources = new HashSet<>();
    }

    public void addResourceToDB(String resource) {
        resources.add(resource);
    }

    public void removeResourceFromDB(String resource) {
        resources.remove(resource);
    }

    public boolean isEmpty() {
        return resources.isEmpty();
    }

    public Set<String> getAllResources() {
        return resources;
    }

    public boolean containsResource(String resource) {
        return resources.contains(resource);
    }
}
