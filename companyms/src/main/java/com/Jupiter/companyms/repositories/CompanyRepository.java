package com.Jupiter.companyms.repositories;

import com.Jupiter.companyms.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
