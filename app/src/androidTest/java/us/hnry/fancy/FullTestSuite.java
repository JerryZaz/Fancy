package us.hnry.fancy;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by Henry on 2/5/2016.
 */
public class FullTestSuite extends TestSuite {
    public static Test suite(){
        return new TestSuiteBuilder(FullTestSuite.class)
                .includeAllPackagesUnderHere().build();
    }
}
