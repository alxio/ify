package pl.poznan.put.cs.ify.api.group;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YReceipt;

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

	/**
	 * Creates communication object that can be used to send data to server.
	 * 
	 * @param receipt
	 *            insert 'this' here
	 * @param group
	 *            name of group, should be receipt's parameter
	 * @return
	 */
	public YComm createComm(YReceipt receipt, String group) {
		// TODO: Get userName and deviceName from somewhere
		YUserData user = new YUserData(receipt.getName(), "", "", group);
		return new YComm(user, this);
	}

	protected void sendData(YCommData commData) {
		String json = commData.toJson();
		// TODO Send the json to server
	}
}
