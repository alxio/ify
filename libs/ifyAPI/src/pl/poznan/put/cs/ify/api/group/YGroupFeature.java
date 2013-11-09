package pl.poznan.put.cs.ify.api.group;

import java.util.List;
import java.util.Map;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.params.YParam;

public class YGroupFeature extends YFeature {
	public static final int ID = Y.Group;

	@Override
	public int getId() {
		return ID;
	}

	@Override
	protected void init(IYReceiptHost srv) {
		// TODO Auto-generated method stub

	}

	@Override
	public void uninitialize() {
		// TODO Auto-generated method stub

	}

	protected void sendData(YCommData commData) {
		// TODO Auto-generated method stub
	}
}
