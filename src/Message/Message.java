package Message;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Message implements Serializable{ //abstract class because of toString() (not necessary...)

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//fields
	public enum Header {LOGIN,
		LOGOUT,
		GET_ALL_TENANTS_PAYMENTS,
		GET_PAYMENTS_BY_TENANT_ID,
		SET_NEW_BUILDING,
		GET_BUILDING_PAYMENTS_BY_MONTH,
		UPDATE_TENANT_PAYMENT_BY_MONTH,
		DELETE_TENANT_PAYMENT_BY_MONTH,
		GET_BUILDING_MONTHLY_REVENUE,
		GET_CONTRACTOR,
		INSERT_CONTRACTOR}
	public enum Sender {TENANT,COMMITTEE}
	private ArrayList<String> content;

	//Constructors
	public Message(ArrayList<String> args) {
		this.content=args;
	}
	
	//AbstractMethods
	public abstract void ToString();
	
	//ConcreteMtehods
	public boolean isArgsEmpty(){
		return content.size()==0;
	}

	public ArrayList<String> getArgs(){
		return content;
	}

	


}
