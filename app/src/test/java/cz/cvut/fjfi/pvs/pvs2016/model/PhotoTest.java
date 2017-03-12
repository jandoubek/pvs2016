package cz.cvut.fjfi.pvs.pvs2016.model;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import android.os.Parcel;
import nl.jqno.equalsverifier.EqualsVerifier;

public class PhotoTest {

	@Test
	public void generateId() {

		List<String> ble = new ArrayList<>();
		ble.add("ble");

		List<Series> bli = new ArrayList<>();
		bli.add(new Series("bli"));
		Photo testPhoto = new Photo("IMG_19920915_004842", "bla", ble, bli, "2016-12-11T09:42");

		assertThat(testPhoto.getId(), is("IMG_19920915_004842"));
	}

	@Test
	@Ignore
	public void equalsVer() {
		EqualsVerifier.forClass(Photo.class).verify();
	}

	@Test
	@Ignore
	public void construct() {

		List<String> ble = new ArrayList<>();
		ble.add("ble");

		List<Series> bli = new ArrayList<>();
		bli.add(new Series("bli"));
		Photo testPhoto = new Photo("IMG_19920915_004842", "bla", ble, bli, "2016-12-11T09:42");
		Parcel parcel = Parcel.obtain();
		parcel.writeString("IMG_19920915_004842");
		parcel.writeString("bla");
		Photo testPhoto2 = new Photo(parcel);

		// FIXME compare fields of photos
		//		assertThat(testPhoto, is(equalTo(testPhoto2)));
	}
}