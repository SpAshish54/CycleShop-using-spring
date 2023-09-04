package com.prodapt.learningnewspring.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prodapt.learningnewspring.Repository.CycleRepository;
import com.prodapt.learningnewspring.entity.Cycle;



@Controller
public class CycleController {
	
	private List<Cycle> cycles = new ArrayList<>();

	@Autowired
	private CycleRepository cycleRepository;
	
	@GetMapping("/Cycle")
	public String get(Model model) {
		cycles = (List<Cycle>) cycleRepository.findAll();
		model.addAttribute("cycles" , cycles);
		return "Cycle";
	}
	
	
	@GetMapping("/borrow/{id}")
	public String borrow(@PathVariable int id) {
	    Cycle cycle = cycleRepository.findById(id).orElse(null);
	    if (cycle != null) {
	        cycle.setBorrow(1);
	        if(cycle.getStock()>0)
	        	cycle.setStock(cycle.getStock()-1);
	        cycleRepository.save(cycle);
	    }
	    return "redirect:/Cycle"; 
	}

	@GetMapping("/return/{id}")
	public String returnCycle(@PathVariable int id) {
	    Cycle cycle = cycleRepository.findById(id).orElse(null);
	    if (cycle != null) {
	        cycle.setBorrow(0);
	        cycle.setStock(cycle.getStock()+1);
	        cycleRepository.save(cycle);
	    }
	    return "redirect:/Cycle";
	}

	@GetMapping("/restock")
	public String restockCycles(Model model) {
		cycles = (List<Cycle>) cycleRepository.findAll();
		model.addAttribute("cycles",cycles);
		model.addAttribute("new_Cycle",new Cycle());
		return "restock";
	}
	
	@PostMapping("/restock/addCycle")
	public String addCycle(@ModelAttribute Cycle new_Cycle) {
			cycleRepository.save(new_Cycle);
			return "redirect:/restock";
		}
		
	@PostMapping("/restock/add/{id}")
	public String restockCycle(Model model,@PathVariable int id, @RequestParam int quantity ) {
		Cycle cycle = cycleRepository.findById(id).orElse(null);
		if (cycle != null) {
			cycle.setStock(cycle.getStock() + quantity);
			cycleRepository.save(cycle);
		}
		return "redirect:/restock";
	}
}