package net.greenmanov.iqdb.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Search options used for matching on the iqdb
 * Can specify which services should be search and if image color should be ignored.
 * Use Options.DEFAULT for matching on all services without ignoring colors
 */
public class Options {
    public final static Options DEFAULT = new Options(new HashSet<ServiceType>(Arrays.asList(ServiceType.values())));

    protected boolean ignoreColors = false;
    protected Set<ServiceType> services;

    /**
     * Ignores colors and use no service
     */
    public Options() {
        services = new HashSet<ServiceType>();
    }

    /**
     * Ignores colors
     *
     * @param services Set of services used for matching
     */
    public Options(Set<ServiceType> services) {
        this(services, false);
    }

    /**
     * @param services     HashSet of services used for matching
     * @param ignoreColors Specify if image color should be ignored
     */
    public Options(Set<ServiceType> services, boolean ignoreColors) {
        this.ignoreColors = ignoreColors;
        this.services = new HashSet<ServiceType>(services);
    }

    /**
     * Set if colors should be ignored
     *
     * @param ignoreColors true if yes
     */
    public void setIgnoreColors(boolean ignoreColors) {
        this.ignoreColors = ignoreColors;
    }

    /**
     * Add service if not already present
     *
     * @param service New service
     */
    public void addService(ServiceType service) {
        services.add(service);
    }

    /**
     * Remove service if present
     *
     * @param service Service to be removed
     */
    public void removeService(ServiceType service) {
        services.remove(service);
    }

    /**
     * Should ignore colors?
     *
     * @return true if yes
     */
    public boolean isIgnoreColors() {
        return ignoreColors;
    }

    /**
     * Get set of services
     *
     * @return Immutable set
     */
    public Set<ServiceType> getServices() {
        return Collections.unmodifiableSet(services);
    }
}
