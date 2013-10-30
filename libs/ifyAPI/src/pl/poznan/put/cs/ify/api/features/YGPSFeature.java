package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;

public class YGPSFeature extends YFeature {

	public static final int ID = Y.GPS;

	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getName() {
		return "YGPSFeature";
	}

	@Override
	protected void init(IYReceiptHost srv) {
		// TODO Auto-generated method stub
	}

	@Override
	public void uninitialize() {
		// TODO Auto-generated method stub
	}
}
