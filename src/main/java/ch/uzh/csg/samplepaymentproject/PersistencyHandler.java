package ch.uzh.csg.samplepaymentproject;

import java.util.ArrayList;

import android.util.Log;
import ch.uzh.csg.coinblesk.customserialization.Currency;
import ch.uzh.csg.coinblesk.customserialization.exceptions.UnknownCurrencyException;
import ch.uzh.csg.paymentlib.persistency.IPersistencyHandler;
import ch.uzh.csg.paymentlib.persistency.PersistedPaymentRequest;

public class PersistencyHandler implements IPersistencyHandler {
	private static final String TAG = "PersistencyHandler";
	
	private ArrayList<PersistedPaymentRequest> list = new ArrayList<PersistedPaymentRequest>();
	
	@Override
	public PersistedPaymentRequest getPersistedPaymentRequest(String username, Currency currency, long amount) {
		Log.i(TAG, "getPersistedPaymentRequest");
		try {
			for (PersistedPaymentRequest request : list) {
				if (request.getUsername().equals(username) && request.getCurrency().getCode() == currency.getCode() && request.getAmount() == amount) {
					return request;
				}
			}
		} catch (UnknownCurrencyException e) {
		}
		return null;
	}
	
	@Override
	public boolean addPersistedPaymentRequest(PersistedPaymentRequest paymentRequest) {
		Log.i(TAG, "add");
		boolean exists = false;
		for (PersistedPaymentRequest request : list) {
			if (request.equals(paymentRequest)) {
				exists = true;
				break;
			}
		}
		if (!exists) {
			list.add(paymentRequest);
		}
		return true;
	}
	
	@Override
	public boolean deletePersistedPaymentRequest(PersistedPaymentRequest paymentRequest) {
		Log.i(TAG, "delete");
		for (int i=0; i<list.size(); i++) {
			PersistedPaymentRequest request = list.get(i);
			if (request.equals(paymentRequest)) {
				list.remove(i);
				break;
			}
		}
		return true;
	}
	
	public ArrayList<PersistedPaymentRequest> getList() {
		return list;
	}
	
}

