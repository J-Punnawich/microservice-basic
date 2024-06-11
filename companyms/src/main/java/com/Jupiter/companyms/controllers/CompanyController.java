package com.Jupiter.companyms.controllers;

import com.Jupiter.companyms.models.Company;
import com.Jupiter.companyms.services.CompanyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

  private final CompanyService companyService;

  @GetMapping
  public ResponseEntity<List<Company>> getAllCompanies() {
    return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  ResponseEntity<Company> getCompanyById(@PathVariable Long id) throws NotFoundException {
    Company byId = companyService.getById(id);
    return new ResponseEntity<>(byId, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> addCompany(@RequestBody Company company) {
    companyService.createCompany(company);

    return new ResponseEntity<>("Created", HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company company) {
    boolean updateCompany = companyService.updateCompany(id, company);
    return new ResponseEntity<>("Updated", HttpStatus.OK);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteCompanyById(@PathVariable Long id) {
    boolean b = companyService.deleteCompany(id);
    return new ResponseEntity<>(b, HttpStatus.OK);
  }

}
