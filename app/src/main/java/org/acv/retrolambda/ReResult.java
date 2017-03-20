package org.acv.retrolambda;

/**
 * Created by acvmobiledev02 on 3/20/17.
 */
@FunctionalInterface
public interface ReResult {
    public String getResult(int left, int right, String option, boolean chooser);
}
