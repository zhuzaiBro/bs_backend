package com.gs.project.biz.controller;

import com.gs.common.utils.SecurityUtils;
import com.gs.framework.web.domain.AjaxResult;
import com.gs.project.biz.domain.Ticket;
import com.gs.project.biz.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @GetMapping("/")
    public AjaxResult getMyTicket() {
        AjaxResult ajax = AjaxResult.success();
        long userId = SecurityUtils.getLoginUser().getUser().getId();
        List<Ticket> list = ticketService.getMyTicket(userId, 0, 10);

        ajax.put("data", list);
        return ajax;
    }

    @PostMapping("/")
    public AjaxResult setIsUsed(@RequestBody Ticket ticket) {
        AjaxResult ajax = AjaxResult.success();
        ticketService.setUsed(ticket);
        return ajax;
    }

    @GetMapping("/admin")
    public AjaxResult getTickets(@RequestBody Ticket ticket) {
        AjaxResult ajax = AjaxResult.success();
        List<Ticket> list = ticketService.getTickets(1, 20);

        ajax.put("data", list);
        return ajax;
    }

}
