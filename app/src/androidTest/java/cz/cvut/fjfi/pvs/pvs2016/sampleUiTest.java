package cz.cvut.fjfi.pvs.pvs2016;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import cz.cvut.fjfi.pvs.pvs2016.camera.CameraActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class sampleUiTest {

	//	@Rule
	//	public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
	//			MainActivity.class);
	@Rule
	public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<MainActivity>(
			MainActivity.class);

	@Test
	public void turnOnCamera() {
		Espresso.onView(ViewMatchers.withId(R.id.create_new)).perform(ViewActions.click());
		Intents.intended(IntentMatchers.hasComponent(CameraActivity.class.getName()));
		Espresso.onView(ViewMatchers.withId(R.id.cameraPreview)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
	}
}
