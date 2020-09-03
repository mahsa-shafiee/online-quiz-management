package com.maktab.onlineQuizManagement.model.dao;

import com.maktab.onlineQuizManagement.model.entity.User.User;
import com.maktab.onlineQuizManagement.model.entity.User.UserRegistrationStatus;
import com.maktab.onlineQuizManagement.model.entity.User.UserRole;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public interface UserSpecifications {

    static Specification<User> findMaxMatch(String name,
                                            String family,
                                            String emailAddress,
                                            String password,
                                            String role,
                                            String registrationStatus) {

        return (Specification<User>) (root, criteriaQuery, builder) -> {
            CriteriaQuery<User> resultCriteria = builder.createQuery(User.class);

            List<Predicate> predicates = new ArrayList<Predicate>();
            if (!StringUtils.isEmpty(name) || name.length() != 0) {
                predicates.add(builder.like(root.get("name"), "%" + name + "%"));
            }
            if (!StringUtils.isEmpty(family) || family.length() != 0) {
                predicates.add(builder.like(root.get("family"), "%" + family + "%"));
            }
            if (!StringUtils.isEmpty(emailAddress) || emailAddress.length() != 0) {
                predicates.add(builder.like(root.get("emailAddress"), "%" + emailAddress + "%"));
            }
            if (!StringUtils.isEmpty(password) || password.length() != 0) {
                predicates.add(builder.like(root.get("password"), "%" + password + "%"));
            }
            if (!StringUtils.isEmpty(role) || role.length() != 0) {
                predicates.add(builder.equal(root.get("role"), UserRole.valueOf(role.toUpperCase())));
            }
            if (!StringUtils.isEmpty(registrationStatus)) {
                predicates.add(builder.equal(root.get("registrationStatus")
                        , UserRegistrationStatus.valueOf(registrationStatus.toUpperCase())));
            }

            resultCriteria.select(root).where(predicates.toArray(new Predicate[0]));
            return resultCriteria.getRestriction();
        };

    }
}
