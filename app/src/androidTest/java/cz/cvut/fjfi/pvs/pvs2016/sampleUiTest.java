package cz.cvut.fjfi.pvs.pvs2016;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class sampleUiTest {

	@Rule
	public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<MainActivity>(
			MainActivity.class);

	@Test
	public void turnOnCamera() {
		Assert.assertTrue(true);
	}
}
