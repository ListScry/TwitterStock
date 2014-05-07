package Filtration;

/**
 * Created by Stefan Mellem on 5/7/14.
 */
public abstract class Weighter extends Filter {
    public enum WeightingType {LINEAR, SQRT, LOG, EXPASYMP}
    protected WeightingType type;
}
