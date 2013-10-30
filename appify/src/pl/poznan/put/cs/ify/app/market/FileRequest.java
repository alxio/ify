package pl.poznan.put.cs.ify.app.market;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;

public class FileRequest extends Request<byte[]> {

	public interface onFileDeliveredListener {
		public void onResponseDelivered(byte[] response);
	}

	private onFileDeliveredListener mListener;

	public FileRequest(int method, String url, ErrorListener listener,
			onFileDeliveredListener responseListener) {
		super(method, url, listener);
		mListener = responseListener;
	}

	@Override
	protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
		return Response.success(response.data, null);
	}

	@Override
	protected void deliverResponse(byte[] response) {
		mListener.onResponseDelivered(response);
	}

}
