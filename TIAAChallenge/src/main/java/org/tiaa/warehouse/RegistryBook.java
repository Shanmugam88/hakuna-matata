package org.tiaa.warehouse;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class RegistryBook {

	private static final Logger logger = Logger.getLogger(RegistryBook.class);
	private static RegistryBook registryBook = new RegistryBook();
	private List<Record> records = new ArrayList<>();
	
	private RegistryBook() {
		super();
	}
	
	public static RegistryBook getInstance()
	{
		if(registryBook == null)
			 return new RegistryBook();
		return registryBook;
	}
	
	public boolean register(String name, String batch, String productDesc)
	{
		return register(name,batch,productDesc,"Available");
	}
	
	public boolean register(String name, String batch, String productDesc,String status)
	{
		records.add(new Record(name,batch,productDesc,status));
		return true;
	}
	
	public void printRegistry()
	{
		logger.debug("************************** START OF REGISTRY **************************");
		for(Record record : records)
		{
			logger.debug(record.toString());
		}
		logger.debug("************************** END OF REGISTRY **************************");
	}
}
class Record
{
	private String name;
	private String batch;
	private String productDesc;
	private String status;
	
	public Record(String name, String batch, String productDesc,String status) {
		super();
		this.name = name;
		this.batch = batch;
		this.productDesc = productDesc;
		this.status = status;
	}
	
	public String getName() {
		return name;
	}
	public String getBatch() {
		return batch;
	}
	public String getProductDesc() {
		return productDesc;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Record [name=");
		builder.append(name);
		builder.append(", batch=");
		builder.append(batch);
		builder.append(", productDesc=");
		builder.append(productDesc);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
