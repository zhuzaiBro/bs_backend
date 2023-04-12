package com.gs.project.biz.mapper;

import com.gs.project.biz.domain.Ticket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface TicketMapper {
    public List<Ticket> getMyTicket(long userId, Integer begin, Integer end);

    public void insertTicket(Ticket ticket);

    public List<Ticket> getTickets(Integer begin, Integer end);

    public void setIsUsed(Ticket ticket);
}

