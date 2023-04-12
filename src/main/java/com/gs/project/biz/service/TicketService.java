package com.gs.project.biz.service;

import com.gs.project.biz.domain.Ticket;

import java.util.List;

public interface TicketService {
    public List<Ticket> getMyTicket (long userId, Integer begin, Integer end);

    public void setUsed (Ticket ticket);

    public List<Ticket> getTickets(Integer begin, Integer end);

    public void insertTicket(Ticket ticket);
}
