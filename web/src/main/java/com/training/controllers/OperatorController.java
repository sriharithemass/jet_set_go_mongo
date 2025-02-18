package com.training.controllers;

import com.training.models.Operator;
import com.training.repositories.OperatorRepository;
import com.training.services.impl.OperatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/operators")
public class OperatorController {

    @Autowired
    private OperatorServiceImpl operatorService;

    @PostMapping
    public ResponseEntity<Operator> createOperator(@RequestBody Operator operator){
        Operator savedOperator = operatorService.createOperator(operator);

        return new ResponseEntity<>(operator,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Operator>> getAllOperators(){
        List<Operator> operators = operatorService.getAllOperators();

        return new ResponseEntity<>(operators, HttpStatus.OK);
    }
}
