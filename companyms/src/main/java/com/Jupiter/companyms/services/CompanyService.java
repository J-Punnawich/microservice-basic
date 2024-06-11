package com.Jupiter.companyms.services;

import com.Jupiter.companyms.clients.ReviewClient;
import com.Jupiter.companyms.dto.ReviewMessage;
import com.Jupiter.companyms.models.Company;
import com.Jupiter.companyms.repositories.CompanyRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

  @Autowired
  CompanyRepository companyRepository;
  @Autowired
  ReviewClient reviewClient;

  public List<Company> getAllCompanies() {
    List<Company> companies = companyRepository.findAll();

    return companies;
  }

  public void createCompany(Company company) {
    companyRepository.save(company);

  }

  public Company getById(Long companyId) throws NotFoundException {
    Company company = companyRepository.findById(companyId).orElseThrow(NotFoundException::new);

    return company;
  }

  public boolean deleteCompany(Long id) {
    if (companyRepository.existsById(id)) {
      companyRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public boolean updateCompany(Long id, Company updatedCompany) {
    Optional<Company> companyOptional = companyRepository.findById(id);
    if (companyOptional.isPresent()) {
      Company company = companyOptional.get();
      company.setName(updatedCompany.getName());
      company.setDescription(company.getDescription());
      companyRepository.save(company);
      return true;
    }
    return false;
  }

  public void updateCompanyRating(ReviewMessage reviewMessage) {
    System.out.println(reviewMessage.getDescription());
    Company company = companyRepository.findById(reviewMessage.getCompanyId())
        .orElseThrow(
            () -> new jakarta.ws.rs.NotFoundException("Company not found" + reviewMessage));

    double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
    company.setRating(averageRating);
    companyRepository.save(company);
  }
}
