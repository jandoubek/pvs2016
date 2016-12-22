package cz.cvut.fjfi.pvs.pvs2016.model;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import android.os.Parcel;
import nl.jqno.equalsverifier.EqualsVerifier;

public class PhotoTest {

	@Test
	public void generateId(){

		Set<String> ble = new HashSet<>();
		ble.add("ble");

		Set<Series> bli = new HashSet<>();
		bli.add(new Series("bli"));
		Photo testPhoto = new Photo("IMG_19920915_004842", "bla", ble, bli, "2016-12-11T09:42");

		assertThat(testPhoto.getId(), is("IMG_19920915_004842"));
	}

	@Test@Ignore
	public void equalsVer() {
		EqualsVerifier.forClass(Photo.class).verify();
	}

	@Test@Ignore
	public void construct() {

		Set<String> ble = new HashSet<>();
		ble.add("ble");

		Set<Series> bli = new HashSet<>();
		bli.add(new Series("bli"));
		Photo testPhoto = new Photo("IMG_19920915_004842", "bla", ble, bli, "2016-12-11T09:42");
		Parcel parcel = Parcel.obtain();
		parcel.writeString("IMG_19920915_004842");
		parcel.writeString("bla");
		Photo testPhoto2 = new Photo(parcel);

		assertThat(testPhoto, is(equalTo(testPhoto2)));
	}
}