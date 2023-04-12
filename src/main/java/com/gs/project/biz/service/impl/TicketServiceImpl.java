package com.gs.project.biz.service.impl;


import com.gs.project.biz.domain.Ticket;
import com.gs.project.biz.mapper.TicketMapper;
import com.gs.project.biz.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketMapper ticketMapper;

    @Override
    public List<Ticket> getMyTicket(long userId, Integer begin, Integer end) {
        return ticketMapper.getMyTicket(userId, begin, end);
    }

    @Override
    public void setUsed(Ticket ticket) {
        ticketMapper.setIsUsed(ticket);
    }

    @Override
    public List<Ticket> getTickets(Integer begin, Integer end) {
        return ticketMapper.getTickets(begin, end);
    }

    @Override
    public void insertTicket(Ticket ticket) {
        ticketMapper.insertTicket(ticket);
    }
}
