/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfa.vo.sed.builder.photfilters;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author olaurino
 */
public class EnergyBin implements PassBand {

    private Double min;
    public static final String PROP_MIN = "min";

    /**
     * Get the value of min
     *
     * @return the value of min
     */
    public Double getMin() {
        return min;
    }

    /**
     * Set the value of min
     *
     * @param min new value of min
     */
    public void setMin(Double min) {
        Double oldMin = this.min;
        this.min = min;
        propertyChangeSupport.firePropertyChange(PROP_MIN, oldMin, min);
    }
    
    private Double max;
    public static final String PROP_MAX = "max";

    /**
     * Get the value of max
     *
     * @return the value of max
     */
    public Double getMax() {
        return max;
    }

    /**
     * Set the value of max
     *
     * @param max new value of max
     */
    public void setMax(Double max) {
        Double oldMax = this.max;
        this.max = max;
        propertyChangeSupport.firePropertyChange(PROP_MAX, oldMax, max);
    }

    private String units;
    public static final String PROP_UNITS = "units";

    /**
     * Get the value of units
     *
     * @return the value of units
     */
    public String getUnits() {
        return units;
    }

    /**
     * Set the value of units
     *
     * @param units new value of units
     */
    public void setUnits(String units) {
        String oldUnits = this.units;
        this.units = units;
        propertyChangeSupport.firePropertyChange(PROP_UNITS, oldUnits, units);
    }
    
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    @Override
    public String toString() {
        return min+"-"+max+" "+units;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EnergyBin other = (EnergyBin) obj;
        if (this.min != other.min && (this.min == null || !this.min.equals(other.min))) {
            return false;
        }
        if (this.max != other.max && (this.max == null || !this.max.equals(other.max))) {
            return false;
        }
        if ((this.units == null) ? (other.units != null) : !this.units.equals(other.units)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.min != null ? this.min.hashCode() : 0);
        hash = 97 * hash + (this.max != null ? this.max.hashCode() : 0);
        hash = 97 * hash + (this.units != null ? this.units.hashCode() : 0);
        return hash;
    }

    @Override
    public String getId() {
        return toString();
    }
    
}
