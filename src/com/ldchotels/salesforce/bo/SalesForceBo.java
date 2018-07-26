package com.ldchotels.salesforce.bo;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.sforce.async.AsyncApiException;
import com.sforce.async.ConcurrencyMode;
import com.sforce.ws.ConnectionException;

public interface SalesForceBo {
	public void upsertToSF(String sobjectType, String authorization, String userName, String password,
			String fileName, String externalIdFieldName, ConcurrencyMode connectionMode) throws AsyncApiException,
			ConnectionException, IOException;
	
	public void deleteInSF(String sobjectType, String authorization, String userName, String password,
			String fileName, String externalIdFieldName, ConcurrencyMode connectionMode) throws AsyncApiException,
			ConnectionException, IOException;

	public void doBulkQueryAccount(String authorization, String userName,
			String password, String filePath) throws AsyncApiException,
			InterruptedException, ConnectionException, FileNotFoundException,
			IOException;

	public void doBulkQueryAccountForDelete(String authorization, String userName,
			String password, String filePath, String delChgBegin,
			String delChgEnd) throws AsyncApiException, InterruptedException,
			ConnectionException, FileNotFoundException, IOException;
			
	public void doBulkQueryReservationForDelete(String authorization, String userName,
			String password, String filePath, String delArrBegin,
			String delArrEnd) throws AsyncApiException, InterruptedException,
			ConnectionException, FileNotFoundException, IOException;
			
	public void doBulkQueryTransactionForDelete(String authorization, String userName,
			String password, String filePath, String delDepBegin,
			String delDepEnd) throws AsyncApiException, InterruptedException,
			ConnectionException, FileNotFoundException, IOException;
}
