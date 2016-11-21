package cz.cvut.fjfi.pvs.pvs2016.camera;

public interface ICaptureDialogListener {

	/**
	 * User wants continue in capturing images.
	 */
	void onContinueClick();

	/**
	 * User do not want use captured image.
	 */
	void onCancelClick();

	/**
	 * User wants end action and save captured images.
	 */
	void onSaveClick();

}
