package whscheduler.service;

import whscheduler.model.ATBillModel;

public class ATBillService {

	public String getAtBillDetailsHTML(ATBillModel billDTO,String domainName) {
		String html = "<table>"
						+ "<thead><tr align='center'><td width='70%'>Description</td><td width='30%'>Amount(BDT)</td></tr></thead>"
						+ "<tbody>"
						+ "<tr><td>Domain Name: www."+domainName+"<br>Application Type: Renew Webhosting<br>Year: 1</td>"
						+ "<td>"+billDTO.getGrandTotal()+"</td></tr><tr><td>Late Fee: </td><td>-</td></tr>"
			 			+ "<tr><td>VAT</td><td>"+billDTO.getVAT()+"</td></tr><tr><td>Total</td><td>"+billDTO.getTotalPayable()+"</td>"
		 				+ "</tr></tbody>"
	 				+ "</table><br/>";
	
		return html;
	}
}
