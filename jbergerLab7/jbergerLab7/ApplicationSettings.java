package jbergerlab7;

public class ApplicationSettings {

	public boolean mDateTimeSelected = true;
	public boolean mItalicsSelected = false;
	public boolean mBoldSelected = false;
	public String mUserString = "";
	private int mFontSize = 12;
	
	private static ApplicationSettings single_instance = null;
		
	private ApplicationSettings() {

	}
	
	public static ApplicationSettings getInstance() {
		if (single_instance == null) {
			single_instance = new ApplicationSettings();
		}
		
		return single_instance;
	}
	
	public int getFontSize() {
		return mFontSize;
	}
	
	public void setFontSize(int size) {
		if (size >= 8 && size <= 40) {
			mFontSize = size;
		}
	}
}
