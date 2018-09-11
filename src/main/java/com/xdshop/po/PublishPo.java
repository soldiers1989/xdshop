package com.xdshop.po;

import com.xdshop.dal.domain.Publish;

public class PublishPo extends Publish{
	private int ticketRemainNow;

	public int getTicketRemainNow() {
		return ticketRemainNow;
	}

	public void setTicketRemainNow(int ticketRemainNow) {
		this.ticketRemainNow = ticketRemainNow;
	}
	
}
